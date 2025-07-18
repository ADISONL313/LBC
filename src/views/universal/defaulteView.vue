<template>
    <div class="page-container">
        <videoComponent />
        <div style="margin: 2vh;">
            <logoLBC/>
        </div>
        <div>
            <newsListComponent :listNum="false" />
        </div>
        <div class="bottom-container">
            <div class="bottom-img"><img src="../../assets/pexels-edwardeyer-1170899.jpg" /></div>
            <div class="bottom-text">
                <div>
                    <p style="font-size: 30px;font-weight: bolder;">采取行动</p>
                    <p style="font-size: 20px;width: 70%;">留守儿童需要帮助。参与其中、发表意见、志愿服务或成为捐助者，为每一名儿童提供一个公平的成功机会。</p>
                </div>
                <el-button type="primary" @click="router.push('/register')" class="cool-button">去注册</el-button>
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { useRouter } from 'vue-router';
const router = useRouter();
import logoLBC from '@/components/logoLBC.vue';
import newsListComponent from '@/components/ordinaryUsers/news/newsListComponent.vue';
import videoComponent from '@/components/universal/videoComponent.vue';
</script>

<style scoped>
div {
    margin: 0px;
    padding: 0px;
}

img {
    width: 100%;
    height: auto;
    margin: 0px;
}

.page-container {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.page-title {
    font-family: Arial, sans-serif;
    font-size: 36px;
    color: black;
    margin: 20px 0;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 20px;
}

.title-divider {
    border-color: rgb(148.6, 212.3, 117.1);
    height: 40px;
}

.bottom-text {
    background-color: rgb(148.6, 212.3, 117.1);
}

.bottom-img {
    width: 65%;
}

/* 清除默认的内外边距 */
* {
    margin: 0;
    padding: 0;
}

/* 让父级div开启flex布局 */
.bottom-container {
    display: flex;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
    /* 添加阴影效果 */
    overflow: visible;
    /* 显示溢出部分 */
    background-color: #f9f9f9;
    /* 设置背景颜色 */
    position: relative;
    /* 为动画提供定位参考 */
}

/* 让第一个div保持原有的样式 */
.bottom-img {
    width: 65%;
    overflow: hidden;
    /* 隐藏图片超出部分 */
}

/* 让第二个div和第三个div各占50%的宽度 */
.bottom-text {
    width: 35%;
    display: flex;
    flex-direction: column;
    /* 让子元素垂直排列 */
    justify-content: center;
    /* 垂直居中 */
    
    text-align: left;
    padding: 20px;
    /* 增加内边距 */
    box-sizing: border-box;
    /* 让内边距包含在元素宽度内 */
    font-family: Arial, sans-serif;
    /* 设置字体 */
    color: #333;
    /* 设置字体颜色 */
}

/* 图片宽度设置为100%以适应容器 */
.bottom-img img {
    width: 100%;
    display: block;
    transition: transform 0.3s ease;
    /* 添加过渡效果，让图片在鼠标悬停时更平滑 */
    transform-origin: center;
    /* 设置图片的变换中心 */
}

/* 鼠标悬停时图片放大 */
.bottom-img img:hover {
    transform: scale(1.1);
    /* 放大1.1倍 */
}

/* 设置文本样式 */
.bottom-text p {
    margin: 0;
    line-height: 1.6;
    /* 文本居中 */
    text-align: left;
    color: #000;
}

.cool-button {
    background-color: black;
    color: rgb(148.6, 212.3, 117.1);
    border: none;
    border-radius: 8px;
    padding: 10px 15px;
    font-weight: bold;
    transition: all 0.3s ease;
    width: 45%;
    margin-top: 2vh;
}
.cool-button:hover {
    background-color: rgb(148.6, 212.3, 117.1);
    color: black;
    transform: scale(1.05);
    box-shadow: 0 0 10px rgba(39, 39, 38, 0.5);
}
</style>