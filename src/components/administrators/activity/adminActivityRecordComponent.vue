<template>
    <div class="record-container">
        <div class="r-title">
            添加记录
        </div>
        <el-form>
            <el-form-item>
                <el-date-picker type="date" v-model="activityRecord.recordTime" value-format="YYYY-MM-DD"
                    placeholder="记录时间"></el-date-picker>
            </el-form-item>
            <el-form-item>
                <el-input placeholder="记录内容" type="textarea" v-model="activityRecord.recordContent"></el-input>
            </el-form-item>
            <fileUploadComponent :uploadKey="'activityrecord'" />
        </el-form>
    </div>
    <div class="record-container">
        <el-form v-for="(item, index) in recordList" :key="index">
            <div class="r-title">
                <el-row>
                    <el-col :span="12">
                        {{ "记录" + (index + 1) }}</el-col>
                    <el-col style="text-align: right;" :span="12"><el-button type="danger"
                            style="background-color:#F56C6C" @click="delThis(item.activityRecordId)">删除</el-button></el-col>
                </el-row>
            </div>
            <el-form-item>
                <el-date-picker type="date" v-model="item.recordTime" value-format="YYYY-MM-DD"
                    placeholder="记录时间"></el-date-picker>
            </el-form-item>
            <el-form-item>
                <el-input placeholder="记录内容" type="textarea" v-model="item.recordContent"></el-input>
            </el-form-item>
            <el-form-item>
                <div v-show="false">
                </div>
                <el-upload :limit="4" v-model:file-list="item.fileList" :multiple="true" :drag="true"
                    :auto-upload="false" list-type="picture-card" :before-upload="handBeforeUpload"
                    :on-preview="handlePictureCardPreview"
                    :on-remove="(file: UploadFiles, files: any) => handleRemove(item.activityRecordId, file, files)">
                    <el-icon>
                        <Plus />
                    </el-icon>
                </el-upload>
                <el-dialog v-model="dialogVisible">
                    <img w-full :src="dialogImageUrl" alt="Preview Image" />
                </el-dialog>
            </el-form-item>
            <el-form-item>
                <el-button style="width: 100%;" type="primary" class="submit-button"
                    @click="updateRecord(item)">修改</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script lang="ts" setup>

import { deleteRemove, post } from '@/axiosConfig';
import fileUploadComponent from '@/components/universal/fileUploadComponent.vue';
import { useActivityRecordStore } from '@/stores/activity/activityRecordStore';
import { storeToRefs } from 'pinia';
import { onMounted, ref, watch } from 'vue';
const activityRecordStore = useActivityRecordStore()
const { activityRecord } = storeToRefs(activityRecordStore)

import { useRoute } from 'vue-router';
const route = useRoute()

const recordList = ref()

import { ElMessage, type UploadFiles, type UploadProps, type UploadUserFile } from 'element-plus'
import { deleteThisFile, getActivityRecord } from './ts/adminActivityMethods';

const fileList = ref<UploadUserFile[]>([])

const baseurl = 'http://47.108.29.91:9000/activityrecord/'

const generateRecordList = async () => {
    const res = await getActivityRecord(route.query.activityId)
    recordList.value = res
    recordList.value.forEach((item: {
        activityRecordId: string; recordImage: string; fileList: any;
    }) => {
        const imageNames = item.recordImage.split(';').slice(0, -1).filter(name => name);
        item.fileList = imageNames.map(name => ({
            name: name,
            url: baseurl + item.activityRecordId + '/' + name // 替换为实际的图片URL前缀
        }));
    });
}

onMounted(async () => {
    activityRecord.value.activityId = route.query.activityId + ''
    await generateRecordList()
})


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
const handleRemove = (activityRecordId: any, uploadFile: any, uploadFiles: any) => {

    deleteRemove(`/activityRecord/delFile?fileId=${activityRecordId}&fileName=${uploadFile.name}`).then((res) => {
        ElMessage(res.message)
    })
};

