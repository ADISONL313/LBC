<template>
    <div v-if="userDetail.donationPermission===1">
        <donationCertificateComponent />
    </div>
    <div v-if="userDetail.userRole === 2" style="margin-top: 2vh;">
        <activityCertificateComponent />
    </div>
</template>
<script lang="ts" setup>
import { useUserDetailStore } from '@/stores/user/userDetailStore';
import { storeToRefs } from 'pinia';
const userDetailStore = useUserDetailStore();
const { userDetail } = storeToRefs(userDetailStore);
import donationCertificateComponent from '@/components/ordinaryUsers/user/donationCertificateComponent.vue';
import activityCertificateComponent from '@/components/ordinaryUsers/user/activityCertificateComponent.vue';
</script>