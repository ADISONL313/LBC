<template>
    <div style="justify-items: center;margin-top: 20vh;">
        <el-card>
            <template #header>
                <h2 style="color: rgb(148.6, 212.3, 117.1);">
                    管理员登录</h2>
            </template>

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
                    <el-input v-model="password" type="password" placeholder="请输入密码" size="large">
                        <template #suffix>
                            <el-icon size="large">
                                <Lock />
                            </el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item>
                    <el-button size="large" style="width: 100%;color: black;border: none;" type="success" @click="login">登录</el-button>
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>
<script lang="ts" setup>
//响应式对象
import { ref } from 'vue';
import { loginFormVerify } from './tsMethods/tsMethods';
import { post } from '@/axiosConfig';
import { md5 } from 'js-md5';
import { ElMessage } from 'element-plus';
//邮箱、密码
const password = ref('')
const mail = ref('')
//登录方法
import { useRouter } from 'vue-router';
const router=useRouter()
const login = () => {
    //验证表单
    if (loginFormVerify(mail.value, password.value) === false) {
        return
    }
    //请求密码登录接口
    post('/user/signInByPwd', { mail: mail.value, password: md5(password.value) }).then((res) => {
        ElMessage(res.message)
        if (res.message === '成功') {
            console.log(res.data)
            sessionStorage.setItem('token', res.data.token)
            sessionStorage.setItem('mail', mail.value)
            sessionStorage.setItem('nickName',res.data.nickName)
            router.push('/home')
        }
    })
}

</script>

<style scoped>
.el-card__header {
    padding: 0px;
    border-bottom: 1px solid #67C23A;
}

.el-card {
    background-color:black;
    width: 500px;
}
h2{
    margin: 0;
    color: white;
}
</style>