<template>
  <div class="app-container">
    <el-row :gutter="20">
      <splitpanes class="default-theme">
        <pane size="20">
          <el-col>
            <DeviceTree @clickEvent="clickEvent"
                        :isContextmenu="false"
            ></DeviceTree>
          </el-col>
        </pane>

        <pane size="80">
          <el-col>
            <div id="live" class="live-container">
              <div v-loading="loading" class="live-content" element-loading-text="拼命加载中">
                <div class="video-container">
                  <div class="control-bar">
                    <div class="split-controls">
                      分屏:
                      <i class="iconfont icon-a-mti-1fenpingshi btn" :class="{active:spiltIndex === 0}"
                         @click="spiltIndex=0"/>
                      <i class="iconfont icon-a-mti-4fenpingshi btn" :class="{active: spiltIndex === 1}"
                         @click="spiltIndex=1"/>
                      <i class="iconfont icon-a-mti-6fenpingshi btn" :class="{active: spiltIndex === 2}"
                         @click="spiltIndex=2"/>
                      <i class="iconfont icon-a-mti-9fenpingshi btn" :class="{active: spiltIndex === 3}"
                         @click="spiltIndex=3"/>
                    </div>
                    <div class="fullscreen-control">
                      <el-icon class="btn" @click="fullScreen()">
                        <FullScreen/>
                      </el-icon>
                    </div>
                  </div>
                  <div class="player-container">
                    <div
                        ref="playBox"
                        class="play-grid"
                        :style="liveStyle"
                    >
                      <div
                          v-for="i in layout[spiltIndex].spilt"
                          :key="i"
                          class="play-box"
                          :class="getPlayerClass(spiltIndex, i)"
                          @click="playerIdx = (i-1)"
                          style="position: relative;"
                      >
                        <div v-if="!videoUrl[i-1]" class="no-signal">{{
                            videoTip[i - 1] ? videoTip[i - 1] : "无信号"
                          }}
                        </div>
                        <div v-else style="width: 100%;height: 100%;">
                          <div @click="deleteClick(i-1)"
                               style="position: absolute;z-index: 999;right: 10px;cursor: pointer;top: 10px">
                            <el-icon size="25" color="#F56C6C">
                              <Delete/>
                            </el-icon>
                          </div>

                          <EasyPlayer
                              :ref="'player'+[i-1]"
                              :id="'player'+[i-1]"
                              style="width: 100%;height: 100%;"
                              width="100"
                              height="100"
                              :isPercentage="true"
                              :quality="quality"
                              :defaultQuality="defaultQuality"
                              :isPtz="isPtz"
                              :isQuality="isQuality"
                              :hasAudio="enableAudio[i-1] === '1'"
                              :isMute="enableAudio[i-1] === '1'"
                              :isLive="isLive"
                              :videoUrl="videoUrl[i-1]"/>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </el-col>
        </pane>
      </splitpanes>
    </el-row>
  </div>
</template>

<script setup lang="ts" name="Live">
import EasyPlayer from "@/components/EasyPlayer";
import {Pane, Splitpanes} from "splitpanes"
import "splitpanes/dist/splitpanes.css"
import DeviceTree from '@/components/DeviceTree'
import screenFull from 'screenfull'
import {ElMessageBox} from "element-plus";
import {PullConfig, RTPServerParam} from "@/types/api";
import {loadRecord, rtpPlay, streamPullPlay, streamPullPush} from "@/api/qs/zlm";
import {getDevice, getVideoSnapshot} from "@/api/qs/device";

const {proxy} = getCurrentInstance()

// 响应式状态
const enableAudio = ref([''])
const videoUrl = ref([''])
const videoTip = ref([''])
const spiltIndex = ref(2)        // 分屏索引
const playerIdx = ref(0)         // 激活播放器
const count = ref(15)
const total = ref(0)
const loading = ref(false)

const wsUrl = ref('');
const quality = ref(['普清', '高清', '超清']);
const defaultQuality = ref('高清');
const isPtz = ref(true);
const isQuality = ref(true);
const isLive = ref(true);

const layout = reactive([
  {
    spilt: 1,
    columns: '1fr',
    rows: '1fr',
    style: () => ({})
  },
  {
    spilt: 4,
    columns: '1fr 1fr',
    rows: '1fr 1fr',
    style: () => ({})
  },
  {
    spilt: 6,
    columns: '1fr 1fr 1fr',
    rows: '1fr 1fr 1fr',
    style: (index) => {
      if (index === 0) {
        return {
          gridColumn: '1 / span 2',
          gridRow: '1 / span 2'
        }
      }
      return {}
    }
  },
  {
    spilt: 9,
    columns: '1fr 1fr 1fr',
    rows: '1fr 1fr 1fr',
    style: () => ({})
  }
])


function clickEvent(id) {
  sendDevicePush(id)
}

