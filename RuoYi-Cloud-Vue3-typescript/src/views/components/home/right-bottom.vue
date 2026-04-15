<script setup name="RightBottom" lang="ts" >
import * as echarts from 'echarts';

defineExpose({setData})

const myChart = ref({})

function setData(diskList) {
  let pathList = diskList?.map(i => i.path)
  let freeList = diskList?.map(i => i.free.toFixed(2))
  let useList = diskList?.map(i => i.use.toFixed(2))
  nextTick(() => {
    const chartDom = document.getElementById('rightBottomChart');
    if(!chartDom){
      return
    }
    let chart = echarts.init(chartDom);
    myChart.value = chart
    let option;
    option = {
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'shadow'
        }
      },
      toolbox: {
        show: true,
        feature: {
          saveAsImage: { show: true }
        }
      },
      legend: {},
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: {
        type: 'value',
        boundaryGap: [0, 0.01],
        axisLabel: {
          formatter: '{value} GB'
        }
      },
      yAxis: {
        type: 'category',
        data: pathList,
      },
      series: [
        {
          name: '未使用',
          type: 'bar',
          data: freeList
        },
        {
          name: '已使用',
          type: 'bar',
          data: useList
        }
      ]
    };
    window.addEventListener("resize", resize);

    chart.setOption(option);
  })
}

function resize() {
  myChart.value.resize();
}

onBeforeUnmount(() => {
  window.removeEventListener("resize",resize);
})
</script>

<template>
  <div style="width: 100%;height: 350px;" id="rightBottomChart"></div>
</template>

<style scoped lang="scss"></style>
