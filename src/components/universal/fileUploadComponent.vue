<template>
    <!-- 上传文件组件 -->
    <el-form-item>
        <el-upload :limit="4" v-model:file-list="fileList" :multiple="true" :drag="true" :auto-upload="false"
            list-type="picture-card" :on-preview="handlePictureCardPreview" :on-remove="handleRemove">
            <el-icon>
                <Plus />
            </el-icon>
        </el-upload>
        <el-dialog v-model="dialogVisible">
            <img w-full :src="dialogImageUrl" alt="Preview Image" />
        </el-dialog>
    </el-form-item>
    <el-form-item>
        <el-button type="primary" class="submit-button" @click="uploadFilesToServer">完成</el-button>
    </el-form-item>
</template>
<script lang="ts" setup>
//ref响应式对象
import { ref } from 'vue'
//el的自带方法，用于文件接收和上传
import { ElMessage, type UploadInstance, type UploadProps, type UploadUserFile } from 'element-plus'
//图片预览变量第一个显示图片,第二个默认隐藏预览组件
const dialogImageUrl = ref('')
const dialogVisible = ref(false)
//文件变量，可以同时上传多个文件，服务器带宽问题，最多支持四个，且单个不能大于200kb
const fileList = ref<UploadUserFile[]>([])
//删除已选择图片的钩子函数
const handleRemove: UploadProps['onRemove'] = (uploadFile, uploadFiles) => {
}
//预览图片的钩子函数
const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {
    dialogImageUrl.value = uploadFile.url!
    dialogVisible.value = true
}
//导入pinia中的新闻数据
import { useNewStore } from '@/stores/news/newStore'
import { storeToRefs } from 'pinia'
const newStore = useNewStore()
const { news } = storeToRefs(newStore)
import { verifyNewData, verifyActivityData, verifyDonationData, verifyHelpRecordData, getNowTime } from './user/tsMethods/tsMethods'
import { post } from '@/axiosConfig'
//uuid生产工具
import { v4 } from 'uuid'
//接收来自父组件的参数，用于告知该组件是什么类型的文件
const props = defineProps({
    uploadKey: String
})
// 自定义上传文件请求方法

//导入pinia用户数据
import { useUserDetailStore } from '@/stores/user/userDetailStore';
const userDetailStore = useUserDetailStore()
const { userDetail } = storeToRefs(userDetailStore)
//导入pinia活动数据
import { useActivityStore } from '@/stores/activity/activityStore';
const activityStore = useActivityStore()
const { activity } = storeToRefs(activityStore)


import { useDonationStore } from '@/stores/donation/donationStore';
const donationStore = useDonationStore()
const { donation } = storeToRefs(donationStore)

import { useActivityRecordStore } from '@/stores/activity/activityRecordStore';
import { verifyarrivalStateData } from '../administrators/donation/ts/donationStateAPI'
const activityRecordStore = useActivityRecordStore()
const { activityRecord } = storeToRefs(activityRecordStore)

import { useSearchStore } from '@/stores/universal/searchStore'
const searchStore=useSearchStore()
const {freshRecord,freshValue}=storeToRefs(searchStore);

