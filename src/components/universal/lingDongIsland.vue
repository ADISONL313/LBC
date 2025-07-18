<template>
    <div class="lingDong-box">
        <div class="lingDong-circle"></div>
        <el-button class="back-btn" type="success" @click="router.back()">返回</el-button>
    </div>
</template>
<script lang="ts" setup>
import { useRouter } from 'vue-router';
const router = useRouter()
</script>
<style scoped>
.el-popper.is-light,
.el-popper.is-light>.el-popper__arrow:before {
    place-items: center;
    background: var(--el-text-color-regular);
}

.lingDong-box {
    position: fixed;
    top: 100px;
    right: 1%;
    background-color: rgba(0, 0, 0, 0.8);
    border-radius: 10vh;
    display: flex;
    align-items: center;
    padding: 5px 15px;
    box-shadow: 0 0 20px rgba(148.6, 212.3, 117.1, 0.6);
    animation: fadeInSlide 0.5s ease-out;
    transition: all 0.3s ease;
    overflow: hidden;
}

.lingDong-box:hover {
    box-shadow: 0 0 30px rgba(148.6, 212.3, 117.1, 0.8);
    transform: scale(1.05);
}

.lingDong-circle {
    height: 20px;
    width: 20px;
    background: radial-gradient(circle at center, rgba(255, 255, 255, 0.8), rgb(148.6, 212.3, 117.1));
    margin: 5px;
    border-radius: 100%;
    box-shadow:
        0 0 10px rgba(148.6, 212.3, 117.1, 0.8),
        0 0 20px rgba(148.6, 212.3, 117.1, 0.6),
        0 0 30px rgba(148.6, 212.3, 117.1, 0.4),
        0 0 40px rgba(148.6, 212.3, 117.1, 0.2);
    animation:
        pulse 1.2s infinite,
        rotate 3s linear infinite,
        moveInOrbit 5s linear infinite,
        colorChange 4s infinite,
        bounceOrbit 3s ease-in-out infinite;
    position: relative;
}

.lingDong-circle::before {
    content: '';
    position: absolute;
    top: -10px;
    left: -10px;
    right: -10px;
    bottom: -10px;
    border-radius: 50%;
    border: 2px solid rgba(148.6, 212.3, 117.1, 0.5);
    animation:
        expandShrink 2s ease-in-out infinite,
        borderColorChange 4s infinite,
        rotateReverse 3s linear infinite;
}

.lingDong-circle::after {
    content: '';
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 6px;
    height: 6px;
    background-color: rgba(255, 255, 255, 0.9);
    border-radius: 50%;
    box-shadow:
        0 0 10px rgba(255, 255, 255, 0.8),
        0 0 20px rgba(255, 255, 255, 0.6);
    animation:
        innerPulse 0.8s infinite,
        innerRotate 3s linear infinite,
        innerBounce 3s ease-in-out infinite;
}

.back-btn {
    margin: 5px;
    border-radius: 10vh;
    background: linear-gradient(45deg, rgba(148.6, 212.3, 117.1, 0.6), rgba(148.6, 212.3, 117.1, 0.8));
    border: none;
    color: white;
    font-weight: bold;
    box-shadow:
        0 0 10px rgba(148.6, 212.3, 117.1, 0.5),
        0 0 20px rgba(148.6, 212.3, 117.1, 0.3);
    transition: all 0.3s ease;
    position: relative;
    overflow: hidden;
    animation: buttonPulse 2s infinite;
}

.back-btn:hover {
    background: linear-gradient(45deg, rgba(148.6, 212.3, 117.1, 0.8), rgba(148.6, 212.3, 117.1, 1));
    box-shadow:
        0 0 20px rgba(148.6, 212.3, 117.1, 0.7),
        0 0 30px rgba(148.6, 212.3, 117.1, 0.5);
    transform: scale(1.1);
}

.back-btn::before {
    content: '';
    position: absolute;
    top: 0;
    left: -100%;
    width: 100%;
    height: 100%;
    background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.4), transparent);
    transition: all 0.6s ease;
    animation: shine 3s linear infinite;
}

@keyframes fadeInSlide {
    from {
        opacity: 0;
        transform: translateX(-20px);
    }

    to {
        opacity: 1;
        transform: translateX(0);
    }
}

