<template>
    <div class="tb-card">
        <div style="padding: 2vh;">
            <activitySearchComponent />
        </div>
        <el-table :data="newsList.slice((currentPage - 1) * 10, currentPage * 10)" style="width: 100%">
            <el-table-column label="活动名称">
                <template #default="scope">
                    {{ scope.row.activityName }}
                </template>
            </el-table-column>
            <el-table-column label="报名状态">
                <template #default="scope">
                    {{ (new Date(getNowTime())) < (new Date(scope.row.activityStartTime)) ? "报名中" : (new Date(getNowTime())) >
                        (new Date(scope.row.activityStartTime)) && (new Date(getNowTime()) < (new
                            Date(scope.row.activityEndTime)))?"活动开展中":"已结束" }} </template>
            </el-table-column>
            <el-table-column label="开始时间" width="180">
                <template #default="scope">
                    {{ scope.row.activityStartTime }}
                </template>
            </el-table-column>
            <el-table-column label="结束时间" width="180">
                <template #default="scope">
                    {{ scope.row.activityEndTime }}
                </template>
            </el-table-column>
            <el-table-column label="活动地址">
                <template #default="scope">
                    {{ scope.row.address.split(';')[0] }}
                </template>
            </el-table-column>
            <el-table-column label="报名人数" width="90">
                <template #default="scope">
                    {{ scope.row.participantsNumber }}
                </template>
            </el-table-column>
            <el-table-column label="操作" width="300">
                <template #default="scope">
                    <el-button class="cool-button" :disabled="new Date(getNowTime()) < new Date(scope.row.activityStartTime)" @click="goRecord(scope.row)">活动记录</el-button>
                    <el-button class="cool-button" @click="goView(scope.row)">查看</el-button>
                    <el-button type="danger" @click="delActivity(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <paging-component :pageNum="Math.ceil(newsList.length / 10)" />
    </div>
</template>

<script lang="ts" setup>
import { useRouter } from 'vue-router'
const router = useRouter()
//导入搜索组件
import activitySearchComponent from './activitySearchComponent.vue'
import { storeToRefs } from 'pinia'
//导入分页组件
import pagingComponent from '@/components/universal/pagingComponent.vue'
import { deleteRemove, get, post } from '@/axiosConfig'
import { ElMessage } from 'element-plus'
import { onMounted, ref, watch } from 'vue'
const newsList = ref([])
//获取新闻列表方法
const getActivity = (activityName: string, activityStartTime: string, activityEndTime: string) => {
    get('/activity/getActivity', { activityName: activityName, activityStartTime: activityStartTime, activityEndTime: activityEndTime }).then((res) => {
        newsList.value = res.data
    })
}
onMounted(() => {
    //页面挂载时执行一次查询方法
    getActivity('', '', '')
})
//删除新闻方法
const delActivity = (item: any) => {
    //请求删除接口
    deleteRemove(`/activity/delActivity?activityId=${item.activityId}`).then((res) => {
        ElMessage(res.message)
        //删除成功重新查询一次表格数据
        if (res.message === '成功') {
            getActivity('', '', '')
        }
    })
}
//导入pinia中的查询数据
import { useSearchStore } from '@/stores/universal/searchStore';
const searchStore = useSearchStore()
const { selActivityEndTime, selActivityStartTime, selActivityName, currentPage } = storeToRefs(searchStore)
import { useActivityStore } from '@/stores/activity/activityStore'
import { getNowTime } from '@/components/universal/user/tsMethods/tsMethods'
const activityStore = useActivityStore()
const { activity } = storeToRefs(activityStore)
//用watch钩子函数来监听查询数据变化，改变时查询
watch([selActivityEndTime, selActivityStartTime, selActivityName], () => {
    if (selActivityStartTime.value === null) {
        selActivityStartTime.value = ''
    }
    if (selActivityEndTime.value === null) {
        selActivityEndTime.value = ''
    }
    getActivity(selActivityName.value, selActivityStartTime.value, selActivityEndTime.value)
})
const goView = (item: any) => {
    activity.value = item
    router.push({
        name: 'arAndac',
        query: {
            activityId: item.activityId
        }
    })
}
const goRecord=(item: any) => {
    activity.value = item
    router.push({
        path:'adminActivityRecord',
        query:{
            activityId:item.activityId
        }
    })
}
</script>

<style scoped>
/* 表格样式 */
.tb-card {
    overflow: hidden;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
    animation: fadeIn 0.5s ease-out;
    margin-bottom: 2vh;
    padding-bottom: 2vh;
}

@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(-20px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.el-table__header th {
    background-color: black;
    color: rgb(148.6, 212.3, 117.1);
    font-weight: bold;
    text-transform: uppercase;
    letter-spacing: 1px;
}

.el-table__body tr:nth-child(even) {
    background-color: rgba(148.6, 212.3, 117.1, 0.1);
}

.el-table__body tr:hover {
    background-color: rgba(148.6, 212.3, 117.1, 0.3);
    transform: scale(1.01);
    transition: all 0.3s ease;
}

/* 按钮样式 */
.cool-button {
    background-color: black;
    color: rgb(148.6, 212.3, 117.1);
    border: none;
    border-radius: 5px;
    padding: 8px 16px;
    cursor: pointer;
    transition: all 0.3s ease;
}

.cool-button:hover {
    background-color: rgb(148.6, 212.3, 117.1);
    color: black;
    transform: scale(1.1);
    box-shadow: 0 0 10px rgba(148.6, 212.3, 117.1, 0.5);
}
</style>