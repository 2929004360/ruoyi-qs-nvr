<template>
  <div>
    <el-input
        v-model="searchStr"
        placeholder="请输入内容"
    >
      <template #append v-if="showIndex">
        <el-checkbox v-model="checked" label="编号"/>
      </template>
      <template #prepend>
        <el-button @click="refresh">刷新</el-button>
      </template>
    </el-input>

    <el-tree
        v-if="!searchStr"
        node-key="id"
        style="margin-top: 10px"
        ref="treeRef"
        :data="treeData"
        :props="propsTree"
        @node-click="handleNodeClick"
        lazy
        :load="loadNode"
        :default-expanded-keys="defaultExpandedKeys"
    >
      <template #default="{ node, data }">
        <div v-if="showContextmenu"
             class="custom-tree-node"
             @contextmenu="onContextMenu($event,data)"
             style="display: flex;">

          <div v-if="!data.leaf">
            <el-icon :color="chooseId === data.name ? '#0094ff' : ''" :size="18" style="margin-right: 4px">
              <FolderOpened/>
            </el-icon>
          </div>

          <div>{{ node.label }}</div>

          <div v-if="data.deviceId">
               <span v-if="showIndex && checked">
                （编号：{{ data.deviceId }}）
               </span>
          </div>
        </div>

        <div v-else
             class="custom-tree-node">
          <div v-if="data.leaf">
            <div v-if="isContextmenu" @contextmenu="onContextMenu($event,data)"
                 style="display: flex;align-items: center">
              <el-icon color="#409efc" :size="18" style="margin-top: 2px;margin-right: 4px" v-if="data.status === 'ON'">
                <VideoCamera/>
              </el-icon>
              <el-icon :size="18" style="margin-top: 2px;margin-right: 4px" v-if="data.status === 'OFFLINE'">
                <VideoCamera/>
              </el-icon>

              <div>{{ node.label }}</div>

              <div v-if="data.deviceId">
               <span v-if="showIndex && checked">
                （编号：{{ data.deviceId }}）
               </span>
              </div>
            </div>

            <div v-else style="display: flex;align-items: center">
              <el-icon color="#409efc" :size="18" style="margin-top: 2px;margin-right: 4px" v-if="data.status === 'ON'">
                <VideoCamera/>
              </el-icon>
              <el-icon :size="18" style="margin-top: 2px;margin-right: 4px" v-if="data.status === 'OFFLINE'">
                <VideoCamera/>
              </el-icon>

              <div>{{ node.label }}</div>

              <div v-if="data.deviceId">
               <span v-if="showIndex && checked">
                （编号：{{ data.deviceId }}）
               </span>
              </div>
            </div>
          </div>

          <div v-else style="display: flex;align-items: center">
            <el-icon :color="chooseId === data.name ? '#0094ff' : ''" :size="18"
                     style="margin-right: 4px">
              <FolderOpened/>
            </el-icon>

            <div>{{ node.label }}</div>

            <div v-if="data.deviceId">
               <span v-if="showIndex && checked">
                （编号：{{ data.deviceId }}）
               </span>
            </div>
          </div>
        </div>
      </template>
    </el-tree>

    <div v-else style="color: #606266; height: calc(100% - 32px); overflow: auto !important;">
      <ul v-if="groupList.length > 0" style="list-style: none; margin: 0; padding: 10px">
        <li v-for="item in groupList" :key="item.id" class="channel-list-li"
            style="height: 26px; align-items: center;cursor: pointer;" @click="handleNodeClick(item)">
          <span
              v-if="chooseId !== item.name"
              style="color: #409EFF; font-size: 20px"
              class="iconfont icon-bianzubeifen3"
          />
          <span
              v-if="chooseId === item.name"
              style="color: #c60135; font-size: 20px"
              class="iconfont icon-bianzubeifen3"
          />
          <div>
            <div style="margin-left: 4px; margin-bottom: 3px; font-size: 15px">{{ item.name }}</div>
            <div style="margin-left: 4px; font-size: 13px; color: #808181">{{ item.deviceId }}</div>
          </div>
        </li>

        <pagination
            layout="total, prev, pager, next"
            v-show="total > 0"
            :total="total"
            v-model:page="queryParamsGroup.pageNum"
            v-model:limit="queryParamsGroup.pageSize"
            @pagination="getQueryForGroupQuery(searchStr)"
        />
      </ul>
    </div>

    <ContextMenu
        v-model:show="show"
        :options="optionsComponent"
    >
      <context-menu-item label="刷新节点" @click="refreshNode" v-if="contextMenu.includes('refresh')">
        <template #icon>
          <el-icon :size="15">
            <Refresh/>
          </el-icon>
        </template>
      </context-menu-item>
      <context-menu-item label="新增节点" @click="addNode" v-if="contextMenu.includes('add')">
        <template #icon>
          <el-icon :size="15">
            <Plus/>
          </el-icon>
        </template>
      </context-menu-item>
      <context-menu-item label="修改节点" :disabled="nodeData.name === '根资源组'" @click="updateNode"
                         v-if="contextMenu.includes('update')">
        <template #icon>
          <el-icon :size="15">
            <Edit/>
          </el-icon>
        </template>
      </context-menu-item>
      <context-menu-item label="删除节点" :disabled="nodeData.name === '根资源组'" @click="deleteNode"
                         v-if="contextMenu.includes('delete')">
        <template #icon>
          <el-icon :size="15">
            <Delete/>
          </el-icon>
        </template>
      </context-menu-item>

      <context-menu-item label="播放通道" :disabled="nodeData.name === '根资源组'" @click="playChannel"
                         v-if="contextMenu.includes('playChannel')">
        <template #icon>
          <el-icon :size="15">
            <VideoPlay/>
          </el-icon>
        </template>
      </context-menu-item>
      <context-menu-item label="修改位置" :disabled="nodeData.name === '根资源组'" @click="updatePosition"
                         v-if="contextMenu.includes('updatePosition')">
        <template #icon>
          <el-icon :size="15">
            <Place/>
          </el-icon>
        </template>
      </context-menu-item>
    </ContextMenu>

    <el-dialog :title="title" v-model="openGroup" width="500px" append-to-body draggable>
      <el-form ref="formGroupRef" :model="formGroup" :rules="rulesGroup" label-width="80px">
        <el-form-item label="节点编号" prop="deviceId">
          <el-input v-model="formGroup.deviceId" placeholder="请输入编码">
            <template #append>
              <el-button @click="buildDeviceIdCode(formGroup.deviceId)">生成</el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="节点名称" prop="name">
          <el-input v-model="formGroup.name" clearable></el-input>
        </el-form-item>
        <el-form-item label="行政区划" prop="civilCode">
          <el-input v-model="formGroup.civilCode">
            <template #append>
              <el-button @click="chooseCivilCodeFun(formGroup.civilCode)">选择</el-button>
            </template>
          </el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitFormGroup">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <ChannelCode ref="channelCodeRef" @handleOk="handleOk"></ChannelCode>

    <ChooseCivilCode ref="chooseCivilCodeRef" @onSubmit="gbCivilCodeOnSubmit"></ChooseCivilCode>


  </div>
