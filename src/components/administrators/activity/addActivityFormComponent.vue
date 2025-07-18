<template>
    <div class="form-container">
        <el-form ref="form" class="custom-form">
            <el-form-item class="form-item">
                <el-input placeholder="活动名称" v-model="activity.activityName" style="font-size: larger;font-weight: bolder;"
                    type="textarea" class="input-field">
                </el-input>
            </el-form-item>
            <el-form-item class="form-item">
                <el-date-picker value-format="YYYY-MM-DD HH:mm:ss" v-model="activity.activityStartTime" clearable type="datetime"
                    placeholder="开始时间" class="input-field" />
                -
                <el-date-picker value-format="YYYY-MM-DD HH:mm:ss" v-model="activity.activityEndTime" clearable type="datetime"
                    placeholder="结束时间" class="input-field" />
                <el-divider direction="vertical" class="divider" />
                <el-input placeholder="人数限制" type="number" v-model="activity.participantsNumber" style="width: 20vh;"></el-input>
            </el-form-item>
            <div style="font-size: medium;font-weight: bold;">
                活动地址:{{ activity.address===""?"--请点击地图选择--":activity.address.split(";")[0] }}
            </div>
            <div v-if="true"  >
                <mapChooseComponent @send-data="handleMapData" />
            </div>
            <el-form-item class="form-item">
                <el-input placeholder="正文" v-model="activity.activityContent" type="textarea" :rows="20" class="input-field">
                </el-input>
            </el-form-item>
            <div v-if="props.changeOrAdd==='add'">
                <fileUploadComponent :uploadKey="'activity'" />
            </div>
            <div v-else>
                <processFilesComponent :fileType="'activity'" />
            </div>
            <!-- 示例按钮 -->
        </el-form>
    </div>
</template>
<script lang="ts" setup>
import processFilesComponent from '@/components/universal/processFilesComponent.vue';
const props=defineProps({
    changeOrAdd:String
})
//导入地图组件
import mapChooseComponent from '@/components/universal/mapChooseComponent.vue';
//导入pinia中的新闻数据
import { storeToRefs } from 'pinia'
import { useActivityStore } from '@/stores/activity/activityStore';
const activityStore=useActivityStore()
const {activity}=storeToRefs(activityStore)
import fileUploadComponent from '@/components/universal/fileUploadComponent.vue';

const handleMapData=(data:string)=>{
    activity.value.address=data
}
</script>

<style scoped>
.form-container {
    background-color: rgba(255, 255, 255, 0.9);
    border-radius: 15px;
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
    padding: 2vh;
    transition: box-shadow 0.3s ease, transform 0.3s ease;
}

.custom-form {
    padding: 20px;
}

.form-item {
    margin-bottom: 20px;
}

.input-field {
    border: 2px solid rgba(148.6, 212.3, 117.1, 0.5);
    border-radius: 5px;
    transition: all 0.3s ease;

    &::placeholder {
        color: rgba(0, 0, 0, 0.5);
    }

    &:focus {
        border-color: rgb(148.6, 212.3, 117.1);
        box-shadow: 0 0 5px rgba(148.6, 212.3, 117.1, 0.5);
    }
}

.divider {
    border-color: rgba(148.6, 212.3, 117.1, 0.3);
    margin: 0 10px;
}
</style>