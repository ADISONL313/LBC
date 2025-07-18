<template>
    <div class="help-card-container">
        <el-tooltip v-for="help in (helpList.slice((currentPage - 1) * 6, currentPage * 6))" effect="dark" raw-content
            :content="`<div style='font-size:20px'>电话:${help.phone};紧急电话:${help.emergencyPhone}</div>`">
            <div class="help-card">
                <div style="cursor: pointer;" @click="goDetail(help.helpId,help.helpStatus)">
                    <div class="help-title">
                        {{ help.helpTitle.slice(0,21)+"......" }}
                    </div>
                    <div class="help-baseInfo">
                        <span>发布时间：{{ help.releaseTime }}</span>
                        <span
                            style="background-color: rgb(148.6, 212.3, 117.1);padding: 5px;border-radius: 5px;width: 160px;">求救状态：{{
                                help.helpStatus === 0 ? "待处理" : help.helpStatus===1?"已指派志愿者":"已完成" }}</span>
                    </div>
                    <div class="help-content">
                        {{ help.helpContent }}
                    </div>
                    <div class="help-address">
                        地址：{{ help.address }}
                    </div>
                    <div class="help-time">完成时间：{{ help.helpTime === null ? "工作人员正在救助" : help.helpTime }}
                    </div>
                </div>
                <div class="help-option">
                    <el-row>
                        <el-col :span="12">
                            <el-button type="danger" @click="cancelHelp(help.helpId, help.helpStatus)">取消求助</el-button>
                        </el-col>
                        <el-col :span="12" style="text-align: right;">
                            <el-button type="success" @click="updateHelp(help)">修改信息</el-button>
                        </el-col>
                    </el-row>
                </div>
            </div>
        </el-tooltip>
        <n-drawer v-model:show="active" :on-after-leave="handleClose" :default-width="1004" placement="left" resizable>
            <n-drawer-content :title="'用户详情'">
                <update-help-component :helpData="helpData" />
            </n-drawer-content>
        </n-drawer>
    </div>
    <paging-component :pageNum="Math.ceil(helpList.length / 6)" />
</template>
<script lang="ts" setup>
import pagingComponent from '@/components/universal/pagingComponent.vue';
import { watch, onMounted, ref } from 'vue';
import updateHelpComponent from './updateHelpComponent.vue';
const active = ref(false)
import { cancelHelpTs, getMyHelp } from './ts/helpTsMethods';
import { useRouter } from 'vue-router';
const router=useRouter()
const helpList = ref<any>([])
const getHelp = async () => {
    helpList.value = await getMyHelp({ mail: sessionStorage.getItem('mail') })
}
onMounted(async () => {
    await getHelp()
})
const cancelHelp = async (helpId: string, helpStatus: number) => {
    const res = await cancelHelpTs(helpId, helpStatus)
    if (res === true) {
        await getHelp()
    }
}
import { useSearchStore } from "@/stores/universal/searchStore"
import { storeToRefs } from "pinia"
const searchStore = useSearchStore()
const { freshHelp,currentPage } = storeToRefs(searchStore)

const handleClose = () => {
    freshHelp.value = true
}
const helpData = ref()
const updateHelp = (item: any) => {
    helpData.value = item
    active.value = true
}

const goDetail=(helpId:any,helpStatus:any)=>{
    console.log(helpId)
    if(helpStatus!==0){
        router.push({path:'helpDetail',query:{helpId:helpId}})
    }
}

watch(freshHelp, async () => {
    if (freshHelp.value === true) {
        await getHelp()
        freshHelp.value = false
    }
})
</script>
<style scoped>
.help-card-container {
    display: grid;
    grid-template-columns: repeat(3, 1fr); /* 可以根据需要调整列数和宽度 */
    gap: 40px; /* 加大横向和纵向间距，这里设置为 40px，你可以根据实际需求调整 */
    padding: 20px;
    min-height: 100vh;
    place-items: center;
}

.help-card {
    background-color: rgb(239.8, 248.9, 235.3);
    border-radius: 15px;
    box-shadow: 0 8px 20px rgba(148.6, 212.3, 117.1, 0.3);
    padding: 20px;
    color: #333;
    transition: all 0.3s ease;
}

.help-card:hover {
    transform: scale(1.02);
    box-shadow: 0 12px 30px rgba(148.6, 212.3, 117.1, 0.5);
}

.help-title {
    font-size: 26px;
    font-weight: bold;
    margin-bottom: 10px;
    text-align: left;
    text-indent: 2em;
    color: black;
    text-shadow: 1px 1px 2px rgba(255, 255, 255, 0.3);
    border-bottom: 2px solid rgba(0, 0, 0, 0.1);
    padding-bottom: 8px;
}

.help-baseInfo span {
    display: block;
    margin-bottom: 6px;
    font-size: 14px;
}

.help-baseInfo span:nth-child(2) {
    font-weight: bold;
}

.help-content {
    margin: 10px 0;
    font-size: 16px;
    font-weight: bold;
    line-height: 1.5;
    text-indent: 2em;
}

.help-address {
    margin-bottom: 10px;
    font-size: 14px;
}

.help-time {
    margin-bottom: 10px;
    font-size: 14px;
}

.help-option {
    margin-top: 15px;
    border-top: 2px solid rgba(0, 0, 0, 0.1);
    padding-top: 10px;
}

.el-button {
    border-radius: 8px;
    transition: all 0.3s ease;
}

.el-button:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
</style>