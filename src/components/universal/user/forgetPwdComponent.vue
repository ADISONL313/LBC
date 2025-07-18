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
            <el-button style="width: 100%;background-color: black;" size="large" type="success" @click="forget">修改</el-button>
        </el-form-item>
        <div style="text-align: center;font-size: small;cursor: pointer;" @click="router.back()">
            返回登录!
        </div>
    </el-form>
</template>
<script lang="ts" setup>
//导入路由方法
import { useRouter } from 'vue-router';
const router=useRouter()
//导入验证码发送组件
import codeInputComponent from './codeInputComponent.vue';
//pinia登录数据存储方法
import { useLoginStore } from '@/stores/user/loginStore'
import { storeToRefs } from 'pinia';
const loginStore = useLoginStore()
const { count, verifyCode, mail } = storeToRefs(loginStore)
//数据响应化
import { ref } from 'vue';
import { forgetPwdFormVerify } from './tsMethods/tsMethods';
import { ElMessage } from 'element-plus';
import { post } from '@/axiosConfig';
import { md5 } from 'js-md5';
//密码
const password=ref('')
//修改密码方法
//验证邮箱格式正则
const regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
const forget=()=>{
    //判断表单是否为空
    if(forgetPwdFormVerify(mail.value,password.value,verifyCode.value)===false){
        return
    }
    //提示用户邮箱是否正确
    if(regex.test(mail.value)===false){
        ElMessage("邮箱地址错误")
        return
    }
    console.log(verifyCode.value)
    //请求修改接口
    post(`/user/updatePwd?code=${verifyCode.value}`,{mail:mail.value,password:md5(password.value)}).then((res)=>{
        //修改信息提示给用户
        ElMessage(res.message)
    })
}
</script>