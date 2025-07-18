<template>
    <div class="certificate-container">
        <logoLBC />
        <h1 class="certificate-title">捐赠证书</h1>
        <div class="recipient">敬爱的{{ userDetail.realName }}：</div>
        <div class="message">
            您好！
            我们诚挚的感谢您为留守儿童公益事业做出的卓越贡献！您通过我们的平台，累计向孩子们贡献了<span class="highlight-span">{{ clothing?.recordCount+book?.recordCount}}</span>批物资，
            其中衣物<span class="highlight-span">{{ clothing?.recordCount }}</span>批；书籍<span class="highlight-span">{{ book?.recordCount }}</span>批！我们再次为您献上最真挚的敬意，感谢您为孩子们做出的贡献！
        </div>
        <div class="issuing-info">
            <div class="issuing-unit">发证单位：Left - Behind children</div>
            <div class="issuer">发证人：管理员</div>
        </div>
        <div class="floating-elements">
            <span class="circle circle-1"></span>
            <span class="circle circle-2"></span>
            <span class="circle circle-3"></span>
            <span class="star star-1"></span>
            <span class="star star-2"></span>
            <span class="star star-3"></span>
        </div>
        <div class="shadow-overlay"></div>
        <div class="glow-overlay"></div>
        <img src="../../../assets/lbc-gz.png">
    </div>
</template>

<script lang="ts" setup>
import logoLBC from '@/components/logoLBC.vue';
import { useUserDetailStore } from '@/stores/user/userDetailStore';
import { storeToRefs } from 'pinia';
import { onMounted, ref } from 'vue';
import { donationStatistics } from './ts/userMethods';
const userDetailStore = useUserDetailStore()
const { userDetail } = storeToRefs(userDetailStore)
const clothing = ref()
const book = ref()
onMounted(async () => {
    const res = await donationStatistics()
    clothing.value = res[1]
    book.value = res[0]
})
</script>

<style scoped>
.certificate-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    min-height: 100vh;
    background-color: white;
    position: relative;
    perspective: 1000px;
    overflow: hidden;
    box-shadow: 0 0 50px rgba(148.6, 212.3, 117.1, 0.8);
    border-radius: 30px;
    margin: 0 auto;
}

@keyframes borderBlink {
    0% {
        opacity: 0.3;
        transform: scale(0.98);
    }
    100% {
        opacity: 1;
        transform: scale(1.02);
    }
}

.certificate-title {
    font-size: 80px;
    font-family: 'Arial Black', sans-serif; /* 使用更粗体的字体 */
    background: linear-gradient(45deg, rgb(148.6, 212.3, 117.1), black);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    text-shadow: 0 0 20px rgba(148.6, 212.3, 117.1, 0.9), 0 0 40px rgba(148.6, 212.3, 117.1, 0.7); /* 增强文字阴影效果 */
    margin-bottom: 30px; /* 增加底部间距 */
    animation: titleShimmer 2s infinite linear, titleFloat 3s infinite ease-in-out;
    transform-style: preserve-3d;
    letter-spacing: 10px; /* 增加字母间距 */
    font-weight: 900;
    filter: drop-shadow(0 0 25px rgba(148.6, 212.3, 117.1, 0.8)); /* 增强投影效果 */
}

@keyframes titleShimmer {
    0% {
        background-position: 0 0;
    }
    100% {
        background-position: 200% 0;
    }
}

@keyframes titleFloat {
    0%,
    100% {
        transform: translateY(0);
    }
    50% {
        transform: translateY(-15px); /* 增加浮动幅度 */
    }
}

.recipient {
    font-size: 32px; /* 增大字体大小 */
    font-family: 'Helvetica Neue', sans-serif; /* 使用更现代的字体 */
    color: black;
    margin-bottom: 15px; /* 增加底部间距 */
    transform: translateZ(30px);
    text-shadow: 2px 2px 8px rgba(0, 0, 0, 0.3); /* 增强文字阴影效果 */
    filter: drop-shadow(0 0 12px rgba(0, 0, 0, 0.2)); /* 增强投影效果 */
    width: 80%;
    text-indent: 2em;
}

