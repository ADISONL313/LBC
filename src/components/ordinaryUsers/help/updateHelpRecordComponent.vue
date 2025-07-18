<template>

    <div class="v-card">
        <el-form v-for="(item, index) in helpRecordList" :key="index">
            <div class="r-title">
                <el-row>
                    <el-col :span="12">
                        {{ "记录" + (index + 1) }}</el-col>
                    <el-col style="text-align: right;" :span="12"><el-button type="danger"
                            style="background-color:#F56C6C" @click="delThis(item.helpRecordId)">删除</el-button></el-col>
                </el-row>
            </div>
            <el-form-item>
                <el-input placeholder="记录内容" v-model="item.recordDescription" type="textarea"></el-input>
            </el-form-item>
            <el-form-item>
                <div v-show="false">
                </div>
                <el-upload :limit="4" v-model:file-list="item.fileList" :multiple="true" :drag="true"
                    :auto-upload="false" list-type="picture-card" :before-upload="handBeforeUpload"
                    :on-preview="handlePictureCardPreview"
                    :on-remove="(file: UploadFiles, files: any) => handleRemove(item?.helpRecordId, file, files)">
                    <el-icon>
                        <Plus />
                    </el-icon>
                </el-upload>
                <el-dialog v-model="dialogVisible">
                    <img w-full :src="dialogImageUrl" alt="Preview Image" />
                </el-dialog>
            </el-form-item>
            <el-form-item>
                <el-button style="width: 100%;" type="success" class="submit-button"
                    @click="updateRecord(item)">修改</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script lang="ts" setup>
import { storeToRefs } from 'pinia';
import { onMounted, ref, watch } from 'vue';

import { ElMessage, type UploadFiles, type UploadProps, type UploadUserFile } from 'element-plus'
import { deleteRemove, post } from '@/axiosConfig';
import { verifyHelpRecordData } from '@/components/universal/user/tsMethods/tsMethods';
import { getHelpRecordList } from './ts/helpTsMethods';

import { useSearchStore } from '@/stores/universal/searchStore';
const searchStore=useSearchStore()
const {freshValue}=storeToRefs(searchStore)

const fileList = ref<UploadUserFile[]>([])
const helpRecordList = ref()
const baseurl = 'http://47.108.29.91:9000/helprecord/'
const props = defineProps({
    nowTask: Object
})
const generateRecordList = async () => {
    console.log(props.nowTask?.helpId)
    const res = await getHelpRecordList(sessionStorage.getItem('mail'),props.nowTask?.helpId + '')
    helpRecordList.value = res
    helpRecordList.value.forEach((item: {
        helpRecordId: string;
        recordImage: string; fileList: any;
    }) => {
        const imageNames = item.recordImage.split(';').slice(0, -1).filter(name => name);
        item.fileList = imageNames.map(name => ({
            name: name,
            url: baseurl + item.helpRecordId + '/' + name // 替换为实际的图片URL前缀
        }));
    });
}
onMounted(async () => {
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
const handleRemove = (helpRecordId: any, uploadFile: any, uploadFiles: any) => {

    deleteRemove(`/helpRecord/delFile?fileId=${helpRecordId}&fileName=${uploadFile.name}`).then((res) => {
        ElMessage(res.message)
    })
};

const updateRecord = async (item: any) => {
    const formData = new FormData();
    if (verifyHelpRecordData(item) === false) {
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
        const res = await post('/helpRecord/updateHelpRecord', formData);
        ElMessage(res.message);
    } catch (error) {
        console.error('上传失败:', error);
        ElMessage.error('上传失败');
    }
}

const delThis = async (helpRecordId: any) => {
    const res = await deleteRemove(`/helpRecord/delHelpRecord?helpRecordId=${helpRecordId}`)
    ElMessage(res.message)
    if (res.message === '成功') {
        await generateRecordList()
    }
}
watch(freshValue,async()=>{
    if(freshValue.value===true){
        await generateRecordList()
        freshValue.value=false
    }
})
</script>
<style scoped>
.v-card {
    margin: 0 10% 2vh 10%;
    padding: 30px;
    border-radius: 15px;
    box-shadow: 0 0 20px rgba(148.6, 212.3, 117.1, 0.8);
    position: relative;
    overflow: hidden;
    animation: fadeInUp 0.5s ease;
    background-color: white;
    border: 1px solid #e0e0e0;
    transition: all 0.3s ease;
}

/* 鼠标悬停效果 */
.v-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 0 30px rgba(148.6, 212.3, 117.1, 1);
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