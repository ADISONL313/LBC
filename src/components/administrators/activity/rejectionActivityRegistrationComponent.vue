<template>
    <div class="detail-box">
        <el-form-item>
            <el-input type="textarea" v-model="activity.registrationRejectionReason" placeholder="驳回原因"></el-input>
        </el-form-item>
        <div style="text-align: right;">
            <el-button type="danger" @click="rejectionThis" class="custom-button danger-button">驳回</el-button>
            <el-button type="success" class="custom-button success-button" @click="pass">通过</el-button>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { post,get } from '@/axiosConfig';
import { ElMessage } from 'element-plus';
import { useUserDetailStore } from '@/stores/user/userDetailStore';
import { useActivityStore } from '@/stores/activity/activityStore';
import { storeToRefs } from 'pinia';
const userDetailStore=useUserDetailStore();
const activityStore=useActivityStore()
const {userDetail}=storeToRefs(userDetailStore)
const {activity}=storeToRefs(activityStore)
// 这里暂时没有额外的逻辑，如果后续有可以添加
const rejectionThis=()=>{
    if(activity.value.registrationRejectionReason===''){
        ElMessage.error("请填写表单信息")
        return
    }
    post('/AR/rejectionAR',{
        mail:userDetail.value.mail,
        activityId:activity.value.activityId,
        rejectionReason:activity.value.registrationRejectionReason,
        registrationStatus:2
    }).then((res)=>{
        ElMessage(res.message)
    })
}
const pass=()=>{
    get('/AR/passAr',{mail:userDetail.value.mail,activityId:activity.value.activityId}).then((res)=>{
        ElMessage(res.message)
    })
}
</script>

<style scoped>
.detail-box {
    background-color: rgba(255, 255, 255, 0.9);
    border-radius: 15px;
    box-shadow: 0 0 15px rgba(148.6, 212.3, 117.1, 0.3);
    padding: 2vh;
    margin-top: 2vh;
    transition: box-shadow 0.3s ease, transform 0.3s ease;
}

.detail-box:hover {
    box-shadow: 0 0 25px rgba(148.6, 212.3, 117.1, 0.5);
    transform: scale(1.02);
}

.el-input,
.el-button {
    color: #333;
    font-size: 16px;
    padding: 10px 0;
    border-bottom: 1px solid rgba(148.6, 212.3, 117.1, 0.2);
    position: relative;
    transition: color 0.3s ease, transform 0.3s ease;
}

.el-input:last-child,
.el-button:last-child {
    border-bottom: none;
}

.el-input::after,
.el-button::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    width: 0;
    height: 2px;
    background-color: rgb(148.6, 212.3, 117.1);
    transition: width 0.3s ease;
}

.el-input:hover,
.el-button:hover {
    color: rgb(148.6, 212.3, 117.1);
    transform: translateX(5px);
    font-weight: bold;
}

.el-input:hover::after,
.el-button:hover::after {
    width: 100%;
}

/* 自定义按钮的通用样式 */
.custom-button {
    padding: 12px 20px;
    border-radius: 8px;
    font-size: 16px;
    font-weight: bold;
    transition: all 0.3s ease;
    border: none;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    margin: 0 10px;
}

/* 危险按钮（驳回）的样式 */
.danger-button {
    background-color: #ff4d4f;
    color: white;
}

.danger-button:hover {
    background-color: #ff7875;
    box-shadow: 0 4px 8px rgba(255, 77, 79, 0.3);
}

/* 成功按钮（通过）的样式 */
.success-button {
    background-color: #52c41a;
    color: white;
}

.success-button:hover {
    background-color: #73d13d;
    box-shadow: 0 4px 8px rgba(82, 196, 26, 0.3);
}
</style>