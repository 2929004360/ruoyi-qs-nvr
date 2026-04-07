<template>
  <el-dropdown v-if="streamInfo" trigger="click" @command="copyUrl">
    <el-button :icon="ArrowDownBold">
      更多地址
    </el-button>
    <template #dropdown>
      <el-dropdown-menu>
        <!-- FLV -->
        <el-dropdown-item v-if="streamInfo.flv" :command="streamInfo.flv">
          <el-tag>FLV:</el-tag>
          <span>{{ streamInfo.flv }}</span>
        </el-dropdown-item>
        <el-dropdown-item v-if="streamInfo.https_flv" :command="streamInfo.https_flv">
          <el-tag>FLV(https):</el-tag>
          <span>{{ streamInfo.https_flv }}</span>
        </el-dropdown-item>
        <el-dropdown-item v-if="streamInfo.ws_flv" :command="streamInfo.ws_flv">
          <el-tag>FLV(ws):</el-tag>
          <span>{{ streamInfo.ws_flv }}</span>
        </el-dropdown-item>
        <el-dropdown-item v-if="streamInfo.wss_flv" :command="streamInfo.wss_flv">
          <el-tag>FLV(wss):</el-tag>
          <span>{{ streamInfo.wss_flv }}</span>
        </el-dropdown-item>

        <!-- FMP4 -->
        <el-dropdown-item v-if="streamInfo.fmp4" :command="streamInfo.fmp4">
          <el-tag>FMP4:</el-tag>
          <span>{{ streamInfo.fmp4 }}</span>
        </el-dropdown-item>
        <el-dropdown-item v-if="streamInfo.https_fmp4" :command="streamInfo.https_fmp4">
          <el-tag>FMP4(https):</el-tag>
          <span>{{ streamInfo.https_fmp4 }}</span>
        </el-dropdown-item>
        <el-dropdown-item v-if="streamInfo.ws_fmp4" :command="streamInfo.ws_fmp4">
          <el-tag>FMP4(ws):</el-tag>
          <span>{{ streamInfo.ws_fmp4 }}</span>
        </el-dropdown-item>
        <el-dropdown-item v-if="streamInfo.wss_fmp4" :command="streamInfo.wss_fmp4">
          <el-tag>FMP4(wss):</el-tag>
          <span>{{ streamInfo.wss_fmp4 }}</span>
        </el-dropdown-item>

        <!-- HLS -->
        <el-dropdown-item v-if="streamInfo.hls" :command="streamInfo.hls">
          <el-tag>HLS:</el-tag>
          <span>{{ streamInfo.hls }}</span>
        </el-dropdown-item>
        <el-dropdown-item v-if="streamInfo.https_hls" :command="streamInfo.https_hls">
          <el-tag>HLS(https):</el-tag>
          <span>{{ streamInfo.https_hls }}</span>
        </el-dropdown-item>
        <el-dropdown-item v-if="streamInfo.ws_hls" :command="streamInfo.ws_hls">
          <el-tag>HLS(ws):</el-tag>
          <span>{{ streamInfo.ws_hls }}</span>
        </el-dropdown-item>
        <el-dropdown-item v-if="streamInfo.wss_hls" :command="streamInfo.wss_hls">
          <el-tag>HLS(wss):</el-tag>
          <span>{{ streamInfo.wss_hls }}</span>
        </el-dropdown-item>

        <!-- TS -->
        <el-dropdown-item v-if="streamInfo.ts" :command="streamInfo.ts">
          <el-tag>TS:</el-tag>
          <span>{{ streamInfo.ts }}</span>
        </el-dropdown-item>
        <el-dropdown-item v-if="streamInfo.https_ts" :command="streamInfo.https_ts">
          <el-tag>TS(https):</el-tag>
          <span>{{ streamInfo.https_ts }}</span>
        </el-dropdown-item>
        <el-dropdown-item v-if="streamInfo.ws_ts" :command="streamInfo.ws_ts">
          <el-tag>TS(ws):</el-tag>
          <span>{{ streamInfo.ws_ts }}</span>
        </el-dropdown-item>
        <el-dropdown-item v-if="streamInfo.wss_ts" :command="streamInfo.wss_ts">
          <el-tag>TS(wss):</el-tag>
          <span>{{ streamInfo.wss_ts }}</span>
        </el-dropdown-item>

        <!-- RTC -->
        <el-dropdown-item v-if="streamInfo.rtc" :command="streamInfo.rtc">
          <el-tag>RTC:</el-tag>
          <span>{{ streamInfo.rtc }}</span>
        </el-dropdown-item>
        <el-dropdown-item v-if="streamInfo.rtcs" :command="streamInfo.rtcs">
          <el-tag>RTCS:</el-tag>
          <span>{{ streamInfo.rtcs }}</span>
        </el-dropdown-item>

        <!-- RTMP -->
        <el-dropdown-item v-if="streamInfo.rtmp" :command="streamInfo.rtmp">
          <el-tag>RTMP:</el-tag>
          <span>{{ streamInfo.rtmp }}</span>
        </el-dropdown-item>
        <el-dropdown-item v-if="streamInfo.rtmps" :command="streamInfo.rtmps">
          <el-tag>RTMPS:</el-tag>
          <span>{{ streamInfo.rtmps }}</span>
        </el-dropdown-item>

        <!-- RTSP -->
        <el-dropdown-item v-if="streamInfo.rtsp" :command="streamInfo.rtsp">
          <el-tag>RTSP:</el-tag>
          <span>{{ streamInfo.rtsp }}</span>
        </el-dropdown-item>
        <el-dropdown-item v-if="streamInfo.rtsps" :command="streamInfo.rtsps">
          <el-tag>RTSPS:</el-tag>
          <span>{{ streamInfo.rtsps }}</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<script setup lang="ts">
import {defineProps} from 'vue';
import {ElMessage} from 'element-plus';
import {ArrowDownBold} from '@element-plus/icons-vue'

import useClipboard from 'vue-clipboard3'

const {toClipboard} = useClipboard()

// 定义 Props
const props = defineProps({
  streamInfo: {
    type: Object,
    required: true,
    default: () => ({})
  }
});

// 复制 URL 的方法
const copyUrl = async (url: string) => {
  if (!url) {
    ElMessage.error('内容为空，无法复制');
    return;
  }

  try {
    await toClipboard(url)
    ElMessage.success('成功拷贝到粘贴板');
  } catch (e) {
    console.error(e)
  }
};
</script>

<style scoped>
.el-dropdown {
  display: inline-block;
}
</style>
