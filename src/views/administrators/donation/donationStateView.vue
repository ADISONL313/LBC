<template>
    <viewDonationDetailComponent />
    <div v-if="donation.auditStatus===1">
        <logisticsStateComponent />
    </div>
    <div v-if="donationState.logisticsState===1">
        <transportStateComponent/>
    </div>
    <div v-if="donationState.transportState===1">
        <arrivalStateComponent/>
    </div>
    <div v-if="donationState.arrivalState===1">
        <distributeStateComponent/>
    </div>
</template>
<script lang="ts" setup>
import viewDonationDetailComponent from '@/components/administrators/donation/viewDonationDetailComponent.vue';
import logisticsStateComponent from '@/components/administrators/donation/logisticsStateComponent.vue';
import transportStateComponent from '@/components/administrators/donation/transportStateComponent.vue';
import arrivalStateComponent from '@/components/administrators/donation/arrivalStateComponent.vue';
import distributeStateComponent from '@/components/administrators/donation/distributeStateComponent.vue';

import { storeToRefs } from 'pinia';
import { useDonationStore } from '@/stores/donation/donationStore';
import {onBeforeMount, onMounted, ref } from 'vue';

const donationStore = useDonationStore()
const { donation } = storeToRefs(donationStore)
import { useDonationStateStore } from '@/stores/donation/donationStateStore';
const donationStateStore = useDonationStateStore()
const { donationState } = storeToRefs(donationStateStore)
</script>