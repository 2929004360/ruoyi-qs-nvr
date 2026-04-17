<template>
  <div class="app-container">
    <el-row :gutter="20">
      <splitpanes class="default-theme">
        <pane size="20">
          <el-col>
            <DeviceTree ref="deviceTree"
                        @clickEvent="treeChannelClickEvent"
                        :isContextmenu="true"
                        @playChannel="playChannelFun"
                        @updatePosition="updatePositionFun"
                        @change="deviceTreeChange"
            ></DeviceTree>
          </el-col>
        </pane>

        <pane size="80" style="width: 100%">
          <el-col>
            <div id="Map" style="width: 100%" :style="'height:' + height + 'min-width:' + width"></div>
          </el-col>
        </pane>
      </splitpanes>
    </el-row>

    <!--修改设备位置-->
    <el-dialog draggable title="修改设备位置" v-model="openPosition" width="500px" append-to-body>
      <el-form ref="formPositionRef" :model="formPosition" label-width="85px">
        <el-form-item label="经度">
          <el-input v-model="formPosition.longitude" placeholder="请输入经度">
            <template #append>
              <el-button @click="selectMapPositionFun">
                选择
              </el-button>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item label="纬度">
          <el-input v-model="formPosition.latitude" placeholder="请输入纬度">
            <template #append>
              <el-button @click="selectMapPositionFun">
                选择
              </el-button>
            </template>
          </el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="formPositionSubmit">确 定</el-button>
          <el-button @click="openPosition = false">取消</el-button>
        </div>
      </template>
    </el-dialog>

    <SelectMapPosition ref="selectMapPositionRef" @onSubmit="selectMapPositionSubmit"/>


    <el-dialog :title="`视频播放-${deviceRow.deviceName}`"
               v-model="easyPlayerOpen"
               width="840px"
               append-to-body
               draggable
               @close="getList"
    >
      <div style="width: 100%;height: 100%;display: flex;justify-content: center" v-if="easyPlayerOpen">
        <EasyPlayer
            ref="EasyPlayerRef"
            style="width: 800px;height: 400px;"
            width="100"
            height="100"
            :isPercentage="true"
            :quality="quality"
            :defaultQuality="defaultQuality"
            :isPtz="isPtz"
            :isQuality="isQuality"
            :hasAudio="deviceRow.enableAudio === '1'"
            :isMute="deviceRow.enableAudio === '1'"
            :isLive="isLive"
            :videoUrl="wsUrl"/>
      </div>

      <el-tabs v-model="tabActiveName"
               type="card"
               :stretch="true"
               v-if="easyPlayerOpen"
               style="margin-top: 10px;">
        <el-tab-pane label="实时视频" name="media">
          <el-row :gutter="10">
            <el-col :span="3"><span style="width: 80px; line-height: 40px; text-align: right;">播放地址：</span></el-col>
            <el-col :span="21">
              <el-input v-model="flvUrl" :disabled="true">
                <template #prepend>flv地址</template>
                <template #append>
                  <el-button type="primary" :icon="DocumentCopy" @click="handleCopy(flvUrl)"/>
                </template>
              </el-input>
            </el-col>
          </el-row>

          <el-row :gutter="10" style="margin-top: 10px">
            <el-col :span="3"><span style="width: 80px; line-height: 40px; text-align: right;">资源地址：</span></el-col>
            <el-col :span="21">
              <el-input v-model="rtcUrl" :disabled="true">
                <template #prepend>
                  <StreamDropdown :stream-info="streamInfo"/>
                </template>
                <template #append>
                  <el-button type="primary" :icon="DocumentCopy" @click="handleCopy(wsUrl)"/>
                </template>
              </el-input>
            </el-col>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="编码信息" name="codec">
          <MediaInfo v-if="tabActiveName === 'codec' && streamInfo" ref="mediaInfo" :app="streamInfo.app"
                     :stream="streamInfo.stream" :mediaServerId="streamInfo.mediaServerId"></MediaInfo>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
  </div>
</template>

