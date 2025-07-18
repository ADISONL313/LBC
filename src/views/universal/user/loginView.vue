<template>
    <div style="justify-items: center;align-items: center;margin-top: 20vh;">
        <el-card>
            <template #header>
                <logoLBC />
            </template>
            <loginComponent />
            <forgetPwdAndRegisterComponent />
        </el-card>
    </div>
    <div class="admin-log">
        <img src="../../../assets/user/xiaohai.png"  class="login-btn-img" @click="router.push('/adminLogin')" />
    </div>
</template>
<script lang="ts" setup>
import logoLBC from '@/components/logoLBC.vue';
import loginComponent from '@/components/universal/user/loginComponent.vue'
import forgetPwdAndRegisterComponent from '@/components/universal/user/forgetPwdAndRegisterComponent.vue';
import { useRouter } from 'vue-router';
const router=useRouter()
</script>
<style scoped>
.el-card__header {
    padding: 0px;
    border-bottom: 1px solid #67C23A;
}

.el-card {
    background-color: rgb(148.6, 212.3, 117.1);
    width: 500px;
}
.admin-log {
    position: absolute;
    right: 5vh;
    bottom: 1vh;
    cursor: pointer;
}
.login-btn-img {
    width: 150px; /* 可根据实际情况调整图片大小 */
    height: auto;
    border-radius: 10px;
    transition: all 0.3s ease;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}

.login-btn-img::before {
    content: '';
    position: absolute;
    top: 0;
    left: -100%;
    width: 100%;
    height: 100%;
    background: linear-gradient(90deg, transparent, rgba(148.6, 212.3, 117.1, 0.3), transparent);
    transition: left 0.6s ease;
}

.login-btn-img:hover {
    transform: scale(1.05);
    filter: drop-shadow(0 0 20px rgba(148.6, 212.3, 117.1, 0.6));
}

.login-btn-img:hover::before {
    left: 100%;
}
</style>