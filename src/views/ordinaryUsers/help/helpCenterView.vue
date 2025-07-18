<template>
    <div style="padding-left: 10%;padding-right: 10%;">
        <adminMyHelpComponent />
    </div>
    <releaseHelpComponent />
</template>
<script lang="ts" setup>
import adminMyHelpComponent from '@/components/ordinaryUsers/help/adminMyHelpComponent.vue';
import releaseHelpComponent from '@/components/ordinaryUsers/help/releaseHelpComponent.vue'
import { provide,reactive } from 'vue';
const updateHelpListSign=reactive({
    message:false
})
const setMessage=(tf:boolean)=>{
    updateHelpListSign.message=tf
}
provide('updateHelpListSign',updateHelpListSign)
provide('setMessage',setMessage)
</script>