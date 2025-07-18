<template>
    <div class="act-box">
        <div class="act-title">
            {{ activity.activityName }}
        </div>
        <div class="act-time">
            <span>{{ "开始时间：" + activity.activityStartTime }}</span>
            <span>{{ "结束时间：" + activity.activityEndTime }}</span>
            <span>{{ "需要人数：" + activity.participantsNumber }}</span>
        </div>
        <div v-if="activity.activityImg !== null && activity.activityImg !== ''" class="act-img-one">
            <img :src="baseUrl + activity.activityId + '/' + activity.activityImg.split(';')[0]" />
        </div>
        <div class="act-content">
            {{ activity.activityContent }}
        </div>
        <div class="act-map" v-if="true">
            <mapViewComponent />
        </div>
        <div v-for="(i, index) in activity.activityImg.split(';').slice(1, -1)" :key="index">
            <img :src="baseUrl + activity.activityId + '/' + i" />
        </div>
        <div class="act-footer">
            <logoLBC />
        </div>
    </div>
    <div v-if="btnStatus">
        <userRegistrationActivityStatusComponent />
    </div>
    <div v-if="(new Date(activity.activityStartTime) < new Date(getNowTime())) && (activityRecord !== null)">
        <viewActivityRecordComponent :activityId="activity.activityId" />
    </div>
</template>

<script lang="ts" setup>
import logoLBC from '@/components/logoLBC.vue';
const token = sessionStorage.getItem('token')
import viewActivityRecordComponent from './viewActivityRecordComponent.vue';
import mapViewComponent from './mapViewComponent.vue';
import userRegistrationActivityStatusComponent from './userRegistrationActivityStatusComponent.vue';
const baseUrl = 'http://47.108.29.91:9000/activity/'
//pinia
import { storeToRefs } from 'pinia';
import { useActivityStore } from '@/stores/activity/activityStore';
const activityStore = useActivityStore()
const { activity } = storeToRefs(activityStore)
import { useSearchStore } from '@/stores/universal/searchStore';
import { getNowTime } from '@/components/universal/user/tsMethods/tsMethods';
import { onMounted } from 'vue';
const searchStore = useSearchStore()
const { btnStatus } = storeToRefs(searchStore)

import { useActivityRecordStore } from '@/stores/activity/activityRecordStore';
import { getActivityRecord } from '@/components/administrators/activity/ts/adminActivityMethods';
const activityRecordStore = useActivityRecordStore()
const { activityRecord } = storeToRefs(activityRecordStore)
onMounted(async () => {
    const res = await getActivityRecord(activity.value.activityId)
    activityRecord.value = res
    console.log(activityRecord.value)
})
</script>

<style scoped>
/* 活动盒子整体样式 */
.act-box {
    background-color: white;
    border-radius: 15px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    padding: 30px;
    margin: 20px auto;
    max-width: 800px;
    position: relative;
    overflow: hidden;
    background: linear-gradient(45deg, rgba(148.6, 212.3, 117.1, 0.1), rgba(148.6, 212.3, 117.1, 0.05));
}

/* 活动标题样式 */
.act-title {
    font-family: 'Poppins', sans-serif;
    color: black;
    font-size: 32px;
    font-weight: bold;
    margin-bottom: 20px;
    text-align: left;
    /* 标题改为左对齐以实现首行缩进效果 */
    border-bottom: 2px solid rgba(148.6, 212.3, 117.1, 0.4);
    padding-bottom: 10px;
    text-indent: 2em;
    /* 标题首行缩进2个字符 */
}

/* 活动时间和人数信息样式 */
.act-time {
    display: flex;
    justify-content: space-around;
    font-family: 'Roboto', sans-serif;
    color: black;
    font-size: 18px;
    margin-bottom: 20px;
    flex-wrap: wrap;
    /* 当空间不足时换行 */
    gap: 10px;
    /* 元素之间的间距 */
}

.act-time span {
    background-color: rgba(148.6, 212.3, 117.1, 0.2);
    padding: 10px 20px;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

/* 首张图片样式及动画 */
.act-img-one {
    margin-bottom: 20px;
    overflow: hidden;
}

.act-img-one img {
    width: 100%;
    height: auto;
    border-radius: 15px;
    object-fit: cover;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    border: 2px solid rgba(148.6, 212.3, 117.1, 0.2);
    transform: scale(1);
    transition: transform 0.3s ease;
}

.act-img-one img:hover {
    transform: scale(1.05);
}

/* 活动内容样式 */
.act-content {
    
    font-family: 'Montserrat', sans-serif;
    color: black;
    font-size: 16px;
    line-height: 1.6;
    margin-bottom: 20px;
    text-align: justify;
    /* 文本两端对齐 */
    text-indent: 2em;
    white-space:pre-wrap;
    /* 正文首行缩进2个字符 */
}

/* 其他图片样式及动画 */
.act-box img:not(.act-img-one img) {
    width: 100%;
    height: auto;
    border-radius: 15px;
    object-fit: cover;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    margin-bottom: 15px;
    border: 2px solid rgba(148.6, 212.3, 117.1, 0.2);
    transform: scale(1);
    transition: transform 0.3s ease;
}

.act-box img:not(.act-img-one img):hover {
    transform: scale(1.05);
}

/* 页脚样式 */
.act-footer {
    text-align: center;
    margin-top: 20px;
    border-top: 2px solid rgba(148.6, 212.3, 117.1, 0.2);
    padding-top: 20px;
}

/* 地图容器样式 */
.act-map {
    margin-bottom: 20px;
    border-radius: 15px;
    overflow: hidden;
    box-shadow: 0 4px 8px var(--shadow-color);
    border: 2px solid var(--border-color);
}
</style>