<script setup lang="ts" name="Map">
import EasyPlayer from "@/components/EasyPlayer";
import {DocumentCopy} from '@element-plus/icons-vue'
import StreamDropdown from "@/components/Channel/streamDropdown.vue";
import MediaInfo from "@/components/Channel/mediaInfo.vue";
import useClipboard from "vue-clipboard3";
import DeviceTree from '@/components/DeviceTree'
import SelectMapPosition from '@/components/SelectMapPosition'
import {Pane, Splitpanes} from "splitpanes"
import "splitpanes/dist/splitpanes.css"
import {getConfigKey} from "@/api/system/config";
import {queryRegionForDevice} from "@/api/qs/region";
import {queryGroupForDevice} from "@/api/qs/group";
import {getDevice, getVideoSnapshot, updateDevice} from "@/api/qs/device";
import {PullConfig, RTPServerParam} from "@/types/api";
import {loadRecord, rtpPlay, streamPullPlay, streamPullPush} from "@/api/qs/zlm";
import {ElLoading} from "element-plus";

const {toClipboard} = useClipboard()

const {proxy} = getCurrentInstance()

var map;
var channelMap = [];
let currentOpenInfoWindow = null;
let markerMap = new Map();
const height = ref(document.documentElement.clientHeight - 150 + "px;")
const width = ref(document.documentElement.clientWidth - 600 + "px;")

// 位置
const openPosition = ref(false);
const formPosition = ref({});

// 播放
const easyPlayerOpen = ref(false)
const deviceRow = ref({})
const rtcUrl = ref("");
const flvUrl = ref("");
const wsUrl = ref('');
const tabActiveName = ref('media');
const streamInfo = ref({});
const quality = ref(['普清', '高清', '超清']);
const defaultQuality = ref('高清');
const isPtz = ref(true);
const isQuality = ref(true);
const isLive = ref(true);

onMounted(async () => {
  window.onresize = function temp() {
    height.value = document.documentElement.clientHeight - 150 + "px;"
    // width.value = document.documentElement.clientWidth - 200 + "px;"
  }
  const res = await getConfigKey("sys.map.param")

  nextTick(async () => {
    if (res.msg === null || res.msg === '') {
      proxy.$modal.msgError("请配置地图默认参数！")
      return
    }
    const [lng, lat, zoom] = res.msg.split(',').map(Number);
    map = new T.Map('Map', {
      projection: 'EPSG:4326'
    });
    let center = new T.LngLat(lng, lat, zoom);
    //设置显示地图的中心点和级别
    map.centerAndZoom(center, zoom);

    //创建版权控件对象
    // var copyControl = new T.Control.Copyright({position: T_ANCHOR_TOP_LEFT});
    // map.addControl(copyControl);
    // var bs = map.getBounds();   //返回地图可视区域
    // copyControl.addCopyright({
    //   id: 1,
    //   content: "<a href='https://www.tianditu.gov.cn' target='_blank' style='font-size:14px;background:yellow'>自定义的版权控件</a>",
    //   bounds: bs
    // });

    //创建缩放平移控件对象
    var control = new T.Control.Zoom();
    //添加缩放平移控件
    map.addControl(control);
    //创建比例尺控件对象
    var scale = new T.Control.Scale();
    //添加比例尺控件
    map.addControl(scale);

    var miniMap = new T.Control.OverviewMap({
      isOpen: true,
      size: new T.Point(150, 150)
    });
    map.addControl(miniMap);

    //创建对象
    var ctrl = new T.Control.MapType();
    //添加控件
    map.addControl(ctrl);

    var menu = new T.ContextMenu({
      width: 100
    });
    var txtMenuItem = [
      {
        text: '放大',
        callback: function () {
          map.zoomIn()
        }
      },
      {
        text: '缩小',
        callback: function () {
          map.zoomOut()
        }
      },
      {
        text: '查看全国',
        callback: function () {
          map.setZoom(4)
        }
      }
    ];

    for (var i = 0; i < txtMenuItem.length; i++) {
      //添加菜单项
      var menuItem = new T.MenuItem(txtMenuItem[i].text, txtMenuItem[i].callback);
      menu.addItem(menuItem);
      if (i == 1) {
        //添加分割线
        menu.addSeparator();
      }
    }
    //添加右键菜单
    map.addContextMenu(menu);

    await queryForDeviceFun()
  })
})

