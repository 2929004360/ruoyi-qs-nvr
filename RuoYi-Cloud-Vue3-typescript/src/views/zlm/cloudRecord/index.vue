<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="应用名" prop="app">
        <el-input
            v-model="queryParams.app"
            placeholder="请输入应用名"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="流id" prop="stream">
        <el-input
            v-model="queryParams.stream"
            placeholder="请输入流id"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开始时间" prop="queryStartTime">
        <el-date-picker
            v-model="queryParams.queryStartTime"
            type="datetime"
            style="width: 240px"
            value-format="YYYY-MM-DD HH:mm:ss"
            placeholder="选择日期时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" prop="queryEndTime">
        <el-date-picker
            v-model="queryParams.queryEndTime"
            type="datetime"
            style="width: 240px"
            value-format="YYYY-MM-DD HH:mm:ss"
            placeholder="选择日期时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="媒体节点" prop="mediaServerId">
        <el-select
            v-model="queryParams.mediaServerId"
            style="width: 240px"
            placeholder="请选择节点选择"
        >
          <el-option
              v-for="item in mediaServerList"
              :key="item.id"
              :label="item.id"
              :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            :disabled="multiple"
            @click="downloadZip"
        >下载
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="cloudRecordList" @selection-change="handleSelectionChange" border>
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" prop="id" width="80"/>
      <el-table-column label="应用名" align="center" prop="app" width="100"/>
      <el-table-column label="流id" align="center" prop="stream"/>
      <el-table-column label="开始时间" align="center">
        <template v-slot:default="scope">
          {{ formatTimeStamp(scope.row.startTime) }}
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center">
        <template v-slot:default="scope">
          {{ formatTimeStamp(scope.row.endTime) }}
        </template>
      </el-table-column>

      <el-table-column label="媒体节点" align="center" prop="mediaServerId"/>
      <el-table-column label="文件名称" align="center" prop="fileName"/>
      <el-table-column label="大小" align="center" prop="fileSize">
        <template v-slot:default="scope">
          {{ formatBytes(scope.row.fileSize) }}
        </template>
      </el-table-column>
      <el-table-column label="时长" align="center" width="150px">
        <template v-slot:default="scope">
          <el-tag>{{ formatTime(scope.row.timeLen) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150px">
        <template #default="scope">
          <el-button link
                     type="primary"
                     icon="VideoPlay"
                     @click="handlePlay(scope.row)"
                     :loading="scope.row.loading"
          >
            播放
          </el-button>
          <el-button icon="Download" type="text" @click="downloadZip(scope.row)">下载</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
        v-show="total>0"
        :total="total"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
    />

    <el-dialog :title="`录像播放-${cloudRecordRow.fileName}`"
               v-model="easyPlayerOpen"
               width="840px"
               append-to-body
               draggable
               @close="handleCloseStreams"
    >
      <div id="cloudRecordPlayer">
        <div style="width: 100%;display: flex;justify-content: center" v-if="easyPlayerOpen" :style="'height:'+ jessibucaHeight">
          <EasyPlayer
              :style="'height:'+ jessibucaHeight"
              ref="EasyPlayerRef"
              style="width: 100%"
              width="100"
              height="100"
              @error="easyPlayeError"
              :isPercentage="true"
              :quality="quality"
              :defaultQuality="defaultQuality"
              :isPtz="isPtz"
              :isQuality="isQuality"
              :hasAudio="true"
              :isMute="true"
              :isLive="isLive"
              :videoUrl="wsUrl"/>
        </div>

        <div class="cloud-record-player-option-box">
          <div class="cloud-record-show-time">
            {{ showPlayTimeValue }}
          </div>
          <div class="cloud-record-time-process" ref="timeProcess" @click="timeProcessClick($event)"
               @mouseenter="timeProcessMouseEnter($event)" @mousemove="timeProcessMouseMove($event)"
               @mouseleave="timeProcessMouseLeave($event)">
            <div v-if="streamInfo">
              <div class="cloud-record-time-process-value" :style="playTimeValue"></div>
              <transition name="el-fade-in-linear">
                <div v-show="showTimeLeft" class="cloud-record-time-process-title" :style="playTimeTitleStyle">
                  {{ showPlayTimeTitle }}
                </div>
              </transition>
            </div>
          </div>
          <div class="cloud-record-show-time">
            {{ showPlayTimeTotal }}
          </div>
        </div>

        <div style="height: 40px; background-color: #383838;display: flex;justify-content: space-between;width: 100%">
          <div>
            <div class="cloud-record-record-play-control"
                 style="background-color: transparent; box-shadow: 0 0 10px transparent">
              <a target="_blank" class="cloud-record-record-play-control-item iconfont icon-camera1196054easyiconnet"
                 title="截图" @click="snap()"/>
              <a target="_blank" style="zoom:0.8;" class="cloud-record-record-play-control-item iconfont icon-shuaxin11"
                 title="刷新" @click="refresh()"/>
              <a target="_blank" class="cloud-record-record-play-control-item iconfont icon-xiazai011" title="下载"
                 @click="download()"/>
            </div>
          </div>

          <div>
            <div class="cloud-record-record-play-control">
              <a target="_blank" class="cloud-record-record-play-control-item iconfont icon-kuaijin" title="快退五秒"
                 @click="seekBackward()"/>
              <a target="_blank" class="cloud-record-record-play-control-item iconfont icon-stop1" style="font-size: 14px"
                 title="停止" @click="stopPLay()"/>
              <a v-if="playing" target="_blank" class="cloud-record-record-play-control-item iconfont icon-zanting"
                 title="暂停" @click="pausePlay()"/>
              <a v-if="!playing" target="_blank" class="cloud-record-record-play-control-item iconfont icon-kaishi"
                 title="播放" @click="playRecord()"/>
              <a target="_blank" class="cloud-record-record-play-control-item iconfont icon-houtui" title="快进五秒"
                 @click="seekForward()"/>
              <el-dropdown @command="changePlaySpeed" :popper-append-to-body='false' style="margin-top: 10px;">
                <a target="_blank" class="cloud-record-record-play-control-item record-play-control-speed"
                   title="倍速播放">{{
                    playSpeed
                  }}X</a>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item
                        v-for="item in playSpeedRange"
                        :key="item"
                        :command="item"
                    >{{ item }}X
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </div>
          </div>

          <div>
            <div class="cloud-record-record-play-control"
                 style="background-color: transparent; box-shadow: 0 0 10px transparent">
              <a v-if="!isFullScreen" target="_blank"
                 class="cloud-record-record-play-control-item iconfont icon-fangdazhanshi" title="全屏"
                 @click="fullScreen()"/>
              <a v-else target="_blank" class="cloud-record-record-play-control-item iconfont icon-suoxiao1" title="全屏"
                 @click="fullScreen()"/>
            </div>
          </div>
        </div>
      </div>

      <el-tabs v-model="tabActiveName"
               v-if="easyPlayerOpen"
               type="card"
               :stretch="true"
               style="margin-top: 10px;">
        <el-tab-pane label="实时视频" name="media">
          <el-row :gutter="10">
            <el-col :span="3"><span style="width: 80px; line-height: 40px; text-align: right;">播放地址：</span></el-col>
            <el-col :span="21">
              <el-input v-model="flvUrl" :disabled="true" style="margin-top: 10px">
                <template #prepend>flv地址</template>
                <template #append>
                  <el-button type="primary" :icon="DocumentCopy" @click="handleCopy(flvUrl)"/>
                </template>
              </el-input>
              <el-input v-model="wsUrl" :disabled="true" style="margin-top: 10px">
                <template #prepend>wsUrl地址</template>
                <template #append>
                  <el-button type="primary" :icon="DocumentCopy" @click="handleCopy(wsUrl)"/>
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

<script setup lang="ts" name="CloudRecord">
import useClipboard from "vue-clipboard3";
import screenfull from 'screenfull'
import moment from 'moment'
import EasyPlayer from "@/components/EasyPlayer";
import type {CloudRecordQueryParams, ZlmCloudRecord} from "@/types/api/qs/cloudRecord"
import {
  closeStreams,
  delCloudRecord,
  listCloudRecord,
  loadRecord,
  seekCloudRecord,
  setCloudRecordSpeed
} from "@/api/qs/cloudRecord"
import momentDurationFormatSetup from 'moment-duration-format'
import {DocumentCopy} from '@element-plus/icons-vue'
import StreamDropdown from "@/components/Channel/streamDropdown.vue";
import MediaInfo from "@/components/Channel/mediaInfo.vue";
import {getAllOnlineMediaServe} from "@/api/qs/zlm";

const {toClipboard} = useClipboard()

momentDurationFormatSetup(moment)

const {proxy} = getCurrentInstance()

const cloudRecordList = ref<ZlmCloudRecord[]>([])
const loading = ref<boolean>(true)
const showSearch = ref<boolean>(true)
const ids = ref<number[]>([])
const multiple = ref<boolean>(true)
const total = ref<number>(0)

// 播放
const easyPlayerOpen = ref(false)
const cloudRecordRow = ref({})
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

const currentWidth = ref(0);
const playerTime = ref(0);
const showTimeLeft = ref(0);
const step = ref(0);
const showPlayTime = ref('');
const timeLen = ref(null);
const startTime = ref(null);
const playing = ref(false);
const timer = ref(null);
const playSpeed = ref(1);
const isFullScreen = ref(false);
const lastDiable = ref(true);
const nextDiable = ref(false);
const playData = ref({});
const playSpeedRange = ref([1, 2, 4]);
const jessibucaHeight = ref("400px");
const isPaused = ref(false);


const mediaServerList = ref([]);

const data = reactive({
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    app: undefined,
    stream: undefined,
    callId: undefined,
    queryStartTime: undefined,
    queryEndTime: undefined,
    mediaServerId: undefined,
  } as CloudRecordQueryParams,
})

const {queryParams} = toRefs(data)

/** 查询云端录像列表 */
function getList() {
  loading.value = true
  listCloudRecord(queryParams.value).then(response => {
    cloudRecordList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

// 多选框选中数据
function handleSelectionChange(selection: ZlmCloudRecord[]) {
  ids.value = selection.map(item => item.id)
  multiple.value = !selection.length
}

/** 删除按钮操作 */
function handleDelete(row: ZlmCloudRecord) {
  const _ids = row.id || ids.value
  proxy.$modal.confirm('是否确认删除云端录像编号为"' + _ids + '"的数据项？').then(function () {
    return delCloudRecord(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {
  })
}

/**
 * 播放
 *
 * @param row
 */
const handlePlay = (row: ZlmCloudRecord) => {
  loadRecord(row.id).then(async res => {
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
      cloudRecordRow.value = row
      row.loading = false
      playing.value = true

      currentWidth.value = 0
      playerTime.value = 0
      playSpeed.value = 1
      isFullScreen.value = false
      jessibucaHeight.value = "400px"
      step.value = 100 / (cloudRecordRow.value.timeLen / 1000);
      timer.value = setInterval(() => {

        if (isPaused.value) return;

        // 增加进度
        currentWidth.value = currentWidth.value + step.value * playSpeed.value;

        playerTime.value += 1000 * playSpeed.value

        // 判断是否结束
        if (currentWidth.value >= 100) {
          currentWidth.value = 100;
          clearInterval(timer.value); // 停止计时器
        }
      }, 1000)

      timeLen.value = row.timeLen
      startTime.value = row.startTime
      easyPlayerOpen.value = true
    })
  })
}

/**
 * 关闭流文件形成播放地址
 *
 * @param row
 */
const handleCloseStreams = () => {
  streamInfo.value = null
  currentWidth.value = 0
  playerTime.value = 0
  playSpeed.value = 0
  isPaused.value = false
  clearInterval(timer.value); // 停止计时器
  closeStreams(cloudRecordRow.value.id)
}

/**
 * 播放器错误回调
 */
const easyPlayeError = () => {
  clearInterval(timer.value); // 停止计时器
  currentWidth.value = 0
  playerTime.value = 0
  playSpeed.value = 0
  isPaused.value = false
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

/**
 * 下载
 */
function downloadZip(row: ZlmCloudRecord) {
  let cloudRecordIds = []
  if (row.id) {
    cloudRecordIds.push(row.id)
  } else {
    cloudRecordIds = ids.value
  }

  let idsStr = cloudRecordIds.join(',')
  const link = document.createElement('a')
  link.target = '_blank'
  let baseUri = import.meta.env.VITE_APP_BASE_API
  let downloadUrl = `${location.origin}${baseUri}/zlm/cloudRecord/download/zip?ids=${idsStr}`
  link.href = downloadUrl
  link.click()
}

// 全屏
const fullScreen = () => {
  if (isFullScreen.value) {
    screenfull.exit()
    isFullScreen.value = false
    jessibucaHeight.value = "400px"
    return
  }
  screenfull.request(document.getElementById('cloudRecordPlayer'))
  screenfull.on('change', (event) => {
    isFullScreen.value= screenfull.isFullscreen
  })
  isFullScreen.value = true
  jessibucaHeight.value = "95%"
}

// 倍速播放
const changePlaySpeed = (speed) => {
  if (!streamInfo.value) {
    return
  }
  playSpeed.value = speed
  setCloudRecordSpeed({
    mediaServerId: streamInfo.value.mediaServerId,
    app: streamInfo.value.app,
    stream: streamInfo.value.stream,
    key: streamInfo.value.key,
    speed: playSpeed.value,
    schema: 'ts'
  }).then((res)=>{

  })
}

// 暂停
const pausePlay = () => {
  proxy.$refs["EasyPlayerRef"].pause()
  playing.value = false
  togglePause()
}

// 播放
const playRecord = () => {
  playing.value = true
  togglePause()
  if (!streamInfo.value) {
    handlePlay(cloudRecordRow.value)
    return
  }
  proxy.$refs["EasyPlayerRef"].play()
}

// 开启/暂停 切换函数
const togglePause = () => {
  isPaused.value = !isPaused.value;
}

/**
 * 停止播放
 */
function stopPLay() {
  // 停止
  if (proxy.$refs["EasyPlayerRef"]) {
    proxy.$refs["EasyPlayerRef"].destroy()
  }
  playing.value = false
  playSpeed.value = 1
  handleCloseStreams()
}

/**
 * 快退5秒
 */
const seekBackward = () => {
  if (!streamInfo.value) {
    return
  }
  const newVal = (playerTime.value || 0) - 5 * 1000
  playerTime.value = newVal < 0 ? 0 : newVal
  seekRecord(playerTime.value)
}

/**
 * 快进5秒
 */
const seekForward = () => {
  if (!streamInfo.value) {
    return
  }
  playerTime.value = playerTime.value + 5 * 1000
  seekRecord(playerTime.value)
}

// 下载
const download = () => {
  downloadZip(cloudRecordRow.value);
}

// 截图
const snap = () => {
  proxy.$refs["EasyPlayerRef"].screenshot()
}

// 刷新
const refresh = () => {
  proxy.$refs["EasyPlayerRef"].play()
}

/**
 * 播放进度条点击
 *
 * @param event
 */
function timeProcessClick(event) {
  let x = event.offsetX
  let clientWidth = proxy.$refs["timeProcess"].clientWidth
  seekRecord(x / clientWidth * timeLen.value)
}

function seekRecord(playSeekValue) {
  seekCloudRecord({
    mediaServerId: streamInfo.value.mediaServerId,
    app: streamInfo.value.app,
    stream: streamInfo.value.stream,
    stamp: playSeekValue,
    schema: 'fmp4'
  }).then((data) => {
    currentWidth.value = playSeekValue / 1000 * step.value
  }).catch((error) => {
    console.log(error)
  })
}

/**
 * 获取播放时间
 *
 * @returns {string|*}
 */
const showPlayTimeValue = computed(() => {
  return streamInfo.value === null ? '--:--:-- --:--:--' : formatTimeStamp(cloudRecordRow.value.startTime)
})

/**
 * 获取播放时间
 *
 * @returns {string|*}
 */
const showPlayTimeTotal = computed(() => {
  if (streamInfo.value === null) {
    return '--:--:-- --:--:--'
  } else {
    return formatTimeStamp(cloudRecordRow.value.endTime)
  }
})

/**
 * 获取播放时间
 *
 * @type {ComputedRef<unknown>}
 */
const showPlayTimeTitle = computed(() => {
  if (showTimeLeft.value) {
    let time = showTimeLeft.value / proxy.$refs["timeProcess"].clientWidth * cloudRecordRow.value.timeLen
    let realTime = timeLen.value / cloudRecordRow.value.timeLen * time + startTime.value
    return `${moment(time + 1000).format('mm:ss')}(${moment(realTime).format('HH:mm:ss')})`
  } else {
    return ''
  }
})

/**
 * 获取播放进度条样式
 *
 * @type {ComputedRef<{left: string}>}
 */
const playTimeTitleStyle = computed(() => {
  return {left: (showTimeLeft.value - 32) + 'px'}
})

function timeProcessMouseEnter(event) {
  showTimeLeft.value = event.offsetX
}

function timeProcessMouseMove(event) {
  showTimeLeft.value = event.offsetX
}

function timeProcessMouseLeave(event) {
  showTimeLeft.value = null
}

/**
 * 获取播放进度条值
 *
 * @type {ComputedRef<{width: string}>}
 */
const playTimeValue = computed(() => {
  return {width: currentWidth.value + '%'}
})

/**
 * 格式化时间
 *
 * @param time
 * @returns {*}
 */
function formatTimeStamp(time) {
  return moment.unix(time / 1000).format('yyyy-MM-DD HH:mm:ss')
}

/**
 * 格式化时长
 *
 * @param time
 */
function formatTime(time) {
  const h = parseInt(time / 3600 / 1000)
  const minute = parseInt((time - h * 3600 * 1000) / 60 / 1000)
  let second = Math.ceil((time - h * 3600 * 1000 - minute * 60 * 1000) / 1000)
  if (second < 0) {
    second = 0
  }
  return (h > 0 ? h + `小时` : '') + (minute > 0 ? minute + '分' : '') + (second > 0 ? second + '秒' : '')
}

/**
 * 格式化文件大小
 *
 * @param bytes
 * @param decimals
 */
function formatBytes(bytes, decimals = 2) {
  if (bytes === 0) return '0 Bytes';
  const k = 1024;
  const dm = decimals < 0 ? 0 : decimals;
  const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
  const i = Math.floor(Math.log(bytes) / Math.log(k));
  return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
}

// 更新全屏状态的函数
const updateFullscreenState = () => {
  isFullScreen.value = !!document.fullscreenElement;
  if (!isFullScreen.value) {
    screenfull.exit()
    isFullScreen.value = false
    jessibucaHeight.value = "400px"
  }
};

/**
 * 获取所有在线媒体服务器
 */
function getAllOnlineMediaServeFun(){
  getAllOnlineMediaServe().then((res) => {
    mediaServerList.value = res.data
  })
}

// 组件挂载时添加事件监听
onMounted(() => {
  document.addEventListener('fullscreenchange', updateFullscreenState);
  getList()
  getAllOnlineMediaServeFun()
});

// 组件卸载时移除事件监听，防止内存泄漏
onUnmounted(() => {
  document.removeEventListener('fullscreenchange', updateFullscreenState);
});

</script>

<style scoped>
/* 添加 scoped 以避免样式污染，如果原项目是全局样式则去掉 scoped */
.cloud-record-playBox {
  width: 100%;
  background-color: #000000;
  display: flex;
  align-items: center;
  justify-content: center;
}

.cloud-record-record-play-control {
  height: 32px;
  line-height: 32px;
  display: inline-block;
  width: fit-content;
  padding: 0 10px;
  -webkit-box-shadow: 0 0 10px #262626;
  box-shadow: 0 0 10px #262626;
  background-color: #262626;
  margin: 4px 0;
}

.cloud-record-record-play-control-item {
  display: inline-block;
  padding: 0 10px;
  color: #fff;
  margin-right: 2px;
  cursor: pointer; /* 确保有手型指针 */
  text-decoration: none;
}

.cloud-record-record-play-control-item:hover {
  color: #1f83e6;
}

.cloud-record-record-play-control-speed {
  font-weight: bold;
  color: #fff;
  user-select: none;
  cursor: pointer;
}

.cloud-record-player-option-box {
  height: 20px;
  width: 100%;
  display: grid;
  grid-template-columns: 140px auto 140px;
  background-color: rgb(0, 0, 0);
}

.cloud-record-time-process {
  width: 100%;
  height: 8px;
  margin: 6px 0;
  border-radius: 4px;
  border: 1px solid #505050;
  background-color: rgb(56, 56, 56);
  cursor: pointer;
  position: relative; /* 确保子元素定位正确 */
}

.cloud-record-show-time {
  color: #FFFFFF;
  text-align: center;
  font-size: 14px;
  line-height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.cloud-record-time-process-value {
  width: 100%; /* 动态控制宽度 */
  height: 6px;
  background-color: rgb(162, 162, 162);
  border-radius: 4px;
  transition: width 0.1s linear;
}

/* 原 CSS 中的 .cloud-record-time-process-value1::after 似乎未在模板中使用，如需进度条拖拽手柄可取消注释并应用类名 */
/*
.cloud-record-time-process-value::after {
  content: '';
  display: block;
  width: 12px;
  height: 12px;
  background-color: rgb(192, 190, 190);
  border-radius: 50%;
  position: absolute;
  top: -3px;
  right: -6px;
}
*/
.cloud-record-time-process-title {
  width: fit-content;
  text-align: center;
  position: absolute; /* 改为 absolute 以配合 top/left 定位 */
  top: -35px;
  color: rgb(217, 217, 217);
  font-size: 14px;
  text-shadow: -1px -1px 0 black,
  1px -1px 0 black,
  -1px 1px 0 black,
  1px 1px 0 black;
  pointer-events: none;
  white-space: nowrap;
  z-index: 99;
}

.record-play-control-player {
  width: fit-content;
  height: 32px;
  display: inline-block;
}
</style>
