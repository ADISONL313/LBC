<template>
    <el-table :data="helpHistoryList.slice((currentPage - 1) * 10, currentPage * 10)" style="width: 100%">
        <el-table-column label="帮助用户">
            <template #default="scope">
                {{ scope.row.mail }}
            </template>
        </el-table-column>
        <el-table-column label="发布时间" width="180">
            <template #default="scope">
                {{ scope.row.releaseTime }}
            </template>
        </el-table-column>
        <el-table-column label="求救标题">
            <template #default="scope">
                {{ scope.row.helpTitle }}
            </template>
        </el-table-column>
        <el-table-column label="任务地点">
            <template #default="scope">
                {{ scope.row.address }}
            </template>
        </el-table-column>
        <el-table-column label="完成时间">
            <template #default="scope">
                {{ scope.row.helpTime }}
            </template>
        </el-table-column>
        <el-table-column label="操作" width="300">
            <template #default="scope">

                <el-button class="cool-button" @click="goView(scope.row)">查看</el-button>
            </template>
        </el-table-column>
    </el-table>
    <paging-component :pageNum="Math.ceil(helpHistoryList.length / 10)" />
    <n-drawer v-model:show="active"  :default-width="1004" placement="left" resizable>
        <n-drawer-content :title="'帮助详情'">
            <helpDetailCardComponent :helpData="helpData"/>
            <helpRecordListComponent :helpData="helpData"/>
        </n-drawer-content >
    </n-drawer>
</template>
<script lang="ts" setup>
import helpRecordListComponent from './helpRecordListComponent.vue';
import helpDetailCardComponent from '@/components/administrators/help/helpDetailCardComponent.vue';
import pagingComponent from '@/components/universal/pagingComponent.vue';
import { useSearchStore } from '@/stores/universal/searchStore';
import { storeToRefs } from 'pinia';
import { onMounted, ref } from 'vue';
import { getHelpHistory } from './ts/helpTsMethods';
const searchStore = useSearchStore()
const { currentPage } = storeToRefs(searchStore)
const helpHistoryList = ref([])
onMounted(async () => {
    const res = await getHelpHistory()
    console.log(res)
    helpHistoryList.value = res
})
const helpData=ref()
const active=ref(false)
const goView = (help: any) => {
    helpData.value=help
    active.value=true
}
</script>
<style scoped>
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