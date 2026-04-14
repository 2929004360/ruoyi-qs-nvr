<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="计划名称" prop="name">
        <el-input
            v-model="queryParams.name"
            placeholder="请输入计划名称"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 240px">
          <el-option label="启用" value="ENABLE"/>
          <el-option label="停用" value="DEACTIVATE"/>
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
            icon="Plus"
            @click="handleAdd"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
        >修改
        </el-button>
      </el-col>
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

    <el-table v-loading="loading" :data="recordPlanList" @selection-change="handleSelectionChange" border>
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" prop="id" width="100"/>
      <el-table-column label="计划名称" align="center" prop="name"/>
      <el-table-column prop="channelCount" label="关联设备" align="center"/>
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <el-tag type="primary" v-if="scope.row.snap === 'ENABLE'">启用</el-tag>
          <el-tag type="primary" v-if="scope.row.snap === 'DEACTIVATE'">停用</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" align="center"/>
      <el-table-column prop="updateTime" label="更新时间" align="center"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150">
        <template #default="scope">
          <el-button type="text" @click="handleLink(scope.row)">关联设备</el-button>
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)">修改</el-button>
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

    <!-- 添加或修改录像计划对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body draggable>
      <el-form ref="recordPlanRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="计划名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入计划名称"/>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio value="ENABLE">启用</el-radio>
            <el-radio value="DEACTIVATE">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <ByteWeekTimePicker v-model="byteTime" name="name"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>


    <el-dialog title="关联设备" v-model="openLink" width="1000px" append-to-body draggable>
      <AssociatedSevice :planId="recordPlanRow.id" v-if="openLink"/>
    </el-dialog>
  </div>
</template>

<script setup lang="ts" name="RecordPlan">
import type {RecordPlanQueryParams, ZlmRecordPlan} from "@/types/api/qs/recordPlan"
import ByteWeekTimePicker from "./byteWeekTimePicker.vue";
import AssociatedSevice from "./associatedDevice.vue";
import {addRecordPlan, delRecordPlan, getRecordPlan, listRecordPlan, updateRecordPlan} from "@/api/qs/recordPlan"
import {QsDevice} from "@/types/api";

const {proxy} = getCurrentInstance()

const recordPlanList = ref<ZlmRecordPlan[]>([])
const open = ref<boolean>(false)
const loading = ref<boolean>(true)
const showSearch = ref<boolean>(true)
const ids = ref<number[]>([])
const single = ref<boolean>(true)
const multiple = ref<boolean>(true)
const total = ref<number>(0)
const title = ref<string>("")
const byteTime = ref("");
const id = ref(null);


const openLink = ref(false);
const recordPlanRow = ref(null);

const data = reactive({
  form: {} as ZlmRecordPlan,
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: undefined,
    status: undefined,
  } as RecordPlanQueryParams,
  rules: {
    name: [{required: true, message: "请输入名称", trigger: "blur"}],
  }
})

const {queryParams, form, rules} = toRefs(data)

/** 查询录像计划列表 */
function getList() {
  loading.value = true
  listRecordPlan(queryParams.value).then(response => {
    recordPlanList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

// 取消按钮
function cancel() {
  open.value = false
  byteTime.value = ""
  reset()
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    snap: "ENABLE",
    name: null,
    status: "ENABLE",
    createTime: null,
    updateTime: null
  }
  proxy.resetForm("recordPlanRef")
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
function handleSelectionChange(selection: ZlmRecordPlan[]) {
  ids.value = selection.map(item => item.id)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  byteTime.value = "";
  title.value = "添加录像计划"
}

/** 修改按钮操作 */
function handleUpdate(row: ZlmRecordPlan) {
  reset()
  const _id = row.id || ids.value[0]
  byteTime.value = "";
  getRecordPlan(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改录像计划"
    nextTick(() => {
      byteTime.value = plan2Byte(response.data.planItemList)
    })
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["recordPlanRef"].validate((valid: boolean) => {
    if (valid) {
      form.value.planItemList = byteTime2PlanList()
      if (form.value.id != null) {
        updateRecordPlan(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addRecordPlan(form.value).then(() => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row: ZlmRecordPlan) {
  const _ids = row.id || ids.value
  proxy.$modal.confirm('是否确认删除录像计划编号为"' + _ids + '"的数据项？').then(function () {
    return delRecordPlan(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {
  })
}

/**
 * 关联设备
 *
 * @param planList
 */
const handleLink = (row: QsDevice) => {
  recordPlanRow.value = row
  openLink.value = true
}

const byteTime2PlanList = () => {
  if (byteTime.value.length === 0) {
    return;
  }

  const DayTimes = 24 * 2;
  let planList = [];
  let week = 1;

  // 把 336 长度的 list 分成 7 组，每组 48 个
  for (let i = 0; i < byteTime.value.length; i += DayTimes) {
    let planArray = byteTime2Plan(byteTime.value.slice(i, i + DayTimes));
    if (!planArray || planArray.length === 0) {
      week++;
      continue;
    }
    for (let j = 0; j < planArray.length; j++) {
      planList.push({
        planId: id.value,
        start: planArray[j].start,
        stop: planArray[j].stop,
        weekDay: week,
      });
    }
    week++;
  }
  return planList;
};

const byteTime2Plan = (weekItem) => {
  let start = null;
  let stop = null;
  let result = [];

  for (let i = 0; i < weekItem.length; i++) {
    let item = weekItem[i];
    if (item === '1') { // 表示选中
      stop = i;
      if (start === null) {
        start = i;
      }
      if (i === weekItem.length - 1 && start != null && stop != null) {
        result.push({
          start: start,
          stop: stop,
        });
      }
    } else {
      if (stop !== null) {
        result.push({
          start: start,
          stop: stop,
        });
        start = null;
        stop = null;
      }
    }
  }
  return result;
};

const plan2Byte = (planList) => {
  let byte = "";
  let indexArray = {};

  for (let i = 0; i < planList.length; i++) {
    let weekDay = planList[i].weekDay;
    let index = planList[i].start;
    let endIndex = planList[i].stop;
    for (let j = index; j <= endIndex; j++) {
      indexArray["key_" + (j + (weekDay - 1) * 48)] = 1;
    }
  }

  for (let i = 0; i < 336; i++) {
    if (indexArray["key_" + i]) {
      byte += "1";
    } else {
      byte += "0";
    }
  }
  return byte;
};

getList()
</script>
