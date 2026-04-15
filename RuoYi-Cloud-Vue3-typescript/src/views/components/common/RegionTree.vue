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
        :props="{label: 'name', children: 'children'}"
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
            <div v-if="isContextmenu" @contextmenu="onContextMenu($event,data)" style="display: flex;align-items: center">
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
      <ul v-if="regionList.length > 0" style="list-style: none; margin: 0; padding: 10px">
        <li v-for="item in regionList" :key="item.id" class="channel-list-li"
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
            v-model:page="queryParamsRegion.pageNum"
            v-model:limit="queryParamsRegion.pageSize"
            @pagination="getQueryForRegionQuery(searchStr)"
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

    <el-dialog :title="title" v-model="openRegion" draggable width="1000px" append-to-body>
      <el-tabs v-model="activeKeyRegion" style="padding: 0 1rem; margin: auto 0" @tab-click="getRegionList">
        <el-tab-pane name="0">
          <template #label>
            <div class="show-code-item">{{ allValRegion[0].val }}</div>
            <div style="text-align: center">{{ allValRegion[0].meaning }}</div>
          </template>
          <el-radio v-for="item in regionList" v-model="allValRegion[0].val" :key="item.deviceId" :name="item.name"
                    :label="item.deviceId" @change="deviceChange(item)" style="line-height: 2rem">
            {{ item.name }} - {{ item.deviceId }}
          </el-radio>
        </el-tab-pane>
        <el-tab-pane name="1">
          <template #label>
            <div class="show-code-item">{{ allValRegion[1].val ? allValRegion[1].val : "--" }}</div>
            <div style="text-align: center">{{ allValRegion[1].meaning }}</div>
          </template>
          <el-radio :key="-1" v-model="allValRegion[1].val" @change="deviceChange" label=""
                    style="line-height: 2rem">
            不添加
          </el-radio>
          <el-radio v-for="item in regionList" v-model="allValRegion[1].val" @change="deviceChange(item)"
                    :key="item.deviceId" :label="item.deviceId.substring(2)" style="line-height: 2rem">
            {{ item.name }} - {{ item.deviceId.substring(2) }}
          </el-radio>
        </el-tab-pane>
        <el-tab-pane name="2">
          <template #label>
            <div class="show-code-item">{{ allValRegion[2].val ? allValRegion[2].val : "--" }}</div>
            <div style="text-align: center">{{ allValRegion[2].meaning }}</div>
          </template>
          <el-radio :key="-1" label="" v-model="allValRegion[2].val" style="line-height: 2rem"
                    @change="deviceChange">
            不添加
          </el-radio>
          <el-radio v-for="item in regionList" v-model="allValRegion[2].val" @change="deviceChange(item)"
                    :key="item.deviceId" :label="item.deviceId.substring(4)" style="line-height: 2rem">
            {{ item.name }} - {{ item.deviceId.substring(4) }}
          </el-radio>
        </el-tab-pane>
        <el-tab-pane name="3">

          <template #label>
            <div class="show-code-item">{{ allValRegion[3].val ? allValRegion[3].val : "--" }}</div>
            <div style="text-align: center">{{ allValRegion[3].meaning }}</div>
          </template>
          <el-input
              style="width: 400px"
              type="text"
              placeholder="请手动输入基层接入单位编码,两位数字"
              v-model="allValRegion[3].val"
              maxlength="2"
              :disabled="allValRegion[3].lock"
              show-word-limit
              @input="deviceChange"
          >
          </el-input>
        </el-tab-pane>
      </el-tabs>
      <el-divider/>
      <el-form ref="formRegionRef" :model="formRegion" :rules="rulesRegion" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="名称" prop="name">
              <el-input v-model="formRegion.name" autocomplete="off" placeholder="请输入名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="编号" prop="deviceId">
              <el-input v-model="formRegion.deviceId" disabled autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitFormRegion">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="RegionTree" lang="ts">
import {ContextMenu, ContextMenuItem} from '@imengyu/vue3-context-menu';

import {
  addRegion,
  updateRegion,
  deleteRegion,
  getAllChild,
  queryForRegionTree,
  queryForRegionQuery,
} from "@/api/qs/region.js";

const emit = defineEmits(['handleNodeClick', 'playChannel', 'updatePosition']);
const {proxy} = getCurrentInstance();

const searchStr = ref('');
const treeData = ref([]);

