<template>
  <div>
    <el-dialog
        draggable
        title="选择地图位置"
        width="800"
        top="5re"
        :close-on-click-modal="false"
        v-model="showDialog"
        :destroy-on-close="true"
        @close="close()"
    >
      <div style="width: 100%;height: 500px" id="SelectMapPosition"></div>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="onSubmit">确 定</el-button>
          <el-button @click="close">取消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="SelectMapPosition" lang="ts">
import {defineEmits} from "vue";
import {ElMessage} from "element-plus";

const emit = defineEmits(['onSubmit']);

defineExpose({openDialog})

var map;
var zoom = 12;
var marker = null
const showDialog = ref(false);
const data = ref({
  lat: null,
  lng: null,
});

function openDialog(e) {
  showDialog.value = true;
  nextTick(() => {
    map = new T.Map('SelectMapPosition', {
      projection: 'EPSG:4326'
    });
    let center;
    if (e.lat || e.lng) {
      center = new T.LngLat(e.lng, e.lat, zoom);
    } else {
      center = new T.LngLat(116.39122, 39.90684, zoom);
    }
    //设置显示地图的中心点和级别
    map.centerAndZoom(center, zoom);

    map.addEventListener("click", mapClick);

    //创建版权控件对象
    // var copyControl = new T.Control.Copyright({position: T_ANCHOR_TOP_LEFT});
    // map.addControl(copyControl);
    // var bs = map.getBounds();   //返回地图可视区域
    // copyControl.addCopyright({
    //   id: 1,
    //   content: "<a href='https://www.tianditu.gov.cn' target='_blank' style='font-size:14px;background:yellow'>自定义的版权控件</a>",
    //   bounds: bs
    // });

    //创建缩放平移控件对象
    var control = new T.Control.Zoom();
    //添加缩放平移控件
    map.addControl(control);
    //创建比例尺控件对象
    var scale = new T.Control.Scale();
    //添加比例尺控件
    map.addControl(scale);

    var miniMap = new T.Control.OverviewMap({
      isOpen: true,
      size: new T.Point(150, 150)
    });
    map.addControl(miniMap);

    //创建对象
    var ctrl = new T.Control.MapType();
    //添加控件
    map.addControl(ctrl);

    var menu = new T.ContextMenu({
      width: 100
    });
    var txtMenuItem = [
      {
        text: '放大',
        callback: function () {
          map.zoomIn()
        }
      },
      {
        text: '缩小',
        callback: function () {
          map.zoomOut()
        }
      },
      {
        text: '查看全国',
        callback: function () {
          map.setZoom(4)
        }
      }
    ];

    for (var i = 0; i < txtMenuItem.length; i++) {
      //添加菜单项
      var menuItem = new T.MenuItem(txtMenuItem[i].text, txtMenuItem[i].callback);
      menu.addItem(menuItem);
      if (i == 1) {
        //添加分割线
        menu.addSeparator();
      }
    }
    //添加右键菜单
    map.addContextMenu(menu);

    if (e.lat || e.lng) {
      data.value.lat = e.lat
      data.value.lng = e.lng
      var icon = new T.Icon({
        iconUrl: "http://api.tianditu.gov.cn/img/map/markerA.png",
        iconSize: new T.Point(19, 27),
        iconAnchor: new T.Point(10, 25)
      });

      if (marker) {
        map.removeOverLay(marker);
      }
      //创建信息窗口对象
      marker = new T.Marker(new T.LngLat(e.lng, e.lat), {icon: icon});
      map.addOverLay(marker);
    } else {
      data.value.lat = null
      data.value.lng = null
    }
  })
}

function close() {
  showDialog.value = false;
}

function mapClick(e) {
  var icon = new T.Icon({
    iconUrl: "http://api.tianditu.gov.cn/img/map/markerA.png",
    iconSize: new T.Point(19, 27),
    iconAnchor: new T.Point(10, 25)
  });

  if (marker) {
    map.removeOverLay(marker);
  }
  //创建信息窗口对象
  marker = new T.Marker(new T.LngLat(e.lnglat.getLng(), e.lnglat.getLat()), {icon: icon});
  map.addOverLay(marker);
  data.value.lat = e.lnglat.getLat()
  data.value.lng = e.lnglat.getLng()
}

function onSubmit() {
  if (!data.value.lat || !data.value.lng) {
    ElMessage({
      message: '请选择位置',
      type: 'error',
    })
    return
  }
  close();
  emit('onSubmit', data.value);
}
</script>

<style scoped>

</style>