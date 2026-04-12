<template>
  <div class="weektime">
    <div class="weektime-main">
      <div class="weektime-hd">
        <div class="weektime-hd-title">星期\时间</div>
        <div class="weektime-hd-con">
          <div class="weektime-hd-con-top">
            <div class="weektime-date-range">00:00 - 12:00</div>
            <div class="weektime-date-range">12:00 - 24:00</div>
          </div>
          <div class="weektime-hd-con-bottom">
            <span class="weektime-date-cell" v-for="hour in 24" :key="hour">{{ hour - 1 }}</span>
          </div>
        </div>
      </div>
      <div class="weektime-bd">
        <div class="week-body">
          <div v-for="week in weekDays" :key="week" class="week-item">{{ week }}</div>
        </div>
        <div class="time-body" @mousedown="handleMousedown" @mouseup="handleMouseup" @mousemove="handleMousemove">
          <el-tooltip
              v-for="(i, key) in weekTimes"
              :key="key"
              :content="tiptxt(key)"
              :show-after="800"
              placement="top"
              effect="dark"
          >
            <div
                :data-index="key"
                class="time-cell"
                :class="{
                active: list[key] === '1',
                'pre-active': preViewIndex.includes(key),
                disable: disableTimes.includes(key),
              }"
            ></div>
          </el-tooltip>
        </div>
      </div>
    </div>
    <div class="weektime-help">
      <div class="weektime-help-tx">
        <div class="weektime-help-bd">
          <span class="color-box"></span>
          <span class="text-box">未选</span>
          <span class="color-box color-active"></span>
          <span class="text-box">已选</span>
        </div>
        <div class="weektime-help-ft" @click="initList()">清空选择</div>
      </div>
      <div class="weektime-help-select">
        <div v-for="(week, key) in weekDays" :key="key">
          <div v-show="showTimeText[key]">
            <span class="weektime-help-week-tx">{{ week + '：' }}</span>
            <span>{{ showTimeText[key] }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup name="ByteWeekTimePicker" lang="ts">
import 'element-plus/dist/index.css';

const DayTimes = 24 * 2;

// 组件的属性
const props = defineProps({
  modelValue: String,
  startTime: Number,
  endTime: Number,
  customDisableTimes: Array,
});

// 组件内部的响应式数据
const isMove = ref(false);
const list = ref([]);
const weekTimes = 7 * DayTimes;
const weekDays = ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日'];
const timeTextList = ref([]);
const startIndex = ref(0);
const axis = ref({});
const preViewIndex = ref([]);
const showTimeText = ref([]);

// 计算属性
const disableTimes = computed(() => {
  if (Array.isArray(props.customDisableTimes) && props.customDisableTimes.every(num => typeof num === 'number')) {
    return props.customDisableTimes;
  }
  if (props.startTime > -1 && props.endTime > -1) {
    const disabled = [];
    for (let index = 0; index < weekTimes; index++) {
      const firstIdx = index % DayTimes;
      if (props.startTime > firstIdx || props.endTime < firstIdx) {
        disabled.push(index);
      }
    }
    return disabled;
  }
  return [];
});

// 方法
const tiptxt = (index) => {
  let timeIndex = index % DayTimes;
  let weekIndex = ~~(index / DayTimes);
  return `${weekDays[weekIndex]} ${timeTextList.value[timeIndex]}~${timeTextList.value[timeIndex + 1]}`;
};

const initTimeText = () => {
  let timeTextList = [],
      hours = [],
      minutes = ['00', '30'];
  for (let i = 0; i <= 24; i++) {
    i < 10 ? hours.push('0' + i) : hours.push(i.toString());
  }
  for (const hour of hours) {
    for (const minute of minutes) {
      timeTextList.push(`${hour}:${minute}`);
    }
  }
  return timeTextList;
};

const handleMousedown = (event) => {
  startIndex.value = event.target.getAttribute('data-index');
  if (disableTimes.value.includes(~~startIndex.value)) return;
  isMove.value = true;
  axis.value.startx = startIndex.value % DayTimes;
  axis.value.starty = ~~(startIndex.value / DayTimes);
};

const handleMouseup = (event) => {
  handleMousemove(event);
  resetMousemove();
};

const handleMousemove = (event) => {
  if (!isMove.value) return;
  let index = event.target.getAttribute('data-index');
  axis.value.endx = index % DayTimes;
  axis.value.endy = ~~(index / DayTimes);
  preViewIndex.value = getSelectIndex();
};

const resetMousemove = () => {
  if (!isMove.value) return;
  setSelectIndex(preViewIndex.value);
  isMove.value = false;
  axis.value = {};
  preViewIndex.value = [];
};

const getSelectIndex = () => {
  let indexList = [],
      newAxis = {
        startx: Math.min(axis.value.startx, axis.value.endx),
        starty: Math.min(axis.value.starty, axis.value.endy),
        endx: Math.max(axis.value.startx, axis.value.endx),
        endy: Math.max(axis.value.starty, axis.value.endy),
      };
  for (let y = newAxis.starty; y <= newAxis.endy; y++) {
    for (let x = newAxis.startx; x <= newAxis.endx; x++) {
      indexList.push(x + y * DayTimes);
    }
  }
  return indexList.filter(v => !disableTimes.value.includes(v));
};

const setSelectIndex = (indexList) => {
  if (!Array.isArray(indexList)) return;
  let listLength = indexList.length;
  let newData = list.value[startIndex.value] === '1' ? '0' : '1';
  for (let i = 0; i < listLength; i++) {
    list.value.splice(indexList[i], 1, newData);
  }
  const inputValue = list.value.join('');
  emit('update:modelValue', inputValue);
  showSelectTime(list.value);
};

const showSelectTime = (list) => {
  if (!Array.isArray(list)) return;
  let weeksSelect = [],
      listlength = list.length;
  showTimeText.value = [];
  if (listlength === 0) return;
  // 把336长度的list分成7组，每组48个
  for (var i = 0; i < listlength; i += DayTimes) {
    weeksSelect.push(list.slice(i, i + DayTimes));
  }
  weeksSelect.forEach((item) => {
    showTimeText.value.push(getTimeText(item));
  });
};

const getTimeText = (arrIndex) => {
  if (!Array.isArray(arrIndex)) return '';

  let timeLength = arrIndex.length;
  let isSelect = false;
  let timeText = '';
  arrIndex.forEach((value, index) => {
    if (value === '1') {
      if (!isSelect) {
        timeText += timeTextList.value[index];
        isSelect = true;
      }
      if (index === timeLength - 1) {
        timeText += '~' + timeTextList.value[index + 1] + '、';
      }
    } else {
      if (isSelect) {
        timeText += '~' + timeTextList.value[index] + '、';
        isSelect = false;
      }
    }
  });

  return timeText.slice(0, -1);
};

const initList = (value) => {
  let reg = new RegExp('^[01]{' + weekTimes + '}$');
  if (value && reg.test(value)) {
    list.value = value.split('');
    return showSelectTime(list.value);
  }
  list.value = new Array(weekTimes).fill('0');
  emit('update:modelValue', list.value.join(''));
  showSelectTime(list.value);
};


watch(() => props.modelValue, (newVal) => {
  timeTextList.value = initTimeText();
  initList(newVal);
});

onUnmounted(() => {
  document.removeEventListener('mouseup', resetMousemove);
});

// 定义组件的暴露事件
const emit = defineEmits(['update:modelValue']);
</script>

<style scoped>
div,
span,
p {
  margin: 0;
  padding: 0;
  border: 0;
  font-weight: normal;
  vertical-align: baseline;
  -webkit-tap-highlight-color: transparent;
  -ms-tap-highlight-color: transparent;
  -moz-box-sizing: border-box;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}

.weektime {
  width: 658px;
  font-size: 14px;
  line-height: 32px;
  color: #515a6e;
  user-select: none;
}

.weektime .weektime-main {
  border: 1px solid #dcdee2;
  position: relative;
}

.weektime .weektime-hd {
  display: flex;
  background: #f8f8f9;
}

.weektime .weektime-hd-title {
  display: flex;
  align-items: center;
  padding: 0 6px;
  width: 80px;
  height: 65px;
}

.weektime .weektime-hd-con {
  flex: 1;
  display: flex;
  -webkit-box-orient: vertical;
  flex-direction: column;
}

.weektime .weektime-hd-con-top {
  display: flex;
  border-bottom: 1px solid #dcdee2;
}

.weektime .weektime-date-range {
  width: 288px;
  height: 32px;
  line-height: 32px;
  text-align: center;
  border-left: 1px solid #dcdee2;
}

.weektime .weektime-hd-con-bottom {
  display: flex;
}

.weektime .weektime-date-cell {
  width: 24px;
  height: 32px;
  line-height: 32px;
  text-align: center;
  border-left: 1px solid #dcdee2;
}

.weektime .weektime-bd {
  display: flex;
}

.weektime .week-body {
  width: 80px;
  flex-shrink: 0;
}

.weektime .week-item {
  border-top: 1px solid #dcdee2;
  text-align: center;
  height: 30px;
  line-height: 30px;
}

.weektime .time-body {
  width: 576px;
  height: 210px;
  display: flex;
  flex-wrap: wrap;
  align-items: flex-start;
  position: relative;
}

.weektime .time-cell {
  position: relative;
  width: 12px;
  height: 30px;
  border-left: 1px solid #efefef;
  border-top: 1px solid #efefef;
  overflow: hidden;
  transition: all 0.3s ease;
  outline-width: 0;
}

.weektime .time-cell.active {
  background: #2d8cf0;
}

.weektime .time-cell.disable {
  cursor: no-drop;
}

.weektime .time-cell::after {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: transparent;
  opacity: 0.5;
  transition: all 866ms ease;
  z-index: 99999;
}

.weektime .pre-active::after {
  background: #113860;
}

.weektime .disable::after {
  background: #cccccc;
}

.time-area {
  width: 576px;
  height: 210px;
  position: absolute;
  top: 0;
  left: 0;
  z-index: 100;
  background: transparent;
}

.weektime .weektime-help {
  width: 658px;
  border: 1px solid #dcdee2;
  border-top: none;
  padding: 5px 15px;
}

.weektime .weektime-help-tx {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.weektime .weektime-help-week-tx {
  color: #999;
}

.weektime .weektime-help-bd {
  display: flex;
  align-items: center;
  -webkit-box-pack: start;
  -ms-flex-pack: start;
  justify-content: flex-start;
  padding: 4px 0;
}

.weektime .weektime-help .color-box {
  width: 14px;
  height: 20px;
  background: #fff;
  border: 1px solid #dddddd;
  display: block;
  margin-right: 6px;
}

.weektime .weektime-help-bd .color-box.color-active {
  background: #2d8cf0;
}

.weektime .weektime-help .text-box {
  margin-right: 15px;
}

.weektime .weektime-help .weektime-help-ft {
  color: #2d8cf0;
  cursor: pointer;
}
</style>
