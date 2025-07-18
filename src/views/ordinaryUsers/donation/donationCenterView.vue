<template>
    <n-tabs type="segment" animated placement="right" style="margin: 0%;">
        <n-tab-pane name="chap1" style="padding:0" tab="我要捐赠">
            <donationFormComponent/>
        </n-tab-pane>
        <n-tab-pane name="chap2" style="padding:0" tab="我的捐赠">
            <userDonationTableComponent/>
        </n-tab-pane>
    </n-tabs>
</template>
<script lang="ts" setup>
import donationFormComponent from '@/components/ordinaryUsers/donation/donationFormComponent.vue';
import userDonationTableComponent from '@/components/ordinaryUsers/donation/userDonationTableComponent.vue';
</script> 