async function sendDevicePush(id) {
  loading.value = true
  const idxTmp = playerIdx.value
  videoTip.value[idxTmp] = '正在拉流...'

  getDevice(id).then((res) => {
    let row = res.data

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
          let videoUrl
          if (location.protocol === 'https:') {
            videoUrl = res.data.wss_flv
          } else {
            videoUrl = res.data.ws_flv
          }

          setPlayUrl(videoUrl, idxTmp)
          setEnableAudio(row.enableAudio, idxTmp)

          quality.value = []
          defaultQuality.value = ''
          isPtz.value = false
          isQuality.value = false
          isLive.value = true
        })
      }).catch(err => {
        videoTip.value[idxTmp] = '播放失败'
      })
          .finally(() => {
            loading.value = false
          })
    } else if (row.type === '6') {
      loadRecord(row.id).then(async (res: any) => {
        await nextTick(async () => {
          let videoUrl
          if (location.protocol === 'https:') {
            videoUrl = res.data.wss_flv
          } else {
            videoUrl = res.data.ws_flv
          }

          setPlayUrl(videoUrl, idxTmp)
          setEnableAudio(row.enableAudio, idxTmp)

          quality.value = []
          defaultQuality.value = ''
          isPtz.value = false
          isQuality.value = false
          isLive.value = true

          getVideoSnapshot(row.id);
        })
      }).catch(err => {
        videoTip.value[idxTmp] = '播放失败'
      })
          .finally(() => {
            loading.value = false
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
          let videoUrl
          if (location.protocol === 'https:') {
            videoUrl = res.data.wss_flv
          } else {
            videoUrl = res.data.ws_flv
          }

          setPlayUrl(videoUrl, idxTmp)
          setEnableAudio(row.enableAudio, idxTmp)

          quality.value = []
          defaultQuality.value = ''
          isPtz.value = false
          isQuality.value = false
          isLive.value = true
        })
      }).catch(err => {
        videoTip.value[idxTmp] = '播放失败'
      })
          .finally(() => {
            loading.value = false
          })
    } else if (row.type === '13') {
      streamPullPush(row.id).then(async (res: any) => {
        await nextTick(async () => {
          let videoUrl
          if (location.protocol === 'https:') {
            videoUrl = res.data.wss_flv
          } else {
            videoUrl = res.data.ws_flv
          }

          setPlayUrl(videoUrl, idxTmp)
          setEnableAudio(row.enableAudio, idxTmp)

          quality.value = []
          defaultQuality.value = ''
          isPtz.value = false
          isQuality.value = false
          isLive.value = true
        })
      }).catch(err => {
        videoTip.value[idxTmp] = '播放失败'
      })
          .finally(() => {
            loading.value = false
          })
    }
  })
}

function setPlayUrl(url, idx) {
  videoUrl.value[idx] = url
}

function setEnableAudio(url, idx) {
  enableAudio.value[idx] = url
}

function fullScreen() {
  if (screenFull.isEnabled) {
    screenFull.toggle(proxy.$refs["playBox"])
  }
}

const liveStyle = computed(() => {
  return {
    display: 'grid',
    gridTemplateColumns: layout[spiltIndex.value].columns,
    gridTemplateRows: layout[spiltIndex.value].rows,
    gap: '4px',
    backgroundColor: '#a9a8a8'
  }
})

function getPlayerClass(splitIndex, i) {
  let classStr = 'play-box-' + splitIndex + '-' + i
  if (playerIdx.value === (i - 1)) {
    classStr += ' redborder'
  }
  return classStr
}

/**
 * 删除视频
 */
function deleteClick(index) {
  ElMessageBox.confirm(
      '是否删除该播放端口?',
      '温馨提示！',
      {
        confirmButtonText: '确实',
        cancelButtonText: '取消',
        type: 'warning',
      }
  )
      .then(() => {
        videoUrl.value.splice(index, 1);
        videoTip.value.splice(index, 1);
        proxy.$modal.msgSuccess("删除成功")
      })
      .catch(() => {

      })

}
</script>

<style scoped>
.live-container {
  height: calc(100vh - 124px);
  width: 100%;
}

.live-content {
  height: 100%;
  display: flex;
  flex-direction: row;
}

@media (max-width: 768px) {
  .live-content {
    flex-direction: column;
  }
}

.video-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.control-bar {
  height: 5vh;
  min-height: 40px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 17px;
}

.split-controls {
  text-align: left;
  padding-left: 10px;
}

.fullscreen-control {
  text-align: right;
  padding-right: 10px;
}

.player-container {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 10px;
  overflow: hidden;
}

.play-grid {
  width: 100%;
  height: 100%;
  max-height: calc(100vh - 180px);
  aspect-ratio: 16/9;
}

.btn {
  margin: 0 10px;
  cursor: pointer;
}

.btn:hover {
  color: #0094ff;
}

.btn.active {
  color: #0094ff;
}

.redborder {
  border: 2px solid #0094ff !important;
}

.play-box {
  background-color: #000000;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

.no-signal {
  color: #ffffff;
  font-size: 15px;
  font-weight: bold;
}

.play-box-2-1 {
  grid-column: 1 / span 2;
  grid-row: 1 / span 2;
}

/* Responsive adjustments for smaller screens */
@media (max-width: 576px) {
  .control-bar {
    flex-direction: column;
    height: auto;
    padding: 5px 0;
  }

  .split-controls, .fullscreen-control {
    width: 100%;
    text-align: center;
    padding: 5px 0;
  }

  .btn {
    margin: 0 5px;
  }
}


</style>