const updateRecord =async (item: any) => {
    const formData = new FormData();
    if (verifyActivityRecord(item) === false) {
        return
    }
    // 将文件数组添加到 FormData 中
    item.fileList.forEach((file: UploadUserFile) => {
        // 提取原生 File 对象
        if (file.raw) {
            formData.append('file', file.raw);
        }
    });
    for (const [key, value] of Object.entries(item)) {
        formData.append(key, String(value));
    }
    try {
        const res = await post('/activityRecord/updateActivityRecord', formData);
        ElMessage(res.message);
    } catch (error) {
        console.error('上传失败:', error);
        ElMessage.error('上传失败');
    }
}

const delThis=async(activityRecordId:any)=>{
    const res=await deleteRemove(`/activityRecord/delActivityRecord?activityRecordId=${activityRecordId}`)
    ElMessage(res.message)
    if(res.message==='成功'){
        await generateRecordList()
    }
}

import { useSearchStore } from '@/stores/universal/searchStore'
import { verifyActivityRecord } from '@/components/universal/user/tsMethods/tsMethods';
const searchStore = useSearchStore()
const { freshRecord } = storeToRefs(searchStore);

watch(freshRecord, async () => {
    if (freshRecord.value === true) {
        await generateRecordList()
        freshRecord.value = false
    }
})
</script>
<style scoped>
.record-container {
    padding: 30px 50px;
    color: black;
    margin: 5% 10%;
    background-color: white;
    border-radius: 12px;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease;
}

.record-container:hover {
    box-shadow: 0 8px 25px rgba(0, 0, 0, 0.2);
    transform: translateY(-5px);
}

.el-button {
    background-color: rgb(148.6, 212.3, 117.1);
    border-color: rgb(148.6, 212.3, 117.1);
    color: black;
    transition: background-color 0.3s ease, transform 0.3s ease;
    border-radius: 6px;
}

.el-button:hover {
    background-color: rgba(148.6, 212.3, 117.1, 0.8);
    border-color: rgba(148.6, 212.3, 117.1, 0.8);
    transform: translateY(-3px);
}

.el-form {
    margin-top: 20px;
}

.el-form-item {
    margin-bottom: 25px;
}

.el-date-picker,
.el-input {
    width: 100%;
    border: 1px solid #ddd;
    border-radius: 6px;
    padding: 10px;
    transition: border-color 0.3s ease;
}

.el-date-picker:hover,
.el-input:hover {
    border-color: rgb(148.6, 212.3, 117.1);
}

.el-date-picker:focus,
.el-input:focus {
    outline: none;
    border-color: rgb(148.6, 212.3, 117.1);
    box-shadow: 0 0 5px rgba(148.6, 212.3, 117.1, 0.5);
}

.r-title {
    text-align: left;
    /* 文本样式 */
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    font-size: 24px;
    font-weight: 600;
    color: #333;
    letter-spacing: 1px;
    text-transform: capitalize;

    /* 背景和边框样式 */
    border: none;
    border-bottom: 3px solid rgb(148.6, 212.3, 117.1);
    /* 增加底部边框宽度 */
    box-sizing: border-box;
    /* 确保边框和内边距包含在宽度内 */

    /* 内边距和外边距 */
    padding: 12px 0;
    /* 调整内边距，仅保留上下内边距 */
    margin: 10px 0;
    /* 调整外边距 */
    width: 100%;
    /* 设置宽度为百分百 */

    /* 其他样式 */
    position: relative;
    /* 为可能的伪元素定位做准备 */
}

.r-title::before {
    content: "";
    position: absolute;
    left: 50%;
    bottom: 0;
    width: 0;
    height: 2px;
    background-color: rgba(148.6, 212.3, 117.1, 0.6);
    /* 半透明的底部装饰线 */
    transition: width 0.3s ease;
    /* 过渡效果，可在有交互时使用 */
}
</style>