import { useHelpRecordStore } from '@/stores/help/helpRecordStore'
const helpRecordStore=useHelpRecordStore()
const {helpRecord}=storeToRefs(helpRecordStore)
const uploadFilesToServer = async () => {
    const formData = new FormData();
    if (props.uploadKey === 'newImg') {
        if (userDetail.value.reportPermission !== 1&&sessionStorage.getItem("mail")!=='admin') {
            ElMessage("请等待管理员通过撰稿权限")
            return
        }
        news.value.newId = v4();
        news.value.mail = sessionStorage.getItem('mail') + '';
        // 判断新闻数据完整性，方法会自动提示给用户数据是否正确
        if (verifyNewData(news.value) === false) {
            return;
        }

        // 将文件数组添加到 FormData 中
        fileList.value.forEach((file: UploadUserFile) => {
            // 提取原生 File 对象
            if (file.raw) {
                formData.append('file', file.raw);
            }
        });
        formData.append('fileFolderName', String(news.value.newId));
        // 将 news 对象的属性逐个添加到 FormData 中
        for (const [key, value] of Object.entries(news.value)) {
            formData.append(key, String(value));
        }
        if (props.uploadKey === 'newImg') {
            formData.append('whatFile', 'news');
        }
        try {
            const res = await post('/news/upload', formData);
            ElMessage(res.message);
        } catch (error) {
            console.error('上传失败:', error);
            ElMessage.error('上传失败');
        }
    }
    if (props.uploadKey === 'activity') {
        if (verifyActivityData(activity.value) === false) {
            return
        }
        activity.value.activityId = v4()
        fileList.value.forEach((file: UploadUserFile) => {
            // 提取原生 File 对象
            if (file.raw) {
                formData.append('file', file.raw);
            }
        });
        for (const [key, value] of Object.entries(activity.value)) {
            formData.append(key, String(value));
        }
        try {
            const res = await post('/activity/addActivity', formData);
            ElMessage(res.message);
        } catch (error) {
            console.error('上传失败:', error);
            ElMessage.error('上传失败');
        }
    }
    if (props.uploadKey === 'donation') {
        if (userDetail.value.reportPermission !== 1) {
            ElMessage("请等待管理员通过撰稿权限")
            return
        }
        donation.value.donationId = v4();
        donation.value.mail = sessionStorage.getItem('mail') + '';
        // 判断新闻数据完整性，方法会自动提示给用户数据是否正确
        if (verifyDonationData(donation.value) === false) {
            return;
        }

        // 将文件数组添加到 FormData 中
        fileList.value.forEach((file: UploadUserFile) => {
            // 提取原生 File 对象
            if (file.raw) {
                formData.append('file', file.raw);
            }
        });
        // 将 news 对象的属性逐个添加到 FormData 中
        for (const [key, value] of Object.entries(donation.value)) {
            formData.append(key, String(value));
        }
        try {
            const res = await post('/donation/item', formData);
            ElMessage(res.message);
            newStore.clear()
        } catch (error) {
            console.error('上传失败:', error);
            ElMessage.error('上传失败');
        }
    }
    if (props.uploadKey === 'activityrecord') {
        activityRecord.value.activityRecordId=v4()
        console.log(activityRecord.value)
        if (verifyarrivalStateData(activityRecord.value) === false) {
            return
        }
        // 将文件数组添加到 FormData 中
        fileList.value.forEach((file: UploadUserFile) => {
            // 提取原生 File 对象
            if (file.raw) {
                formData.append('file', file.raw);
            }
        });
        // 将 news 对象的属性逐个添加到 FormData 中
        for (const [key, value] of Object.entries(activityRecord.value)) {
            formData.append(key, String(value));
        }
        try {
            const res = await post('/activityRecord/addRecord', formData);
            ElMessage(res.message);
            if(res.message==="成功"){
                activityRecordStore.clear()
                freshRecord.value=true
            }
        } catch (error) {
            console.error('上传失败:', error);
            ElMessage.error('上传失败');
        }
    }
    if (props.uploadKey === 'helprecord') {
        console.log(111)
        helpRecord.value.helpRecordId=v4()
        helpRecord.value.mail=sessionStorage.getItem('mail')+''
        helpRecord.value.recordDate=getNowTime()
        if (verifyHelpRecordData(helpRecord.value) === false) {
            return
        }
        // 将文件数组添加到 FormData 中
        fileList.value.forEach((file: UploadUserFile) => {
            // 提取原生 File 对象
            if (file.raw) {
                formData.append('file', file.raw);
            }
        });
        // 将 news 对象的属性逐个添加到 FormData 中
        for (const [key, value] of Object.entries(helpRecord.value)) {
            formData.append(key, String(value));
        }
        try {
            const res = await post('/helpRecord/addHelpRecord', formData);
            ElMessage(res.message);
            if(res.message==="成功"){
                activityRecordStore.clear()
                freshValue.value=true
            }
        } catch (error) {
            console.error('上传失败:', error);
            ElMessage.error('上传失败');
        }
    }
}
</script>
<style scoped>
.submit-button {
    background-color: rgb(148.6, 212.3, 117.1);
    width: 100%;
    border: none;
    color: black;
    font-weight: bold;
    transition: background-color 0.3s ease;

    &:hover {
        background-color: rgba(148.6, 212.3, 117.1, 0.8);
    }
}
</style>