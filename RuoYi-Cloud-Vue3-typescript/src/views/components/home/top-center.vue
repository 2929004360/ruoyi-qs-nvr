<template>
  <div>
    <div ref="chartRef" style="width: 100%;height: 400px;"></div>
  </div>
</template>

<script setup name="TopCenter" lang="ts">
import * as echarts from 'echarts';
import {defineExpose} from "vue";
defineExpose({setData})

const apiResponseList = ref([])

const chartRef = ref(null);
let chartInstance = null;

const initChart = () => {
  if (!chartRef.value) return;
  chartInstance = echarts.init(chartRef.value);
  const seriesList = [];

  apiResponseList.value.forEach((node, index) => {
    // 提取当前节点的负载数据 (合并 threads 和 work)
    const nodeLoadData = [
      ...node.threadsLoad.map(i => i.load),
      ...node.workThreadsLoad.map(i => i.load)
    ];

    // 提取当前节点的延迟数据
    const nodeDelayData = [
      ...node.threadsLoad.map(i => i.delay),
      ...node.workThreadsLoad.map(i => i.delay)
    ];

    // 添加负载系列 (柱状图)
    seriesList.push({
      name: `${node.id} - 负载`,
      type: 'bar',
      stack: 'total_load', // 堆叠模式，方便看总负载
      data: nodeLoadData,

      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    });

    // 添加延迟系列 (折线图)
    seriesList.push({
      name: `${node.id} - 延迟`,
      type: 'line',
      yAxisIndex: 1, // 使用右侧 Y 轴
      data: nodeDelayData,
      lineStyle: {width: 2, type: 'dashed'}, // 虚线区分
      symbol: 'circle',
      symbolSize: 6,
      smooth: true
    });
  });

  // --- 3. ECharts 配置 ---
  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {type: 'cross'}
    },
    legend: {
      data: seriesList.map(s => s.name), // 动态生成图例
      top: 5,
      type: 'scroll' // 如果节点多，开启滚动
    },
    grid: {
      left: '3%',
      right: '10%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: [
      {
        type: 'category',
        // 合并所有线程名称作为 X 轴
        data: [
          ...apiResponseList.value[0].threadsLoad.map(i => i.name),
          ...apiResponseList.value[0].workThreadsLoad.map(i => i.name)
        ],
        axisLabel: {
          interval: 0,
          rotate: 45,
          formatter: (value) => {
            return value.substring(value.lastIndexOf(' ') + 1);
          }
        }
      }
    ],
    yAxis: [
      {
        type: 'value',
        name: '负载 (Load)',
        position: 'left',
        axisLine: {show: true, lineStyle: {color: '#5470C6'}},
        axisLabel: {color: '#5470C6'}
      },
      {
        type: 'value',
        name: '延迟 (ms)',
        position: 'right',
        axisLine: {show: true, lineStyle: {color: '#EE6666'}},
        axisLabel: {color: '#EE6666'},
        splitLine: {show: false}
      }
    ],
    series: seriesList
  };

  chartInstance.setOption(option);
};

const handleResize = () => chartInstance?.resize();


function setData(data) {
  apiResponseList.value = data
  initChart();
  window.addEventListener('resize', handleResize);
}

onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize);
  chartInstance?.dispose();
});
</script>