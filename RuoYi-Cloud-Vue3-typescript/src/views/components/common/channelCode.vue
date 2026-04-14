<template>
  <el-dialog
      draggable
      v-model="showVideoDialog"
      width="1200px"
      append-to-body
      title="生成国标编码"
      top="2rem"
      center
      :close-on-click-modal="false"
      :destroy-on-close="false"
  >
    <el-tabs v-model="activeKey" style="padding: 0 1rem; margin: auto 0" @tab-click="getRegionList">
      <el-tab-pane name="0">
        <template #label>
          <div class="show-code-item">{{ allVal[0].val }}</div>
          <div style="text-align: center">{{ allVal[0].meaning }}</div>
        </template>

        <el-radio-group v-model="allVal[0].val">
          <el-radio v-for="item in regionList" :key="item.deviceId" :value="item.deviceId" style="line-height: 2rem">
            {{ item.name }} - {{ item.deviceId }}
          </el-radio>
        </el-radio-group>
      </el-tab-pane>
      <el-tab-pane name="1">
        <template #label>
          <div class="show-code-item">{{ allVal[1].val }}</div>
          <div style="text-align: center">{{ allVal[1].meaning }}</div>
        </template>
        <el-radio-group v-model="allVal[1].val" :disabled="allVal[1].lock">
          <el-radio v-for="item in regionList" :key="item.deviceId" :value="item.deviceId.substring(2)"
                    style="line-height: 2rem">
            {{ item.name }} - {{ item.deviceId.substring(2) }}
          </el-radio>
        </el-radio-group>
      </el-tab-pane>
      <el-tab-pane name="2">
        <template #label>
          <div class="show-code-item">{{ allVal[2].val }}</div>
          <div style="text-align: center">{{ allVal[2].meaning }}</div>
        </template>
        <el-radio-group v-model="allVal[2].val" :disabled="allVal[2].lock">
          <el-radio v-for="item in regionList" :key="item.deviceId" :value="item.deviceId.substring(4)"
                    style="line-height: 2rem">
            {{ item.name }} - {{ item.deviceId.substring(4) }}
          </el-radio>
        </el-radio-group>
      </el-tab-pane>
      <el-tab-pane name="3">
        请手动输入基层接入单位编码,两位数字
        <template #label>
          <div class="show-code-item">{{ allVal[3].val }}</div>
          <div style="text-align: center">{{ allVal[3].meaning }}</div>
        </template>
        <el-input
            type="text"
            placeholder="请输入内容"
            v-model="allVal[3].val"
            maxlength="2"
            :disabled="allVal[3].lock"
            show-word-limit
        >
        </el-input>
      </el-tab-pane>
      <el-tab-pane name="4">
        <template #label>
          <div class="show-code-item">{{ allVal[4].val }}</div>
          <div style="text-align: center; ">{{ allVal[4].meaning }}</div>
        </template>
        <el-radio-group v-model="allVal[4].val" :disabled="allVal[4].lock">
          <el-radio v-for="item in industryCodeTypeList" :key="item.code" :value="item.code" style="line-height: 2rem">
            {{ item.name }} - {{ item.code }}
          </el-radio>
        </el-radio-group>
      </el-tab-pane>
      <el-tab-pane name="5">
        <template #label>
          <div class="show-code-item">{{ allVal[5].val }}</div>
          <div style="text-align: center">{{ allVal[5].meaning }}</div>
        </template>
        <el-radio-group v-model="allVal[5].val" :disabled="allVal[5].lock">
          <el-radio v-for="item in deviceTypeList" :value="item.code" :key="item.code" style="line-height: 2rem">
            {{ item.name }} - {{ item.code }}
          </el-radio>
        </el-radio-group>
      </el-tab-pane>
      <el-tab-pane name="6">
        <template #label>
          <div class="show-code-item">{{ allVal[6].val }}</div>
          <div style="text-align: center">{{ allVal[6].meaning }}</div>
        </template>
        <el-radio-group v-model="allVal[6].val" :disabled="allVal[6].lock">
          <el-radio v-for="item in networkIdentificationTypeList" :value="item.code" :key="item.code"
                    style="line-height: 2rem">
            {{ item.name }} - {{ item.code }}
          </el-radio>
        </el-radio-group>
      </el-tab-pane>
      <el-tab-pane name="7">
        请手动输入设备/用户序号, 六位数字
        <template #label>
          <div class="show-code-item">{{ allVal[7].val }}</div>
          <div style="text-align: center">{{ allVal[7].meaning }}</div>
        </template>

        <el-input
            type="text"
            placeholder="请输入内容"
            v-model="allVal[7].val"
            maxlength="6"
            :disabled="allVal[7].lock"
            show-word-limit
        >
        </el-input>
      </el-tab-pane>
    </el-tabs>
    <el-form style="">
      <el-form-item style="margin-top: 22px; margin-bottom: 0;">
        <div style="float:right;">
          <el-button type="primary" @click="handleOk">保存</el-button>
          <el-button @click="closeModel">取消</el-button>
        </div>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script setup name="ChannelCode" lang="ts">
