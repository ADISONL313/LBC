<template>
    <div class="signId-container">
        <div>
            <el-button type="success" @click="sendSignIn">发起签到</el-button>
        </div>
        <activity-sign-in-table-component :activityId="route.query.activityId+''" />
    </div>
</template>
<script lang="ts" setup>
import activitySignInTableComponent from './activitySignInTableComponent.vue';
import { sendSign } from './ts/adminActivityMethods';
import { useRoute } from 'vue-router';
const route = useRoute()

import { useSearchStore } from '@/stores/universal/searchStore';
import { storeToRefs } from 'pinia';
const searchStore = useSearchStore()
const { freshValue } = storeToRefs(searchStore)
const sendSignIn = async() => {
    const res=await sendSign(route.query.activityId)
    if(res===true){
        freshValue.value=true
    }
}
</script>
<style scoped>
.signId-container {
    padding: 30px 50px;
    color: black;
    margin: 5% 10%;
    background-color: white;
    border-radius: 12px;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease;
}
</style>