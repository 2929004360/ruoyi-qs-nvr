<template>
  <div class="app-container">
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
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
              v-for="dict in qs_status"
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
            icon="Plus"
            @click="handleAdd"
            v-hasPermi="['qs:device:add']"
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
            v-hasPermi="['qs:device:edit']"
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
            v-hasPermi="['qs:device:remove']"
        >删除
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="deviceList" @selection-change="handleSelectionChange" border>
      <el-table-column type="selection" width="55" align="center" fixed/>
      <el-table-column label="编号" align="center" prop="id" width="60" fixed/>
      <el-table-column label="设备名称" align="center" prop="deviceName" fixed/>
      <el-table-column label="IP地址" align="center" prop="ipAddress"/>
      <el-table-column label="接入类型" align="center" prop="type" width="100">
        <template #default="scope">
          <dict-tag :options="qs_live_stream_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="直播流地址" align="center" prop="liveAddress" min-width="180">
        <template #default="scope">
          <div v-if="scope.row.liveAddress">
            <span>{{ scope.row.liveAddress }}</span>
            <el-button link type="primary" @click="handleCopy(scope.row.liveAddress)">复制</el-button>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="通道号" align="center" prop="channel" width="80"/>
      <el-table-column label="截图" align="center" prop="snap" width="150">
        <template #default="scope">
          <image-preview v-if="scope.row.snap" :src="scope.row.snap" :width="100" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template #default="scope">
          <el-switch
              v-model="scope.row.status"
              active-value="ENABLE"
              inactive-value="DEACTIVATE"
              @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="设备状态" align="center" prop="deviceStatus" width="100">
        <template #default="scope">
          <dict-tag :options="qs_device_status" :value="scope.row.deviceStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" width="180"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150" fixed="right">
        <template #default="scope">
          <el-button link
                     type="primary"
                     icon="VideoPlay"
                     @click="handlePlay(scope.row)"
                     v-hasPermi="['qs:device:play']"
                     :loading="scope.row.loading"
                     v-if="scope.row.deviceStatus === 'ON'"
          >
            播放
          </el-button>
          <el-button link
                     v-if="scope.row.streamStatus === '1'
                     && (scope.row.type === '1'
                     || scope.row.type === '2'
                     || scope.row.type === '3'
                     || scope.row.type === '4'
                     || scope.row.type === '7'
                     || scope.row.type === '8'
                     || scope.row.type === '9'
                     )"
                     type="danger"
                     icon="SwitchButton"
                     @click="handleStopPlay(scope.row)"
                     v-hasPermi="['qs:device:play']"
          >
            停止
          </el-button>
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['qs:device:edit']">
            修改
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['qs:device:remove']">删除
          </el-button>
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

    <!-- 添加或修改视频监控设备对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form ref="deviceRef" :model="form" :rules="rules" label-width="140px">
        <el-form-item label="直播流接入类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择直播流接入类型" @change="liveStreamChange" filterable>
            <el-option
                v-for="dict in qs_live_stream_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="设备名称" prop="deviceName">
          <el-input v-model="form.deviceName" placeholder="请输入设备名称" :maxlength="100" show-word-limit/>
        </el-form-item>
        <el-form-item label="直播流地址"
                      prop="liveAddress"
                      v-if="form.type === '1' || form.type === '2' || form.type === '3' || form.type === '4'"
        >
          <el-input v-model="form.liveAddress" placeholder="请输入直播流地址" :maxlength="1024" show-word-limit/>
        </el-form-item>
        <el-form-item label="视频文件" prop="liveAddress"
                      v-if="form.type === '6'"
        >
          <file-upload
              v-model="form.liveAddress"
              :fileType="['mp4']"
              :limit="1"
              :fileSize="1204"
          />
        </el-form-item>

        <el-form-item label="上线类型" prop="onlineType" v-if="form.type === '9'">
          <el-radio-group v-model="form.onlineType" @change="onlineTypeChange">
            <el-radio
                v-for="dict in qs_online_type"
                :key="dict.value"
                :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="IP地址" prop="ipAddress"
                      v-if="form.type === '7' || (form.type === '9' && form.onlineType === '1')">
          <el-input v-model="form.ipAddress" placeholder="请输入IP地址" :maxlength="50" show-word-limit/>
        </el-form-item>
        <el-form-item label="端口号" prop="port"
                      v-if="form.type === '7' || (form.type === '9' && form.onlineType === '1')">
          <el-input v-model="form.port" placeholder="请输入端口号" disabled :maxlength="10" show-word-limit/>
        </el-form-item>
        <el-form-item label="用户名" prop="userName" v-if="form.type === '7' || (form.type === '9' )">
          <el-input v-model="form.userName" placeholder="请输入用户名" :maxlength="64" show-word-limit/>
        </el-form-item>
        <el-form-item label="密码" prop="password" v-if="form.type === '7' || (form.type === '9' )">
          <el-input v-model="form.password" placeholder="请输入密码" :maxlength="128" show-word-limit/>
        </el-form-item>

        <el-form-item label="海康ISUP设备ID" prop="deviceCode" v-if="form.type === '8'">
          <el-select v-model="form.deviceCode" @change="haikangIsupDeviceCodeChange" placeholder="请选择海康ISUP设备ID"
                     filterable>
            <el-option
                v-for="item in haiKangIsupDeviceList"
                :key="item.deviceId"
                :label="item.deviceId"
                :value="item.deviceId"
            >
              <span style="float: left">{{ item.deviceId }}</span>
              <span
                  style="
                  float: right;
                  color: var(--el-text-color-secondary);
                  font-size: 13px;
                "
              >
                {{ item.ip }}
              </span>
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="大华设备ID" prop="deviceCode" v-if="form.type === '9' && form.onlineType === '2'">
          <el-select v-model="form.deviceCode" @change="dahuaDeviceCodeChange" placeholder="请选择大华设备ID"
                     filterable>
            <el-option
                v-for="item in dahuaDeviceList"
                :key="item.deviceId"
                :label="item.deviceId"
                :value="item.deviceId"
            >
              <span style="float: left">{{ item.deviceId }}</span>
              <span
                  style="
                  float: right;
                  color: var(--el-text-color-secondary);
                  font-size: 13px;
                "
              >
                {{ item.ip }}
              </span>
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="通道号" prop="channel" v-if="form.type === '7' || form.type === '8' || form.type === '9'">
          <el-input v-model="form.channel" placeholder="请输入通道号" @input="handleNumberInput" :maxlength="5"
                    show-word-limit/>
        </el-form-item>

        <el-form-item label="码流类型" prop="streamType"
                      v-if="form.type === '7' || form.type === '8' || form.type === '9'">
          <el-radio-group v-model="form.streamType">
            <el-radio
                v-for="dict in qs_stream_type"
                :key="dict.value"
                :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="开启音频"
                      prop="enableAudio"
                      v-if="form.type === '1' || form.type === '2' || form.type === '3' || form.type === '4' || form.type === '6'"
        >
          <el-radio-group v-model="form.enableAudio">
            <el-radio label="0">关闭</el-radio>
            <el-radio label="1">开启</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="开启mp4录制"
                      prop="enableMp4"
                      v-if="form.type === '1' || form.type === '2' || form.type === '3' || form.type === '4' || form.type === '6'"
        >
          <el-radio-group v-model="form.enableMp4">
            <el-radio label="0">关闭</el-radio>
            <el-radio label="1">开启</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="传输协议" prop="protocol">
          <el-radio-group v-model="form.protocol">
            <el-radio
                v-for="dict in qs_protocol"
                :key="dict.value"
                :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="经度" prop="longitude">
          <el-input v-model="form.longitude" placeholder="请输入经度" @input="handleNumberInput" :maxlength="20"
                    show-word-limit/>
        </el-form-item>
        <el-form-item label="纬度" prop="latitude">
          <el-input v-model="form.latitude" placeholder="请输入纬度" @input="handleNumberInput" :maxlength="20"
                    show-word-limit/>
        </el-form-item>
        <el-form-item label="国标编码" prop="gbCode">
          <el-input v-model="form.gbCode" placeholder="请输入国标编码" @input="handleNumberInput" :maxlength="100"
                    show-word-limit/>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
                v-for="dict in qs_status"
                :key="dict.value"
                :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="无人观看"
                      prop="enableDisableNoneReader"
                      v-if="form.type === '1'
                      || form.type === '2'
                      || form.type === '3'
                      || form.type === '4'
                      || form.type === '7'
                      || form.type === '8'
                      || form.type === '9'
                      || form.type === '10'
                      || form.type === '11'
