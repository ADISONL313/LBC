<template>
    <div class="detail-box">
        <div class="real-name">真实名字：{{ userDetail.realName }}</div>
        <div class="real-name">身份证：{{ userDetail.idCard }}</div>
        <div class="real-name">联系电话：{{ userDetail.phoneNumber }}</div>
        <div class="real-name">联系地址：{{ userDetail.contactAddress }}</div>
        <div class="real-name">身份：{{ userDetail.userRole===1?"普通用户":userDetail.userRole===2?"志愿者":"" }}</div>
        <div class="real-name">撰稿权限：{{ Number(userDetail.reportPermission)===0?"审核中":Number(userDetail.reportPermission)===1?"审核通过":"审核失败" }}</div>
        <div class="real-name">捐赠权限：{{ Number(userDetail.reportPermission)===0?"审核中":Number(userDetail.reportPermission)===1?"审核通过":"审核失败" }}</div>
        <div class="real-name">驳回原因：{{ userDetail.rejectionReason }}</div>
    </div>
</template>
<script lang="ts" setup>
//导入pinia持久化的个人信息
import { useUserDetailStore } from '@/stores/user/userDetailStore';
import { storeToRefs } from 'pinia';
const userDetailStore = useUserDetailStore()
const { userDetail } = storeToRefs(userDetailStore)
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

.real-name {
    color: #333;
    font-size: 16px;
    padding: 10px 0;
    border-bottom: 1px solid rgba(148.6, 212.3, 117.1, 0.2);
    position: relative;
    transition: color 0.3s ease, transform 0.3s ease;
}

.real-name:last-child {
    border-bottom: none;
}

.real-name::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    width: 0;
    height: 2px;
    background-color: rgb(148.6, 212.3, 117.1);
    transition: width 0.3s ease;
}

.real-name:hover {
    color: rgb(148.6, 212.3, 117.1);
    transform: translateX(5px);
    font-weight: bold;
}

.real-name:hover::after {
    width: 100%;
}
</style>