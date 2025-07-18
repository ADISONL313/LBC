<template>
    <div class="v-card" v-if="volunteerData?.exStatus === 2">
        驳回原因：{{ volunteerData?.rejectReason }}
    </div>
    <div v-if="volunteerData === null || volunteerData?.exStatus === 2">
        <userBecomVolunteerComponent />
    </div>
    <div class="v-card" v-if="volunteerData?.exStatus === 0">
        以提交申请，等待管理员审核！
    </div>
    <div class="v-card" v-if="volunteerData?.exStatus === 1">
        <el-row>
            <el-col :span="12">
                志愿者：{{ userDetail.realName }}</el-col>
            <el-col style="text-align: right;" :span="12">注册时间：{{ volunteerData?.volunteerTime }}</el-col>
        </el-row>
    </div>
    <div class="v-card" v-if="volunteerData?.exStatus === 1">
        <volunteerComponentCard/>
    </div>
</template>
<script lang="ts" setup>
import volunteerComponentCard from '@/components/ordinaryUsers/user/volunteerComponentCard.vue';
import { getVolunteer } from '@/components/ordinaryUsers/user/ts/userMethods';
import userBecomVolunteerComponent from '@/components/ordinaryUsers/user/userBecomVolunteerComponent.vue';
import { onMounted, ref } from 'vue';
const volunteerData = ref()
onMounted(async () => {
    const res = await getVolunteer(sessionStorage.getItem('mail') + '')
    volunteerData.value = res
})
import { useUserDetailStore } from '@/stores/user/userDetailStore';
import { storeToRefs } from 'pinia';
const userDetailStore = useUserDetailStore()
const { userDetail } = storeToRefs(userDetailStore)
</script>
<style scoped>
.v-card {
    padding: 30px;
    border-radius: 15px;
    box-shadow: 0 0 20px rgba(148.6, 212.3, 117.1, 0.8);
    position: relative;
    overflow: hidden;
    animation: fadeInUp 0.5s ease;
    margin-bottom: 2vh;
    /* 添加淡入上滑的动画效果 */
}
</style>