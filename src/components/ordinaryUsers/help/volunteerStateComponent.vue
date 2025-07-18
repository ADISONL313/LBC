<template>
    <div class="v-card">
        <div >
            我的状态：{{ volunteer?.state===0?"空闲":"任务中" }}
        </div>
    </div>
</template>
<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import { getVolunteer } from '../user/ts/userMethods';
const volunteer=ref()
onMounted(async()=>{
    const res=await getVolunteer(sessionStorage.getItem('mail')+'')
    volunteer.value=res
})
</script>
<style scoped>
/* 卡片样式 */
.v-card {
    margin: 2vh 10% 2vh 10%;
    padding: 30px;
    border-radius: 15px;
    box-shadow: 0 0 20px rgba(148.6, 212.3, 117.1, 0.8);
    position: relative;
    overflow: hidden;
    animation: fadeInUp 0.5s ease;
    background-color: white; /* 添加背景颜色 */
    border: 1px solid #e0e0e0; /* 添加边框 */
    transition: all 0.3s ease; /* 添加过渡效果 */
}
/* 鼠标悬停时卡片的样式变化 */
.v-card:hover {
    box-shadow: 0 0 30px rgba(148.6, 212.3, 117.1, 1);
    transform: translateY(-5px);
}
/* 淡入上滑的动画效果 */
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
/* 内部文本和按钮容器的样式 */
.v-card div {
    font-size: 16px;
    line-height: 1.5;
}
/* 按钮样式优化 */
.el-button {
    margin-left: 10px;
    padding: 8px 16px;
    border-radius: 5px;
    font-weight: bold;
    transition: all 0.3s ease;
}
.el-button:hover {
    background-color: #47a747; /* 鼠标悬停时按钮背景颜色变化 */
    transform: translateY(-2px);
}
</style>