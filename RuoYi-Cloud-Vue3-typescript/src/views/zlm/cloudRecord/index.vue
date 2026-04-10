<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="应用名" prop="app">
        <el-input
            v-model="queryParams.app"
            placeholder="请输入应用名"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="流id" prop="stream">
        <el-input
            v-model="queryParams.stream"
            placeholder="请输入流id"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开始时间" prop="startTime">
        <el-input
            v-model="queryParams.startTime"
            placeholder="请输入开始时间"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="结束时间" prop="endTime">
        <el-input
            v-model="queryParams.endTime"
            placeholder="请输入结束时间"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="媒体节点" prop="mediaServerId">
        <el-input
            v-model="queryParams.mediaServerId"
            placeholder="请输入ZLM Id"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
        >删除
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="cloudRecordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" prop="id" width="80"/>
      <el-table-column label="应用名" align="center" prop="app" width="100"/>
      <el-table-column label="流id" align="center" prop="stream"/>
      <el-table-column label="开始时间" align="center">
        <template v-slot:default="scope">
          {{ formatTimeStamp(scope.row.startTime) }}
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center">
        <template v-slot:default="scope">
          {{ formatTimeStamp(scope.row.endTime) }}
        </template>
      </el-table-column>

      <el-table-column label="媒体节点" align="center" prop="mediaServerId"/>
      <el-table-column label="文件名称" align="center" prop="fileName"/>
      <el-table-column label="大小" align="center" prop="fileSize">
        <template v-slot:default="scope">
          {{ formatBytes(scope.row.fileSize) }}
        </template>
      </el-table-column>
      <el-table-column label="时长" align="center">
        <template v-slot:default="scope">
          <el-tag>{{ formatTime(scope.row.timeLen) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link
                     type="primary"
                     icon="VideoPlay"
                     @click="handlePlay(scope.row)"
                     :loading="scope.row.loading"
          >
            播放
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
        v-show="total>0"
        :total="total"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
    />

    <el-dialog :title="`录像播放-${cloudRecordRow.fileName}`"
               v-model="easyPlayerOpen"
               width="840px"
               append-to-body
               draggable
               @close="getList"
    >
      <div style="width: 100%;height: 100%;display: flex;justify-content: center" v-if="easyPlayerOpen">
        <EasyPlayer
            ref="EasyPlayerRef"
            style="width: 800px;height: 400px;"
            width="100"
            height="100"
            :isPercentage="true"
            :quality="quality"
            :defaultQuality="defaultQuality"
            :isPtz="isPtz"
            :isQuality="isQuality"
            :hasAudio="true"
            :isMute="true"
            :isLive="isLive"
            :videoUrl="wsUrl"/>
      </div>
    </el-dialog>
  </div>
</template>

<script setup lang="ts" name="CloudRecord">
import moment from 'moment'
import EasyPlayer from "@/components/EasyPlayer";
import type {CloudRecordQueryParams, ZlmCloudRecord} from "@/types/api/zlm/cloudRecord"
import {delCloudRecord, listCloudRecord} from "@/api/qs/cloudRecord"
import momentDurationFormatSetup from 'moment-duration-format'

momentDurationFormatSetup(moment)

const {proxy} = getCurrentInstance()

const cloudRecordList = ref<ZlmCloudRecord[]>([])
const loading = ref<boolean>(true)
const showSearch = ref<boolean>(true)
const ids = ref<number[]>([])
const multiple = ref<boolean>(true)
const total = ref<number>(0)

// 播放
const easyPlayerOpen = ref(false)
const wsUrl = ref('');
const quality = ref(['普清', '高清', '超清']);
const defaultQuality = ref('高清');
const isPtz = ref(true);
const isQuality = ref(true);
const isLive = ref(true);
const cloudRecordRow = ref({})

const data = reactive({
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    app: undefined,
    stream: undefined,
    callId: undefined,
    startTime: undefined,
    endTime: undefined,
    mediaServerId: undefined,
    serverId: undefined,
    fileName: undefined,
    folder: undefined,
    filePath: undefined,
    collect: undefined,
    fileSize: undefined,
    timeLen: undefined,
  } as CloudRecordQueryParams,
})

const {queryParams} = toRefs(data)

/** 查询云端录像列表 */
function getList() {
  loading.value = true
  listCloudRecord(queryParams.value).then(response => {
    cloudRecordList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

// 多选框选中数据
function handleSelectionChange(selection: ZlmCloudRecord[]) {
  ids.value = selection.map(item => item.id)
  multiple.value = !selection.length
}

/** 删除按钮操作 */
function handleDelete(row: ZlmCloudRecord) {
  const _ids = row.id || ids.value
  proxy.$modal.confirm('是否确认删除云端录像编号为"' + _ids + '"的数据项？').then(function () {
    return delCloudRecord(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {
  })
}

/**
 * 播放
 *
 * @param row
 */
const handlePlay = (row: ZlmCloudRecord) => {

}

/**
 * 格式化时间
 *
 * @param time
 * @returns {*}
 */
function formatTimeStamp(time) {
  return moment.unix(time / 1000).format('yyyy-MM-DD HH:mm:ss')
}

/**
 * 格式化时长
 *
 * @param time
 */
function formatTime(time) {
  const h = parseInt(time / 3600 / 1000)
  const minute = parseInt((time - h * 3600 * 1000) / 60 / 1000)
  let second = Math.ceil((time - h * 3600 * 1000 - minute * 60 * 1000) / 1000)
  if (second < 0) {
    second = 0
  }
  return (h > 0 ? h + `小时` : '') + (minute > 0 ? minute + '分' : '') + (second > 0 ? second + '秒' : '')
}

/**
 * 格式化文件大小
 *
 * @param bytes
 * @param decimals
 */
function formatBytes(bytes, decimals = 2) {
  if (bytes === 0) return '0 Bytes';
  const k = 1024;
  const dm = decimals < 0 ? 0 : decimals;
  const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
  const i = Math.floor(Math.log(bytes) / Math.log(k));
  return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
}

getList()
</script>