async function deviceTreeChange(data) {
  await queryForDeviceFun()
}

async function queryForDeviceFun() {
  if (map) {
    map.clearOverLays()
  }

  let res = {}
  if (proxy.$refs["deviceTree"].showRegion) {
    res = await queryRegionForDevice();
  } else {
    res = await queryGroupForDevice();
  }


  let data = res.data
  if (data.length > 0) {

    channelMap = JSON.parse(JSON.stringify(data))

    for (let i = 0; i < data.length; i++) {
      var icon = {}
      if (data[i].ptzType) {
        // 球机
        if (data[i].ptzType === 1) {
          icon = new T.Icon({
            iconUrl: "https://ruoyi-wvp-1308576884.cos.ap-guangzhou.myqcloud.com/ruoyi-wvp/upload/%E7%90%83%E6%9C%BA.png",
            iconSize: new T.Point(30, 27),
            iconAnchor: new T.Point(10, 25)
          });

          // 半球或者遥控半球
        } else if (data[i].ptzType === 2 || data[i].ptzType === 5) {
          icon = new T.Icon({
            iconUrl: "https://ruoyi-wvp-1308576884.cos.ap-guangzhou.myqcloud.com/ruoyi-wvp/upload/%E5%8D%8A%E7%90%83.png",
            iconSize: new T.Point(30, 27),
            iconAnchor: new T.Point(10, 25)
          });

          // 固定枪机或遥控枪机
        } else if (data[i].ptzType === 3 || data[i].ptzType === 4) {
          icon = new T.Icon({
            iconUrl: "https://ruoyi-wvp-1308576884.cos.ap-guangzhou.myqcloud.com/ruoyi-wvp/upload/%E6%9E%AA%E6%9C%BA.png",
            iconSize: new T.Point(30, 27),
            iconAnchor: new T.Point(10, 25)
          });
        } else {
          icon = new T.Icon({
            iconUrl: "http://api.tianditu.gov.cn/img/map/markerA.png",
            iconSize: new T.Point(20, 27),
            iconAnchor: new T.Point(10, 25)
          });
        }
      } else {
        icon = new T.Icon({
          iconUrl: "http://api.tianditu.gov.cn/img/map/markerA.png",
          iconSize: new T.Point(20, 27),
          iconAnchor: new T.Point(10, 25)
        });
      }

      //创建信息窗口对象
      let marker = new T.Marker(new T.LngLat(data[i].longitude, data[i].latitude), {icon: icon});
      map.addOverLay(marker);
      marker._channel = data[i]
      markerMap.set(data[i].id, marker);
      marker.addEventListener("click", function (e) {
        if (currentOpenInfoWindow) {
          currentOpenInfoWindow.closeInfoWindow();
        }
        const channel = this._channel
        var infoWin1 = new T.InfoWindow();

        const sContent = `
              <div style="
                width: 300px;
                position: relative;
                font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
                font-size: 14px;
                color: #333;
              ">
                <!-- 标题 -->
                <div style="font-size: 16px; font-weight: bold; margin-bottom: 12px; color: #1a73e8;">
                   ${escapeHtml(channel.name)}
                </div>

                <!-- 字段列表 -->
                <div style="line-height: 1.6;">
                  <div>
                    <span style="color: #666; display: inline-block; width: 80px;">ip：</span>
                    <span>${escapeHtml(channel.ipAddress || '未知')}</span>
                  </div>
                  <div style="margin-top: 6px;">
                    <span style="color: #666; display: inline-block; width: 80px;">生产厂商：</span>
                    <span>${escapeHtml(channel.manufacturer || '未知')}</span>
                  </div>
                  <div style="margin-top: 6px;">
                    <span style="color: #666; display: inline-block; width: 80px;">安装地址：</span>
                    <span>${escapeHtml(channel.address || '未知')}</span>
                  </div>
                  <div style="margin-top: 6px;">
                    <span style="color: #666; display: inline-block; width: 80px;">设备状态：</span>
                    <span>${channel.status === 'ON' ? '在线' : '离线'}</span>
                  </div>
                </div>

                <!-- 操作按钮区域 -->
                <div style="
                  margin-top: 14px;
                  display: flex;
                  gap: 8px;
                  justify-content: center;
                  flex-wrap: wrap;
                ">
                  <button
                    ${channel.status !== 'ON' ? 'disabled' : ''}
                    style="
                      padding: 6px 12px;
                      font-size: 13px;
                      border: none;
                      border-radius: 4px;
                      background: #1a73e8;
                      color: white;
                      cursor: pointer;
                      opacity: ${channel.status === 'ON' ? 1 : 0.5};
                    "
                    onclick="window.handlePlay('${channel.id}')"
                    title="播放"
                  >
                    ▶ 播放
                  </button>

                  <button
                    style="
                      padding: 6px 12px;
                      font-size: 13px;
                      border: none;
                      border-radius: 4px;
                      background: #fbbc04;
                      color: #202124;
                      cursor: pointer;
                    "
                    onclick="window.handlePosition('${channel.id}')"
                    title="位置"
                  >
                    📍 位置
                  </button>
                </div>
              </div>
              `.trim();
        infoWin1.setContent(sContent);
        this.openInfoWindow(infoWin1);


        currentOpenInfoWindow = marker
      }); // 将标注添加到地图中
    }
  }
}

