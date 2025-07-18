<template>
  <el-form>
    <el-form-item >
      <el-input style="width: 700px;" v-model="thisSelKey" placeholder="新闻题目或撰稿人"></el-input>
      <el-divider direction="vertical" />
      <el-date-picker type="date" placeholder="发稿日期" v-model="thisSelNewTime"  value-format="YYYY-MM-DD" ></el-date-picker>
      <el-divider direction="vertical" />
      <el-button type="success" @click="search" style="width: 100px;">搜索</el-button>
    </el-form-item>
  </el-form>
</template>
<script lang="ts" setup>
import { ref } from 'vue';
//导入pinia中的查询数据
import { useSearchStore } from '@/stores/universal/searchStore';
import { storeToRefs } from 'pinia';
const searchStore = useSearchStore()
const { selKey, selNewTime } = storeToRefs(searchStore)
//搜索方法
const thisSelKey = ref('')
const thisSelNewTime = ref('')
const search = () => {
  selKey.value = thisSelKey.value
  selNewTime.value = thisSelNewTime.value
}
</script>

<style scoped>
/* 表单整体样式 */
.el-form {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  animation: fadeIn 0.5s ease-out;
}

@keyframes fadeIn {
  from {
      opacity: 0;
      transform: translateY(-20px);
  }
  to {
      opacity: 1;
      transform: translateY(0);
  }
}

/* 输入框样式 */
.el-input__inner {
  border: 2px solid black;
  border-radius: 8px;
  padding: 10px 15px;
  transition: all 0.3s ease;
}

.el-input__inner:hover,
.el-input__inner:focus {
  border-color: rgba(0, 0, 0, 0.7);
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

/* 日期选择器样式 */
.el-date-picker__editor {
  border: 2px solid black;
  border-radius: 8px;
  padding: 10px 15px;
  transition: all 0.3s ease;
}

.el-date-picker__editor:hover,
.el-date-picker__editor:focus {
  border-color: rgba(0, 0, 0, 0.7);
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

/* 分割线样式 */
.el-divider--vertical {
  background-color: black;
}

/* 搜索按钮样式 */
.el-button {
  background-color: black;
  color: rgb(148.6, 212.3, 117.1);
  border: none;
  border-radius: 8px;
  padding: 10px 15px;
  font-weight: bold;
  text-transform: uppercase;
  letter-spacing: 1px;
  transition: all 0.3s ease;
}

.el-button:hover {
  background-color:rgb(148.6, 212.3, 117.1);
  transform: scale(1.05);
  color: black;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.3);
}
</style>