<template>
    <el-form ref="form">
        <el-form-item>
            <el-input placeholder="新闻标题" v-model="news.newTitle" style="font-size: larger;font-weight: bolder;"
                type="textarea"></el-input>
        </el-form-item>
        <el-form-item><el-date-picker value-format="YYYY-MM-DD" v-model="news.newTime" clearable type="date"
                placeholder="发布时间" /><el-divider direction="vertical" />
            <el-input placeholder="发稿人" v-model="news.reporter" style="width: 30vh;" /><el-divider
                direction="vertical" />
            <el-input placeholder="发布地址" v-model="news.address" style="width: 30vh;" />
        </el-form-item>
        <el-form-item>
            <el-input placeholder="正文" v-model="news.newContent" type="textarea" :rows="20" />
        </el-form-item>
        <processFilesComponent :fileType="'news'" />
        <div v-if="mail==='admin'">
            <rejectionNewComponent :newId="news.newId" />
        </div>
    </el-form>
</template>
<script lang="ts" setup>
const mail=sessionStorage.getItem('mail')
//导入文件审核组件
import processFilesComponent from '@/components/universal/processFilesComponent.vue';
//导入驳回组件
import rejectionNewComponent from './rejectionNewComponent.vue';
//导入pinia新闻对象
import { useNewStore } from '@/stores/news/newStore'
import { storeToRefs } from 'pinia'
const newStore = useNewStore()
//将新闻对象转为响应式对象
const { news } = storeToRefs(newStore)
</script>