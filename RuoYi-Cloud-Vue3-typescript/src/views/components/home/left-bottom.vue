<script setup name="LeftBottom" lang="ts" >
import * as echarts from 'echarts';
import moment from 'moment'

defineExpose({setData})

const myChart = ref({})

function setData(netList) {
  let timeList = netList?.map(i => moment(i.time).format("HH:mm:ss"))
  let inList = netList?.map(i => i.in.toFixed(2))
  let outList = netList?.map(i => i.out.toFixed(2))
  nextTick(() => {
    const chartDom = document.getElementById('leftBottomChart');
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
        },
      },
      toolbox: {
        show: true,
        feature: {
          magicType: { show: true, type: ['line', 'bar'] },
          restore: { show: true },
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
        axisLabel: {
          formatter: '{value} MB'
        }
      },
      yAxis: {
        type: 'category',
        data: timeList,
      },
      series: [
        {
          name: '下载',
          type: 'bar',
          data: inList
        },
        {
          name: '上传',
          type: 'bar',
          data: outList
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
  <div style="width: 100%;height: 350px;" id="leftBottomChart"></div>
</template>

<style scoped lang="scss">

</style>
