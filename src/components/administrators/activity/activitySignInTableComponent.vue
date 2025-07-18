<template>
    <div>
        <el-table :data="signInList.slice((currentPage - 1) * 10, currentPage * 10)" style="width: 100%">
            <el-table-column label="签到发起时间">
                <template #default="scope">
                    {{ scope.row.signInTime }}
                </template>
            </el-table-column>
            <el-table-column label="签到用户">
                <template #default="scope">
                    {{ scope.row.mail }}
                </template>
            </el-table-column>
            <el-table-column label="签到状态" width="180">
                <template #default="scope">
                    {{ scope.row.signInStatus === 0 ? "未签到" : "已签到" }}
                </template>
            </el-table-column>
            <el-table-column label="操作" width="300">
                <template #default="scope">
                    <el-button v-if="mail === 'admin'" type="danger" @click="signFor(scope.row.signInId)">代签</el-button>
                    <el-button v-else :disabled="new Date(getYMD()) > new Date(scope.row.signInTime)" type="success"
                        @click="signIn(scope.row.signInId)">{{ (new Date(getYMD()) > new Date(scope.row.signInTime))&&scope.row.signInStatus===0?"已过期，未签到":scope.row.signInStatus===1?"已签到":"签到" }}</el-button>
                </template>
            </el-table-column>
        </el-table>
        <paging-component :pageNum="Math.ceil(signInList.length / 10)" />
    </div>

</template>
<script lang="ts" setup>
import pagingComponent from '@/components/universal/pagingComponent.vue';
import { useSearchStore } from '@/stores/universal/searchStore';
import { storeToRefs } from 'pinia';
import { onMounted, ref, watch } from 'vue';
import { getSignIn, signInfor } from './ts/adminActivityMethods';
import { getNowTime, getYMD } from '@/components/universal/user/tsMethods/tsMethods';
const searchStore = useSearchStore()
const { freshValue, currentPage } = storeToRefs(searchStore)
const signInList = ref([])
const props = defineProps({
    activityId: String
})
const mail = sessionStorage.getItem('mail')
const getSignInList = async () => {
    const res = await getSignIn(props.activityId, mail === 'admin' ? null : mail)
    console.log(res)
    signInList.value = res
}
onMounted(async () => {
    await getSignInList()
})

const signFor = async (signInId: any) => {
    const res = await signInfor(signInId)
    if (res === true) {
        getSignInList()
    }
}
const signIn=async(signInId:any)=>{
    const res = await signInfor(signInId)
    if (res === true) {
        getSignInList()
    }
}
watch(freshValue, async () => {
    if (freshValue.value === true) {
        getSignInList()
        freshValue.value = false
    }
})
</script>