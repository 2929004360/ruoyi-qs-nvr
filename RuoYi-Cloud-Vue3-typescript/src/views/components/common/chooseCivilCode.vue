<template>
  <div>
    <el-dialog
        draggable
        title="选择行政区划"
        width="30%"
        top="5rem"
        :close-on-click-modal="false"
        v-model="showDialog"
        :destroy-on-close="true"
        @close="close()"
        append-to-body
    >
      <RegionTree ref="regionTree" @handleNodeClick="handleNodeClick"></RegionTree>
      <el-form style="margin-top: 20px">
        <el-form-item>
          <div style="text-align: right">
            <el-button type="primary" @click="onSubmit">保存</el-button>
            <el-button @click="close">取消</el-button>
          </div>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script setup name="ChooseCivilCode" lang="ts">
import RegionTree from "../../components/common/RegionTree.vue";

const showDialog = ref(false);
const regionDeviceId = ref('');
const regionName = ref('');
import {defineEmits} from "vue";
import {ElMessage} from "element-plus";
const emit = defineEmits(['onSubmit']);
defineExpose({openDialog})

function openDialog(callback) {
  showDialog.value = true;
}

function onSubmit() {
  if(!regionDeviceId.value){
    ElMessage({
      message: '行政区划不存在',
      type: 'error',
    })
    return
  }
  close();
  emit('onSubmit', regionDeviceId.value,regionName.value);
}

function close() {
  showDialog.value = false;
}

function handleNodeClick(data) {
  regionDeviceId.value = data.deviceId;
  regionName.value = data.name;
}
</script>