</template>

<script setup name="GroupTree" lang="ts">
import ChannelCode from "../../components/common/channelCode.vue"
import ChooseCivilCode from "../../components/common/chooseCivilCode.vue"
import {ContextMenu, ContextMenuItem} from '@imengyu/vue3-context-menu';
import {addGroup, deleteGroup, queryForGroupQuery, queryForGroupTree, updateGroup,} from "@/api/qs/group.js";

const emit = defineEmits(['handleNodeClick', 'playChannel', 'updatePosition']);
const {proxy} = getCurrentInstance();
const propsTree = ref({
  label: 'name',
  children: 'children',
  disabled: 'disabled'
});
const searchStr = ref('');
const treeData = ref([]);
const nodeData = ref({});
const groupList = ref({});
const total = ref(0);
const show = ref(false);
const optionsComponent = ref({
  zIndex: 99999,
  minWidth: 230,
  x: 500,
  y: 200
});

const title = ref("");
const openGroup = ref(false);
const chooseId = ref('');
const defaultExpandedKeys = ref([])
const checked = ref(false)

const data = reactive({
  formGroup: {},
  rulesGroup: {
    name: [{required: true, message: "请输入节点名称", trigger: "blur"}],
    deviceId: [{required: true, message: "请选择节点编号", trigger: "change"}],
    civilCode: [{required: true, message: "请选择行政区划", trigger: "change"}],
  },
  queryParamsGroup: {
    pageNum: 1,
    pageSize: 10,
    query: null,
  }
});

const {formGroup, rulesGroup, queryParamsGroup} = toRefs(data);

const props = defineProps({
  hasDevice: Boolean,
  showIndex: {
    type: Boolean,
    default: true
  },
  showContextmenu: {
    type: Boolean,
    default: true
  },
  isContextmenu: {
    type: Boolean,
    default: false
  },
  contextMenu: {
    type: Array,
    default: ["refresh", "add", 'update', 'delete']
  }
});

onMounted(() => {
  chooseId.value = null
  getQueryForGroupTree()
})

function getQueryForGroupTree() {
  queryForGroupTree({
    query: searchStr.value,
    parent: null,
    hasDevice: props.hasDevice
  }).then((res) => {
    let data = [
      {
        id: null,
        name: "根资源组",
        children: [],
        disabled: true
      }
    ]
    data[0].children = proxy.handleTree(res.data, "id")
    treeData.value = data
    if (res.data && res.data.length > 0) {
      defaultExpandedKeys.value = []
      res.data.forEach(item => {
        defaultExpandedKeys.value.push(item.id)
      })
    }
  })
}