const nodeData = ref({});
const regionList = ref({});
const total = ref(0);
const show = ref(false);
const optionsComponent = ref({
  zIndex: 99999,
  minWidth: 230,
  x: 500,
  y: 200
});

const title = ref("");
const openRegion = ref(false);
const chooseId = ref('');
const allValRegion = ref([]);
const activeKeyRegion = ref('0');
const defaultExpandedKeys = ref([])
const checked = ref(false)

const data = reactive({
  formRegion: {},
  rulesRegion: {
    name: [{required: true, message: "请输入节点名称", trigger: "blur"}],
    deviceId: [{required: true, message: "请选择节点编号", trigger: "change"}],
  },
  queryParamsRegion: {
    pageNum: 1,
    pageSize: 10,
    query: null,
  }
});

const {formRegion, rulesRegion, queryParamsRegion} = toRefs(data);

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
  activeKeyRegion.value = '0'
  chooseId.value = null
  getQueryForRegionTree()
})

function getQueryForRegionTree() {
  queryForRegionTree({
    query: searchStr.value,
    parent: null,
    hasDevice: props.hasDevice
  }).then((res) => {
    let data = [
      {
        name: "根资源组",
        children: []
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

function getQueryForRegionQuery(val) {
  queryParamsRegion.value.query = val
  queryForRegionQuery(queryParamsRegion.value).then(((res) => {
    regionList.value = res.rows
    total.value = res.total
  }))
}

watch(searchStr, (val) => {
  if (val) {
    getQueryForRegionQuery(val)
  }
})

function refresh() {
  if (!searchStr.value) {
    getQueryForRegionTree()
  } else {
    getQueryForRegionQuery(searchStr.value)
  }
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
    let res = await queryForRegionTree({
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

function onContextMenu(e, data) {
  e.preventDefault();
  nodeData.value = data
  show.value = true
  optionsComponent.value.x = e.x
  optionsComponent.value.y = e.y
}

function handleNodeClick(data) {
  chooseId.value = data.name
  emit('handleNodeClick', data);
}

function resetRegion() {
  formRegion.value = {
    deviceId: undefined,
    name: undefined,
  };
  proxy.resetForm("formRegionRef");
}

function queryChildList(parent) {
  getAllChild({parent: parent,}).then((res) => {
    regionList.value = res.data
  })
}

function getRegionList() {
  nextTick(() => {
    if (activeKeyRegion.value === '0') {
      queryChildList();
    } else if (activeKeyRegion.value === '1' || activeKeyRegion.value === '2') {
      let parent = ''
      if (activeKeyRegion.value === '1') {
        parent = allValRegion.value[0].val
      }
      if (activeKeyRegion.value === '2') {
        if (allValRegion.value[1].val === "") {
          parent = ""
        } else {
          parent = allValRegion.value[0].val + allValRegion.value[1].val
        }
      }

      if (activeKeyRegion.value !== '0' && parent === '') {
        proxy.$modal.msgError("请先选择上级行政区划");
      }
      if (parent !== "") {
        queryChildList(parent);
      } else {
        regionList.value = []
      }
    }
  })
}

function deviceChange(item) {
  nextTick(() => {
    let code = allValRegion.value[0].val

    if (allValRegion.value[1].val) {
      code += allValRegion.value[1].val
      if (allValRegion.value[2].val) {
        code += allValRegion.value[2].val
        if (allValRegion.value[3].val) {
          code += allValRegion.value[3].val
        }
      } else {
        allValRegion.value[3].val = ""
      }
    } else {
      allValRegion.value[2].val = ""
      allValRegion.value[3].val = ""
    }
    formRegion.value.deviceId = code
  })
}

/** 取消按钮 */
function cancel() {
  openRegion.value = false;
  resetRegion();
}

function addNode() {
  resetRegion()
  formRegion.value.parentId = nodeData.value.id
  if (nodeData.value.deviceId) {
    formRegion.value.deviceId = String(nodeData.value.deviceId).slice(0, -2)
  }
  formRegion.value.parentDeviceId = nodeData.value.deviceId
  openRegion.value = true
  title.value = "新增行政区划"
  getRegionList()
  allValRegion.value = [
    {
      id: [1, 2],
      meaning: '省级编码',
      val: '11',
      type: '中心编码',
      lock: false,
    },
    {
      id: [3, 4],
      meaning: '市级编码',
      val: '',
      type: '中心编码',
      lock: false,
    },
    {
      id: [5, 6],
      meaning: '区级编码',
      val: '',
      type: '中心编码',
      lock: false,
    },
    {
      id: [7, 8],
      meaning: '基层接入单位编码',
      val: '',
      type: '中心编码',
      lock: false,
    }
  ]

  activeKeyRegion.value = '0'
  if (formRegion.value.deviceId) {
    if (formRegion.value.deviceId.length >= 2 && allValRegion.value[0]) {
      allValRegion.value[0].val = formRegion.value.deviceId.substring(0, 2)
    }
    if (formRegion.value.deviceId.length >= 4 && allValRegion.value[1]) {
      allValRegion.value[1].val = formRegion.value.deviceId.substring(2, 4)
    }
    if (formRegion.value.deviceId.length >= 6 && allValRegion.value[2]) {
      allValRegion.value[2].val = formRegion.value.deviceId.substring(4, 6)
    }
    activeKeyRegion.value = '3'
  } else {
    if (formRegion.value.parentDeviceId) {
      if (formRegion.value.parentDeviceId.length >= 2) {
        allValRegion.value[0].val = formRegion.value.parentDeviceId.substring(0, 2)
        activeKeyRegion.value = "1"
      }
      if (formRegion.value.parentDeviceId.length >= 4) {
        allValRegion.value[1].val = formRegion.value.parentDeviceId.substring(2, 4)
        activeKeyRegion.value = "2"
      }

    }
  }
}

function submitFormRegion() {
  proxy.$refs["formRegionRef"].validate(valid => {
    if (valid) {
      if (formRegion.value.id) {
        updateRegion(formRegion.value).then(() => {
          proxy.$modal.msgSuccess("修改成功");
          openRegion.value = false;
          getQueryForRegionTree();
        })
      } else {
        addRegion(formRegion.value).then(() => {
          proxy.$modal.msgSuccess("新增成功");
          openRegion.value = false;
          getQueryForRegionTree();
        })
      }
    }
  })
}

function updateNode() {
  resetRegion()
  formRegion.value = JSON.parse(JSON.stringify(nodeData.value))
  openRegion.value = true
  title.value = "新增行政区划"
  getRegionList()
  allValRegion.value = [
    {
      id: [1, 2],
      meaning: '省级编码',
      val: '11',
      type: '中心编码',
      lock: false,
    },
    {
      id: [3, 4],
      meaning: '市级编码',
      val: '',
      type: '中心编码',
      lock: false,
    },
    {
      id: [5, 6],
      meaning: '区级编码',
      val: '',
      type: '中心编码',
      lock: false,
    },
    {
      id: [7, 8],
      meaning: '基层接入单位编码',
      val: '',
      type: '中心编码',
      lock: false,
    }
  ]

  activeKeyRegion.value = '0'
  if (formRegion.value.deviceId) {
    if (formRegion.value.deviceId.length >= 2 && allValRegion.value[0]) {
      allValRegion.value[0].val = formRegion.value.deviceId.substring(0, 2)
    }
    if (formRegion.value.deviceId.length >= 4 && allValRegion.value[1]) {
      allValRegion.value[1].val = formRegion.value.deviceId.substring(2, 4)
    }
    if (formRegion.value.deviceId.length >= 6 && allValRegion.value[2]) {
      allValRegion.value[2].val = formRegion.value.deviceId.substring(4, 6)
    }
    if (formRegion.value.deviceId.length === 8 && allValRegion.value[3]) {
      allValRegion.value[3].val = formRegion.value.deviceId.substring(6, 8)
    }
  } else {
    if (formRegion.value.parentDeviceId) {
      if (formRegion.value.parentDeviceId.length >= 2) {
        allValRegion.value[0].val = formRegion.value.parentDeviceId.substring(0, 2)
        activeKeyRegion.value = "1"
      }
      if (formRegion.value.parentDeviceId.length >= 4) {
        allValRegion.value[1].val = formRegion.value.parentDeviceId.substring(2, 4)
        activeKeyRegion.value = "2"
      }
      if (formRegion.value.parentDeviceId.length >= 6) {
        allValRegion.value[2].val = formRegion.value.parentDeviceId.substring(4, 6)
        activeKeyRegion.value = "3"
      }
    }
  }
}

function deleteNode() {
  proxy.$modal.confirm('是否确认删除名称为"' + nodeData.value.name + '"的数据项?').then(function () {
    deleteRegion(nodeData.value.id).then(() => {
      getQueryForRegionTree()
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
