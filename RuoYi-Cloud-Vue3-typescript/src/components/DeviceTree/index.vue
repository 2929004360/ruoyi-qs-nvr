<template>
  <div id="DeviceTree" class="device-tree-container" style="height: 100%">
    <div class="device-tree-header">
      <div class="header-title">设备列表</div>
      <div class="header-switch">
        <el-switch
            v-model="showRegion"
            active-color="#13ce66"
            inactive-color="rgb(64, 158, 255)"
            active-text="行政区划"
            inactive-text="业务分组"
            @change="change"
        />
      </div>
    </div>

    <div class="tree-content">
      <div class="tree-wrapper">
        <RegionTree
            v-if="showRegion"
            ref="regionTree"
            :showIndex="false"
            :showContextmenu="false"
            :hasDevice="true"
            @handleNodeClick="treeNodeClickEvent"
            :isContextmenu="isContextmenu"
            :contextMenu="['playChannel', 'updatePosition']"
            @playChannel="playChannelFun($event,'region')"
            @updatePosition="updatePositionFun($event,'region')"
        />
        <GroupTree
            v-if="!showRegion"
            ref="groupTree"
            :showIndex="false"
            :showContextmenu="false"
            :hasDevice="true"
            @handleNodeClick="treeNodeClickEvent"
            :isContextmenu="isContextmenu"
            :contextMenu="['playChannel', 'updatePosition']"
            @playChannel="playChannelFun($event,'group')"
            @updatePosition="updatePositionFun($event,'group')"
        />
      </div>
    </div>
  </div>
</template>

<script setup name="DeviceTree" lang="ts">
import GroupTree from "@/views/components/common/GroupTree.vue";
import RegionTree from "@/views/components/common/RegionTree.vue";

const {proxy} = getCurrentInstance()
const emit = defineEmits(['clickEvent', ',playChannel', 'updatePosition']);

const props = defineProps({
  device: {
    type: Object,
    default: () => ({})
  },
  isContextmenu: {
    type: Boolean,
    default: false
  },
})

const showRegion = ref(false)

function treeNodeClickEvent(data) {
  if (data.leaf) {
    emit('clickEvent', data.id);
  }
}

function refresh(id) {
  if (showRegion.value) {
    proxy.$refs["regionTree"].refresh(id)
  } else {
    proxy.$refs["groupTree"].refresh(id)
  }
}

function playChannelFun(data, type) {
  emit('playChannel', data, type);
}

function updatePositionFun(data, type) {
  emit('updatePosition', data, type);
}

function change(){
  emit('change', showRegion.value);
}

defineExpose({
  refresh,
  showRegion
})

</script>

<style scoped>
.device-tree-container {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
  overflow: hidden !important; /* Force no overflow on container */
}

.device-tree-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
  flex-wrap: wrap;
  gap: 10px;
  min-height: 30px;
}

.header-title {
  font-size: 16px;
  font-weight: 500;
}

.tree-content {
  flex: 1;
  overflow: hidden !important;
  width: 100%;
  margin: 0;
  padding: 0;
  position: relative;
}

.tree-wrapper {
  width: 100%;
  height: 100%;
  min-width: 0; /* Prevent flex items from overflowing */
  position: relative;
}

/* Global fixes for Element UI tree components */
.el-tree {
  overflow: visible !important;
  width: 100% !important;
  min-width: 0 !important;
  height: 100% !important;
}

.el-tree-node {
  width: 100% !important;
  min-width: 0 !important;
}

.el-tree-node__content {
  width: 100% !important;
  min-width: 0 !important;
}

.el-tree-node__label {
  word-break: break-word !important;
  white-space: normal !important;
}

/* Fix for any scrollable containers */
[style*="overflow: auto"],
[style*="overflow:auto"],
[style*="overflow: scroll"],
[style*="overflow:scroll"] {
  overflow: visible !important;
}

/* Make sure tree nodes are fully visible */
.el-tree-node__children {
  overflow: visible !important;
}

/* Ensure tree nodes can be expanded/collapsed */
.el-tree-node__expand-icon {
  cursor: pointer;
}

.device-tree-main-box {
  text-align: left;
}

/* Responsive adjustments */
@media (max-width: 768px) {
  .device-tree-container {
    padding: 10px;
  }

  .device-tree-header {
    flex-direction: column;
    align-items: flex-start;
  }

  .header-switch {
    width: 100%;
    margin-top: 5px;
  }
}

@media (max-width: 480px) {
  .device-tree-container {
    padding: 8px;
  }

  .header-title {
    font-size: 14px;
  }

  /* Adjust el-switch text size for mobile */
  .el-switch__label {
    font-size: 12px;
  }
}
</style>