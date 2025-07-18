<template>
    <el-input type="number" v-model="verifyCode" placeholder="请输入验证码" size="large">
        <template #append>
            <el-button link @click="getCode" :disabled="count < 60">{{ count >= 60 ? "获取验证码" : count +
                "秒后重新获取"
                }}</el-button>
        </template>
    </el-input>
</template>
<script lang="ts" setup>
//导入axios封装好的get方法
import { get } from '@/axiosConfig';
//调用pinia定义好的定时功能
import {useLoginStore} from '@/stores/user/loginStore'
import { ElMessage } from 'element-plus';
import { storeToRefs } from 'pinia';
const loginStore=useLoginStore()
const {count,verifyCode,mail}=storeToRefs(loginStore)
//获取验证码方法
const regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
//获取验证码方法
const getCode=()=>{
    if (mail.value === '') {
        ElMessage("请填写邮箱")
    } else if (regex.test(mail.value)) {
        loginStore.startCountdown()
    get("/user/code", { mail: mail.value }).then((res) => {
            console.log(res)
        })
    } else {
        ElMessage("邮箱错误")
    }
}
</script>