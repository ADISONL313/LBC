<template>
    <el-form class="donation-form">
        <el-form-item>
            <input type="text" v-model="donation.itemName" placeholder="物品名称">
        </el-form-item>
        <el-form-item>
            <select v-model="donation.itemType" placeholder="请选择捐赠类型">
                <option style="color: lightgray;font-size: medium;" value="">请选择物品类型</option>
                <option value="衣物">衣物</option>
                <option value="书籍">书籍</option>
                <option value="体育器材">体育器材</option>
                <option value="玩具">体育器材</option>
                <option value="学习用品">学习用品</option>
            </select>
        </el-form-item>
        <fileUploadComponent :uploadKey="'donation'" />
    </el-form>
</template>

<script lang="ts" setup>
import { storeToRefs } from 'pinia';
import fileUploadComponent from '@/components/universal/fileUploadComponent.vue';
import { useDonationStore } from '@/stores/donation/donationStore';
const donationStore=useDonationStore()
const {donation}=storeToRefs(donationStore)
</script>

<style scoped>
/* 引入 Google Fonts 的 Roboto 字体 */
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500&display=swap');

/* 表单整体样式 */
.donation-form {
    max-width: 700px; /* 加宽卡片宽度 */
    margin: 10vh auto;
    padding: 40px; /* 适当增加内边距 */
    background-color: #fff;
    border-radius: 15px; /* 增大圆角半径 */
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1); /* 增强阴影效果 */
    position: relative;
    overflow: hidden;
    font-family: 'Roboto', sans-serif;
    transition: all 0.3s ease;
    align-items: center; /* 水平居中 */
}

.donation-form:hover {
    box-shadow: 0 15px 40px rgba(0, 0, 0, 0.15); /* 悬浮时增强阴影 */
    transform: translateY(-8px); /* 悬浮时上移距离增大 */
}

/* 表单元素容器样式 */
.el-form-items-container {
    width: 100%;
}

/* 表单元素样式 */
.el-form-item {
    margin-bottom: 30px; /* 增加元素间距 */
    position: relative;
}

/* 输入框样式 */
input[type="text"] {
    width: 100%;
    padding: 16px 20px; /* 增加内边距 */
    border: none;
    border-bottom: 2px solid #e0e0e0;
    font-size: 18px; /* 增大字体大小 */
    font-weight: 400;
    background-color: transparent;
    transition: border-color 0.3s ease;
}

input[type="text"]::placeholder {
    color: #a0a0a0;
    transition: color 0.3s ease;
}

input[type="text"]:focus {
    outline: none;
    border-color: rgb(148.6, 212.3, 117.1);
}

input[type="text"]:focus::placeholder {
    color: transparent;
}

/* 下拉选择框样式 */
select {
    width: 100%;
    padding: 16px 20px; /* 增加内边距 */
    border: none;
    border-bottom: 2px solid #e0e0e0;
    font-size: 18px; /* 增大字体大小 */
    font-weight: 400;
    appearance: none;
    -webkit-appearance: none;
    -moz-appearance: none;
    background-color: transparent;
    background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='18' height='18' viewBox='0 0 24 24'%3E%3Cpath fill='%23a0a0a0' d='M7 10l5 5 5-5H7z'/%3E%3C/svg%3E"); /* 增大图标大小 */
    background-repeat: no-repeat;
    background-position: right 20px center;
    transition: border-color 0.3s ease;
}

select:focus {
    outline: none;
    border-color: rgb(148.6, 212.3, 117.1);
}

/* 自定义焦点动画 */
input[type="text"]:focus::after,
select:focus::after {
    content: "";
    position: absolute;
    left: 0;
    bottom: -2px;
    width: 100%;
    height: 2px;
    background-color: rgb(148.6, 212.3, 117.1);
    animation: focusLine 0.3s ease;
}

@keyframes focusLine {
    from {
        width: 0;
    }
    to {
        width: 100%;
    }
}

/* 自定义 select 下拉列表样式 */
select option {
    background-color: #fff;
    color: #333;
    padding: 16px 20px; /* 增加内边距 */
    transition: background-color 0.3s ease;
}

select option:hover {
    background-color: rgba(148.6, 212.3, 117.1, 0.2);
}

select option:checked {
    background-color: rgba(148.6, 212.3, 117.1, 0.4);
    color: #000;
}

/* 文件上传组件样式 */
fileUploadComponent {
    display: block;
    margin-top: 30px;
    position: relative;
    width: 100%;
}
</style>