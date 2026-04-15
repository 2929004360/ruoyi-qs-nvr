<template>
  <div style="background-color: #000"
       :style="isPercentage ? `width: ${width};height: ${height}` : `width: ${width}px;height: ${height}px`">
    <div class="player_box" :id="id ? id : 'player_box'"/>
  </div>
</template>

<script setup lang="ts">

const emit = defineEmits(['error'])

const props = defineProps({
  // 视频地址
  videoUrl: {
    type: String,
  },
  // 播放器宽度
  width: {
    type: Number,
    default: 1000
  },
  // 播放器高度
  height: {
    type: String,
    default: 500
  },
  // 是否百分比宽高
  isPercentage: {
    type: Boolean,
    default: false
  },
  // 是否直播 默认：true
  isLive: {
    type: Boolean,
    default: true
  },
  // 是否解析音频 默认：true
  hasAudio: {
    type: Boolean,
    default: true
  },
  // 水印
  watermark: {
    type: Object,
    // default: {content:'test',color:'',opacity:1,fontSize:''},right: 0,top: 0
  },
  // 全屏水印
  fullWatermark: {
    type: Object,
    // default: {text: 'test',angle:'',color:'',fontSize: '',opacity:''}
  },
  // 全屏水印
  quality: {
    type: Array,
    default: ['普清', '高清', '超清']
  },
  // 默认显示的清晰度，如果不设置，会显示第一个清晰度
  defaultQuality: {
    type: String,
    default: '高清'
  },
  // 是否显示PTZ控制按钮
  isPtz: {
    type: Boolean,
    default: true
  },
  // 是否显示清晰度控制按钮
  isQuality: {
    type: Boolean,
    default: true
  },
  // 封面图
  poster: {
    type: String,
    default: ''
  },
  // 是否渲染音频 默认：false
  isMute: {
    type: Boolean,
    default: true
  },
  // id
  id: {
    type: String,
  },
})

const easyplayer = ref<any>(null)

onMounted(() => {
  playCreate()
})

// 组件卸载时销毁播放器，释放资源
onBeforeUnmount(() => {
  if (easyplayer.value) {
    destroy()
  }
})

/**
 * 创建播放器
 */
const playCreate = () => {
  var container = null
  if (!props.id) {
    container = document.getElementById('player_box');
  } else {
    container = document.getElementById(props.id);
  }

  let config = {
    isLive: props.isLive, // 是否直播 默认：true
    hasAudio: props.hasAudio, // 是否解析音频 默认：true
    isMute: props.isMute, // 是否渲染音频 默认：false
    stretch: false, // 视频拉伸 默认：true
    poster: props.poster, // 封面图
    bufferTime: 0.2, // 加载显设置最小缓冲时长，单位秒，播放器会自动消除延迟。 默认：1
    loadTimeOut: 10, // 视频加载超时,单位秒。默认：10
    loadTimeReplay: 3, // 重连次数 -1为一直加载。默认：3
    MSE: false, // MSE模式 默认：false
    WCS: false, // WCS模式 默认：false
    WASM: false, // WASM模式 默认：false
    WASMSIMD: false, // WASMSIMD模式 默认：false
    gpuDecoder: false, // 硬解码 默认：false
    isFlv: false, // 强制使用Flv解码 默认：false
    webGPU: false, // 渲染方式 默认：false
    canvasRender: false, // 渲染容器 默认：false
    isRtcSRS: false, // SRS类型 默认：false
    isRtcZLM: false, // ZLM类型 默认：false
    isFlow: false, // 裸流 默认：false

    quality: props.quality, // 配置清晰
    defaultQuality: props.defaultQuality, // 默认显示的清晰度，如果不设置，会显示第一个清晰度
    ptzConfig: {ptz: true, ptzMore: true}, // PTZ配置
    debug: false, // 控制台日志打印 默认：false
    isBand: true, // 是否显示网络状态  默认：true
    btns: { // 按钮列表
      play: true, // 播放按钮
      audio: true, // 音量按钮
      record: true, // 录屏按钮
      zoom: true, // 电子放大按钮
      ptz: props.isPtz, // PTZ控制按钮
      quality: props.isQuality, // 清晰度控制按钮
      stretch: true, // 是否拉伸按钮
      screenshot: true, // 截图按钮
      fullscreen: true, // 全屏按钮
    }
  }

  if (props.watermark) {
    config.watermark = props.watermark // 水印
  }

  if (props.fullWatermark) {
    config.fullWatermark = props.fullWatermark // 全屏水印
  }

  easyplayer.value = new EasyPlayerPro(container, config);

  // 3. 修复：使用 easyplayer.value 绑定事件
  // 播放事件
  easyplayer.value.on("play", function (data) {
    console.log('play', data)
  })

  // 暂时事件
  easyplayer.value.on('pause', (data) => {
    console.log('pause', data)
  })

  // 视频信息
  easyplayer.value.on('videoInfo', function (data) {
    console.log('videoInfo', data)
  })

  // 音频信息
  easyplayer.value.on('audioInfo', function (data) {
    console.log('audioInfo', data)
  })

  // 全屏
  easyplayer.value.on('fullscreen', function (data) {
    console.log('fullscreen', data)
  })

  // 音频
  easyplayer.value.on('mute', function (data) {
    console.log('mute', data)
  })

  // 视频信息
  easyplayer.value.on('videoInfo', function (data) {
    console.log('videoInfo', data)
  })

  // 当前网速， 单位KB 每秒1次,
  // easyplayer.value.on('kBps', function (data) {
  //   console.log('kBps', data)
  // })

  // 切换拉伸
  easyplayer.value.on('stretch', function (data) {
    console.log('stretch', data)
  })

  // PTZ 事件
  easyplayer.value.on('ptz', function (data) {
    console.log('ptz', data)
  })

  // 截图回调
  easyplayer.value.on('screenshots', function (data) {
    console.log('screenshots', data)
  })

  // 右击关闭回调
  easyplayer.value.on('contextmenuClose', function (data) {
    console.log('contextmenuClose', data)
  })

  // 视频编码回调
  easyplayer.value.on('decodeHevc', function (data) {
    console.log('decodeHevc', data)
  })

  // 直播结束的事件
  easyplayer.value.on('liveEnd', function (data) {
    console.log('liveEnd', data)
  })

  // 加载超时
  easyplayer.value.on('timeout', function (data) {
    console.log('timeout', data)
  })

  // 录制结束的事件
  easyplayer.value.on('recordEnd', function (data) {
    console.log('recordEnd', data)
  })

  // 录制开始的事件
  easyplayer.value.on('recordStart', function (data) {
    console.log('recordStart', data)
  })

  // 当前是否全屏
  easyplayer.value.on('fullscreen', function (data) {
    console.log('fullscreen', data)
  })

  // 清晰度回调
  easyplayer.value.on('qualityChange', function (data) {
    console.log('qualityChange', data)
  })

  // 录像时间轴跳转回调
  easyplayer.value.on('playbackSeek', function (data) {
    console.log('playbackSeek', data)
  })

  // 录像倍数回调
  easyplayer.value.on('playbackRate', function (data) {
    console.log('playbackRate', data)
  })

  // 播放时间回调
  easyplayer.value.on('timestamps', function (data) {
    console.log('timestamps', data)
  })

  // 播放异常
  easyplayer.value.on('error', function (data) {
    emit('error');

  })

  play(props.videoUrl)
}
/**
 * 播放
 */
