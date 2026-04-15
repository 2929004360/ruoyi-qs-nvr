<template>
  <div class="app-container">
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
      <right-toolbar :search="false" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-row :gutter="12">
      <el-col :xs="24" :sm="24" :md="12" :lg="6" v-for="item in wvpMediaServerList" :key="item.id">
        <el-card shadow="hover" class="server-card">
          <div v-if="item.type === 'zlm'" class="card-img-zlm"></div>
          <div v-if="item.type === 'abl'" class="card-img-abl"></div>
          <div style="padding: 10px;display: flex;justify-content: space-between;align-items: center">
            <div>
              <div style="font-size: 16px">{{ item.id }}</div>
              <div style="font-size: 14px; color: #999; margin-top: 5px; ">{{ item.ip }}</div>
              <div style="font-size: 14px; color: #999; margin-top: 5px; ">
                {{ item.status === "ON" ? '在线' : '离线' }}
              </div>
            </div>

            <div>
              <el-button type="text"
                         @click="handleView(item)">查看
              </el-button>
              <el-button type="text" v-if="!item.defaultServer"
                         @click="handleUpdate(item)">编辑
              </el-button>
              <el-button type="text" @click="handleDelete(item)" v-if="!item.defaultServer">移除
              </el-button>
              <el-button type="text" @click="handleRestartServer(item)">重启
              </el-button>
            </div>
          </div>
          <el-icon v-if="item.defaultServer" class="server-card-status-offline" color="#67C23A">
            <SuccessFilled/>
          </el-icon>
          <i v-if="item.defaultServer" class="server-card-default">默认</i>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog draggable title="媒体节点" v-model="openView" width="1000px" append-to-body>
      <el-descriptions border>
        <el-descriptions-item label="媒体服务IP">
          {{ rowData.ip }}
        </el-descriptions-item>
        <el-descriptions-item label="HTTP端口">
          {{ rowData.httpPort }}
        </el-descriptions-item>
        <el-descriptions-item label="SECRET">
          {{ rowData.secret }}
        </el-descriptions-item>
        <el-descriptions-item label="类型">
          <el-tag type="primary" v-if="rowData.type === 'zlm'">ZLMediaKit</el-tag>
          <el-tag type="primary" v-if="rowData.type === 'abl'">ABLMediaServer</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="媒体服务RTMP_PORT">
          {{ rowData.rtmpPort }}
        </el-descriptions-item>
        <el-descriptions-item label="媒体服务RTMPS_PORT">
          {{ rowData.rtmpSslPort }}
        </el-descriptions-item>
        <el-descriptions-item label="媒体服务HOOK_IP">
          {{ rowData.hookIp }}
        </el-descriptions-item>
        <el-descriptions-item label="媒体服务SDP_IP">
          {{ rowData.sdpIp }}
        </el-descriptions-item>
        <el-descriptions-item label="自动配置媒体服务">
          <el-tag type="primary" v-if="rowData.autoConfig">是</el-tag>
          <el-tag type="primary" v-if="!rowData.autoConfig">否</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="媒体服务流IP">
          {{ rowData.streamIp }}
        </el-descriptions-item>
        <el-descriptions-item label="收流端口模式">
          <el-tag type="primary" v-if="rowData.rtpEnable">多端口</el-tag>
          <el-tag type="primary" v-if="!rowData.rtpEnable">单端口</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="媒体服务HTTPS_PORT">
          {{ rowData.httpSslPort }}
        </el-descriptions-item>
        <el-descriptions-item label="收流端口">
          {{ rowData.rtpPortRange }}
        </el-descriptions-item>
        <el-descriptions-item label="媒体服务RTSP_PORT">
          {{ rowData.rtspPort }}
        </el-descriptions-item>
        <el-descriptions-item label="录像管理服务端口">
          {{ rowData.recordAssistPort }}
        </el-descriptions-item>
        <!--        <el-descriptions-item label="媒体服务RTSPS_PORT">-->
        <!--          {{ rowData.rtspSslPort }}-->
        <!--        </el-descriptions-item>-->
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup name="MediaServer" lang="ts">
import router from "@/router";
import {delMediaServer, getMediaServerList, restartServer} from "../../../api/qs/zlm.js";
import {MediaServer} from "@/types/api";

const {proxy} = getCurrentInstance();

const wvpMediaServerList = ref<MediaServer[]>([]);
const openView = ref(false);

const loading = ref(true);

const rowData = ref({});

/** 查询媒体服务器列表 */
function getList() {
  loading.value = true;
  getMediaServerList().then(response => {
    wvpMediaServerList.value = response.data;
    loading.value = false;
  });
}

/** 搜索按钮操作 */
function handleQuery() {
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

/** 新增按钮操作 */
function handleAdd() {
  router.push(`/mediaServer/addMediaServer/index`);
}

/** 修改按钮操作 */
function handleUpdate(row) {
  router.push({
    path: '/mediaServer/updateMediaServer/index',
    query: {id: row.id}
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id
  proxy.$modal.confirm('确认删除此节点？').then(function () {
    return delMediaServer(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/**
 * 查看按钮操作
 *
 * @param row
 */
function handleView(row) {
  openView.value = true
  rowData.value = row;
}

/** 删除按钮操作 */
function handleRestartServer(row) {
  const _ids = row.id
  proxy.$modal.confirm('重启服务器,只有 Daemon 方式才能重启，否则是直接关闭！是否继续？').then(function () {
    return restartServer(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("重启成功")
  }).catch(() => {
  })
}

getList();
</script>

<style lang="scss" scoped>
.server-card {
  position: relative;
  margin-bottom: 20px;
}

.card-img-zlm {
  width: 200px;
  height: 200px;
  background: url('../../../assets/images/zlm-logo.png') no-repeat center;
  background-position: center;
  background-size: contain;
  margin: 0 auto;
}

//.card-img-abl {
//  width: 200px;
//  height: 200px;
//  background: url('../../../assets/images/zlm-logo.png') no-repeat center;
//  background-position: center;
//  background-size: contain;
//  margin: 0 auto;
//}

.server-card-status-online {
  position: absolute;
  right: 20px;
  top: 20px;
  font-size: 18px;
}

.server-card-status-offline {
  position: absolute;
  right: 20px;
  top: 20px;
  font-size: 18px;
}

.server-card-default {
  position: absolute;
  left: 20px;
  top: 20px;
  color: #808080;
  font-size: 18px;
}

.server-card:hover {
  border: 1px solid #adadad;
}
</style>
