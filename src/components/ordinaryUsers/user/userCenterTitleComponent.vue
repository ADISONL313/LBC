<template>
    <div class="detail-title">
        {{ userCenterState }}
    </div>
</template>
<script lang="ts" setup>
import { useUserDetailStore } from '@/stores/user/userDetailStore';
import { storeToRefs } from 'pinia';
const userDetailStore = useUserDetailStore()
const { userCenterState } = storeToRefs(userDetailStore)
</script>
<style scoped>
.detail-title {
    margin-top: 2vh;
    background-color: white;
    border-radius: 10px;
    text-align: center;
    font-size: 26px;
    font-weight: bold;
    color: rgb(148.6, 212.3, 117.1);
    padding: 15px;
    box-shadow: 0 4px 8px rgba(148.6, 212.3, 117.1, 0.2);
    transition: all 0.3s ease;
    position: relative;
}

.detail-title::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 50%;
    width: 0;
    height: 3px;
    background-color: rgb(148.6, 212.3, 117.1);
    transform: translateX(-50%);
    transition: width 0.3s ease;
}

.detail-title:hover {
    box-shadow: 0 6px 12px rgba(148.6, 212.3, 117.1, 0.3);
    transform: translateY(-3px);
}

.detail-title:hover::after {
    width: 60%;
}
</style>