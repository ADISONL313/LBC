<template>
    <el-timeline v-if="donationState?.logisticsState!==0">
        <el-timeline-item :timestamp="donationState?.logisticsTime.split(' ')[0]" placement="top">
            <el-card>
                <h4>{{ donationState?.logisticsState === 0 ? "等待签收" : "签收成功" }}</h4>
                <p>{{ donationState?.logisticsText }}</p>
                <p>
                    <el-image v-if="donationState?.logisticsImg!==null" style="width: 100px; height: 100px" :src="baseUrl + donationState?.logisticsImg"
                        :zoom-rate="1.2" :max-scale="7" :min-scale="0.2"
                        :preview-src-list="[baseUrl + donationState?.logisticsImg]" show-progress :initial-index="4"
                        fit="cover" />
                </p>
            </el-card>
        </el-timeline-item>
        <el-timeline-item v-if="donationState?.logisticsState===1" :timestamp="donationState?.transportTime===null?'':donationState?.transportTime.split(' ')[0]" placement="top">
            <el-card>
                <h4>{{ donationState?.transportState === 0 ? "待确定物资分配地点" : "正在前往->"+donationState?.transportAddress }}</h4>
                <p>{{ donationState?.transportText }}</p>
                <p>
                    <el-image v-if="donationState?.transportImg!==null" style="width: 100px; height: 100px" :src="baseUrl + donationState?.transportImg"
                        :zoom-rate="1.2" :max-scale="7" :min-scale="0.2"
                        :preview-src-list="[baseUrl + donationState?.transportImg]" show-progress :initial-index="4"
                        fit="cover" />
                </p>
            </el-card>
        </el-timeline-item>
        <el-timeline-item v-if="donationState?.transportState===1" :timestamp="donationState?.arrivalTime===null?'':donationState?.arrivalTime.split(' ')[0]" placement="top">
            <el-card>
                <h4>{{ donationState?.arrivalState === 0 ? "运输中" : "已到达->"+donationState?.transportAddress }}</h4>
                <p>{{ donationState?.arrivalText }}</p>
                <p>
                    <el-image v-if="donationState?.arrivalImg!==null" style="width: 100px; height: 100px" :src="baseUrl + donationState?.arrivalImg"
                        :zoom-rate="1.2" :max-scale="7" :min-scale="0.2"
                        :preview-src-list="[baseUrl + donationState?.arrivalImg]" show-progress :initial-index="4"
                        fit="cover" />
                </p>
            </el-card>
        </el-timeline-item>
        <el-timeline-item v-if="donationState?.arrivalState===1" :timestamp="donationState?.distributeTime===null?'':donationState?.distributeTime.split(' ')[0]" placement="top">
            <el-card>
                <h4>{{ donationState?.distributeState === 0 ? "等待派送" : "已派送" }}</h4>
                <p>{{ donationState?.distributeText }}</p>
                <p>
                    <el-image v-if="donationState?.distributeImg!==null" style="width: 100px; height: 100px" :src="baseUrl + donationState?.distributeImg"
                        :zoom-rate="1.2" :max-scale="7" :min-scale="0.2"
                        :preview-src-list="[baseUrl + donationState?.arrivalImg]" show-progress :initial-index="4"
                        fit="cover" />
                </p>
            </el-card>
        </el-timeline-item>
    </el-timeline>
</template>

<script lang="ts" setup>
import { getDonationState,type DonationStateType } from '@/components/universal/user/tsMethods/tsMethods';
import { onMounted, ref } from 'vue';
import { storeToRefs } from 'pinia';
const donationState = ref<DonationStateType>();
import { useDonationStore } from '@/stores/donation/donationStore';
const donationStore = useDonationStore();
const { donation } = storeToRefs(donationStore);

const baseUrl = ref('http://47.108.29.91:9000/donationstate/')
const stateId = ref()

onMounted(async () => {
    const res: any = await getDonationState(donation.value.donationId);
    donationState.value = res.data[0];
    stateId.value = donationState.value?.stateId
    baseUrl.value = baseUrl.value + stateId.value + '/'
    console.log(donationState.value)
});
</script>
<style scoped>
.el-timeline {
    margin-left: 8%;
    margin-right: 10%;
}
</style>