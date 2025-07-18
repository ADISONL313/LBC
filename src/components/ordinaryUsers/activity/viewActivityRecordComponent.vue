<template>
    <div class="act-box" v-if="recordList.length!==0">
        <div class="act-title">
            活动记录
        </div>
        <div class="record-container" v-for="(item, index) in recordList" :key="index">
            <div class="r-title">
                记录{{ index + 1 }}
            </div>
            <div class="act-time">
                <span>{{ "记录时间：" + item?.recordTime }}</span>
            </div>

            <div v-if="item?.recordImage !== null && item?.recordImage !== ''" class="act-img-one">
                <img :src="baseUrl + item?.activityRecordId + '/' + item?.recordImage.split(';')[0]" />
            </div>

            <div class="act-content">
                {{ item?.recordContent }}
            </div>
            <div v-for="(i, index) in item?.recordImage.split(';').slice(1, -1)" :key="index">
                <img :src="baseUrl + item.activityRecordId + '/' + i" />
            </div>
        </div>

        <div class="act-footer">
            <logoLBC />
        </div>
    </div>
</template>
<script lang="ts" setup>
import logoLBC from '@/components/logoLBC.vue';
const baseUrl = 'http://47.108.29.91:9000/activityrecord/'
import { getActivityRecord } from '@/components/administrators/activity/ts/adminActivityMethods';
import { onMounted, ref } from 'vue';

const props = defineProps({
    activityId: String
})
interface ActivityRecord {
    activityRecordId: string
    recordTime: string
    recordImage: string
    recordContent: string
    activityId:string
}

const recordList = ref<ActivityRecord[]>([])
onMounted(async () => {
    const res = await getActivityRecord(props.activityId)
    recordList.value = res
    console.log(recordList.value)
})
</script>
<style scoped>
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
.act-title {
    font-family: 'Poppins', sans-serif;
    color: black;
    font-size: 32px;
    font-weight: bold;
    margin-bottom: 20px;
    text-align: center;
    /* 标题改为左对齐以实现首行缩进效果 */
    border-bottom: 2px solid rgba(148.6, 212.3, 117.1, 0.4);
    padding-bottom: 10px;
    /* 标题首行缩进2个字符 */
}

.act-time {
    text-align: left;
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

.act-content {
    font-family: 'Montserrat', sans-serif;
    color: black;
    font-size: 16px;
    line-height: 1.6;
    margin-bottom: 20px;
    text-align: justify;
    /* 文本两端对齐 */
    text-indent: 2em;
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

.record-container {
    margin-bottom: 2vh;
    border-bottom: 2px solid rgba(148.6, 212.3, 117.1, 0.4);
}

.r-title {
    text-align: left;
    /* 文本样式 */
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    font-size: 24px;
    font-weight: 600;
    color: #333;
    letter-spacing: 1px;
    text-transform: capitalize;

    /* 背景和边框样式 */
    border: none;
    /* 增加底部边框宽度 */
    box-sizing: border-box;
    /* 确保边框和内边距包含在宽度内 */

    /* 内边距和外边距 */
    padding: 12px 0;
    /* 调整内边距，仅保留上下内边距 */
    margin: 10px 0;
    /* 调整外边距 */
    width: 100%;
    /* 设置宽度为百分百 */

    /* 其他样式 */
    position: relative;
    /* 为可能的伪元素定位做准备 */
}

.r-title::before {
    content: "";
    position: absolute;
    left: 50%;
    bottom: 0;
    width: 0;
    height: 2px;
    background-color: rgba(148.6, 212.3, 117.1, 0.6);
    /* 半透明的底部装饰线 */
    transition: width 0.3s ease;
    /* 过渡效果，可在有交互时使用 */
}
</style>