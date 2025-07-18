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
        <el-form-item v-show="passWdLogin">
            <el-input v-model="password" type="password" placeholder="请输入密码" size="large">
                <template #suffix>
                    <el-icon size="large">
                        <Lock />
                    </el-icon>
                </template>
            </el-input>
        </el-form-item>
        <el-form-item v-show="codeLogin">
            <codeInputComponent />
        </el-form-item>
        <el-row style="padding-bottom: 1vh;">
            <el-col :span="24" style="text-align: right;">
                <el-button v-show="passWdLogin" @click="mailMethod" link
                    style="color:black;font-size: small;">邮箱验证码登录</el-button>
                <el-button v-show="codeLogin" @click="pwdMethod" link
                    style="color:black;font-size: small">密码登录</el-button>
            </el-col>
        </el-row>
        <el-form-item label="">
            <el-button style="width: 100%;background-color: black;" size="large" type="success"
                @click="login">登录</el-button>
        </el-form-item>
    </el-form>
</template>
<script lang="ts" setup>
//导入邮箱验证码组件
import codeInputComponent from './codeInputComponent.vue';
//get,post请求方法
import { get, post } from '@/axiosConfig';
//el弹窗
import { ElMessage } from 'element-plus';
//pinia登录数据存储方法
import { useLoginStore } from '@/stores/user/loginStore'
import { storeToRefs } from 'pinia';
const loginStore = useLoginStore()
const { count, verifyCode, mail } = storeToRefs(loginStore)
//数据响应化
import { ref } from 'vue';
//登录表单信息
//密码
const password = ref('')
//密码方式登录状态
const passWdLogin = ref(true)
//验证码登录状态
const codeLogin = ref(false)
//切换到密码登录方法
const pwdMethod = () => {
    codeLogin.value = false
    passWdLogin.value = true
}
//切换到邮箱验证码登录方法
const mailMethod = () => {
    passWdLogin.value = false
    codeLogin.value = true
}
//登录按钮，登录方法
import { loginFormVerify } from './tsMethods/tsMethods';
//导入md5加密包
import { md5 } from 'js-md5';
//邮箱验证正则
const regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
//导入路由
import { useRouter } from 'vue-router';
const router = useRouter()
//登录方法
const login = () => {
    if (codeLogin.value === true) {
        //验证码登录方式
        if (loginFormVerify(mail.value, verifyCode.value) === false) {
            return
        }
        //判断邮箱地址是否正确
        if (regex.test(mail.value) === false) {
            ElMessage('邮箱地址错误')
            return
        }
        // 验证码方式登录,数据提交到验证码登录接口
        post('/user/signInByCode', { mail: mail.value, code: verifyCode.value }).then((res) => {
            //将后台提示消息提示给用户
            ElMessage(res.message)
            if (res.message === '成功') {
                //设置token方便后续接口请求，只有登录成功才给token
                sessionStorage.setItem('token', res.data.token)
                sessionStorage.setItem('mail', mail.value)
                sessionStorage.setItem('nickName', res.data.nickName)
                //登录成功跳转到首页
                router.push('/home')
            }
        })
    } else {
        //密码登录方式
        //密码登录方式空验证
        if (loginFormVerify(mail.value, password.value) === false) {
            return
        }
        //判断邮箱地址是否正确
        if (regex.test(mail.value) === false) {
            ElMessage('邮箱地址错误')
            return
        }
        //将前端数据提交到密码登录的接口
        post('/user/signInByPwd', { mail: mail.value, password: md5(password.value) }).then((res) => {
            ElMessage(res.message)
            //判断登录是否成功
            //将后台提示消息提示给用户
            if (res.message === '成功') {
                //设置token方便后续接口请求，只有登录成功才给token
                sessionStorage.setItem('token', res.data.token)
                console.log(sessionStorage.getItem('token'))
                sessionStorage.setItem('mail', mail.value)
                sessionStorage.setItem('nickName', res.data.nickName)
                //登录成功跳转到首页
                router.push('/home')
            }
        })
    }
}
</script>