@keyframes pulse {
    0% {
        transform: scale(1);
        box-shadow:
            0 0 10px rgba(148.6, 212.3, 117.1, 0.8),
            0 0 20px rgba(148.6, 212.3, 117.1, 0.6),
            0 0 30px rgba(148.6, 212.3, 117.1, 0.4),
            0 0 40px rgba(148.6, 212.3, 117.1, 0.2);
    }

    50% {
        transform: scale(1.2);
        box-shadow:
            0 0 20px rgba(148.6, 212.3, 117.1, 1),
            0 0 30px rgba(148.6, 212.3, 117.1, 0.8),
            0 0 40px rgba(148.6, 212.3, 117.1, 0.6),
            0 0 50px rgba(148.6, 212.3, 117.1, 0.4);
    }

    100% {
        transform: scale(1);
        box-shadow:
            0 0 10px rgba(148.6, 212.3, 117.1, 0.8),
            0 0 20px rgba(148.6, 212.3, 117.1, 0.6),
            0 0 30px rgba(148.6, 212.3, 117.1, 0.4),
            0 0 40px rgba(148.6, 212.3, 117.1, 0.2);
    }
}

@keyframes rotate {
    from {
        transform: rotate(0deg);
    }

    to {
        transform: rotate(360deg);
    }
}

@keyframes moveInOrbit {
    0% {
        transform: translateX(0) rotate(0deg);
    }

    25% {
        transform: translateX(15px) rotate(90deg);
    }

    50% {
        transform: translateX(0) rotate(180deg);
    }

    75% {
        transform: translateX(-15px) rotate(270deg);
    }

    100% {
        transform: translateX(0) rotate(360deg);
    }
}

@keyframes expandShrink {
    0% {
        transform: scale(1);
        opacity: 0.5;
    }

    50% {
        transform: scale(1.5);
        opacity: 0;
    }

    100% {
        transform: scale(1);
        opacity: 0.5;
    }
}

@keyframes colorChange {
    0% {
        background: radial-gradient(circle at center, rgba(255, 255, 255, 0.8), rgb(148.6, 212.3, 117.1));
    }

    25% {
        background: radial-gradient(circle at center, rgba(255, 255, 255, 0.8), rgb(160, 225, 130));
    }

    50% {
        background: radial-gradient(circle at center, rgba(255, 255, 255, 0.8), rgb(170, 235, 140));
    }

    75% {
        background: radial-gradient(circle at center, rgba(255, 255, 255, 0.8), rgb(160, 225, 130));
    }

    100% {
        background: radial-gradient(circle at center, rgba(255, 255, 255, 0.8), rgb(148.6, 212.3, 117.1));
    }
}

@keyframes borderColorChange {
    0% {
        border-color: rgba(148.6, 212.3, 117.1, 0.5);
    }

    25% {
        border-color: rgba(160, 225, 130, 0.5);
    }

    50% {
        border-color: rgba(170, 235, 140, 0.5);
    }

    75% {
        border-color: rgba(160, 225, 130, 0.5);
    }

    100% {
        border-color: rgba(148.6, 212.3, 117.1, 0.5);
    }
}

@keyframes innerPulse {
    0% {
        transform: translate(-50%, -50%) scale(1);
        box-shadow:
            0 0 10px rgba(255, 255, 255, 0.8),
            0 0 20px rgba(255, 255, 255, 0.6);
    }

    50% {
        transform: translate(-50%, -50%) scale(1.3);
        box-shadow:
            0 0 15px rgba(255, 255, 255, 1),
            0 0 25px rgba(255, 255, 255, 0.8);
    }

    100% {
        transform: translate(-50%, -50%) scale(1);
        box-shadow:
            0 0 10px rgba(255, 255, 255, 0.8),
            0 0 20px rgba(255, 255, 255, 0.6);
    }
}

@keyframes innerRotate {
    from {
        transform: translate(-50%, -50%) rotate(0deg);
    }

    to {
        transform: translate(-50%, -50%) rotate(360deg);
    }
}

@keyframes innerBounce {

    0%,
    100% {
        transform: translate(-50%, -50%) translateY(0);
    }

    50% {
        transform: translate(-50%, -50%) translateY(-5px);
    }
}

@keyframes bounceOrbit {

    0%,
    100% {
        transform: translateY(0);
    }

    50% {
        transform: translateY(-8px);
    }
}

@keyframes rotateReverse {
    from {
        transform: rotate(0deg);
    }

    to {
        transform: rotate(-360deg);
    }
}

@keyframes buttonPulse {
    0% {
        transform: scale(1);
        box-shadow:
            0 0 10px rgba(148.6, 212.3, 117.1, 0.5),
            0 0 20px rgba(148.6, 212.3, 117.1, 0.3);
    }

    50% {
        transform: scale(1.05);
        box-shadow:
            0 0 15px rgba(148.6, 212.3, 117.1, 0.7),
            0 0 25px rgba(148.6, 212.3, 117.1, 0.5);
    }

    100% {
        transform: scale(1);
        box-shadow:
            0 0 10px rgba(148.6, 212.3, 117.1, 0.5),
            0 0 20px rgba(148.6, 212.3, 117.1, 0.3);
    }
}

@keyframes shine {
    0% {
        left: -100%;
    }

    100% {
        left: 100%;
    }
}
</style>