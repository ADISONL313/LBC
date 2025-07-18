<template>
    <n-tabs type="segment" animated placement="right" style="margin: 0%;">
        <n-tab-pane name="chap1" style="padding:0" tab="发布活动">
            <div v-if="true">
                <addActivityFormComponent :changeOrAdd="'add'" />
            </div>
        </n-tab-pane>
        <n-tab-pane name="chap2" style="padding:0" tab="管理活动">
            <activityTableComponent/>
        </n-tab-pane>
    </n-tabs>
</template>
<script lang="ts" setup>
import addActivityFormComponent from '@/components/administrators/activity/addActivityFormComponent.vue';
import activityTableComponent from '@/components/administrators/activity/activityTableComponent.vue';
</script>