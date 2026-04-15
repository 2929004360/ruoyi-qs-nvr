<template>
  <div class="app-container">
    <el-row :gutter="20">
      <splitpanes class="default-theme">
        <pane size="20">
          <el-col>
            <GroupTree
                @handleNodeClick="handleNodeClick"
                :showIndex="true"
                :contextMenu="['refresh', 'add', 'update', 'delete']"
            ></GroupTree>
          </el-col>
        </pane>

        <pane size="80">
          <el-col>
            <div style="margin-bottom: 10px">
              所选业务分组：
              <el-text v-if="groupName" type="primary">{{ groupName }}</el-text>
              <el-text v-else type="warning">未选择业务分组</el-text>
            </div>

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
                <el-button type="primary"
                           plain
                           icon="Plus"
                           :disabled="addDisabled"
                           @click="handleAdd">新增
                </el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button
                    type="danger"
                    plain
                    icon="Delete"
                    :disabled="addDisabled || multiple"
                    @click="handleDelete">
                  删除
                </el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button
                    type="danger"
                    plain
                    icon="Warning"
                    @click="handleUnusual">
                  异常挂载设备
                </el-button>
              </el-col>
              <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
            </el-row>

            <el-table v-loading="loading" :data="deviceList" @selection-change="handleSelectionChange" border>
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
              <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150">
                <template #default="scope">
                  <el-button link type="primary" :disabled="addDisabled" icon="Delete" @click="handleDelete(scope.row)">
                    删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>

            <pagination
                v-show="total > 0"
                :total="total"
                v-model:page="queryParams.pageNum"
                v-model:limit="queryParams.pageSize"
                @pagination="getList"
            />

            <el-dialog :title="title" v-model="open" width="1300px" append-to-body draggable>
              <el-form :model="queryParamsSelect" ref="querySelectRef" :inline="true" v-show="showSearchSelect"
                       label-width="68px">
                <el-form-item label="设备名称" prop="deviceName">
                  <el-input
                      v-model="queryParamsSelect.deviceName"
                      placeholder="请输入设备名称"
                      clearable
                      @keyup.enter="handleSelectQuery"
                  />
                </el-form-item>
                <el-form-item label="IP地址" prop="ipAddress">
                  <el-input
                      v-model="queryParamsSelect.ipAddress"
                      placeholder="请输入IP地址"
                      clearable
                      @keyup.enter="handleSelectQuery"
                  />
                </el-form-item>
                <el-form-item label="接入类型" prop="type">
                  <el-select v-model="queryParamsSelect.type" placeholder="请选择直播流接入类型" clearable>
                    <el-option
                        v-for="dict in qs_live_stream_type"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
                <el-form-item label="设备状态" prop="deviceStatus">
                  <el-select v-model="queryParamsSelect.deviceStatus" placeholder="请选择设备状态" clearable>
                    <el-option
                        v-for="dict in qs_device_status"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" icon="Search" @click="handleSelectQuery">搜索</el-button>
                  <el-button icon="Refresh" @click="resetSelectQuery">重置</el-button>
                </el-form-item>
              </el-form>

              <el-row :gutter="10" class="mb8">
                <el-col :span="1.5">
                  <el-button type="primary"
                             plain
                             icon="Select"
                             :disabled="multipleSelect"
                             @click="handleSelect">
                    选择
                  </el-button>
                </el-col>
                <right-toolbar v-model:showSearch="showSearchSelect" @queryTable="getListDevice"></right-toolbar>
              </el-row>

              <el-table v-loading="loadingSelect" :data="deviceSelectList"
                        @selection-change="handleSelectionSelectChange" border>
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
                <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150">
                  <template #default="scope">
                    <el-button link type="primary" icon="Select" @click="handleSelect(scope.row)">选择</el-button>
                  </template>
                </el-table-column>
              </el-table>

              <pagination
                  v-show="totalSelect > 0"
                  :total="totalSelect"
                  v-model:page="queryParamsSelect.pageNum"
                  v-model:limit="queryParamsSelect.pageSize"
                  @pagination="getListDevice"
              />
            </el-dialog>

            <UnusualGroupDeviceSelect ref="UnusualGroupDeviceSelectRef"></UnusualGroupDeviceSelect>
          </el-col>
        </pane>
      </splitpanes>
    </el-row>
  </div>
</template>

<script setup lang="ts" name="Group">
import GroupTree from "../../components/common/GroupTree.vue";
import {Pane, Splitpanes} from "splitpanes"
import "splitpanes/dist/splitpanes.css"
import {addDeviceIdToGroup, deleteDeviceToGroup, listDevice, queryListByParentId} from "@/api/qs/device";
import UnusualGroupDeviceSelect from "../../components/common/UnusualGroupDeviceSelect.vue"

