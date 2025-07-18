<template>
    <!-- 上传文件组件 -->
    <el-form>
        <el-form-item>
            <el-upload :limit="1" v-model:file-list="fileList" :multiple="true" :drag="true" :auto-upload="false"
                list-type="picture-card" :on-preview="handlePictureCardPreview">
                <el-icon>
                    <Plus />
                </el-icon>
            </el-upload>
            <el-dialog v-model="dialogVisible">
                <img w-full :src="dialogImageUrl" alt="Preview Image" />
            </el-dialog>
        </el-form-item>
        <el-form-item>
            <el-input placeholder="真实姓名" v-model="thisUserDetail.realName" maxLength="20"></el-input>
        </el-form-item>
        <el-form-item>
            <el-select placeholder="性别" v-model="thisUserDetail.gender" size="large" style="width: 240px">
                <el-option label="男" value="男"></el-option>
                <el-option label="女" value="女"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item>
            <el-input placeholder="身份证号码" v-model="thisUserDetail.idCard" maxlength="18"></el-input>
        </el-form-item>
        <el-form-item>
            <el-input placeholder="联系电话" v-model="thisUserDetail.phoneNumber" maxlength="11"></el-input>
        </el-form-item>
        <el-form-item>
            <el-input placeholder="联系地址" v-model="thisUserDetail.contactAddress"></el-input>
        </el-form-item>
        <div style="text-align: center;">
            <el-button type="primary" @click="update">修改</el-button>
        </div>
    </el-form>
</template>
<script lang="ts" setup>
import { onMounted, ref } from 'vue';

import { ElMessage,type UploadUserFile } from 'element-plus'
const dialogVisible = ref(false);
const dialogImageUrl = ref('');

const handlePictureCardPreview = (file:any) => {
    dialogImageUrl.value = file.url;
    dialogVisible.value = true;
};
//表单数据
const thisUserDetail = ref({
    mail:'',
    realName: '',
    gender: '',
    idCard: '',
    phoneNumber: '',
    contactAddress: '',
    userDetailId:''
})
const fileList = ref <UploadUserFile []> ([])
//修改方法
//导入pinia用户详情对象
import { useUserDetailStore } from '@/stores/user/userDetailStore';
import { storeToRefs } from 'pinia';
const userDetailStore = useUserDetailStore()
const { userDetail } = storeToRefs(userDetailStore)
//验证详情表单的方法
import { userDetailVerify } from '@/components/universal/user/tsMethods/tsMethods';
import { post } from '@/axiosConfig';
const update = () => {
    thisUserDetail.value.userDetailId=userDetail.value.userDetailId
    if (userDetailVerify(thisUserDetail.value) === false) {
        return
    }

    const formData = new FormData();
    // 将文件数组添加到 FormData 中
    fileList.value.forEach((file: UploadUserFile) => {
        // 提取原生 File 对象
        if (file.raw) {
            formData.append('file', file.raw);
        }
    });
    thisUserDetail.value.mail=userDetail.value.mail
    for (const [key, value] of Object.entries(thisUserDetail.value)) {
        formData.append(key, String(value));
    }
    try {
        post('/user/updateUser', formData).then((res) => {
            ElMessage(res.message);
            if (res.message === '成功') {
                userDetail.value.contactAddress = thisUserDetail.value.contactAddress
                userDetail.value.gender = thisUserDetail.value.gender
                userDetail.value.donationPermission = 0
                userDetail.value.reportPermission = 0
                userDetail.value.realName = thisUserDetail.value.realName
                userDetail.value.phoneNumber = thisUserDetail.value.phoneNumber
            }
        })
    } catch (error) {
        console.error('上传失败:', error);
        ElMessage.error('上传失败');
    }
}
//页面挂载后把信息加载到页面
onMounted(()=>{
    thisUserDetail.value=userDetail.value
    console.log('http://47.108.29.91:9000/user'+userDetail.value.userDetailId+'/'+userDetail.value.idPhoto)
    fileList.value=[{url:'http://47.108.29.91:9000/user/'+userDetail.value.userDetailId+'/'+userDetail.value.idPhoto,name:userDetail.value.idPhoto}]
})
</script>

<style scoped>
/* 设置表单容器样式 */
.el-form {
    background-color: white;
    padding: 30px;
    border-radius: 15px;
    box-shadow: 0 0 20px rgba(148.6, 212.3, 117.1, 0.8);
    position: relative;
    overflow: hidden;
}