"
        >
          <el-radio-group v-model="form.enableDisableNoneReader">
            <el-radio label="0">不处理</el-radio>
            <el-radio label="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" :maxlength="255" show-word-limit/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog :title="`视频播放-${deviceRow.deviceName}`"
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
            :hasAudio="deviceRow.enableAudio === '1'"
            :isMute="deviceRow.enableAudio === '1'"
            :isLive="isLive"
            :videoUrl="wsUrl"/>
      </div>

      <el-tabs v-model="tabActiveName"
               type="card"
               :stretch="true"
               style="margin-top: 10px;"
               v-if="
               deviceRow.type === '1'
               || deviceRow.type === '2'
               || deviceRow.type === '3'
               || deviceRow.type === '4'
               || deviceRow.type === '7'
               || deviceRow.type === '8'
               || deviceRow.type === '9'
">
        <el-tab-pane label="实时视频" name="media">
          <el-row :gutter="10">
            <el-col :span="3"><span style="width: 80px; line-height: 40px; text-align: right;">播放地址：</span></el-col>
            <el-col :span="21">
              <el-input v-model="flvUrl" :disabled="true" style="margin-top: 10px">
                <template #prepend>flv地址</template>
                <template #append>
                  <el-button type="primary" :icon="DocumentCopy" @click="handleCopy(flvUrl)"/>
                </template>
              </el-input>
              <el-input v-model="wsUrl" :disabled="true" style="margin-top: 10px">
                <template #prepend>wsUrl地址</template>
                <template #append>
                  <el-button type="primary" :icon="DocumentCopy" @click="handleCopy(wsUrl)"/>
                </template>
              </el-input>
            </el-col>
          </el-row>

          <el-row :gutter="10" style="margin-top: 10px">
            <el-col :span="3"><span style="width: 80px; line-height: 40px; text-align: right;">资源地址：</span></el-col>
            <el-col :span="21">
              <el-input v-model="rtcUrl" :disabled="true">
                <template #prepend>
                  <StreamDropdown :stream-info="streamInfo"/>
                </template>
                <template #append>
                  <el-button type="primary" :icon="DocumentCopy" @click="handleCopy(wsUrl)"/>
                </template>
              </el-input>
            </el-col>
          </el-row>
        </el-tab-pane>
        <!--        <el-tab-pane label="编码信息" name="codec">-->
        <!--          <MediaInfo v-if="tabActiveName === 'codec' && streamInfo" ref="mediaInfo" :app="streamInfo.app"-->
        <!--                     :stream="streamInfo.stream" :mediaServerId="streamInfo.mediaServerId"></MediaInfo>-->
        <!--        </el-tab-pane>-->
      </el-tabs>
    </el-dialog>

  </div>