const {proxy} = getCurrentInstance()
const {
  qs_live_stream_type,
  qs_device_status,
} = proxy.useDict('qs_live_stream_type', 'qs_device_status')

const deviceList = ref([]);
const loading = ref(true);
const total = ref(0);
const showSearch = ref(true);
const groupDeviceId = ref('');
const businessGroup = ref('');
const selectionList = ref([]);
const multiple = ref(true);
const addDisabled = ref(true);
const open = ref(false);
const title = ref("");
const dataType = ref('group');

const deviceSelectList = ref([]);
const loadingSelect = ref(true);
const totalSelect = ref(0);
const showSearchSelect = ref(true);
const multipleSelect = ref(true);
const selectionSelectList = ref([]);

const deviceType = ref('');
const groupName = ref('');

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    deviceName: undefined,
    ipAddress: undefined,
    type: undefined,
    deviceStatus: undefined,
    gbParentId: undefined,
    status: 'ENABLE',
  },
  rules: {},

  queryParamsSelect: {
    pageNum: 1,
    pageSize: 10,
    deviceName: undefined,
    ipAddress: undefined,
    type: undefined,
    deviceStatus: undefined,
    gbParentId: undefined,
    status: 'ENABLE',
  }
});

const {queryParams, form, rules, queryParamsSelect} = toRefs(data);

function getList() {
  loading.value = true
  listDevice(queryParams.value).then((res) => {
    deviceList.value = res.rows
    total.value = res.total
    loading.value = false
  })
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

function handleDelete(row) {
  let deviceIds = []
  if (row.id) {
    deviceIds.push(row.id)
  } else {
    for (let i = 0; i < selectionList.value.length; i++) {
      deviceIds.push(selectionList.value[i].id)
    }
  }
  proxy.$modal.confirm('是否删除选择的数据？').then(function () {
    return deleteDeviceToGroup({deviceIds: deviceIds});
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 选择条数  */
function handleSelectionChange(selection) {
  if (queryParams.value.gbParentId === null) {
    multiple.value = true
  } else {
    multiple.value = !selection.length;
  }
  selectionList.value = selection
}

/** 节点单击事件 */
function handleNodeClick(data) {
  if (data.deviceId == null || data.deviceId == undefined) {
    proxy.$modal.msgError("当前为业务分组，挂载设备请选择其下的虚拟组织，如不存在可右键新建");
    return
  }

  if (data.deviceId != null || data.deviceId != undefined) {
    queryParams.value.gbParentId = data.deviceId;
    addDisabled.value = false
  } else {
    queryParams.value.gbParentId = null;
    addDisabled.value = true
  }

  groupName.value = data.name;
  groupDeviceId.value = queryParams.value.gbParentId;
  businessGroup.value = data.businessGroup;
  handleQuery();
}

/** 新增按钮操作 */
function handleAdd() {
  if (groupDeviceId.value === "" || groupDeviceId.value === ' ') {
    proxy.$modal.msgError("请选择左侧业务分组");
    return;
  }

  title.value = "添加设备";
  open.value = true;

  getListDevice()
}

function getListDevice() {
  loadingSelect.value = true
  queryListByParentId(queryParamsSelect.value).then((res) => {
    deviceSelectList.value = res.rows
    totalSelect.value = res.total
    loadingSelect.value = false
  })
}

function handleSelectQuery() {
  queryParamsSelect.value.pageNum = 1;
  getListDevice();
}

function resetSelectQuery() {
  proxy.resetForm("querySelectRef");
  handleSelectQuery();
}

function handleSelectionSelectChange(selection) {
  selectionSelectList.value = selection;
  multipleSelect.value = !selection.length;
}

function handleSelect(row) {
  if (row.id) {
    addDeviceToCivilCode(groupDeviceId.value, businessGroup.value, [row])
  } else {
    addDeviceToCivilCode(groupDeviceId.value, businessGroup.value, selectionSelectList.value)
  }
  proxy.$modal.msgSuccess("选择成功");
  open.value = false;

}

function addDeviceToCivilCode(groupDeviceId, businessGroup, data) {
  let deviceIds = []
  for (let i = 0; i < data.length; i++) {
    deviceIds.push(data[i].id)
  }
  addDeviceIdToGroup({
    parentId: groupDeviceId,
    businessGroup: businessGroup,
    deviceIds: deviceIds
  }).then(() => {
    getList()
  })
}

/**
 * 异常挂载设备
 */
function handleUnusual() {
  proxy.$refs["UnusualGroupDeviceSelectRef"].openDialog();
}

getList()
</script>