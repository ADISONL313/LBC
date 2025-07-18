<template>
    <div class="tb-card">
        <div style="padding: 2vh;">
            <news-search-component />
        </div>
        <el-table :data="newsList.slice((currentPage - 1) * 10, currentPage * 10)" style="width: 100%">
            <el-table-column label="新闻标题">
                <template #default="scope">
                    {{ scope.row.newTitle }}
                </template>
            </el-table-column>
            <el-table-column label="发布时间" width="180">
                <template #default="scope">
                    {{ scope.row.newTime }}
                </template>
            </el-table-column>
            <el-table-column label="撰稿人" width="180">
                <template #default="scope">
                    {{ scope.row.reporter }}
                </template>
            </el-table-column>
            <el-table-column label="发布地点" width="180">
                <template #default="scope">
                    {{ scope.row.address }}
                </template>
            </el-table-column>
            <el-table-column label="点击量" width="90">
                <template #default="scope">
                    {{ scope.row.hits }}
                </template>
            </el-table-column>
            <el-table-column label="审核进度" width="90">
                <template #default="scope">
                    {{ scope.row.process === 0 ? "未审核" : scope.row.process === 1 ? "通过" : "未通过" }}
                </template>
            </el-table-column>
            <el-table-column label="操作" width="180">
                <template #default="scope">
                    <el-button class="cool-button" @click="showDetail(scope.row)">查看</el-button>
                    <el-button type="danger" @click="delNew(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <paging-component :pageNum="Math.ceil(newsList.length / 10)" />
        <n-drawer v-model:show="active" :on-after-leave="handleClose" :default-width="1004" placement="left" resizable>
            <n-drawer-content title="新闻详情">
                <new-detail-component :thisNew="thisNew" />
            </n-drawer-content>
        </n-drawer>
    </div>
</template>

<script lang="ts" setup>
//导入分页组件
import pagingComponent from '@/components/universal/pagingComponent.vue'
//导入新闻搜索组件
import newsSearchComponent from './newsSearchComponent.vue'
import { deleteRemove, get, post } from '@/axiosConfig'
import { ElMessage } from 'element-plus'
import { onMounted, ref, watch } from 'vue'
const newsList = ref([])

//获取session存储的邮件地址
const mail=sessionStorage.getItem('mail')
//获取新闻列表方法
const getNewsList = (selKey: string, newTime: string) => {
    get('/news/adminGetNews', { selKey: selKey, newTime: newTime,mail:mail==='admin'?'':mail }).then((res) => {
        console.log(res.data)
        newsList.value = res.data
    })
}
onMounted(() => {
    //页面挂载时执行一次查询方法
    getNewsList('', '')
})
//删除新闻方法
const delNew = (item: any) => {
    //请求删除接口
    deleteRemove(`/news/delNew?newId=${item.newId}&whatFile=news&newImg=${item.newImg}`).then((res) => {
        ElMessage(res.message)
        //删除成功重新查询一次表格数据
        if (res.message === '成功') {
            getNewsList('', '')
        }
    })
}
//导入详情组件
import newDetailComponent from './newDetailComponent.vue'
//控制抽屉组件显示
const active = ref(false)
//查看详情方法
//定义个响应式对象用来传递给子组件
const thisNew = ref()
//导入pinia新闻对象
import { useNewStore } from '@/stores/news/newStore'
import { storeToRefs } from 'pinia'
const newStore = useNewStore()
//将新闻对象转为响应式对象
const { news } = storeToRefs(newStore)
const showDetail = (item: any) => {
    news.value = item
    active.value = true
}
//naive ui 抽屉关闭回调
const handleClose = () => {
    //关闭后新闻数据可能发生改变，杀心一下表格数据
    getNewsList('', '')
    newStore.clear()
}
//导入pinia中的查询数据
import { useSearchStore } from '@/stores/universal/searchStore';
const searchStore = useSearchStore()
const { selKey, selNewTime, currentPage } = storeToRefs(searchStore)
//用watch钩子函数来监听查询数据变化，改变时查询
watch([selKey, selNewTime], () => {
    getNewsList(selKey.value, selNewTime.value)
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