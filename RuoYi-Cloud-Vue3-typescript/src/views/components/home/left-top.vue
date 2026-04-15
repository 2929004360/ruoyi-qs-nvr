<template>
  <div>
    <div ref="chartRef" style="width: 100%;height: 350px;"></div>
  </div>
</template>

<script setup name="LeftTop" lang="ts">
import {defineExpose, onBeforeUnmount, ref} from 'vue';
import * as echarts from 'echarts';

defineExpose({setData})
let apiData = {};

const chartRef = ref(null);
let chartInstance = null;

const fieldMap = {
  totalOnlineCount: '总在线数',
  totalOfflineCount: '总离线数',
  enableCount: '启用设备数',
  deactivateCount: '停用设备数',
  rtspCount: 'RTSP',
  rtmpCount: 'RTMP',
  flvCount: 'FLV',
  hlsCount: 'HLS',
  onvifCount: 'ONVIF',
  fileCount: '视频文件',
  hikSdkCount: '海康SDK',
  hikIsupCount: '海康ISUP',
  dahuaSdkCount: '大华SDK'
};

function setData(data) {
  apiData = data;
  initChart();
  window.addEventListener('resize', handleResize);
}

const initChart = () => {
  if (!chartRef.value) return;
  chartInstance = echarts.init(chartRef.value);

  // 过滤掉值为 0 或 null 的项，避免图表过于杂乱
  const pieData = Object.keys(fieldMap)
      .map(key => ({
        name: fieldMap[key],
        value: apiData[key] || 0 // 兼容 null 值
      }))
      .filter(item => item.value > 0); // 只显示有数据的类型

  const option = {
    title: {
      // text: '设备总数', // 可以加一个主标题，也可以留空
      subtext: `总数: ${apiData.totalDeviceCount}`,
      left: 'center',

      // --- 核心修改：主标题样式 (如果 text 为空，这个可以忽略) ---
      textStyle: {
        fontSize: 14,
        color: '#999'
      },

      // --- 核心修改：副标题样式 (这是你现在显示的文字) ---
      subtextStyle: {
        fontSize: 24,      // 调大字号，默认是 12-14
        color: '#333',     // 加深颜色，让它更显眼
        fontWeight: 'bold' // 加粗
      }
    },
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c} ({d}%)' // 显示名称、数量、百分比
    },
    legend: {
      orient: 'vertical',
      left: 'left',
      top: 'middle',
      data: pieData.map(item => item.name)
    },
    series: [
      {
        name: '设备类型',
        type: 'pie',
        radius: ['40%', '70%'], // 环形图设置，内半径40%，外半径70%
        center: ['60%', '55%'], // 图表位置偏移，给左侧图例留空间
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: true,
          formatter: '{b}\n{d}%' // 标签显示名称和百分比
        },
        emphasis: {
          label: {
            show: true,
            fontSize: 16,
            fontWeight: 'bold'
          },
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        },
        labelLine: {
          show: true
        },
        data: pieData
      }
    ]
  };

  chartInstance.setOption(option);
};

const handleResize = () => chartInstance?.resize();


onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize);
  chartInstance?.dispose();
});
</script>