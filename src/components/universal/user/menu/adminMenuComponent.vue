<template>
    <!-- 管理员菜单 -->
    <el-menu default-active="0" mode="horizontal" :ellipsis="false" background-color="#000000" text-color="#2e710c" active-text-color="#2e710c">
        <el-menu-item index="0" @click="router.push('/')">
            <img src="../../../../assets/user/logo.png" style="height: 40px;" />
        </el-menu-item>
        <el-menu-item index="1" @click="router.push('newsManager')">新闻管理</el-menu-item>
        <el-menu-item index="2" @click="router.push('adminActivity')">活动管理</el-menu-item>
        <el-menu-item index="3" @click="router.push('donation')">捐赠管理</el-menu-item>
        <el-menu-item index="4" @click="router.push('adminHelp')">求助管理</el-menu-item>
        <el-menu-item index="5" @click="router.push('userAdmin')">用户管理</el-menu-item>
    </el-menu>
</template>

<script lang="ts" setup>
//导入vue路由
import { useRouter } from 'vue-router';
const router = useRouter();
</script>

<style scoped>
/* 整体菜单样式 */
.el-menu {
    background-color: rgb(148.6, 212.3, 117.1);
    border: none;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* 添加阴影效果 */
    overflow: hidden; /* 隐藏溢出内容 */
}

/* 菜单项样式 */
.el-menu-item {
    font-size: 18px;
    color: black;
    font-weight: bold;
    transition: all 0.3s ease; /* 添加过渡效果 */
}

/* 菜单项悬停样式 */
.el-menu-item:hover {
    background-color: rgba(0, 0, 0, 0.1); /* 悬停时背景颜色变深 */
    transform: scale(1.05); /* 悬停时稍微放大 */
}

/* 激活菜单项样式 */
.el-menu-item.is-active {
    background-color: black;
    color: rgb(148.6, 212.3, 117.1);
}

/* 第一个菜单项（logo）样式 */
.el-menu--horizontal>.el-menu-item:nth-child(1) {
    margin-right: auto;
    padding: 0 20px; /* 增加左右内边距 */
}

/* 第一个菜单项悬停样式 */
.el-menu--horizontal>.el-menu-item:nth-child(1):hover {
    background-color: transparent; /* 第一个菜单项悬停时背景透明 */
    transform: none; /* 第一个菜单项悬停时不放大 */
}
</style>