</template>

<script setup lang="ts" name="Device">
import useClipboard from "vue-clipboard3";
import EasyPlayer from "@/components/EasyPlayer";
import type {DeviceQueryParams, QsDevice} from "@/types/api/qs/device"
import {addDevice, changeDeviceStatus, delDevice, getDevice, listDevice, updateDevice} from "@/api/qs/device"
import {listHaiKangIsupDevice} from "@/api/qs/haikang-isup";
import {HaikangIsupDevice, PullConfig, RTPServerParam, Snap} from "@/types/api";
import {DaHuaDevice} from "@/types/api/qs/dahua";
import {listDaHusDevice} from "@/api/qs/dahua";
import {getSnap, rtpPlay, stopRtpPlay, stopStreamPullPlay, streamPullPlay} from "@/api/qs/zlm";
import {DocumentCopy} from '@element-plus/icons-vue'
import StreamDropdown from "@/components/Channel/streamDropdown.vue";

const {toClipboard} = useClipboard()

const {proxy} = getCurrentInstance()
const {
  qs_status,
  qs_live_stream_type,
  qs_stream_type,
  qs_protocol,
  qs_device_status,
  qs_online_type,
} = proxy.useDict('qs_status', 'qs_live_stream_type', 'qs_stream_type', 'qs_protocol', 'qs_device_status', 'qs_online_type')

const deviceList = ref<QsDevice[]>([])
const open = ref<boolean>(false)
const loading = ref<boolean>(true)
const showSearch = ref<boolean>(true)
const ids = ref<number[]>([])
const single = ref<boolean>(true)
const multiple = ref<boolean>(true)
const total = ref<number>(0)
const title = ref<string>("")

