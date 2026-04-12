<template>
  <div>
    <el-tabs v-model="hasLink" class="demo-tabs" @tab-click="handleClick">
      <el-tab-pane label="未关联" :name="false"/>
      <el-tab-pane label="已关联" :name="true"/>

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
        <el-col :span="1.5" v-if="!hasLink">
          <el-button type="primary"
                     plain
                     icon="Plus"
                     :disabled="multiple"
                     @click="handleAdd">新增
          </el-button>
        </el-col>
        <el-col :span="1.5" v-if="hasLink">
          <el-button
              type="danger"
              plain
              icon="Delete"
              :disabled="multiple"
              @click="handleDelete">
            删除
          </el-button>
        </el-col>
        <el-col :span="1.5" v-if="!hasLink">
          <el-button type="primary"
                     plain
                     icon="Plus"
                     @click="handleAddAll">
            添加所有设备
          </el-button>
        </el-col>
        <el-col :span="1.5" v-if="hasLink">
          <el-button
              type="danger"
              plain
              icon="Delete"
              @click="handleRemoveAll">
            移除所有设备
          </el-button>
        </el-col>
        <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="channelList" @selection-change="handleSelectionChange" border>
        <el-table-column type="selection" width="55" align="center" fixed/>
        <el-table-column label="编号" align="center" prop="id" width="60" fixed/>
        <el-table-column label="设备名称" align="center" prop="deviceName" fixed/>
        <el-table-column label="IP地址" align="center" prop="ipAddress"/>
        <el-table-column label="接入类型" align="center" prop="type">
          <template #default="scope">
            <dict-tag :options="qs_live_stream_type" :value="scope.row.type"/>
          </template>
        </el-table-column>
        <el-table-column label="设备号" align="center" prop="channel"/>
        <el-table-column label="设备状态" align="center" prop="deviceStatus">
          <template #default="scope">
            <dict-tag :options="qs_device_status" :value="scope.row.deviceStatus"/>
          </template>
        </el-table-column>
      </el-table>

      <pagination
          v-show="total > 0"
          :total="total"
          v-model:page="queryParams.page"
          v-model:limit="queryParams.count"
          @pagination="getList"
      />

    </el-tabs>
  </div>
</template>

<script setup name="AssociatedDevice" lang="ts">
import {useRoute} from "vue-router";
import {link, listPlanRecord} from "@/api/qs/device";

const {proxy} = getCurrentInstance()
const {
  qs_live_stream_type,
  qs_device_status,
} = proxy.useDict('qs_live_stream_type', 'qs_device_status')

const route = useRoute();

const loading = ref(false)
const channelList = ref([])
const total = ref(0);
const showSearch = ref(true);
const multiple = ref(true);
const selectionList = ref([]);

const hasLink = ref(false);

const props = defineProps({
  planId: {
    type: String,
    default: undefined,
  },
})

const data = reactive({
  form: {},
  queryParams: {
    page: 1,
    count: 10,
    deviceName: undefined,
    ipAddress: undefined,
    type: undefined,
    deviceStatus: undefined,
    recordPlanId: undefined,
  },
});

const {queryParams, form} = toRefs(data);

function getList() {
  loading.value = true

  if(hasLink.value){
    queryParams.value.recordPlanId = props.planId;
  }else {
    queryParams.value.recordPlanId = undefined;
  }
  listPlanRecord(queryParams.value).then((res) => {
    total.value = res.total
    channelList.value = res.rows
    loading.value = false
  })
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.page = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

/** 选择条数  */
function handleSelectionChange(selection) {
  selectionList.value = selection
  multiple.value = !selection.length;
}

function handleAdd() {
  let deviceIds = []
  for (let i = 0; i < selectionList.value.length; i++) {
    deviceIds.push(selectionList.value[i].id)
  }
  if (deviceIds.length === 0) {
    proxy.$modal.msgError("请选择要关联的设备");
    return;
  }
  linkPlan({
    planId: props.planId,
    deviceIds: deviceIds
  })
  proxy.$modal.msgSuccess("关联成功");
}

function linkPlan(data) {
  link(data).then((res) => {
    getList();
  })
}

function handleDelete() {
  let deviceIds = []
  for (let i = 0; i < selectionList.value.length; i++) {
    deviceIds.push(selectionList.value[i].id)
  }
  if (deviceIds.length === 0) {
    proxy.$modal.msgError("请选择要关联的设备");
    return;
  }
  linkPlan({
    deviceIds: deviceIds
  })
  proxy.$modal.msgSuccess("取消关联成功");
}

function handleAddAll() {
  proxy.$modal.confirm('添加所有设备将包括已经添加到其他计划的设备，确定添加所有设备？').then(function () {
    return linkPlan({
      planId: props.planId,
      allLink: true
    })
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("添加成功");
  }).catch(() => {
  });
}

function handleRemoveAll() {
  proxy.$modal.confirm('确定移除所有设备？').then(function () {
    return linkPlan({
      planId: props.planId,
      allLink: false
    })
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("移除成功");
  }).catch(() => {
  });
}

const handleClick = () => {
  nextTick(() => {
    getList();
  })
}


onMounted(() => {
  getList();
})
</script>

<style scoped>

</style>
