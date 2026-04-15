<template>
  <div>
    <el-dialog
        draggable
        title="异常挂载设备"
        width="1300px"
        top="5rem"
        v-model="showDialog"
        :destroy-on-close="true"
        @close="close()"
    >
      <el-alert title="清除后设备可正常添加到行政区划，添加可以自动添加对应的行政区划节点。" type="success"
                :closable="false" style="margin-bottom: 20px"/>

      <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="设备名称" prop="deviceName">
          <el-input
              v-model="queryParams.deviceName"
              placeholder="请输入设备名称"
              clearable
              @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="IP地址" prop="ipAddress">
          <el-input
              v-model="queryParams.ipAddress"
              placeholder="请输入IP地址"
              clearable
              @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="接入类型" prop="type">
          <el-select v-model="queryParams.type" placeholder="请选择直播流接入类型" clearable>
            <el-option
                v-for="dict in qs_live_stream_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="设备状态" prop="deviceStatus">
          <el-select v-model="queryParams.deviceStatus" placeholder="请选择设备状态" clearable>
            <el-option
                v-for="dict in qs_device_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
          <el-button icon="Refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
              type="primary"
              plain
              :disabled="single"
              icon="CircleClose"
              @click="handleClear"
          >清除
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
              type="primary"
              plain
              :disabled="unusualCivilCodeList.length > 0 ? false : true"
              icon="Delete"
              @click="handleClearAll"
          >全部清除
          </el-button>
        </el-col>
        <right-toolbar v-model:showSearch="showSearch" @queryTable="getUnusualCivilCodeListFun"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="unusualCivilCodeList" border @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" fixed/>
        <el-table-column label="编号" align="center" prop="id" width="60"/>
        <el-table-column label="设备名称" align="center" prop="deviceName"/>
        <el-table-column label="IP地址" align="center" prop="ipAddress"/>
        <el-table-column label="接入类型" align="center" prop="type">
          <template #default="scope">
            <dict-tag :options="qs_live_stream_type" :value="scope.row.type"/>
          </template>
        </el-table-column>
        <el-table-column label="设备状态" align="center" prop="deviceStatus">
          <template #default="scope">
            <dict-tag :options="qs_device_status" :value="scope.row.deviceStatus"/>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
          v-show="total > 0"
          :total="total"
          v-model:page="queryParams.pageNum"
          v-model:limit="queryParams.pageSize"
          @pagination="getUnusualCivilCodeListFun"
      />
    </el-dialog>
  </div>
</template>

<script setup name="UnusualRegionDeviceSelect">
import {clearDeviceCivilCode, getUnusualCivilCodeList} from "@/api/qs/device.js";

const {proxy} = getCurrentInstance()
const {
  qs_live_stream_type,
  qs_device_status,
} = proxy.useDict('qs_live_stream_type', 'qs_device_status')


const single = ref(true)
const selectionList = ref([])
const showDialog = ref(false);
const title = ref('');
const unusualCivilCodeList = ref([])
const total = ref(0)
const loading = ref(true)
const showSearch = ref(true)
const data = reactive({
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    deviceName: undefined,
    ipAddress: undefined,
    type: undefined,
    deviceStatus: undefined,
    status: 'ENABLE',
  },
})

const {queryParams} = toRefs(data)

defineExpose({openDialog})

function getUnusualCivilCodeListFun() {
  loading.value = true
  getUnusualCivilCodeList(queryParams.value).then(res => {
    unusualCivilCodeList.value = res.rows
    total.value = res.total
    loading.value = false
  })
}

/** 多选框选中数据 */
function handleSelectionChange(selection) {
  selectionList.value = selection
  single.value = selection.length != 1
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getUnusualCivilCodeListFun()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

function handleClear() {
  clearDeviceCivilCode({
    deviceIds: selectionList.value.map(item => item.id)
  }).then(res => {
    proxy.$modal.msgSuccess("清除成功");
    getUnusualCivilCodeListFun()
  })
}
const handleDelete = (row) => {
  clearDeviceCivilCode({
    deviceIds: [row.id]
  }).then(res => {
    proxy.$modal.msgSuccess("清除成功");
    getUnusualCivilCodeListFun()
  })
}

function handleClearAll() {
  clearDeviceCivilCode({
    all: true,
    deviceIds: []
  }).then(res => {
    proxy.$modal.msgSuccess("全部清除成功");
    getUnusualCivilCodeListFun()
  })
}

function close() {
  showDialog.value = false;
}

function openDialog() {
  showDialog.value = true;
  getUnusualCivilCodeListFun()
}
</script>

<style scoped>

</style>