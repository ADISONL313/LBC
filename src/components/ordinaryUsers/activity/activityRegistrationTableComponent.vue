<template>
    <div class="tb-card">
        <activitySearchComponent />
        <el-table :data="activityList.slice((currentPage - 1) * 10, currentPage * 10)" style="width: 100%">
            <el-table-column label="活动名称">
                <template #default="scope">
                    {{ scope.row.activityName }}
                </template>
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
            <el-table-column label="报名状态" width="90">
                <template #default="scope">
                    {{ scope.row.registrationStatus === 0 ? "审核中" : scope.row.registrationStatus === 1 ? "审核通过" :
                    "无参加资格" }}
                </template>
            </el-table-column>
            <el-table-column label="操作" width="300">
                <template #default="scope">
                    <el-button class="cool-button"
                        :disabled="new Date(getNowTime()) < new Date(scope.row.activityStartTime)||scope.row.registrationStatus!=1"
                        @click="goSignIn(scope.row.activityId)">签到</el-button>
                    <el-button class="cool-button" @click="showDetail(scope.row)">查看</el-button>
                    <el-button type="danger" @click="delActivity(scope.row)">取消报名</el-button>
                </template>
            </el-table-column>
        </el-table>
        <paging-component :pageNum="Math.ceil(activityList.length / 10)" />
        <n-drawer v-model:show="active" :on-after-leave="handleClose" :default-width="1004" placement="left" resizable>
            <n-drawer-content :title="'活动详情'">
                <div>{{ activity.registrationRejectionReason }}</div>
                <viewActiviryComponent />
            </n-drawer-content>
        </n-drawer>
        <n-drawer v-model:show="signActiv" :default-width="1004" placement="left" resizable>
            <n-drawer-content :title="'签到详情'">
                <activitySignInTableComponent :activityId="signActivityId"  />
            </n-drawer-content>
        </n-drawer>
    </div>
</template>

<script lang="ts" setup>
const signActivityId=ref()
import activitySignInTableComponent from '@/components/administrators/activity/activitySignInTableComponent.vue'
import activitySearchComponent from '@/components/administrators/activity/activitySearchComponent.vue'
import viewActiviryComponent from './viewActiviryComponent.vue'
//导入分页组件
import pagingComponent from '@/components/universal/pagingComponent.vue'
import { deleteRemove, get, post } from '@/axiosConfig'
import { ElMessage } from 'element-plus'
import { onMounted, ref, watch } from 'vue'
import { storeToRefs } from 'pinia'
const activityList = ref([])
const mail = ref(sessionStorage.getItem('mail') === 'admin' ? '' : sessionStorage.getItem('mail'))
//获取个人报名活动
const getActivity = (activityStartTime: string, activityEndTime: string, activityName: string, activityId: string) => {
    get('/AR/getUserAr', { mail: mail.value, activityStartTime: activityStartTime, activityEndTime: activityEndTime, activityName: activityName, activityId: activityId }).then((res) => {
        activityList.value = res.data
    })
}
onMounted(() => {
    //页面挂载时执行一次查询方法
    getActivity('', '', '', '')
})
//取消报名方法
const delActivity = (item: any) => {
    //请求删除接口
    deleteRemove(`/AR/delAR?activityId=${item.activityId}&mail=${sessionStorage.getItem('mail')}`).then((res) => {
        ElMessage(res.message)
        //删除成功重新查询一次表格数据
        if (res.message === '成功') {
            getActivity('', '', '', '')
        }
    })
}
//控制抽屉组件显示
const active = ref(false)
const signActiv=ref(false)

//查看详情方法

//导入pinia中的查询数据
import { useSearchStore } from '@/stores/universal/searchStore';
const searchStore = useSearchStore()
const { currentPage, btnStatus, selActivityName, selActivityStartTime, selActivityEndTime } = storeToRefs(searchStore)

//pinia
import { useActivityStore } from '@/stores/activity/activityStore';
import { getNowTime } from '@/components/universal/user/tsMethods/tsMethods'
const activityStore = useActivityStore()
const { activity } = storeToRefs(activityStore)
const showDetail = (item: any) => {
    activity.value = item
    btnStatus.value = false
    active.value = true
}
const goSignIn=(activityId:any)=>{
    signActiv.value=true
    signActivityId.value=activityId
}
//naive ui 抽屉关闭回调
const handleClose = () => {
    btnStatus.value = true
}
//用watch钩子函数来监听查询数据变化，改变时查询
watch([selActivityName, selActivityStartTime, selActivityEndTime], () => {
    if (selActivityEndTime.value === null) {
        selActivityEndTime.value = ''
    }
    if (selActivityStartTime.value === null) {
        selActivityStartTime.value = ''
    }
    getActivity(selActivityStartTime.value, selActivityEndTime.value, selActivityName.value, '')
})
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