import {ElMessage} from 'element-plus'
import {defineEmits} from 'vue';
import {getAllChild,} from "@/api/qs/region";
import {
  getDeviceTypeList,
  getNetworkIdentificationTypeList,
  getIndustryCodeList,
} from "@/api/qs/device";

const emit = defineEmits(['handleOk']);

const showVideoDialog = ref(false);
const activeKey = ref('0');
const allVal = ref([]);
const regionList = ref([]);
const deviceTypeList = ref([]);
const industryCodeTypeList = ref([]);
const networkIdentificationTypeList = ref([]);
const endCallBck = ref(null);


defineExpose({openDialog})

function openDialog(callBck, code, lockIndex, lockContent) {
  allVal.value = [{
    id: [1, 2],
    meaning: '省级编码',
    val: '11',
    type: '中心编码',
    lock: false,
  },
    {
      id: [3, 4],
      meaning: '市级编码',
      val: '01',
      type: '中心编码',
      lock: false,
    },
    {
      id: [5, 6],
      meaning: '区级编码',
      val: '01',
      type: '中心编码',
      lock: false,
    },
    {
      id: [7, 8],
      meaning: '基层接入单位编码',
      val: '01',
      type: '中心编码',
      lock: false,
    },
    {
      id: [9, 10],
      meaning: '行业编码',
      val: '00',
      type: '行业编码',
      lock: false,
    },
    {
      id: [11, 13],
      meaning: '类型编码',
      val: '132',
      type: '类型编码',
      lock: false,
    },
    {
      id: [14],
      meaning: '网络标识编码',
      val: '7',
      type: '网络标识',
      lock: false,
    },
    {
      id: [15, 20],
      meaning: '设备/用户序号',
      val: '000001',
      type: '序号',
      lock: false,
    }]
  showVideoDialog.value = true
  activeKey.value = '0';
  regionList.value = []

  getRegionList()
  if (typeof code != 'undefined' && code != null && code.length === 20) {
    allVal.value[0].val = code.substring(0, 2)
    allVal.value[1].val = code.substring(2, 4)
    allVal.value[2].val = code.substring(4, 6)
    allVal.value[3].val = code.substring(6, 8)
    allVal.value[4].val = code.substring(8, 10)
    allVal.value[5].val = code.substring(10, 13)
    allVal.value[6].val = code.substring(13, 14)
    allVal.value[7].val = code.substring(14)
  }
  if (typeof lockIndex != 'undefined') {
    allVal.value[lockIndex].lock = true
    allVal.value[lockIndex].val = lockContent
  }
  endCallBck.value = callBck;
}

function getRegionList() {
  nextTick(() => {
    if (activeKey.value === '0' || activeKey.value === '1' || activeKey.value === '2') {
      let parent = ''
      if (activeKey.value === '1') {
        parent = allVal.value[0].val
      }
      if (activeKey.value === '2') {
        parent = allVal.value[0].val + allVal.value[1].val
      }
      if (activeKey.value !== '0' && parent === '') {
        ElMessage.error('请先选择上级行政区划')
      }
      queryChildList(parent);
    } else if (activeKey.value === '4') {
      queryIndustryCodeList();
    } else if (activeKey.value === '5') {
      queryDeviceTypeList();
    } else if (activeKey.value === '6') {
      queryNetworkIdentificationTypeList();
    }
  })
}

function queryChildList(parent) {
  regionList.value = []

  getAllChild({parent: parent}).then((res) => {
    regionList.value = res.data
  })
}

function queryIndustryCodeList() {
  industryCodeTypeList.value = []

  getIndustryCodeList().then((res) => {
    industryCodeTypeList.value = res.data
  })
}

function queryDeviceTypeList() {
  deviceTypeList.value = []

  getDeviceTypeList().then((res) => {
    deviceTypeList.value = res.data
  })
}

function queryNetworkIdentificationTypeList() {
  networkIdentificationTypeList.value = []

  getNetworkIdentificationTypeList().then((res) => {
    networkIdentificationTypeList.value = res.data
  })
}

function closeModel() {
  showVideoDialog.value = false
}

function handleOk() {
  const code =
      allVal.value[0].val +
      allVal.value[1].val +
      allVal.value[2].val +
      allVal.value[3].val +
      allVal.value[4].val +
      allVal.value[5].val +
      allVal.value[6].val +
      allVal.value[7].val
  if (endCallBck.value) {
    endCallBck.value = code
  }
  showVideoDialog.value = false
  emit('handleOk', code);
}
</script>

<style>
.show-code-item {
  text-align: center;
  font-size: 3rem;
}
</style>
