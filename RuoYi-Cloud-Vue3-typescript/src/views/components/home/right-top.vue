<script setup name="RightTop" lang="ts" >
import {defineExpose, nextTick, onBeforeUnmount, ref} from 'vue';
import * as echarts from 'echarts';
import moment from 'moment'

defineExpose({setData})

const myChart = ref({})

function setData(cpuList) {
  let timeList = cpuList?.map(i => moment(i.time).format("HH:mm:ss"))
  let dataList = cpuList?.map(i => (i.data*100).toFixed(2))
  nextTick(() => {
    const chartDom = document.getElementById('rightTopChart');
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
          type: 'cross',
          label: {
            backgroundColor: '#6a7985'
          }
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
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: [
        {
          type: 'category',
          boundaryGap: false,
          data: timeList
        }
      ],
      yAxis: [
        {
          type: 'value',
          min: 0,
          max: 100,
          axisLabel: {
            formatter: '{value} %'
          }
        }
      ],
      series: [
        {
          type: 'line',
          stack: 'Total',
          smooth: true,
          lineStyle: {
            width: 0
          },
          showSymbol: false,
          areaStyle: {
            opacity: 0.8,
          },
          emphasis: {
            focus: 'series'
          },
          data: dataList
        },
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
  <div style="width: 100%;height: 350px;" id="rightTopChart"></div>
</template>

<style scoped lang="scss"></style>