function updatePositionFun(data, type) {
  handleEdit({
    id: data.id,
    type: "position"
  })
}

/**
 * 编辑
 *
 * @param row
 */
function handleEdit(row) {
  getDevice(row.id).then((res) => {
    formPosition.value = {
      id: res.data.id,
      longitude: res.data.longitude,
      latitude: res.data.latitude,
    }
    openPosition.value = true
  })
}

function selectMapPositionFun() {
  proxy.$refs["selectMapPositionRef"].openDialog({
    lat: formPosition.value.latitude,
    lng: formPosition.value.longitude,
  })
}

function selectMapPositionSubmit(data) {
  formPosition.value.longitude = data.lng
  formPosition.value.latitude = data.lat
}

function formPositionSubmit() {
  updateDevice(formPosition.value).then(async () => {
    proxy.$modal.msgSuccess('修改成功');
    openPosition.value = false
    if (currentOpenInfoWindow) {
      currentOpenInfoWindow.closeInfoWindow();
    }
    await queryForDeviceFun()
  }).catch(() => {
  })
}

async function treeChannelClickEvent(id) {
  await getDevice(id).then((res) => {
    let data = res.data
    if (!data.longitude || data.longitude < 0 || !data.latitude || data.latitude < 0) {
      proxy.$modal.msgError("无位置信息")
      return
    }
    let lnglat = new T.LngLat(data.longitude, data.latitude);
    map.panTo(lnglat, 15)

    var infoWin1 = new T.InfoWindow();
    const sContent = `
              <div style="
                width: 300px;
                position: relative;
                font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
                font-size: 14px;
                color: #333;
              ">
                <!-- 标题 -->
                <div style="font-size: 16px; font-weight: bold; margin-bottom: 12px; color: #1a73e8;">
                  ${escapeHtml(data.deviceName)}
                </div>

                <!-- 字段列表 -->
                <div style="line-height: 1.6;">
                  <div>
                    <span style="color: #666; display: inline-block; width: 80px;">ip：</span>
                    <span>${escapeHtml(data.ipAddress || '未知')}</span>
                  </div>
                  <div style="margin-top: 6px;">
                    <span style="color: #666; display: inline-block; width: 80px;">生产厂商：</span>
                    <span>${escapeHtml(data.manufacturer || '未知')}</span>
                  </div>
                  <div style="margin-top: 6px;">
                    <span style="color: #666; display: inline-block; width: 80px;">安装地址：</span>
                    <span>${escapeHtml(data.address || '未知')}</span>
                  </div>
                  <div style="margin-top: 6px;">
                    <span style="color: #666; display: inline-block; width: 80px;">设备状态：</span>
                    <span>${data.deviceStatus === 'ON' ? '在线' : '离线'}</span>
                  </div>
                </div>

                <!-- 操作按钮区域 -->
                <div style="
                  margin-top: 14px;
                  display: flex;
                  gap: 8px;
                  justify-content: center;
                  flex-wrap: wrap;
                ">
                  <button
                    ${data.deviceStatus !== 'ON' ? 'disabled' : ''}
                    style="
                      padding: 6px 12px;
                      font-size: 13px;
                      border: none;
                      border-radius: 4px;
                      background: #1a73e8;
                      color: white;
                      cursor: pointer;
                      opacity: ${data.deviceStatus === 'ON' ? 1 : 0.5};
                    "
                    onclick="window.handlePlay('${data.id}')"
                    title="播放"
                  >
                    ▶ 播放
                  </button>
                  <button
                    style="
                      padding: 6px 12px;
                      font-size: 13px;
                      border: none;
                      border-radius: 4px;
                      background: #fbbc04;
                      color: #202124;
                      cursor: pointer;
                    "
                    onclick="window.handlePosition('${data.id}')"
                    title="位置"
                  >
                    📍 位置
                  </button>
                </div>
              </div>
              `.trim();
    infoWin1.setContent(sContent);
    let marker = markerMap.get(id)
    marker.openInfoWindow(infoWin1);
  })
}

