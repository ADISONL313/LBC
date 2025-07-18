<template>

    <el-form>
        <el-form-item>
            <el-input v-model="mail" placeholder="请输入邮箱" size="large">
                <template #suffix>
                    <el-icon size="large">
                        <Message />
                    </el-icon>
                </template>
            </el-input>
        </el-form-item>
        <el-form-item>
            <codeInputComponent />
        </el-form-item>
        <el-form-item>
            <el-input v-model="password" type="password" placeholder="请输入密码" size="large">
                <template #suffix>
                    <el-icon size="large">
                        <Lock />
                    </el-icon>
                </template>
            </el-input>
        </el-form-item>
        <el-form-item label="">
            <el-button style="width: 100%;background-color: black;" size="large" type="success"
                @click="register">注册</el-button>
        </el-form-item>
        <div style="text-align: center;font-size: small;cursor: pointer;" @click="router.push('/login')">
            返回登录!
        </div>
    </el-form>
</template>
<script lang="ts" setup>

//导入路由方法
import { useRouter } from 'vue-router';
const router = useRouter()
//导入邮箱验证码组件
import codeInputComponent from './codeInputComponent.vue';
//el弹窗
import { ElMessage } from 'element-plus';
//pinia登录数据存储方法
import { useLoginStore } from '@/stores/user/loginStore'
import { storeToRefs } from 'pinia';
const loginStore = useLoginStore()
const { count, verifyCode, mail } = storeToRefs(loginStore)
//数据响应化
import { ref } from 'vue';
import { forgetPwdFormVerify } from './tsMethods/tsMethods';
import { post } from '@/axiosConfig';
import { md5 } from 'js-md5';
import { v4 } from 'uuid';
//密码
const password = ref('')
//注册方法

//邮箱验证正则
const regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
const register = () => {
    if (forgetPwdFormVerify(mail.value, password.value, verifyCode.value) === false) {
        return
    }
    //判断邮箱地址是否正确
    if (regex.test(mail.value) === false) {
        ElMessage('邮箱地址错误')
        return
    }
    //请求注册接口
    post(`/user/signUp?code=${verifyCode.value}`,{mail:mail.value,nickName:'普通用户',password:md5(password.value),userDetailId:v4(),rejectionReason:''}).then((res)=>{
        //注册结果提示给用户
        ElMessage(res.message)
    })
}
</script>