<template>
    <div class="permission-container">
        <div class="approval-buttons">
            <el-button type="success" @click="passDonationPermission">通过捐赠权限</el-button>
            <el-button type="success" @click="passReportPermission">通过撰稿权限</el-button>
        </div>
        <div class="reject-section">
            <el-input placeholder="驳回原因" v-model.trim="thisRejectionReason" type="textarea"></el-input>
            <el-button type="danger" @click="rejection">驳回</el-button>
        </div>
    </div>
</template>
<script lang="ts" setup>
//导入pinia用户对象
import { storeToRefs } from 'pinia';
import { useUserDetailStore } from '@/stores/user/userDetailStore';
import { post } from '@/axiosConfig';
import { ElMessage } from 'element-plus';
import { onMounted, ref } from 'vue';
const userDetailStore = useUserDetailStore()
const { userDetail } = storeToRefs(userDetailStore)
//通过捐赠
const passDonationPermission=()=>{
    post('/user/passDonation',{mail:userDetail.value.mail}).then((res)=>{
        ElMessage(res.message)
        if(res.message==='成功'){
            userDetail.value.donationPermission=1
        }
    })
}
//通过撰稿
const passReportPermission=()=>{
    post('/user/passReport',{mail:userDetail.value.mail}).then((res)=>{
        ElMessage(res.message)
        if(res.message==='成功'){
            userDetail.value.reportPermission=1
        }
    })
}
//驳回
const thisRejectionReason=ref()
const rejection=()=>{
    if(thisRejectionReason.value===null||thisRejectionReason.value===''){
        ElMessage('请填写驳回原因')
        return
    }
    post('/user/rejection',{mail:userDetail.value.mail,rejectionReason:thisRejectionReason.value}).then((res)=>{
        ElMessage(res.message)
        if(res.message==='成功'){
            userDetail.value.reportPermission=2
            userDetail.value.donationPermission=2
        }
    })
}
onMounted(()=>{
    thisRejectionReason.value=userDetail.value.rejectionReason
})
</script>
<style scoped>
/* 整体容器样式 */
.permission-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 20px;
    border-radius: 15px;
    background-color: white;
    box-shadow: 0 0 20px rgba(148.6, 212.3, 117.1, 0.6);
    transition: all 0.3s ease;
    perspective: 1000px;
    margin-top: 2vh;
}

.permission-container:hover {
    box-shadow: 0 0 30px rgba(148.6, 212.3, 117.1, 0.8);
    transform: scale(1.05);
    filter: brightness(1.1);
}

/* 通过按钮容器样式 */
.approval-buttons {
    display: flex;
    gap: 20px;
    margin-bottom: 20px;
}

/* Element Plus 成功按钮样式修改 */
.el-button--success {
    background-color: rgba(148.6, 212.3, 117.1, 0.8);
    border-color: rgba(148.6, 212.3, 117.1, 0.8);
    color: white;
    border-radius: 5px;
    transition: all 0.3s ease;
    box-shadow: 0 0 10px rgba(148.6, 212.3, 117.1, 0.5);
}

.el-button--success:hover {
    background-color: rgba(148.6, 212.3, 117.1, 1);
    border-color: rgba(148.6, 212.3, 117.1, 1);
    transform: scale(1.1);
    box-shadow: 0 0 15px rgba(148.6, 212.3, 117.1, 0.7);
}

/* 驳回区域样式 */
.reject-section {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
}

/* Element Plus 输入框样式修改 */
.el-input__inner {
    border: 2px solid rgba(148.6, 212.3, 117.1, 0.3);
    border-radius: 10px;
    padding: 10px;
    transition: all 0.3s ease;
    margin-bottom: 10px;
}

.el-input__inner:focus {
    border-color: rgba(148.6, 212.3, 117.1, 0.8);
    box-shadow: 0 0 15px rgba(148.6, 212.3, 117.1, 0.5);
}

/* Element Plus 危险按钮样式修改 */
.el-button--danger {
    margin-top: 20px;
    background-color: rgba(220, 53, 69, 0.8);
    border-color: rgba(220, 53, 69, 0.8);
    color: white;
    border-radius: 5px;
    transition: all 0.3s ease;
    box-shadow: 0 0 10px rgba(220, 53, 69, 0.5);
}

.el-button--danger:hover {
    background-color: rgba(220, 53, 69, 1);
    border-color: rgba(220, 53, 69, 1);
    transform: scale(1.1);
    box-shadow: 0 0 15px rgba(220, 53, 69, 0.7);
}
</style>