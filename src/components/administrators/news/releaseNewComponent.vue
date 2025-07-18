<template>
  <div class="form-container">
    <el-form ref="form" class="custom-form">
      <el-form-item class="form-item">
        <el-input placeholder="新闻标题" v-model="news.newTitle" style="font-size: larger;font-weight: bolder;"
          type="textarea" class="input-field">
        </el-input>
      </el-form-item>
      <el-form-item class="form-item">
        <el-date-picker value-format="YYYY-MM-DD" v-model="news.newTime" clearable type="date" placeholder="发布时间"
          class="input-field" />
        <el-divider direction="vertical" class="divider" />
        <el-input placeholder="发稿人" v-model="news.reporter" style="width: 40vh;" class="input-field" />
        <el-divider direction="vertical" class="divider" />
        <el-input placeholder="发布地址" v-model="news.address" style="width: 40vh;" class="input-field" />
      </el-form-item>
      <el-form-item class="form-item">
        <el-input placeholder="正文" v-model="news.newContent" type="textarea" :rows="20" class="input-field">
        </el-input>
      </el-form-item>
      <fileUploadComponent :uploadKey="'newImg'" />
      <!-- 示例按钮 -->
    </el-form>
  </div>
</template>
<script lang="ts" setup>
//导入pinia中的新闻数据
import { useNewStore } from '@/stores/news/newStore'
import { storeToRefs } from 'pinia'
const newStore = useNewStore()
const { news } = storeToRefs(newStore)
import fileUploadComponent from '@/components/universal/fileUploadComponent.vue';
</script>

<style scoped>
.form-container {
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 15px;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
  padding: 2vh;
  transition: box-shadow 0.3s ease, transform 0.3s ease;
}

.custom-form {
  padding: 20px;
}

.form-item {
  margin-bottom: 20px;
}

.input-field {
  border: 2px solid rgba(148.6, 212.3, 117.1, 0.5);
  border-radius: 5px;
  transition: all 0.3s ease;

  &::placeholder {
    color: rgba(0, 0, 0, 0.5);
  }

  &:focus {
    border-color: rgb(148.6, 212.3, 117.1);
    box-shadow: 0 0 5px rgba(148.6, 212.3, 117.1, 0.5);
  }
}

.divider {
  border-color: rgba(148.6, 212.3, 117.1, 0.3);
  margin: 0 10px;
}
</style>