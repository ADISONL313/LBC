<template>
    <el-form>
        <div>{{ oath }}</div>
        <el-form-item>
            <el-input placeholder="请输入上面的文字" v-model="inputOath" type="textarea"></el-input>
        </el-form-item>
        <div style="text-align: right;">
            <el-button type="success" @click="applyFor">申请</el-button>
        </div>
    </el-form>
</template>
<script lang="ts" setup>
import { ElMessage } from 'element-plus';
import { ref } from 'vue';
import { volunteerRepply } from './ts/userMethods';
const inputOath=ref('')
const oath=ref('我志愿投身公益，坚守奉献、友爱、互助、进步，以专业行动，为社会福祉全力付出。')
const applyFor=()=>{
    volunteerRepply(inputOath.value,oath.value)
}
</script>
<style scoped>
/* 整体表单样式 */
.el-form {
    padding: 30px;
    border-radius: 15px;
    box-shadow: 0 0 20px rgba(148.6, 212.3, 117.1, 0.8);
    position: relative;
    overflow: hidden;
    animation: fadeInUp 0.5s ease; /* 添加淡入上滑的动画效果 */
}

/* 动画关键帧 */
@keyframes fadeInUp {
    from {
        opacity: 0;
        transform: translateY(20px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

/* 表单内的文本样式 */
.el-form div {
    /* user-select: none; */
    font-size: 18px;
    font-weight: bolder;
    color: #ff0000; /* 这里颜色未修改，可根据需求调整 */
    margin-bottom: 20px;
    line-height: 1.6;
}

/* 表单域样式 */
.el-form-item {
    margin-bottom: 20px;
}

/* 输入框样式 */
.el-input {
    width: 100%;
    border: 1px solid rgb(148.6, 212.3, 117.1); /* 修改边框颜色为主题色 */
    border-radius: 5px;
    padding: 10px;
    font-size: 16px;
    transition: border-color 0.3s ease; /* 输入框边框颜色过渡效果 */
}

.el-input:focus {
    outline: none;
    border-color: rgb(120, 180, 90); /* 输入框聚焦时的边框颜色，使用与主题色搭配的颜色 */
}

/* 按钮样式 */
.el-button {
    padding: 10px 20px;
    font-size: 16px;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease, transform 0.3s ease; /* 按钮背景颜色和缩放过渡效果 */
}

.el-button:hover {
    background-color: rgb(120, 180, 90); /* 按钮鼠标悬停时的背景颜色，使用与主题色搭配的颜色 */
    transform: translateY(-2px); /* 按钮鼠标悬停时上移效果 */
}

.el-button--success {
    background-color: rgb(148.6, 212.3, 117.1); /* 修改按钮背景颜色为主题色 */
    color: white;
}
</style>