<template>
<div style="padding-right: 20vh;padding-left: 20vh;">
    <div style="padding: 2vh;">
        <addActivityFormComponent :changeOrAdd="'change'" />
    </div>
    <div style="padding: 2vh;">
        <currentActivityRegistrationTableComponent :activityId="route.query.activityId+''" />
    </div>
</div>
</template>
<script lang="ts" setup>
import { useRoute } from 'vue-router';
const route=useRoute()
import currentActivityRegistrationTableComponent from '@/components/administrators/activity/currentActivityRegistrationTableComponent.vue';
import addActivityFormComponent from '@/components/administrators/activity/addActivityFormComponent.vue';
</script>