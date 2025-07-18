<template>
    <div style="padding-left: 20%;padding-right: 20%;padding-top: 1%;padding-bottom: 2%;">
        <userCenterBaseInfoComponent />
        <userCenterTitleComponent />
        <div style="margin-top: 2vh;">
            <div v-if="showWitchPage.userDetail">
                <userCenterDetailInfoComponent/>
            </div>
            <div v-if="showWitchPage.userDetailUpdate">
                <userDetailUpdateComponent/>
            </div>
            <div v-if="showWitchPage.certificate">
                <certificateView/>
            </div>
            <div v-if="showWitchPage.becomevolunteer">
                <volunteerCenterView/>
            </div>
        </div>
    </div>
    <userCenterOptionCardComponent />
</template>
<script lang="ts" setup>
import certificateView from './certificateView.vue';
import userDetailUpdateComponent from '@/components/ordinaryUsers/user/userDetailUpdateComponent.vue';
import userCenterTitleComponent from '@/components/ordinaryUsers/user/userCenterTitleComponent.vue';
import userCenterOptionCardComponent from '@/components/ordinaryUsers/user/userCenterOptionCardComponent.vue';
import userCenterBaseInfoComponent from '@/components/ordinaryUsers/user/userCenterBaseInfoComponent.vue';
import userCenterDetailInfoComponent from '@/components/ordinaryUsers/user/userCenterDetailInfoComponent.vue';
import volunteerCenterView from './volunteerCenterView.vue';
import { useUserDetailStore } from '@/stores/user/userDetailStore';
import { storeToRefs } from 'pinia';
const userDetailStore=useUserDetailStore()
const {showWitchPage}=storeToRefs(userDetailStore)
</script>