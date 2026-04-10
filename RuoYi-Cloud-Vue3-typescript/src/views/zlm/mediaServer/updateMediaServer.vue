<template>
  <div class="app-container">
    <el-form ref="mediaServerFormRef" :model="form" :rules="rules" label-width="150px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="IP" prop="ip">
            <el-input v-model="form.ip" placeholder="请输入服务器绑定的 IP 地址"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="HTTP" prop="httpPort">
            <el-input v-model="form.httpPort" placeholder="请输入HTTP 协议端口"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="密钥" prop="secret">
            <el-input v-model="form.secret" placeholder="请输入密钥"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="类型" prop="type">
            <el-select v-model="form.type">
              <el-option key="zlm" label="ZLMediaKit" value="zlm"></el-option>
              <!--            <el-option key="abl" label="ABLMediaServer" value="abl"></el-option>-->
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="RTMP_PORT" prop="rtmpPort">
            <el-input v-model="form.rtmpPort" placeholder="请输入媒体服务RTMP_PORT" clearable
                      :disabled="form.defaultServer"></el-input>
          </el-form-item>

        </el-col>
        <el-col :span="12">
          <el-form-item label="RTMPS PORT" prop="rtmpSslPort">
            <el-input v-model="form.rtmpSslPort" placeholder="请输入媒体服务RTMPS_PORT" clearable
                      :disabled="form.defaultServer"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="HOOK IP" prop="hookIp">
            <el-input v-model="form.hookIp" placeholder="请输入媒体服务HOOK_IP" clearable
                      :disabled="form.defaultServer"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="SDP IP" prop="sdpIp">
            <el-input v-model="form.sdpIp" placeholder="请输入媒体服务SDP_IP" clearable
                      :disabled="form.defaultServer"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="流IP" prop="streamIp">
            <el-input v-model="form.streamIp" placeholder="请输入媒体服务流IP" clearable
                      :disabled="form.defaultServer"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="自动配置媒体服务">
            <el-switch v-model="form.autoConfig" :disabled="form.defaultServer"></el-switch>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="HTTPS PORT" prop="httpSslPort">
            <el-input v-model="form.httpSslPort" placeholder="请输入媒体服务HTTPS_PORT" clearable
                      :disabled="form.defaultServer"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="收流端口">
            <el-input v-model="rtpPortRange1" placeholder="起始" @change="portRangeChange" clearable
                      style="width: 100px" prop="rtpPortRange1" :disabled="form.defaultServer"></el-input>
            -
            <el-input v-model="rtpPortRange2" placeholder="终止" @change="portRangeChange" clearable
                      style="width: 100px" prop="rtpPortRange2" :disabled="form.defaultServer"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="RTSP PORT" prop="rtspPort">
            <el-input v-model="form.rtspPort" placeholder="请输入媒体服务RTSP_PORT" clearable
                      :disabled="form.defaultServer"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="RTSPS PORT" prop="rtspSslPort">
            <el-input v-model="form.rtspSslPort" placeholder="请输入媒体服务RTSPS_PORT" clearable
                      :disabled="form.defaultServer"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item>
            <el-button type="primary" @click="submitForm">提 交</el-button>
            <el-button @click="cancel">取 消</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script setup name="UpdateMediaServer" lang="ts">
import {getMediaServer, saveMediaServer} from "../../../api/qs/zlm.js";
import {useRoute} from "vue-router";
import {MediaServer} from "@/types/api";

const route = useRoute();
const {proxy} = getCurrentInstance();

const rtpPortRange1 = ref(30000)
const rtpPortRange2 = ref(30500)
const sendRtpPortRange1 = ref(50000)
const sendRtpPortRange2 = ref(60000)

const isValidIp = (rule, value, callback) => { // 校验IP是否符合规则
  var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
  if (!reg.test(value)) {
    return callback(new Error('请输入有效的IP地址'))
  } else {
    callback()
  }
  return true
}
const isValidPort = (rule, value, callback) => { // 校验IP是否符合规则
  var reg = /^(([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-5]{2}[0-3][0-5]))$/
  if (!reg.test(value)) {
    return callback(new Error('请输入有效的端口号'))
  } else {
    callback()
  }
  return true
}

const data = reactive({
  form: {},
  rules: {
    ip: [{required: true, message: '请输入有效的IP地址', trigger: 'blur'}],
    httpPort: [{required: true, validator: isValidPort, message: '请输入有效的端口号', trigger: 'blur'}],
    secret: [{required: true, message: "请输入secret", trigger: "blur"}],
    httpSslPort: [{required: true, validator: isValidPort, message: '请输入有效的端口号', trigger: 'blur'}],
    recordAssistPort: [{required: true, validator: isValidPort, message: '请输入有效的端口号', trigger: 'blur'}],
    rtmpPort: [{required: true, validator: isValidPort, message: '请输入有效的端口号', trigger: 'blur'}],
    rtmpSslPort: [{required: true, validator: isValidPort, message: '请输入有效的端口号', trigger: 'blur'}],
    rtpPortRange1: [{required: true, validator: isValidPort, message: '请输入有效的端口号', trigger: 'blur'}],
    rtpPortRange2: [{required: true, validator: isValidPort, message: '请输入有效的端口号', trigger: 'blur'}],
    rtpProxyPort: [{required: true, validator: isValidPort, message: '请输入有效的端口号', trigger: 'blur'}],
    rtspPort: [{required: true, validator: isValidPort, message: '请输入有效的端口号', trigger: 'blur'}],
    rtspSslPort: [{required: true, validator: isValidPort, message: '请输入有效的端口号', trigger: 'blur'}],
    timeout_ms: [{required: true, message: "请输入FFmpeg推流成功超时时间", trigger: "blur"}],
    ffmpeg_cmd_key: [{required: false, message: "请输入FFmpeg命令参数模板（可选）", trigger: "blur"}],
  },
});

const {form, rules} = toRefs(data);

// 取消按钮
function cancel() {
  reset();
  proxy.$tab.closeOpenPage({path: "/mediaServer"});
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    ip: '',
    httpPort: '',
    secret: '',
    type: null,
    autoConfig: true,
    hookIp: null,
    sdpIp: null,
    streamIp: null,
    httpSslPort: null,
    recordAssistPort: null,
    rtmpPort: null,
    rtmpSslPort: null,
    rtpEnable: false,
    rtpPortRange: null,
    sendRtpPortRange: null,
    rtpProxyPort: null,
    rtspPort: null,
    rtspSslPort: null,
  } as MediaServer;
  proxy.resetForm("mediaServerFormRef");
}

function portRangeChange() {
  if (form.value.rtpEnable) {
    form.value.rtpPortRange = rtpPortRange1.value + "," + rtpPortRange2.value
    form.value.sendRtpPortRange = sendRtpPortRange1.value + "," + sendRtpPortRange2.value
  }
}

function submitForm() {
  proxy.$refs["mediaServerFormRef"].validate(valid => {
    if (valid) {
      saveMediaServer(form.value).then(response => {
        proxy.$modal.msgSuccess("保存成功");
        cancel();
      })
    }
  })
}

onMounted(() => {
  let id = route.query && route.query.id;
  reset();
  getMediaServer(id).then(response => {
    form.value = response.data
    rtpPortRange1.value = parseInt(form.value.rtpPortRange.split(",")[0])
    rtpPortRange2.value = parseInt(form.value.rtpPortRange.split(",")[1])
  })

})
</script>

<style scoped>

</style>
