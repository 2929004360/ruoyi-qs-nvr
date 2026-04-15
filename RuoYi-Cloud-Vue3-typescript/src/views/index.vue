<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>设备总览</span>
            </div>
          </template>
          <LeftTop ref="LeftTopRef"/>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>节点统计</span>
            </div>
          </template>
          <TopCenter ref="TopCenterRef"/>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>CPU</span>
            </div>
          </template>
          <RightTop ref="RightTopRef"/>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>网络</span>
            </div>
          </template>
          <LeftBottom ref="LeftBottomRef"/>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>内存</span>
            </div>
          </template>
          <BottomCenter ref="BottomCenterRef"/>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>磁盘</span>
            </div>
          </template>
          <RightBottom ref="RightBottomRef"/>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts" name="Index">
import LeftTop from "./components/home/left-top.vue"
import TopCenter from "./components/home/top-center.vue"
import RightTop from "./components/home/right-top.vue"
import LeftBottom from "./components/home/left-bottom.vue"
import BottomCenter from "./components/home/bottom-center.vue"
import RightBottom from "./components/home/right-bottom.vue"
import {getDeviceStatistics, getSystemInfo} from "@/api/qs/system";
import {getMediaLoad} from "@/api/qs/zlm";

const timer = ref({})
const RightTopRef = ref(null)
const LeftBottomRef = ref(null)
const BottomCenterRef = ref(null)
const RightBottomRef = ref(null)
const TopCenterRef = ref(null)
const LeftTopRef = ref(null)

onMounted(() => {
  nextTick(() => {
    serverInfoFun()
    getMediaLoadFun()
    getDeviceStatisticsFun()
    timer.value = setInterval(() => {
      serverInfoFun()
      getMediaLoadFun()
    }, 2000)
  })
})

function serverInfoFun() {
  getSystemInfo().then(res => {
    RightTopRef.value.setData(res.data.cpu)
    LeftBottomRef.value.setData(res.data.net)
    BottomCenterRef.value.setData(res.data.mem)
    RightBottomRef.value.setData(res.data.disk)
  })
}

function getMediaLoadFun() {
  getMediaLoad().then(res => {
    TopCenterRef.value.setData(res.data)
  })
}

function getDeviceStatisticsFun(){
  getDeviceStatistics().then(res => {
    LeftTopRef.value.setData(res.data)
  })
}
</script>

<style scoped lang="scss">

</style>

