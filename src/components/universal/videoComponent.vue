<template>
    <div class="video-container">
        <video src="../../assets/mixkit-walking-on-a-snowy-path-in-canada-3321-hd-ready.mp4" loop autoplay muted></video>
        <div class="box-j">
            <div class="p-a">加入我们</div>
            <div class="p-b">助力成长，守护他们的未来！</div>
            <el-button class="more" type="success" @click="router.push('more')">了解更多</el-button>
        </div>
    </div>
</template>
<script setup>
import { useRouter } from 'vue-router';
const router=useRouter()
</script>
<style scoped>
/* 视频容器样式 */
.video-container {
    position: relative;
    width: 100%;
    height: 100vh; /* 设置容器高度为视口高度 */
    overflow: hidden; /* 隐藏溢出内容 */
}

.video-container video {
    width: 100%;
    height: 100%;
    object-fit: cover; /* 保持视频比例并覆盖容器 */
}

/* 内容盒子样式 */
.box-j {
    position: absolute;
    left: 10%;
    bottom: 10%; /* 调整位置，使其在视频中更合适 */
    animation: fadeInUp 1s ease-out; /* 添加淡入向上动画 */
}

/* 标题样式 */
.p-a {
    font-size: 60px;
    width: fit-content; /* 根据内容自适应宽度 */
    background-color: black;/* 半透明黑色背景 */
    color: white;
    font-weight: bold;
    border-top-left-radius: 10px;
    border-top-right-radius: 10px;
    padding: 10px 20px; /* 增加内边距 */
    backdrop-filter: blur(5px); /* 添加毛玻璃效果 */
}

/* 副标题样式 */
.p-b {
    font-size: 30px;
    background-color:black; /* 半透明黑色背景 */
    padding: 10px 20px;
    color: white;
    font-weight: bold;
    border-bottom-left-radius: 10px;
    border-bottom-right-radius: 10px;
    border-top-right-radius: 10px;
    backdrop-filter: blur(5px); /* 添加毛玻璃效果 */
}

/* 按钮样式 */
.more {
    border: none;
    margin-top: 20px; /* 增加顶部间距 */
    width: 260px;
    font-size: 20px;
    font-weight: bold;
    height: 50px;
    background-color: #94d475; /* 使用更准确的颜色值 */
    color: black;
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2); /* 添加阴影 */
    transition: all 0.3s ease; /* 添加过渡效果 */
}

.more:hover {
    background-color: #b3e09c; /* 鼠标悬停时的颜色 */
    transform: translateY(-3px); /* 鼠标悬停时向上移动 */
    box-shadow: 0 12px 24px rgba(0, 0, 0, 0.3); /* 鼠标悬停时增加阴影 */
}

/* 淡入向上动画 */
@keyframes fadeInUp {
    from {
        opacity: 0;
        transform: translateY(50px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}
</style>