const play = (url) => {
  if (easyplayer.value) easyplayer.value.play(url)
}

/**
 * 播放 (录像回放)
 */
const playback = (url) => {
  if (easyplayer.value) easyplayer.value.playback(url)
}

/**
 * 暂停播放
 */
const pause = () => {
  if (easyplayer.value) easyplayer.value.pause()
}

/**
 * 音频
 */
const setMute = (mute) => {
  if (easyplayer.value) easyplayer.value.setMute(mute)
}

/**
 * 返回是否静音
 */
const isMute = () => {
  return easyplayer.value ? easyplayer.value.isMute() : false
}

/**
 * 获取快照 ('test', 'png | jpeg', '0-1(压缩率)','download | base64 | blob')
 */
const screenshot = (data) => {
  if (easyplayer.value) easyplayer.value.screenshot(data)
}

/**
 * 全屏(取消全屏)播放视频
 */
const setFullscreen = () => {
  if (easyplayer.value) easyplayer.value.setFullscreen()
}

/**
 * 退出全屏
 */
const exitFullscreen = () => {
  if (easyplayer.value) easyplayer.value.exitFullscreen()
}

/**
 * 设置分辨率必须是quality里面的数据
 */
const setQuality = () => {
  if (easyplayer.value) easyplayer.value.setQuality()
}

/**
 * 设置录像倍数
 */
const setRate = () => {
  if (easyplayer.value) easyplayer.value.setRate()
}

/**
 * 设置录像跳转时间/s
 */
const seekTime = () => {
  if (easyplayer.value) easyplayer.value.seekTime()
}

/**
 * 获取视频信息
 */
const getVideoInfo = () => {
  return easyplayer.value ? easyplayer.value.getVideoInfo() : null
}

/**
 * 获取音频信息
 */
const getAudioInfo = () => {
  return easyplayer.value ? easyplayer.value.getAudioInfo() : null
}

/**
 * 设置语音对讲状态(PTZ需开启)
 */
const setMic = (mic) => {
  if (easyplayer.value) easyplayer.value.setMic(mic)
}

/**
 * 关闭视频，释放底层资源
 */
const destroy = () => {
  if (easyplayer.value) easyplayer.value.destroy()
}

defineExpose({
  play,
  playback,
  pause,
  setMute,
  isMute,
  screenshot,
  setFullscreen,
  exitFullscreen,
  setQuality,
  setRate,
  seekTime,
  getVideoInfo,
  getAudioInfo,
  setMic,
  destroy
})
</script>

<style scoped>

</style>