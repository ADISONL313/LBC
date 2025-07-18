<template>
    <ul class="sidebar">
        <li @click="showDetail">详细信息</li>
        <li @click="showDetailUpdate">修改信息</li>
        <li @click="showVolunteer">志愿者</li>
        <li @click="showCertificate">我的证书</li>
        <li @click="logOut">退出登录</li>
    </ul>
</template>
<script lang="ts" setup>
import { useUserDetailStore } from '@/stores/user/userDetailStore';
import { storeToRefs } from 'pinia';
const userDetailStore=useUserDetailStore()
const {userCenterState,showWitchPage}=storeToRefs(userDetailStore)

//显示详情
const showDetail=()=>{
    userCenterState.value='详细信息'
    showWitchPage.value.certificate=false
    showWitchPage.value.userDetailUpdate=false
    showWitchPage.value.becomevolunteer=false
    showWitchPage.value.userDetail=true
}
//显示证书
const showCertificate=()=>{
    userCenterState.value='我的证书'
    showWitchPage.value.userDetail=false
    showWitchPage.value.userDetailUpdate=false
    showWitchPage.value.becomevolunteer=false
    showWitchPage.value.certificate=true
}
//修改信息
const showDetailUpdate=()=>{
    userCenterState.value='修改信息'
    showWitchPage.value.certificate=false
    showWitchPage.value.userDetail=false
    showWitchPage.value.becomevolunteer=false
    showWitchPage.value.userDetailUpdate=true
}
const showVolunteer=()=>{
    userCenterState.value='志愿者'
    showWitchPage.value.certificate=false
    showWitchPage.value.userDetail=false
    showWitchPage.value.userDetailUpdate=false
    showWitchPage.value.becomevolunteer=true
}
import { useRouter } from 'vue-router';
const router=useRouter()
//退出登录
const logOut=()=>{
    sessionStorage.clear()
    localStorage.clear()
    router.push('/login')
}
</script>
<style scoped>
.sidebar {
    position: fixed;
    top: 30%;
    left: 20px;
    transform: translateY(-50%);
    list-style-type: none;
    padding: 0;
    margin: 0;
    background: linear-gradient(45deg, rgba(148.6, 212.3, 117.1, 0.9), rgba(148.6, 212.3, 117.1, 0.7)); /* 使用主题色渐变背景 */
    border-radius: 10px;
    box-shadow: 0 0 20px rgba(148.6, 212.3, 117.1, 0.8);
    overflow: hidden;
    z-index: 100;
    animation: neonGlow 1.5s ease-in-out infinite alternate; /* 添加光影闪烁动画 */
    transition: transform 0.3s ease;
}

@keyframes neonGlow {
    from {
        box-shadow: 0 0 20px rgba(148.6, 212.3, 117.1, 0.8);
    }
    to {
        box-shadow: 0 0 30px rgba(148.6, 212.3, 117.1, 1);
    }
}

.sidebar:hover {
    transform: scale(1.05) translateY(-50%); /* 鼠标悬停时侧边栏放大 */
}

.sidebar li {
    padding: 15px 20px;
    color: white; /* 文字颜色改为白色 */
    font-size: 16px;
    font-weight: bold;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    cursor: pointer;
    transition: all 0.3s ease;
    border-bottom: 1px solid rgba(255, 255, 255, 0.2); /* 分隔线颜色改为白色半透明 */
    position: relative;
    overflow: hidden;
}

.sidebar li:last-child {
    border-bottom: none;
}

.sidebar li::before {
    content: '';
    position: absolute;
    top: 0;
    left: -100%;
    width: 100%;
    height: 100%;
    background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent); /* 光影扫过效果 */
    transition: left 0.6s ease;
}

.sidebar li:hover {
    background-color: rgba(255, 255, 255, 0.1);
    color: black;
    transform: translateX(10px); /* 鼠标悬停时列表项移动距离加大 */
}

.sidebar li:hover::before {
    left: 100%;
}
</style>