/* 添加发光边框动画 */
.el-form::before {
    content: "";
    position: absolute;
    top: -50%;
    left: -50%;
    width: 200%;
    height: 200%;
    background: conic-gradient(from 0deg, transparent 0%, rgb(148.6, 212.3, 117.1) 25%, transparent 50%);
    animation: rotate 4s linear infinite;
    z-index: -1;
}

.el-form::after {
    content: "";
    position: absolute;
    top: 2px;
    left: 2px;
    right: 2px;
    bottom: 2px;
    background-color: white;
    border-radius: 13px;
    z-index: -1;
}

@keyframes rotate {
    0% {
        transform: rotate(0deg);
    }

    100% {
        transform: rotate(360deg);
    }
}

/* 设置表单元素样式 */
.el-form-item {
    margin-bottom: 20px;
}

/* 设置输入框样式 */
.el-input__inner {
    background-color: rgba(148.6, 212.3, 117.1, 0.1);
    color: rgb(148.6, 212.3, 117.1);
    border: 1px solid rgb(148.6, 212.3, 117.1);
    border-radius: 8px;
    transition: all 0.3s;
    position: relative;
    overflow: hidden;
}

.el-input__inner:hover {
    border-color: rgba(148.6, 212.3, 117.1, 0.8);
    box-shadow: 0 0 10px rgba(148.6, 212.3, 117.1, 0.5);
}

.el-input__inner:focus {
    border-color: rgb(148.6, 212.3, 117.1);
    box-shadow: 0 0 15px rgba(148.6, 212.3, 117.1, 0.8);
}

/* 添加输入框内发光动画 */
.el-input__inner::before {
    content: "";
    position: absolute;
    top: -50%;
    left: -50%;
    width: 200%;
    height: 200%;
    background: conic-gradient(from 0deg, transparent 0%, rgb(148.6, 212.3, 117.1) 25%, transparent 50%);
    animation: rotate 4s linear infinite;
    z-index: -1;
    opacity: 0;
    transition: opacity 0.3s;
}

.el-input__inner::after {
    content: "";
    position: absolute;
    top: 1px;
    left: 1px;
    right: 1px;
    bottom: 1px;
    background-color: rgba(148.6, 212.3, 117.1, 0.1);
    border-radius: 7px;
    z-index: -1;
}

.el-input__inner:hover::before,
.el-input__inner:focus::before {
    opacity: 1;
}

/* 设置下拉选择框样式 */
.el-select .el-input__inner {
    background-color: rgba(148.6, 212.3, 117.1, 0.1);
    color: rgb(148.6, 212.3, 117.1);
}

.el-select-dropdown {
    background-color: white;
    border: 1px solid rgb(148.6, 212.3, 117.1);
    box-shadow: 0 0 10px rgba(148.6, 212.3, 117.1, 0.5);
}

.el-select-dropdown__item {
    color: rgb(148.6, 212.3, 117.1);
}

.el-select-dropdown__item:hover {
    background-color: rgba(148.6, 212.3, 117.1, 0.1);
}

/* 设置按钮样式 */
.el-button--primary {
    background-color: rgb(148.6, 212.3, 117.1);
    border-color: rgb(148.6, 212.3, 117.1);
    color: white;
    border-radius: 8px;
    transition: all 0.3s;
    position: relative;
    overflow: hidden;
}

.el-button--primary:hover {
    background-color: rgba(148.6, 212.3, 117.1, 0.8);
    border-color: rgba(148.6, 212.3, 117.1, 0.8);
    box-shadow: 0 0 15px rgba(148.6, 212.3, 117.1, 0.8);
}

/* 添加按钮发光动画 */
.el-button--primary::before {
    content: "";
    position: absolute;
    top: -50%;
    left: -50%;
    width: 200%;
    height: 200%;
    background: conic-gradient(from 0deg, transparent 0%, white 25%, transparent 50%);
    animation: rotate 4s linear infinite;
    z-index: -1;
    opacity: 0;
    transition: opacity 0.3s;
}

.el-button--primary::after {
    content: "";
    position: absolute;
    top: 1px;
    left: 1px;
    right: 1px;
    bottom: 1px;
    background-color: rgb(148.6, 212.3, 117.1);
    border-radius: 7px;
    z-index: -1;
}

.el-button--primary:hover::before {
    opacity: 1;
}
</style> 