<template>
    <div v-if="token!==null" style="position: fixed;right: 1%;bottom: 40%;">
        <el-button :style="status>0?'background-color:red;color:white':''" :disabled="new Date(activity.activityStartTime) < new Date(getNowTime())" type="success"
            @click="rAR">{{ status > 0 ? "取消报名" : new
                Date(activity.activityStartTime)
                < new Date(getNowTime()) ? "报名截止" : "立即报名" }}</el-button>
    </div>
</template>
<script lang="ts" setup>
import { deleteRemove, get, post } from '@/axiosConfig';
import { onMounted, ref } from 'vue';
const token=sessionStorage.getItem('token')
//pinia
import { storeToRefs } from 'pinia';
import { useActivityStore } from '@/stores/activity/activityStore';
import { getNowTime } from '@/components/universal/user/tsMethods/tsMethods';
import { ElMessage } from 'element-plus';
import { v4 } from 'uuid';
const activityStore = useActivityStore()
const { activity } = storeToRefs(activityStore)
// 如果有需要可以在这里添加点击事件的逻辑
const status = ref()
const isAr = async (activityId: string, thisMail: string) => {
    get('/AR/getIsAR', { activityId: activityId, mail: thisMail }).then((res) => {
        status.value = res.data
    })
}
onMounted(() => {
    isAr(activity.value.activityId, sessionStorage.getItem('mail') + '')
})
const rAR = () => {
    if (status.value === 0) {
        post('/AR/activityRegistration', {
            registrationId: v4(),
            mail: sessionStorage.getItem('mail'),
            activityId: activity.value.activityId
        }).then((res) => {
            ElMessage.success(res)
            if (res.message === '成功') {
                status.value = 1
            }
        })
    }else{
        deleteRemove(`/AR/delAR?mail=${sessionStorage.getItem('mail')}&activityId=${activity.value.activityId}`).then((res)=>{
            ElMessage(res.message)
            if(res.message==='成功'){
                status.value=0
            }
        })
    }
}
</script>

<style scoped>
.el-button {
    /* 增加垂直方向的内边距，让按钮更高 */
    padding: 25px 30px;
    background-color: rgb(148.6, 212.3, 117.1);
    border: none;
    color: black;
    font-family: 'Montserrat', sans-serif;
    font-weight: bold;
    font-size: 20px;
    border-radius: 12px;
    transition: all 0.3s ease;
    box-shadow:
        0 0 15px rgba(148.6, 212.3, 117.1, 0.7),
        0 0 30px rgba(148.6, 212.3, 117.1, 0.5),
        0 0 45px rgba(148.6, 212.3, 117.1, 0.3);
    position: relative;
    overflow: hidden;
    z-index: 2;
}

.el-button::before {
    content: '';
    position: absolute;
    top: 0;
    left: -100%;
    width: 100%;
    height: 100%;
    background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3));
    transition: left 0.3s ease;
    z-index: 3;
}

.el-button::after {
    content: '';
    position: absolute;
    top: -8px;
    left: -8px;
    right: -8px;
    bottom: -8px;
    border: 3px solid rgba(148.6, 212.3, 117.1, 0.8);
    border-radius: 18px;
    opacity: 0;
    transition: opacity 0.3s ease;
    z-index: 1;
}

.el-button:hover {
    background-color: rgba(148.6, 212.3, 117.1, 0.9);
    /* 去掉倾斜动画，只保留缩放 */
    transform: scale(1.12);
    box-shadow:
        0 0 30px rgba(148.6, 212.3, 117.1, 0.9),
        0 0 60px rgba(148.6, 212.3, 117.1, 0.7),
        0 0 90px rgba(148.6, 212.3, 117.1, 0.5);
}

.el-button:hover::before {
    left: 100%;
}

.el-button:hover::after {
    opacity: 1;
}

.el-button:disabled {
    background-color: black;
    color: rgb(148.6, 212.3, 117.1);
    box-shadow: none;
    cursor: not-allowed;
}

.el-button:disabled::before,
.el-button:disabled::after {
    display: none;
}
</style>