function getQueryForGroupQuery(val) {
  queryParamsGroup.value.query = val
  queryForGroupQuery(queryParamsGroup.value).then(((res) => {
    groupList.value = res.rows
    total.value = res.total
  }))
}


watch(searchStr, (val) => {
  if (val) {
    getQueryForGroupQuery(val)
  }
})

function refresh() {
  if (!searchStr.value) {
    getQueryForGroupTree()
  } else {
    getQueryForGroupQuery(searchStr.value)
  }
}

function handleNodeClick(data) {
  chooseId.value = data.name
  emit('handleNodeClick', data);
}

function onContextMenu(e, data) {
  e.preventDefault();
  nodeData.value = data
  show.value = true
  optionsComponent.value.x = e.x
  optionsComponent.value.y = e.y
}

function resetGroup() {
  formGroup.value = {
    deviceId: undefined,
    name: undefined,
    civilCode: undefined,
    businessGroup: "",
  };
  proxy.resetForm("formGroupRef");
}

function buildDeviceIdCode(deviceId) {
  let lockContent = formGroup.value.businessGroup ? "216" : "215"
  proxy.$refs["channelCodeRef"].openDialog(code => {

  }, deviceId, 5, lockContent);
}

function handleOk(code) {
  formGroup.value.deviceId = code
}

function chooseCivilCodeFun() {
  proxy.$refs["chooseCivilCodeRef"].openDialog(code => {

  });
}

function gbCivilCodeOnSubmit(data) {
  formGroup.value.civilCode = data;
}

/** 取消按钮 */
function cancel() {
  openGroup.value = false;
  resetGroup()
}

function submitFormGroup() {
  proxy.$refs["formGroupRef"].validate(valid => {
    if (valid) {
      if (formGroup.value.id) {
        updateGroup(formGroup.value).then(() => {
          proxy.$modal.msgSuccess("修改成功");
          openGroup.value = false;
          getQueryForGroupTree();
        })
      } else {
        addGroup(formGroup.value).then(() => {
          proxy.$modal.msgSuccess("新增成功");
          openGroup.value = false;
          getQueryForGroupTree();
        })
      }
    }
  })
}

async function loadNode(node, resolve) {
  if (node.level === 0) {
    return resolve([{
      treeId: "",
      deviceId: "",
      name: "根资源组",
      isLeaf: false,
      type: 0
    }]);
  } else if (node.level > 0) {
    if (node.data.leaf) {
      return resolve([])
    }
    let res = await queryForGroupTree({
      query: '',
      parent: node.data.id,
      leaf: false,
      hasDevice: props.hasDevice
    });

    let terr = [...proxy.handleTree(res.data, "id")]
    if (res.data && res.data.length > 0) {
      defaultExpandedKeys.value = []
      res.data.forEach(item => {
        defaultExpandedKeys.value.push(item.id)
      })
    }

    resolve(terr);
  } else {
    resolve([]);
  }
}

function addNode() {
  resetGroup()
  formGroup.value.parentId = nodeData.value.id
  formGroup.value.businessGroup = nodeData.value.businessGroup
  formGroup.value.parentDeviceId = nodeData.value.deviceId
  openGroup.value = true;
  title.value = "新增虚拟组织";
}

function updateNode() {
  resetGroup()
  formGroup.value = JSON.parse(JSON.stringify(nodeData.value))
  openGroup.value = true;
  title.value = "修改虚拟组织";
}

function deleteNode() {
  proxy.$modal.confirm('是否确认删除名称为"' + nodeData.value.name + '"的数据项?').then(function () {
    deleteGroup(nodeData.value.id).then(() => {
      getQueryForGroupTree()
      proxy.$modal.msgSuccess("删除成功");
    })
  })
}

function refreshNode() {
  refresh()
}

function playChannel() {
  emit('playChannel', nodeData.value);
}

function updatePosition() {
  emit('updatePosition', nodeData.value);
}

defineExpose({
  refresh
})
</script>

<style scoped>
.show-code-item {
  text-align: center;
  font-size: 3rem;
}

.custom-tree-node .el-radio__label {
  padding-left: 4px !important;
}

.flow-tree {
  overflow: auto;
  padding-top: 10px;
}

.flow-tree .vue-recycle-scroller__item-wrapper {
  height: 100%;
  overflow-x: auto;
}

.channel-list-li {
  height: 40px;
  align-items: center;
  cursor: pointer;
  display: grid;
  grid-template-columns: 26px 1fr;
  margin-bottom: 20px;
}
</style>