<template>
    <div class="total-container">
        <div v-for="i in (activityList.slice((currentPage - 1) * 6, currentPage * 6))" class="act-box" :key="i.activityId">
            <div @click="goView(i)">
                <h1>
                    {{ i.activityName }}
                </h1>
                <div>
                    <img v-if="i.activityImg === ''" src="../../../assets/image.png" />
                    <img v-else :src="baseUrl + i.activityId + '/' + i.activityImg.split(';')[0]" alt="">
                </div>
                <h3>
                    截止时间：{{ i.activityStartTime }}
                </h3>
            </div>
            <div v-if="token!==null" style="text-align: right;">
                <el-button :disabled="new Date(i.activityStartTime) < new Date(getNowTime())" type="success"
                    @click="AR(i.activityId)">{{ new Date(i.activityStartTime) < new Date(getNowTime()) ? "报名截止"
                        : "立即报名" }}</el-button>
            </div>
        </div>
    </div>
    <paging-component :pageNum="Math.ceil(activityList.length / 6)" />
</template>
<script lang="ts" setup>
const token=sessionStorage.getItem('token')
import pagingComponent from '@/components/universal/pagingComponent.vue';
//请求方法()
import { get, post } from '@/axiosConfig';
//获取当前时间
import { getNowTime } from '@/components/universal/user/tsMethods/tsMethods';
import { ElMessage } from 'element-plus';
import { v4 } from 'uuid';
import { onMounted, ref } from 'vue';
//导入路由
import { useRouter } from 'vue-router';
const router = useRouter()
//pinia
import { storeToRefs } from 'pinia';
import { useActivityStore } from '@/stores/activity/activityStore';
const activityStore = useActivityStore()
const { activity } = storeToRefs(activityStore)
const baseUrl = 'http://47.108.29.91:9000/activity/'
const activityList = ref<any>([])


import { useSearchStore } from '@/stores/universal/searchStore';
const searchStore = useSearchStore()
const { currentPage } = storeToRefs(searchStore)

const getActivity = (activityName: string, activityStartTime: string, activityEndTime: string) => {
    get('/activity/getActivity', { activityName: activityName, activityStartTime: activityStartTime, activityEndTime: activityEndTime }).then((res) => {
        activityList.value = res.data
    })
}
onMounted(() => {
    getActivity('', '', '')
})
//报名按钮
const AR = async (activityId: any) => {
    const i = await get('/AR/getIsAR', { activityId: activityId, mail: sessionStorage.getItem('mail') })
    if (i.data > 0) {
        ElMessage.error('已经报过名了')
        return
    }
    post('/AR/activityRegistration', {
        registrationId: v4(),
        mail: sessionStorage.getItem('mail'),
        activityId: activityId
    }).then((res) => {
        ElMessage.success(res)
    })
}
//查看活动详情
const goView = (item: any) => {
    console.log(item)
    activity.value = item
    router.push({
        name:'viewActivity'
    })
}
</script>
<style scoped>
/* 总容器样式 */
.total-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: 30px;
    padding: 5vh 10vw;
    background: linear-gradient(135deg, #f9f9f9, #ffffff);
}

/* 图片样式 */
img {
    width: 100%;
    height: 250px;
    object-fit: cover;
    border-radius: 15px 15px 0 0;
    transition: transform 0.3s ease;
    filter: brightness(0.9);
}

.act-box:hover img {
    transform: scale(1.07);
    filter: brightness(1);
}

/* 活动盒子样式 */
.act-box {
    cursor: pointer;
    flex: 1 1 calc(33.333% - 60px);
    min-width: 300px;
    background-color: white;
    border-radius: 15px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    overflow: hidden;
    position: relative;
    transition: all 0.3s ease;
    perspective: 1000px;
}

.act-box::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: linear-gradient(45deg, rgba(148.6, 212.3, 117.1, 0.2), rgba(148.6, 212.3, 117.1, 0.05));
    opacity: 0;
    transition: opacity 0.3s ease;
    z-index: 1;
    pointer-events: none;
}

.act-box:hover {
    box-shadow: 0 12px 30px rgba(148.6, 212.3, 117.1, 0.4);
    transform: translateY(-8px) rotateX(5deg);
}

.act-box:hover::before {
    opacity: 1;
}

/* 标题样式 */
.act-box h1 {
    font-family: 'Poppins', sans-serif;
    color: black;
    font-size: 22px;
    padding: 20px;
    margin: 0;
    text-indent: 2em;
    text-align: left;
    background-color: rgba(148.6, 212.3, 117.1, 0.08);
    border-bottom: 2px solid rgba(148.6, 212.3, 117.1, 0.2);
    letter-spacing: 0.5px;
}

/* 截止时间样式 */
.act-box h3 {
    font-family: 'Roboto', sans-serif;
    color: black;
    font-size: 18px;
    padding: 15px 20px;
    margin: 0;
    text-align: left;
    border-top: 1px solid rgba(148.6, 212.3, 117.1, 0.1);
}

/* 按钮样式 */
.el-button {
    margin: 20px;
    background-color: rgb(148.6, 212.3, 117.1);
    border: none;
    color: black;
    font-family: 'Montserrat', sans-serif;
    font-weight: bold;
    font-size: 16px;
    border-radius: 8px;
    padding: 12px 20px;
    transition: all 0.3s ease;
    box-shadow: 0 5px 15px rgba(148.6, 212.3, 117.1, 0.3);
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
}

.el-button:hover {
    background-color: rgba(148.6, 212.3, 117.1, 0.9);
    transform: scale(1.08);
    box-shadow: 0 8px 20px rgba(148.6, 212.3, 117.1, 0.5);
}

.el-button:hover::before {
    left: 100%;
}

.el-button[disabled] {
    background-color: black;
    color: rgb(148.6, 212.3, 117.1);
}
</style>