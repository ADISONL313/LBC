<template>
    <div class="ds-container" v-if="donation.auditStatus === 1">
        <div class="ds-card-title">
            分配状态：{{
                donationState.transportState === 0 ? "等待确定分配地点" : "运输中"
            }}
        </div>
        <div>
            <el-form>
                <el-form-item>
                    <textarea v-model="transportState.transportText" placeholder="备注"></textarea>
                </el-form-item>
                <el-form-item>
                    <el-date-picker v-model="transportState.transportTime" type="date" value-format="YYYY-MM-DD"
                        placeholder="物资抵达日期"></el-date-picker>
                </el-form-item>
                <div>
                    物资将送往：{{ transportState?.transportAddress===null?"": transportState?.transportAddress.split(/;/)[0] }}
                </div>
                <div v-if="true">
                    <mapChooseComponent @send-data="handleMapData" />
                </div>
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
                    <el-button type="success" @click="updatetransportState">修改</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>
<script lang="ts" setup>
import mapChooseComponent from '@/components/universal/mapChooseComponent.vue';
import { onMounted, ref } from 'vue';
import { post } from '@/axiosConfig';
import { storeToRefs } from 'pinia';
import { useDonationStore } from '@/stores/donation/donationStore';
const donationStore = useDonationStore()
const { donation } = storeToRefs(donationStore)


import { useDonationStateStore } from '@/stores/donation/donationStateStore';
const donationStateStore = useDonationStateStore()
const { donationState } = storeToRefs(donationStateStore)

const transportState = ref(
    {
        transportState: 0,
        transportTime: '',
        transportAddress: '',
        transportText: '',
        transportImg: '',
        stateId: ''
    }
)

const handleMapData = (data: string) => {
    transportState.value.transportAddress = data
}

const baseUrl = 'http://47.108.29.91:9000/donationstate/'
import { ElMessage, type UploadProps, type UploadUserFile } from 'element-plus'
import { delStateImg, verifyTransportStateData } from './ts/donationStateAPI';
const fileList = ref<UploadUserFile[]>()

onMounted(async () => {
    console.log(donationState.value)
    transportState.value.transportImg = donationState.value.transportImg
    transportState.value.transportState = donationState.value.transportState
    transportState.value.transportText = donationState.value.transportText
    transportState.value.transportTime = donationState.value.transportTime
    transportState.value.transportAddress = donationState.value.transportAddress
    transportState.value.stateId = donationState.value.stateId
    if (transportState.value.transportImg !== null && transportState.value.transportImg !== '') {
        fileList.value = [{ url: baseUrl + transportState.value.stateId + '/' + transportState.value.transportImg, name: transportState.value.transportImg }]
    }
})

const dialogImageUrl = ref('')
const dialogVisible = ref(false)

const handleRemove: UploadProps['onRemove'] = async (uploadFile, uploadFiles) => {
    const res:any=await delStateImg(transportState.value.stateId,uploadFile.name,'transport')
    ElMessage.success(res.message)
}

const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {
    dialogImageUrl.value = uploadFile.url!
    dialogVisible.value = true
}

const updatetransportState = async () => {
    if(verifyTransportStateData(transportState.value)===false){
        return
    }
    const formData = new FormData();
    transportState.value.transportState = 1

    fileList.value?.forEach((file: UploadUserFile) => {
        // 提取原生 File 对象
        if (file.raw) {
            formData.append('file', file.raw);
        }
    });
    // 将 news 对象的属性逐个添加到 FormData 中
    for (const [key, value] of Object.entries(transportState.value)) {
        formData.append(key, String(value));
    }
    try {
        const res = await post('/donationState/updateTransportState', formData);
        ElMessage(res.message);
        if (res.message === '成功') {
            donationState.value.transportState = 1
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