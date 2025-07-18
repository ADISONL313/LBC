<template>
    <div class="ds-container" v-if="donation.auditStatus === 1">
        <div class="ds-card-title">
            签收状态：{{
                donationState.logisticsState === 0 ? "等待签收" : "签收完成"
            }}
        </div>
        <div>
            <el-form>
                <el-form-item>
                    <textarea v-model="logisticsState.logisticsText" placeholder="备注"></textarea>
                </el-form-item>
                <el-form-item>
                    <el-date-picker v-model="logisticsState.logisticsTime" type="date" value-format="YYYY-MM-DD"
                        placeholder="收件日期"></el-date-picker>
                </el-form-item>
                <el-form-item>
                    <el-upload action="#" :auto-upload="false" v-model:file-list="fileList" list-type="picture-card"
                        :drag="true" :limit="1" :on-preview="handlePictureCardPreview" :on-remove="handleRemove">
                        <el-icon>
                            <Plus />
                        </el-icon>
                    </el-upload>

                    <el-dialog v-model="dialogVisible">
                        <img w-full :src="dialogImageUrl" alt="Preview Image" />
                    </el-dialog>
                </el-form-item>
                <el-form-item>
                    <el-button type="success" @click="updateLogisticsState">修改</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>
<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import { delStateImg } from './ts/donationStateAPI';
import { post } from '@/axiosConfig';
import { logisticsStateVerify } from '@/components/universal/user/tsMethods/tsMethods';
import { storeToRefs } from 'pinia';
import { useDonationStore } from '@/stores/donation/donationStore';
const donationStore = useDonationStore()
const { donation } = storeToRefs(donationStore)


import { useDonationStateStore } from '@/stores/donation/donationStateStore';
const donationStateStore = useDonationStateStore()
const { donationState } = storeToRefs(donationStateStore)

const logisticsState = ref(
    {
        logisticsState: 0,
        logisticsTime: '',
        logisticsImg: '',
        logisticsText: '',
        stateId: ''
    }
)

const baseUrl = 'http://47.108.29.91:9000/donationstate/'
import { ElMessage, type UploadProps, type UploadUserFile } from 'element-plus'
const fileList = ref<UploadUserFile[]>()

onMounted(async() => {
    logisticsState.value.logisticsImg=donationState.value.logisticsImg
    logisticsState.value.logisticsState=donationState.value.logisticsState
    logisticsState.value.logisticsText=donationState.value.logisticsText
    logisticsState.value.logisticsTime=donationState.value.logisticsTime
    logisticsState.value.stateId=donationState.value.stateId
    if (logisticsState.value.logisticsImg !== null && logisticsState.value.logisticsImg !== '') {
        fileList.value = [{ url: baseUrl + logisticsState.value.stateId + '/' + logisticsState.value.logisticsImg, name: logisticsState.value.logisticsImg }]
    }
})

const dialogImageUrl = ref('')
const dialogVisible = ref(false)

const handleRemove: UploadProps['onRemove'] = async(uploadFile, uploadFiles) => {
    console.log(uploadFile.name)
    const res:any=await delStateImg(logisticsState.value.stateId,uploadFile.name,'logistics')
    ElMessage(res.message)
}

const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {
    dialogImageUrl.value = uploadFile.url!
    dialogVisible.value = true
}

const updateLogisticsState = async () => {
    const formData = new FormData();
    logisticsState.value.logisticsState = 1
    // 将文件数组添加到 FormData 中
    if (logisticsStateVerify(logisticsState.value) === false) {
        return
    }

    fileList.value?.forEach((file: UploadUserFile) => {
        // 提取原生 File 对象
        if (file.raw) {
            formData.append('file', file.raw);
        }
    });
    // 将 news 对象的属性逐个添加到 FormData 中
    for (const [key, value] of Object.entries(logisticsState.value)) {
        formData.append(key, String(value));
    }
    try {
        const res = await post('/donationState/updateLogisticsState', formData);
        ElMessage(res.message);
        if(res.message==='成功'){
            donationState.value.logisticsState=1
        }
    } catch (error) {
        console.error('上传失败:', error);
        ElMessage.error('上传失败');
    }
}
</script>
<style>
/* 定义动画关键帧 */
@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(10px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.ds-container {
    background-color: white;
    border-radius: 16px;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.15);
    padding: 30px;
    color: black;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    margin: 30px auto;
    max-width: 800px;
    animation: fadeIn 0.6s ease-out;
    transition: all 0.3s ease;
}

.ds-container:hover {
    box-shadow: 0 6px 20px rgba(0, 0, 0, 0.2);
    transform: translateY(-3px);
}

.ds-card-title {
    display: flex;
    flex-wrap: wrap;
    gap: 15px;
    margin-bottom: 25px;
    font-size: 18px;
    font-weight: 600;
    border-bottom: 2px solid rgba(148.6, 212.3, 117.1, 0.6);
    padding-bottom: 15px;
    color: black;
    transition: color 0.3s ease;
}

.ds-card-title:hover {
    color: rgba(148.6, 212.3, 117.1, 0.8);
}

/* 表单文本域样式 */
.el-form-item textarea {
    width: 100%;
    padding: 12px;
    border: 1px solid #ccc;
    border-radius: 8px;
    transition: border-color 0.3s ease;
    resize: vertical;
    /* 只允许垂直调整大小 */
    font-family: inherit;
    font-size: 14px;
}

.el-form-item textarea:focus {
    outline: none;
    border-color: rgba(148.6, 212.3, 117.1, 0.6);
    box-shadow: 0 0 5px rgba(148.6, 212.3, 117.1, 0.3);
    /* 聚焦时添加轻微阴影 */
}

/* 日期选择器样式 */
.el-date-picker {
    width: 100%;
}

/* 上传按钮样式 */
.el-upload--picture-card {
    border: 2px dashed rgba(148.6, 212.3, 117.1, 0.4);
    border-radius: 8px;
    cursor: pointer;
    transition: border-color 0.3s ease;
}

.el-upload--picture-card:hover {
    border-color: rgba(148.6, 212.3, 117.1, 0.6);
}

/* 修改按钮样式 */
.el-button--success {
    background-color: rgba(148.6, 212.3, 117.1, 1);
    border-color: rgba(148.6, 212.3, 117.1, 1);
    transition: background-color 0.3s ease, border-color 0.3s ease;
    padding: 10px 20px;
    /* 调整按钮内边距 */
    font-size: 14px;
    font-weight: 500;
}

.el-button--success:hover {
    background-color: rgba(148.6, 212.3, 117.1, 0.9);
    border-color: rgba(148.6, 212.3, 117.1, 0.9);
}
</style>