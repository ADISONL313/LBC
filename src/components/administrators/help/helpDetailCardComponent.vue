<template>
    <div class="help-container">
        <div class="help-title">
            {{ props.helpData?.helpTitle }}
        </div>
        <div class="help-releaseTime">
            <span class="help-label">发布时间：</span>{{ props.helpData?.releaseTime }}
        </div>
        <div class="help-address">
            <span class="help-label">地址：</span>{{ props.helpData?.address }}
        </div>
        <div class="help-content">
            {{ props.helpData?.helpContent }}
        </div>
        <div class="help-phone">
            <span class="help-label">电话：</span>{{ props.helpData?.phone }}
        </div>
        <div class="help-emergencyPhone">
            <span class="help-label">紧急电话：</span>{{ props.helpData?.emergencyPhone }}
        </div>
        <div class="help-time">
            {{ props.helpData?.helpTime }}
        </div>
        <div class="help-status">
            <el-row>
                <el-col :span="12">{{ helpStatus === 0 ? "等待救援" : helpStatus === 1 ? "救助中" : "救助完成" }}</el-col>
                <el-col :span="12" style="text-align: right;">
                    <el-button v-if="mail==='admin'" type="success" @click="finish">完成</el-button></el-col>
            </el-row>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import { finishHelp } from './ts/adminHelpMethod';
const mail=ref(sessionStorage.getItem('mail'))
const props = defineProps({
    helpData: Object
})
const helpStatus = ref()
onMounted(() => {

    helpStatus.value = props.helpData?.helpStatus
})
const finish=()=>{
    finishHelp(props.helpData?.helpId)
    helpStatus.value=2
}
</script>

<style scoped>
.help-container {
    border-radius: 12px;
    padding: 24px;
    margin: 24px;
    background-color: rgba(148.6, 212.3, 117.1, 0.1);
    transition: all 0.3s ease;
    box-shadow: 0 4px 8px rgba(148.6, 212.3, 117.1, 0.1), 0 6px 20px rgba(148.6, 212.3, 117.1, 0.15);
}

.help-container:hover {
    transform: translateY(-8px);
    box-shadow: 0 16px 32px rgba(148.6, 212.3, 117.1, 0.3), 0 24px 48px rgba(148.6, 212.3, 117.1, 0.35);
}

.help-title {
    font-size: 28px;
    font-weight: bold;
    color: rgb(148.6, 212.3, 117.1);
    margin-bottom: 12px;
    text-shadow: 1px 1px 2px rgba(148.6, 212.3, 117.1, 0.2);
}

.help-releaseTime,
.help-address,
.help-content,
.help-phone,
.help-emergencyPhone,
.help-time {
    font-size: 18px;
    color: #555;
    margin-bottom: 10px;
    line-height: 1.5;
}

.help-label {
    font-weight: bold;
    color: rgb(148.6, 212.3, 117.1);
}

.help-status {
    font-size: 20px;
    font-weight: bold;
    color: rgb(148.6, 212.3, 117.1);
    margin-top: 16px;
    text-shadow: 1px 1px 2px rgba(148.6, 212.3, 117.1, 0.2);
}
</style>