.message {
    font-size: 20px; /* 增大字体大小 */
    font-family: 'Georgia', serif; /* 使用衬线字体增加可读性 */
    color: black;
    width: 80%;
    text-align: left;
    margin-bottom: 30px; /* 增加底部间距 */
    line-height: 2; /* 增加行高 */
    transform: translateZ(20px);
    text-shadow: 1px 1px 5px rgba(0, 0, 0, 0.3); /* 增强文字阴影效果 */
    filter: drop-shadow(0 0 10px rgba(0, 0, 0, 0.2)); /* 增强投影效果 */
    text-indent: 2em;
}

.issuing-info {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    font-family: 'Arial', sans-serif; /* 使用清晰的无衬线字体 */
    color: black;
    font-size: 18px; /* 增大字体大小 */
    transform: translateZ(25px);
    text-shadow: 1px 1px 5px rgba(0, 0, 0, 0.3); /* 增强文字阴影效果 */
    filter: drop-shadow(0 0 12px rgba(0, 0, 0, 0.2)); /* 增强投影效果 */
    width: 80%;
}

.issuing-unit,
.issuer {
    margin-bottom: 8px; /* 增加底部间距 */
}

.floating-elements {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    pointer-events: none;
}

.circle {
    position: absolute;
    border-radius: 50%;
    opacity: 0.3;
    animation: float 8s infinite ease-in-out;
    filter: drop-shadow(0 0 15px rgba(148.6, 212.3, 117.1, 0.6));
}

.circle-1 {
    width: 100px;
    height: 100px;
    background: linear-gradient(45deg, rgb(148.6, 212.3, 117.1), rgba(148.6, 212.3, 117.1, 0.2));
    top: 10%;
    left: 20%;
    animation-delay: 0s;
}

.circle-2 {
    width: 150px;
    height: 150px;
    background: linear-gradient(45deg, black, rgba(0, 0, 0, 0.2));
    top: 70%;
    left: 70%;
    animation-delay: 2s;
}

.circle-3 {
    width: 80px;
    height: 80px;
    background: linear-gradient(45deg, rgb(148.6, 212.3, 117.1), rgba(148.6, 212.3, 117.1, 0.2));
    top: 40%;
    left: 80%;
    animation-delay: 4s;
}

.star {
    position: absolute;
    width: 20px;
    height: 20px;
    background: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><polygon points="256 0 319.1 185.4 512 185.4 362.7 304.6 426.7 496 256 380.6 85.3 496 149.3 304.6 0 185.4 192.9 185.4 256 0" fill="rgb(148.6, 212.3, 117.1)"/></svg>');
    background-size: contain;
    opacity: 0.8;
    animation: starFloat 6s infinite ease-in-out, starBlink 2s infinite alternate;
}

.star-1 {
    top: 20%;
    left: 10%;
    animation-delay: 0s;
}

.star-2 {
    top: 60%;
    left: 30%;
    animation-delay: 1s;
}

.star-3 {
    top: 30%;
    left: 80%;
    animation-delay: 2s;
}

@keyframes float {
    0%,
    100% {
        transform: translate3d(0, 0, 0);
    }
    50% {
        transform: translate3d(20px, -30px, 10px);
    }
}

@keyframes starFloat {
    0%,
    100% {
        transform: translate3d(0, 0, 0);
    }
    50% {
        transform: translate3d(-10px, 20px, 0);
    }
}

@keyframes starBlink {
    0% {
        opacity: 0.2;
    }
    100% {
        opacity: 0.8;
    }
}

.shadow-overlay {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: radial-gradient(circle at center, rgba(0, 0, 0, 0.1) 0%, rgba(0, 0, 0, 0) 70%);
    pointer-events: none;
    animation: shadowPulse 6s infinite ease-in-out;
}

@keyframes shadowPulse {
    0%,
    100% {
        transform: scale(1);
        opacity: 0.3;
    }
    50% {
        transform: scale(1.2);
        opacity: 0.6;
    }
}

.glow-overlay {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: radial-gradient(circle at center, rgba(148.6, 212.3, 117.1, 0.2) 0%, rgba(0, 0, 0, 0) 70%);
    pointer-events: none;
    animation: glowPulse 4s infinite ease-in-out;
}

@keyframes glowPulse {
    0%,
    100% {
        transform: scale(1);
        opacity: 0.2;
    }
    50% {
        transform: scale(1.1);
        opacity: 0.5;
    }
}

img {
    position: absolute;
    right: 2%;
    bottom: 1%;
    opacity: 0.7
}

.highlight-span {
    color: #e74c3c; /* 红色字体，更醒目 */
    font-weight: bold; /* 加粗字体 */
    text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.3); /* 添加文字阴影 */
}
</style>