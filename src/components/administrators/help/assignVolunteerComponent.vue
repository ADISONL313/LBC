<template>
    <div class="help-container">
        <el-row :gutter="24">
            <el-col :span="12">
                <div class="volunteer-list-container">
                    <div class="list-title">
                        志愿者
                    </div>
                    <el-scrollbar height="400px">
                        <div v-if="props.helpData?.helpStatus!==2">
                            <div v-for="item in idleVolunteerList" :key="item.id" class="volunteer-item">
                                <span class="volunteer-name">{{ item.realName }}</span>
                                <span class="volunteer-mail">{{ item.mail }}</span>
                                <span class="volunteer-gender">
                                    {{ item.gender }}
                                </span>
                                <span class="volunteer-action">
                                    <el-button type="success" @click="assignThis(item)">指派</el-button>
                                </span>
                            </div>
                        </div>
                    </el-scrollbar>
                </div>
            </el-col>
            <el-col :span="12">
                <div class="volunteer-list-container">
                    <div class="list-title">
                        已指派
                    </div>
                    <el-scrollbar height="400px">
                        <div v-for="item in assignList" :key="item.id" class="volunteer-item">
                            <span class="volunteer-name">{{ item.realName }}</span>
                            <span class="volunteer-mail">{{ item.mail }}</span>
                            <span class="volunteer-gender">
                                {{ item.gender }}
                            </span>
                            <span class="volunteer-action">
                                <el-button v-if="item.volunteerHelpState === 0 && props.helpData?.helpStatus !== 2"
                                    type="danger" @click="cancelThis(item)">取消</el-button>
                                <el-button v-else type="success">已完成</el-button>
                            </span>
                        </div>
                    </el-scrollbar>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import { assign, cancelAssign, getAssign, idleVolunteer } from './ts/adminHelpMethod';
const props = defineProps({
    helpData: Object
})
const idleVolunteerList = ref()
const getIdleVolunteerList = async () => {
    const res = await idleVolunteer()
    idleVolunteerList.value = res
}

const assignList = ref()
const getAssignVolunteerList = async () => {
    const res = await getAssign(props.helpData?.helpId)
    assignList.value = res
}
onMounted(async () => {
    await getIdleVolunteerList()
    await getAssignVolunteerList()
})

const assignThis = async (item: any) => {
    const res = await assign(item, props.helpData?.helpId)
    if (res === true) {
        await getIdleVolunteerList()
        await getAssignVolunteerList()
    }
}
const cancelThis = async (item: any) => {
    console.log(item)
    const res = await cancelAssign(item.volunteerHelpId, item.mail, props.helpData?.helpId)
    if (res === true) {
        await getIdleVolunteerList()
        await getAssignVolunteerList()
    }
}
</script>

<style scoped>
.help-container {
    border-radius: 12px;
    padding: 24px;
    margin: 24px;
    background-color: rgba(148.6, 212.3, 117.1, 0.1);
    transition: all 0.3s ease;
    box-shadow: 0 4px 8px rgba(148.6, 212.3, 117.1, 0.1), 0 6px 20px rgba(148.6, 212.3, 117.1, 0.15);
}

.help-container:hover {
    transform: translateY(-8px);
    box-shadow: 0 16px 32px rgba(148.6, 212.3, 117.1, 0.3), 0 24px 48px rgba(148.6, 212.3, 117.1, 0.35);
}

.volunteer-list-container {
    background-color: #fff;
    border-radius: 8px;
    padding: 16px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    margin-bottom: 16px;
}

.list-title {
    font-size: 20px;
    font-weight: bold;
    color: #333;
    margin-bottom: 12px;
    border-bottom: 2px solid #f0f0f0;
    padding-bottom: 8px;
}

.volunteer-item {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 12px 0;
    border-bottom: 1px solid #f0f0f0;
    transition: background-color 0.3s ease;
}

.volunteer-item:hover {
    background-color: #f9f9f9;
}

.volunteer-item:last-child {
    border-bottom: none;
}

.volunteer-name {
    font-size: 18px;
    font-weight: 600;
    color: #333;
}

.volunteer-mail {
    font-size: 16px;
    color: #666;
}

.volunteer-gender {
    font-size: 16px;
    color: #888;
}

.volunteer-action .el-button {
    border-radius: 4px;
    padding: 8px 16px;
}
</style>