// 防 XSS（简单 escape）
function escapeHtml(str) {
  if (typeof str !== 'string') return str == null ? '' : String(str)
  return str.replace(/&/g, '&amp;')
      .replace(/</g, '&lt;')
      .replace(/>/g, '&gt;')
      .replace(/"/g, '&quot;')
      .replace(/'/g, '&#039;')
}

async function playChannelFun(data, type) {
  await treeChannelClickEvent(data.id)
  await play(data.id)
}

/**
 * 播放
 */
function play(id) {
  getDevice(id).then((res) => {
    let row = res.data
    if (!row.longitude || row.longitude < 0 || !row.latitude || row.latitude < 0) {
      return
    }

    const loading = ElLoading.service({
      lock: true,
      text: '请求中...',
      background: 'rgba(0, 0, 0, 0.7)',
    })

    if (row.type === '1' || row.type === '2' || row.type === '3' || row.type === '4' || row.type === '5') {
      let data = {
        deviceId: row.id,
        app: '',
        stream: row.deviceCode,
        url: row.liveAddress,
        enable_audio: false,
        enable_mp4: false,
        rtp_type: '1',
        timeOut: 10,
      } as PullConfig;

      if (row.type === '1') {
        data.app = "rtsp"
      } else if (row.type === '2') {
        data.app = "rtmp"
      } else if (row.type === '3') {
        data.app = "flv"
      } else if (row.type === '4') {
        data.app = "hls"
      } else if (row.type === '5') {
        data.app = "onvif"
      }

      if (row.type === '3' && row.flvType === 'ws') {
        if (row.liveAddress != null) {
          data.url = convertWsToHttp(row.liveAddress)
        }
      }

      if (row.protocol === 'UDP') {
        data.rtp_type = '0'
      } else if (row.protocol === 'TCP') {
        data.rtp_type = '1'
      }

      if (row.enableAudio === '1') {
        data.enable_audio = true
      }

      if (row.enableMp4 === '1') {
        data.enable_mp4 = true
      }

      streamPullPlay(data).then(async (res: any) => {
        await nextTick(async () => {
          if (location.protocol === "https:") {
            flvUrl.value = res.data.https_flv;
            rtcUrl.value = res.data.rtcs;
            wsUrl.value = res.data.wss_flv;
          } else {
            flvUrl.value = res.data.flv;
            rtcUrl.value = res.data.rtc;
            wsUrl.value = res.data.ws_flv;
          }

          streamInfo.value = res.data;
          quality.value = []
          defaultQuality.value = ''
          isPtz.value = false
          isQuality.value = false
          isLive.value = true
          deviceRow.value = row
          easyPlayerOpen.value = true
        })
      }).finally(() => {
        loading.close()
      })
    } else if (row.type === '6') {
      loadRecord(row.id).then(async (res: any) => {
        await nextTick(async () => {
          if (location.protocol === "https:") {
            flvUrl.value = res.data.https_flv;
            rtcUrl.value = res.data.rtcs;
            wsUrl.value = res.data.wss_flv;
          } else {
            flvUrl.value = res.data.flv;
            rtcUrl.value = res.data.rtc;
            wsUrl.value = res.data.ws_flv;
          }

          streamInfo.value = res.data;
          quality.value = []
          defaultQuality.value = ''
          isPtz.value = false
          isQuality.value = false
          isLive.value = true
          deviceRow.value = row
          easyPlayerOpen.value = true

          getVideoSnapshot(row.id);
        })
      }).finally(() => {
        loading.close()
      })
    } else if (row.type === '7' || row.type === '8' || row.type === '9') {
      let data = {
        app: "haikang",
        streamId: row.deviceCode,
        tcpMode: "0",
        type: row.type,
        id: row.id
      } as RTPServerParam;

      if (row.type === '7') {
        data.app = "haikang"
      } else if (row.type === '8') {
        data.app = "haikang_isup"
      } else if (row.type === '9') {
        data.app = "dahua"
      }

      rtpPlay(data).then(async (res: any) => {
        await nextTick(async () => {
          if (location.protocol === "https:") {
            flvUrl.value = res.data.https_flv;
            rtcUrl.value = res.data.rtcs;
            wsUrl.value = res.data.wss_flv;
          } else {
            flvUrl.value = res.data.flv;
            rtcUrl.value = res.data.rtc;
            wsUrl.value = res.data.ws_flv;
          }

          streamInfo.value = res.data;
          quality.value = []
          defaultQuality.value = ''
          isPtz.value = false
          isQuality.value = false
          isLive.value = true
          deviceRow.value = row
          easyPlayerOpen.value = true
        })
      }).finally(() => {
        loading.close()
      })
    }else if (row.type === '13') {
      streamPullPush(row.id).then(async (res: any) => {
        await nextTick(async () => {
          if (location.protocol === "https:") {
            flvUrl.value = res.data.https_flv;
            rtcUrl.value = res.data.rtcs;
            wsUrl.value = res.data.wss_flv;
          } else {
            flvUrl.value = res.data.flv;
            rtcUrl.value = res.data.rtc;
            wsUrl.value = res.data.ws_flv;
          }

          streamInfo.value = res.data;
          quality.value = []
          defaultQuality.value = ''
          isPtz.value = false
          isQuality.value = false
          isLive.value = true
          deviceRow.value = row
          easyPlayerOpen.value = true
        })
      }).finally(() => {
        loading.close()
      })
    }

  })
}

/**
 * 复制内容到粘贴板
 *
 * @param text
 */
const handleCopy = async (text: string) => {
  if (!text) {
    proxy.$modal.msgError('内容为空，无法复制');
    return;
  }

  try {
    await toClipboard(text)
    proxy.$modal.msgSuccess('成功拷贝到粘贴板');
  } catch (e) {
    console.error(e)
  }
};

// 暴露全局方法（在 Vue setup 中）
window.handlePlay = (id) => {
  channelMap.forEach(async (channel) => {
    if (String(channel.id) === id) {
      if (channel) {
        await treeChannelClickEvent(channel.id)
        await play(channel.id)
      }
    }
  })
}

window.handlePosition = (id) => {
  channelMap.forEach((item) => {
    if (String(item.id) === id) {
      if (item) {
        handleEdit({
          id: item.id,
          type: "position"
        })
      }
    }
  })
}

onBeforeUnmount(() => {
  // 可选：清理地图实例（天地图官方未提供 destroy，但可清空容器）
  if (map) {
    map.clearOverLays() // 清除所有覆盖物
    // 注意：天地图 Map 实例没有标准 destroy 方法，通常不需要手动销毁
    delete window.handlePlay
    delete window.handlePosition
  }
})
</script>

<style scoped>

</style>