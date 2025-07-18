<template>
    <el-form-item>
        <el-input placeholder="驳回原因" v-model="rejectionReason" type="textarea"></el-input>
    </el-form-item>
    <div style="text-align: right;padding-bottom: 20px;">
        <el-button type="danger" style="width: 20vh;" @click="rejection">驳回</el-button>
    </div>
</template>
<script lang="ts" setup>
import { post } from '@/axiosConfig';
import { ElMessage } from 'element-plus';
import { ref } from 'vue';

const props=defineProps({
    newId:String
})
//驳回原因
const rejectionReason=ref('')
//驳回方法
const rejection=()=>{
    console.log(props.newId)
    post('/news/rejectionNew',{newId:props.newId,process:2,rejectionReason:rejectionReason.value}).then((res)=>{
        ElMessage(res.message)
    })
}
</script>