<template>
    <div class="hv-container">
        <div v-for="item in volunteerList" :key="item.id" class="volunteer-item">
            <div class="volunteer-info">
                <span class="label">志愿者</span>
                <span class="value">{{ item.realName }}</span>
                <span class="value">{{ item.gender }}</span>
                <span class="value">{{ item.mail }}</span>
                <span class="value">{{ item.phoneNumber }}</span>
            </div>
        </div>
    </div>
</template>
<script lang="ts" setup>
import { getAssign } from '@/components/administrators/help/ts/adminHelpMethod';
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
const route = useRoute()
const volunteerList = ref()
const getHelpVolunteer = async () => {
    const res = await getAssign(route.query.helpId)
    volunteerList.value = res
}
onMounted(async () => {
    await getHelpVolunteer()
})
</script>
<style scoped>
.hv-container {
    display: flex;
    flex-direction: column;
    gap: 15px;
    padding: 20px;
    background-color: white;
    border-radius: 10px;
    box-shadow: 0 0 15px rgba(148.6, 212.3, 117.1, 0.7);
    margin: 2vh;
    margin-bottom: 2vh;
}

.volunteer-item {
    background-color: rgba(148.6, 212.3, 117.1, 0.1);
    border: 1px solid rgba(148.6, 212.3, 117.1, 0.3);
    padding: 15px;
    border-radius: 8px;
    transition: all 0.3s ease;
    display: flex;
    justify-content: space-around;
    align-items: center;
}

.volunteer-item:hover {
    transform: scale(1.02);
    background-color: rgba(148.6, 212.3, 117.1, 0.2);
    box-shadow: 0 0 10px rgba(148.6, 212.3, 117.1, 0.5);
}

.volunteer-info {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    align-items: center;
    width: 100%;
    justify-content: space-around;
}

.label {
    color: rgb(148.6, 212.3, 117.1);
    font-weight: bold;
    text-transform: uppercase;
    letter-spacing: 1px;
}

.value {
    color: #333;
    background-color: rgba(148.6, 212.3, 117.1, 0.1);
    padding: 5px 10px;
    border-radius: 5px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    transition: background-color 0.3s ease;
}

.value:hover {
    background-color: rgba(148.6, 212.3, 117.1, 0.3);
}
</style>