const haiKangIsupDeviceList = ref<HaikangIsupDevice[]>([])
const dahuaDeviceList = ref<DaHuaDevice[]>([])

// 播放
const easyPlayerOpen = ref(false)
const deviceRow = ref({})
const rtcUrl = ref("");
const flvUrl = ref("");
const wsUrl = ref('');
const tabActiveName = ref('media');
const streamInfo = ref({});
const quality = ref(['普清', '高清', '超清']);
const defaultQuality = ref('高清');
const isPtz = ref(true);
const isQuality = ref(true);
const isLive = ref(true);

const data = reactive({
  form: {} as QsDevice,
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    deviceName: undefined,
    ipAddress: undefined,
    type: undefined,
    status: undefined,
    deviceStatus: undefined,
  } as DeviceQueryParams,
  rules: {
    deviceName: [
      {required: true, message: "设备名称不能为空", trigger: "blur"}
    ],
    ipAddress: [
      {required: true, message: "IP地址不能为空", trigger: "blur"}
    ],
    liveAddress: [
      {required: true, message: "直播流地址不能为空", trigger: "blur"}
    ],
    port: [
      {required: true, message: "端口号不能为空", trigger: "blur"}
    ],
    userName: [
      {required: true, message: "用户名不能为空", trigger: "blur"}
    ],
    password: [
      {required: true, message: "密码不能为空", trigger: "blur"}
    ],
    fileAddress: [
      {required: true, message: "视频文件不能为空", trigger: "blur"}
    ],
    channel: [
      {required: true, message: "通道号不能为空", trigger: "blur"}
    ],
    deviceCode: [
      {required: true, message: "设备唯一标识不能为空", trigger: "blur"}
    ],
  }
})

const {queryParams, form, rules} = toRefs(data)

