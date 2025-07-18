<template>
    <el-row :gutter="0">
        <el-col :span="12" style="font-size: small;" @click="router.push('/forgetPwd')">忘记密码？</el-col>
        <el-col :span="12" style="text-align: right;font-size: small;" @click="router.push('/register')">没有账号？去注册！</el-col>
    </el-row>
</template>
<script lang="ts" setup>
import { useRouter } from 'vue-router';
const router=useRouter()
</script>
<style scoped>
.el-col{
    cursor: pointer;
}
</style>