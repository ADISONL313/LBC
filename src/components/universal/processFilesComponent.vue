<template>
    <!-- 上传文件组件 -->
    <el-form-item>
        <el-upload :limit="4" v-model:file-list="fileList" :multiple="true" :drag="true" :auto-upload="false"
            list-type="picture-card" :before-upload="handBeforeUpload" :on-preview="handlePictureCardPreview"
            :on-remove="handleRemove">
            <el-icon>
                <Plus />
            </el-icon>
        </el-upload>
        <el-dialog v-model="dialogVisible">
            <img w-full :src="dialogImageUrl" alt="Preview Image" />
        </el-dialog>
    </el-form-item>
    <el-form-item>
        <el-button style="width: 100%;" type="primary" class="submit-button" @click="uploadFilesToServer">修改</el-button>
    </el-form-item>
</template>
<script lang="ts" setup>
//导入pinia新闻对象
import { useNewStore } from '@/stores/news/newStore'
import { storeToRefs } from 'pinia'
const newStore = useNewStore()
import { useActivityStore } from '@/stores/activity/activityStore'
const activityStore = useActivityStore()
const { activity } = storeToRefs(activityStore)
//将新闻对象转为响应式对象
const { news } = storeToRefs(newStore)
import { onMounted, ref } from 'vue';

import { useDonationStore } from '@/stores/donation/donationStore';
const donationStore = useDonationStore()
const { donation } = storeToRefs(donationStore)

import { useActivityRecordStore } from '@/stores/activity/activityRecordStore'
const activityRecordStore = useActivityRecordStore()
const { activityRecord } = storeToRefs(activityRecordStore)
//el的自带方法，用于文件接收和上传
import { ElMessage, type UploadProps, type UploadUserFile } from 'element-plus'
import { verifyActivityData, verifyActivityRecord, verifyDonationData, verifyNewData } from './user/tsMethods/tsMethods';
import { deleteRemove, post } from '@/axiosConfig';
import { getActivityRecord } from '../administrators/activity/ts/adminActivityMethods'
//文件列表,响应式
const fileList = ref<UploadUserFile[]>([])
//接收父组件参数
const props = defineProps({
    fileType: String
})
//在页面挂载后把服务器的图片加载到组件
onMounted(async() => {
    //服务器基础地址
    let fileFolder = ''
    let imgArry:any
    if (props.fileType === 'news') {
        fileFolder = news.value.newId
        imgArry=news.value.newImg.split(';').filter(item=>item)
    }
    if (props.fileType === 'activity') {
        fileFolder = activity.value.activityId
        imgArry=activity.value.activityImg.split(';').filter(item=>item)
    }
    if (props.fileType === 'donation') {
        
        fileFolder = donation.value.donationId
        imgArry=donation.value.itemImg.split(';').filter(item=>item)
    }
    if (props.fileType === 'activityrecord') {
        const res=await getActivityRecord(activityRecord.value.activityId)
        activityRecord.value=res
        fileFolder = activityRecord.value?.activityRecordId
        imgArry=activityRecord.value?.recordImage.split(';').filter(item=>item)
    }
    const baseUrl = 'http://47.108.29.91:9000/' + props.fileType + '/' + fileFolder + "/"
    // const imgArry = props.fileType === 'news' ? news.value.newImg.split(';').filter(item => item) : props.fileType === 'activity' ? activity.value.activityImg.split(';').filter(item => item) : props.fileType === 'donation' ? donation.value.itemImg.split(';').filter(item => item) : activityRecord.value.recordImage?.split(';').filter(item => item)
    fileList.value = imgArry?.map((img: string) => ({ url: baseUrl + img, name: img }));
})
//文件上传之前的钩子函数
const handBeforeUpload: UploadProps['onProgress'] = (file: any) => {
    console.log(file)
}
//图片预览变量第一个显示图片,第二个默认隐藏预览组件
const dialogImageUrl = ref('')
const dialogVisible = ref(false)
//预览图片的钩子函数
const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {
    dialogImageUrl.value = uploadFile.url!
}
//删除已选择图片的钩子函数
const handleRemove: UploadProps['onRemove'] = (uploadFile, uploadFiles) => {
    if (props.fileType === 'news') {
        deleteRemove(`/news/delFile?fileId=${news.value.newId}&fileName=${uploadFile.name}`).then((res) => {
            ElMessage(res.message)
        })
    }
    if (props.fileType === 'activity') {
        deleteRemove(`/activity/delFile?fileId=${activity.value.activityId}&fileName=${uploadFile.name}`).then((res) => {
            ElMessage(res.message)
        })
    }
    if (props.fileType === 'donation') {
        deleteRemove(`/donation/delFile?fileId=${donation.value.donationId}&fileName=${uploadFile.name}`).then((res) => {
            ElMessage(res.message)
        })
    }
    if (props.fileType === 'activityrecord') {
        deleteRemove(`/activityRecord/delFile?fileId=${activityRecord.value.activityRecordId}&fileName=${uploadFile.name}`).then((res) => {
            ElMessage(res.message)
        })
    }
}
const mail = sessionStorage.getItem('mail')
const uploadFilesToServer = async () => {
    const formData = new FormData();
    if (props.fileType === 'news') {
        //上传文件到服务器
        news.value.mail = mail + ''
        console.log(mail)
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
        // 将 news 对象的属性逐个添加到 FormData 中
        for (const [key, value] of Object.entries(news.value)) {
            formData.append(key, String(value));
        }
        try {
            const res = await post('/news/updateNew', formData);
            ElMessage(res.message);
        } catch (error) {
            console.error('上传失败:', error);
            ElMessage.error('上传失败');
        }
    }
    if (props.fileType === 'activity') {
        // 判断新闻数据完整性，方法会自动提示给用户数据是否正确
        if (verifyActivityData(activity.value) === false) {
            return;
        }
        // 将文件数组添加到 FormData 中
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
            const res = await post('/activity/updateActivity', formData);
            ElMessage(res.message);
        } catch (error) {
            console.error('上传失败:', error);
            ElMessage.error('上传失败');
        }
    }
    if (props.fileType === 'donation') {
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
        for (const [key, value] of Object.entries(donation.value)) {
            formData.append(key, String(value));
        }
        try {
            const res = await post('/donation/updateItem', formData);
            ElMessage(res.message);
            newStore.clear()
        } catch (error) {
            console.error('上传失败:', error);
            ElMessage.error('上传失败');
        }
    }
    if (props.fileType === 'activityrecord') {
        // 判断新闻数据完整性，方法会自动提示给用户数据是否正确
        if (verifyActivityRecord(activityRecord.value) === false) {
            return
        }
        // 将文件数组添加到 FormData 中
        fileList.value.forEach((file: UploadUserFile) => {
            // 提取原生 File 对象
            if (file.raw) {
                formData.append('file', file.raw);
            }
        });
        for (const [key, value] of Object.entries(activityRecord.value)) {
            formData.append(key, String(value));
        }
        try {
            const res = await post('/activityRecord/addRecord', formData);
            ElMessage(res.message);
            newStore.clear()
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
    border: none;
    color: black;
    font-weight: bold;
    transition: background-color 0.3s ease;

    &:hover {
        background-color: rgba(148.6, 212.3, 117.1, 0.8);
    }
}
</style>