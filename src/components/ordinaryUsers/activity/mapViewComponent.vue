<template>
    <iframe
        :src="`https://m.amap.com/navi/?dest=${activity.address.split(';')[1]}&destName=${activity.address.split(';')[0]}&hideRouteIcon=1&key=e9417ad15f42bee9edce9a08f08bb922&jscode = f2f6202df04429228226af759917cf27`"
        frameborder="0"></iframe>
</template>
<script lang="ts" setup>
//pinia
import { storeToRefs } from 'pinia';
import { useActivityStore } from '@/stores/activity/activityStore';
const activityStore = useActivityStore()
const { activity } = storeToRefs(activityStore)
</script>
<style scoped>
iframe{
    width: 100%;
    height: 400px;
}
</style>