<template>
    <n-tabs type="segment" animated placement="right" style="margin: 0%;">
        <n-tab-pane name="chap1" style="padding:0" tab="活动记录">
            <adminActivityRecordComponent />
        </n-tab-pane>
        <n-tab-pane name="chap2" style="padding:0" tab="发起签到">
            <adminActivitySignInComponent />
        </n-tab-pane>
    </n-tabs>
</template>
<script lang="ts" setup>
import adminActivityRecordComponent from '@/components/administrators/activity/adminActivityRecordComponent.vue';
import adminActivitySignInComponent from '@/components/administrators/activity/adminActivitySignInComponent.vue';
</script>