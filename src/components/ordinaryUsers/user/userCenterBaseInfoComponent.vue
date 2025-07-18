<template>
    <div class="baseInfo-box">
        <div class="bj-box">
            <el-row class="info-item">
                <el-col :span="8">
                    <img v-if="userDetail.idPhoto !== null" class="head-img" :src="baseUrl+userDetail.userDetailId+'/'+userDetail.idPhoto" />
                    <img v-else class="head-img" src="../../../assets/image.png" />
                </el-col>
                <el-col :span="16" style="padding-left: 2vh;">
                    <div class="nickName">{{ nickName }}</div>
                    <div class="user-info">
                        <span>
                            性别：{{ userDetail.gender }}
                        </span>
                        <span>
                            <el-icon>
                                <Message />
                            </el-icon>
                            ：{{ userDetail.mail }}
                        </span>
                    </div>
                </el-col>
            </el-row>
        </div>
        <div class="input-section" style="padding: 5vh;">
            <el-input :disabled="showInputBorder" placeholder="个人简介" style="width: 95%;"
                v-model="thisIntroduction"></el-input>
            <el-button link @click="updateIntroduction">
                <el-icon v-if="showInputBorder === false">
                    <Check />
                </el-icon>
                <el-icon v-else>
                    <Edit />
                </el-icon>
            </el-button>
        </div>
    </div>
</template>

<script setup>
const baseUrl='http://47.108.29.91:9000/user/'
import { computed, onBeforeMount, onMounted, ref } from 'vue';
import { Check, Edit } from '@element-plus/icons-vue';
//导入pinia持久化的个人信息
import { useUserDetailStore } from '@/stores/user/userDetailStore';
import { storeToRefs } from 'pinia';
import { post } from '@/axiosConfig';
import { ElMessage } from 'element-plus';
const userDetailStore = useUserDetailStore()
const { userDetail } = storeToRefs(userDetailStore)
//获取昵称
const nickName = sessionStorage.getItem("nickName")

const thisIntroduction = ref();
const showInputBorder = ref(true);
//修改个人简介
const updateIntroduction = () => {
    showInputBorder.value = !showInputBorder.value;
    if (showInputBorder.value) {
        post('/user/updateIntroduction', { mail: sessionStorage.getItem('mail'), introduction: thisIntroduction.value }).then((res) => {
            ElMessage(res.message)
        })
    }
};
onMounted(()=>{
    thisIntroduction.value=userDetail.value.introduction
})
</script>

<style scoped>
.baseInfo-box {
    height: 45vh;
    background: linear-gradient(135deg, rgba(255, 255, 255, 0.9), rgba(255, 255, 255, 0.7));
    box-shadow: 0 0 30px rgba(148.6, 212.3, 117.1, 0.8);
    border-radius: 20px;
    overflow: hidden;
    position: relative;
    animation: fadeIn 0.5s ease-out, glow 2s infinite alternate;
    transition: box-shadow 0.3s ease;
}

@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(-20px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
}

@keyframes glow {
    from {
        box-shadow: 0 0 30px rgba(148.6, 212.3, 117.1, 0.8);
    }

    to {
        box-shadow: 0 0 40px rgba(148.6, 212.3, 117.1, 1);
    }
}

.baseInfo-box:hover {
    box-shadow: 0 0 50px rgba(148.6, 212.3, 117.1, 1);
}

.info-item {
    position: absolute;
    bottom: -10%;
    left: 3%;
    display: flex;
    align-items: center;
    z-index: 2;
    animation: slideInLeft 0.5s ease-out;
}

@keyframes slideInLeft {
    from {
        opacity: 0;
        transform: translateX(-20px);
    }

    to {
        opacity: 1;
        transform: translateX(0);
    }
}

.head-img {
    height: 15vh;
    width: 15vh;
    object-fit: cover;
    border-radius: 100%;
    border: 5px solid rgba(255, 255, 255, 0.9);
    box-shadow: 0 0 20px rgba(148.6, 212.3, 117.1, 0.6), 0 0 0 5px rgba(148.6, 212.3, 117.1, 0.2);
    transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.head-img:hover {
    transform: scale(1.1);
    box-shadow: 0 0 30px rgba(148.6, 212.3, 117.1, 0.8), 0 0 0 8px rgba(148.6, 212.3, 117.1, 0.3);
}

.bj-box {
    background-image: url(../../../assets/ea660f3b9fea660f3b9f.jpg);
    height: 30vh;
    background-color: gray;
    position: relative;
    background-size: cover;
    background-position: center;
    filter: brightness(0.85) blur(1px);
    transition: filter 0.3s ease;
}

.bj-box:hover {
    filter: brightness(1) blur(0);
}

.nickName {
    color: #222;
    font-size: 32px;
    font-weight: bold;
    padding: 1vh 0;
    text-shadow: 0 0 10px rgba(148.6, 212.3, 117.1, 0.5), 0 0 20px rgba(148.6, 212.3, 117.1, 0.3);
    animation: fadeInText 0.5s ease-out;
}

@keyframes fadeInText {
    from {
        opacity: 0;
        transform: translateY(-10px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.user-info {
    color: #ffffff;
    font-size: small;
    text-shadow: 0 0 5px rgba(0, 0, 0, 0.5);
    animation: fadeInText 0.5s ease-out;
}

.user-info span {
    margin-right: 15px;
}

.input-section {
    display: flex;
    align-items: center;
    justify-content: space-between;
    animation: fadeIn 0.5s ease-out;
}

.el-input {
    border: 2px solid rgba(148.6, 212.3, 117.1, 0.3);
    border-radius: 10px;
    transition: border-color 0.3s ease, box-shadow 0.3s ease;
    background-color: rgba(255, 255, 255, 0.8);
}

.el-input:focus-within {
    border-color: rgb(148.6, 212.3, 117.1);
    box-shadow: 0 0 15px rgba(148.6, 212.3, 117.1, 0.5);
}

.el-button {
    color: rgb(148.6, 212.3, 117.1);
    transition: color 0.3s ease, transform 0.3s ease, background-color 0.3s ease;
    border-radius: 50%;
    padding: 10px;
    background-color: rgba(255, 255, 255, 0.3);
}

.el-button:hover {
    color: rgba(148.6, 212.3, 117.1, 0.8);
    transform: scale(1.1);
    background-color: rgba(255, 255, 255, 0.5);
}
</style>