/** 查询视频监控设备列表 */
function getList() {
  loading.value = true
  queryParams.value.params = {}
  listDevice(queryParams.value).then(response => {
    deviceList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

// 取消按钮
function cancel() {
  open.value = false
  reset()
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    deviceCode: null,
    deviceName: null,
    ipAddress: null,
    port: null,
    userName: null,
    password: null,
    type: "1",
    deviceType: null,
    onlineType: null,
    channel: null,
    alarmChannelId: null,
    status: "ENABLE",
    streamType: "1",
    protocol: "TCP",
    enableAudio: "0",
    enableMp4: "0",
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null
  }
  proxy.resetForm("deviceRef")
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
function handleSelectionChange(selection: QsDevice[]) {
  ids.value = selection.map(item => item.id)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加视频监控设备"
}

/** 修改按钮操作 */
function handleUpdate(row: QsDevice) {
  reset()
  const _id = row.id || ids.value[0]
  getDevice(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改视频监控设备"

    // 海康isup
    if (form.value.type === '8') {
      listHaiKangIsupDevice().then((res) => {
        haiKangIsupDeviceList.value = res.data
      })
    }

    // 大华主动上线
    if (form.value.type === '9' && form.value.onlineType === '2') {
      listDaHusDevice().then((res) => {
        res.data.forEach((item) => {
          item.deviceId = "dahua_" + item.deviceId
        })
        dahuaDeviceList.value = res.data
      })
    }
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["deviceRef"].validate((valid: boolean) => {
    if (valid) {
      if (form.value.id != null) {
        updateDevice(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addDevice(form.value).then(() => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row: QsDevice) {
  const _ids = row.id || ids.value
  proxy.$modal.confirm('是否确认删除视频监控设备编号为"' + _ids + '"的数据项？').then(function () {
    return delDevice(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {
  })
}

/**
 * 复制内容到粘贴板
 *
 * @param text
 */
const handleCopy = async (text: string) => {
  if (!text) {
    proxy.$modal.msgError('内容为空，无法复制');
    return;
  }

  try {
    await toClipboard(text)
    proxy.$modal.msgSuccess('成功拷贝到粘贴板');
  } catch (e) {
    console.error(e)
  }
};

/**
 * 直播流接入类型
 *
 * @param text
 */
const liveStreamChange = (e: string) => {
  form.value.deviceCode = null
  form.value.deviceName = null
  form.value.ipAddress = null
  form.value.port = null
  form.value.userName = null
  form.value.password = null
  form.value.channel = null

  // 海康sdk
  if (e === '7') {
    form.value.port = '8000';
    return
  }

  // 海康isup
  if (e === '8') {
    listHaiKangIsupDevice().then((res) => {
      haiKangIsupDeviceList.value = res.data
    })
  }

  // 海康sdk
  if (e === '9') {
    form.value.port = '37777';
    form.value.onlineType = '1';
    return
  }
}

/**
 * 直播流接入类型
 *
 * @param text
 */
const handleNumberInput = (val: string) => {
  form.value.channel = val.replace(/\D/g, '');
}

/** 状态修改  */
function handleStatusChange(row: QsDevice) {
  const text = row.status === "ENABLE" ? "启用" : "停用"
  proxy.$modal.confirm('确认要"' + text + '"该设备吗?').then(function () {
    return changeDeviceStatus(row.id!, row.status!)
  }).then(() => {
    proxy.$modal.msgSuccess(text + "成功")
  }).catch(function () {
    row.status = row.status === "DEACTIVATE" ? "ENABLE" : "DEACTIVATE"
  })
}

/**
 * 海康isup设备code改变
 *
 * @param e
 */
const haikangIsupDeviceCodeChange = (e: string) => {
  const device = haiKangIsupDeviceList.value.find(item => item.deviceId === e);
  form.value.ipAddress = device.ip
}

/**
 * 上线类型改变
 *
 * @param e
 */
const onlineTypeChange = (e: string) => {
  if (e === '2') {
    listDaHusDevice().then((res) => {
      res.data.forEach((item) => {
        item.deviceId = "dahua_" + item.deviceId
      })
      dahuaDeviceList.value = res.data
    })
  }
}

/**
 * 大华设备code改变
 *
 * @param e
 */
const dahuaDeviceCodeChange = (e: string) => {
  const device = dahuaDeviceList.value.find(item => item.deviceId === e);
  form.value.ipAddress = device.ip
  form.value.port = device.port
}

/**
 * 播放
 *
 * @param row
 */
const handlePlay = (row: QsDevice) => {
  row.loading = true
  // rtsp协议或者rtmp
  if (row.type === '1' || row.type === '2') {
    let data = {
      deviceId: row.id,
      app: 'rtsp',
      stream: row.deviceCode,
      url: row.liveAddress,
      enable_audio: false,
      enable_mp4: false,
      rtp_type: '1',
      timeOut: 10,
    } as PullConfig;

    if (row.type === '1') {
      data.app = "rtsp"
    } else {
      data.app = "rtmp"
    }

    if (row.protocol === 'UDP') {
      data.rtp_type = '0'
    } else if (row.protocol === 'TCP') {
      data.rtp_type = '1'
    }

    if (row.enableAudio === '1') {
      data.enable_audio = true
    }

    if (row.enableMp4 === '1') {
      data.enable_mp4 = true
    }

    streamPullPlay(data).then(async (res: any) => {
      await nextTick(async () => {
        if (location.protocol === "https:") {
          flvUrl.value = res.data.https_flv;
          rtcUrl.value = res.data.rtcs;
          wsUrl.value = res.data.wss_flv;
        } else {
          flvUrl.value = res.data.flv;
          rtcUrl.value = res.data.rtc;
          wsUrl.value = res.data.ws_flv;
        }

        streamInfo.value = res.data;
        quality.value = []
        defaultQuality.value = ''
        isPtz.value = false
        isQuality.value = false
        isLive.value = true
        deviceRow.value = row
        row.loading = false
        easyPlayerOpen.value = true
      })
    }).catch((err) => {
      row.loading = false
    })
  } else if (row.type === '3' || row.type === '4') {
    if ((row.type === '3' && row.flvType === 'flv') || row.type === '4') {
      let data = {
        deviceId: row.id,
        app: 'flv',
        stream: row.deviceCode,
        url: row.liveAddress,
        enable_audio: false,
        enable_mp4: false,
        rtp_type: '1',
        timeOut: 10,
      } as PullConfig;

      if (row.type === '3') {
        data.app = "flv"
      } else {
        data.app = "hls"
      }

      if (row.protocol === 'UDP') {
        data.rtp_type = '0'
      } else if (row.protocol === 'TCP') {
        data.rtp_type = '1'
      }

      if (row.enableAudio === '1') {
        data.enable_audio = true
      }

      if (row.enableMp4 === '1') {
        data.enable_mp4 = true
      }

      streamPullPlay(data).then(async (res: any) => {
        await nextTick(async () => {
          if (location.protocol === "https:") {
            flvUrl.value = res.data.https_flv;
            rtcUrl.value = res.data.rtcs;
            wsUrl.value = res.data.wss_flv;
          } else {
            flvUrl.value = res.data.flv;
            rtcUrl.value = res.data.rtc;
            wsUrl.value = res.data.ws_flv;
          }

          streamInfo.value = res.data;
          quality.value = []
          defaultQuality.value = ''
          isPtz.value = false
          isQuality.value = false
          isLive.value = true
          deviceRow.value = row
          row.loading = false
          easyPlayerOpen.value = true
        })
      }).catch((err) => {
        row.loading = false
      })
    } else {
      nextTick(async () => {
        wsUrl.value = row.liveAddress
        quality.value = []
        defaultQuality.value = ''
        isPtz.value = false
        isQuality.value = false
        isLive.value = true
        deviceRow.value = row
        row.loading = false
        easyPlayerOpen.value = true
      })

      let data = {
        app: "flv",
        stream: row.deviceCode,
        url: row.liveAddress,
      } as Snap;
      getSnap(data).then((res) => {
        updateDevice({
          snap: res.msg,
          id: row.id
        })
      })
    }
  } else if (row.type === '6') {
    nextTick(async () => {
      wsUrl.value = row.liveAddress
      quality.value = []
      defaultQuality.value = ''
      isPtz.value = false
      isQuality.value = false
      isLive.value = false
      deviceRow.value = row
      row.loading = false
      easyPlayerOpen.value = true

      let data = {
        app: "video_file",
        stream: row.deviceCode,
        url: row.liveAddress,
      } as Snap;
      getSnap(data).then((res) => {
        updateDevice({
          snap: res.msg,
          id: row.id
        })
      })
    })
  } else if (row.type === '7' || row.type === '8' || row.type === '9')  {
    let data = {
      app: "haikang",
      streamId: row.deviceCode,
      tcpMode: "0",
      type: row.type,
      id: row.id
    } as RTPServerParam;

    if(row.type === '7'){
      data.app = "haikang"
    }else if(row.type === '8'){
      data.app = "haikang_isup"
    }else if(row.type === '9'){
      data.app = "dahua"
    }

    rtpPlay(data).then(async (res: any) => {
      await nextTick(async () => {
        if (location.protocol === "https:") {
          flvUrl.value = res.data.https_flv;
          rtcUrl.value = res.data.rtcs;
          wsUrl.value = res.data.wss_flv;
        } else {
          flvUrl.value = res.data.flv;
          rtcUrl.value = res.data.rtc;
          wsUrl.value = res.data.ws_flv;
        }

        streamInfo.value = res.data;
        quality.value = []
        defaultQuality.value = ''
        isPtz.value = false
        isQuality.value = false
        isLive.value = true
        deviceRow.value = row
        row.loading = false
        easyPlayerOpen.value = true
      })
    }).catch((err) => {
      row.loading = false
    })
  }

}

/**
 * 停止播放
 */
const handleStopPlay = (row: QsDevice) => {
  if (row.type === '1' || row.type === '2' || row.type === '3' || row.type === '4') {
    let data = {
      deviceId: row.id,
      mediaServerId: row.mediaServerId,
      streamKey: row.streamKey,
    }
    stopStreamPullPlay(data).then((res) => {
      getList()
      proxy.$modal.msgSuccess("停止播放成功");
    })
  } else if (row.type === '7' || row.type === '8' || row.type === '9') {
    let data = {
      type: row.type,
      streamId: streamInfo.value.stream,
      id: row.id
    }
    stopRtpPlay(data).then((res) => {
      getList()
      proxy.$modal.msgSuccess("停止播放成功");
    })
  }
}


let timer = null
/**
 * 获取列表
 */
const startTimer = () => {
  // 清除可能存在的旧定时器，防止重复创建
  if (timer) clearInterval(timer);

  // 立即先执行一次
  getList();

  // 30000 毫秒 = 30 秒
  timer = setInterval(() => {
    getList();
  }, 30000);
};

startTimer()
</script>
