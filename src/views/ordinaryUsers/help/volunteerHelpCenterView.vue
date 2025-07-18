<template>
    <n-tabs type="segment" animated placement="right" style="margin: 0%;">
        <n-tab-pane name="chap1" style="padding:0" tab="当前任务">
            <div v-if="task">
                <volunteerStateComponent />
                <nowHelpCardComponent :nowTask="task" />
                <addHelpRecordComponent :nowTask="task" />
                <updateHelpRecordComponent :nowTask="task" />
            </div>
            <div v-else>
                <noMessageComponet />
            </div>
        </n-tab-pane>
        <n-tab-pane name="chap2" style="padding:0" tab="历史任务">
            <helpHistoryTableComponent/>
        </n-tab-pane>
    </n-tabs>
</template>
<script lang="ts" setup>
import helpHistoryTableComponent from '@/components/ordinaryUsers/help/helpHistoryTableComponent.vue';
import noMessageComponet from '@/components/universal/noMessageComponet.vue';
import updateHelpRecordComponent from '@/components/ordinaryUsers/help/updateHelpRecordComponent.vue';
import nowHelpCardComponent from '@/components/ordinaryUsers/help/nowHelpCardComponent.vue';
import volunteerStateComponent from '@/components/ordinaryUsers/help/volunteerStateComponent.vue';
import addHelpRecordComponent from '@/components/ordinaryUsers/help/addHelpRecordComponent.vue';
import { onBeforeMount, onMounted, ref } from 'vue';
import { getVolunteerNowTask } from '@/components/ordinaryUsers/help/ts/helpTsMethods';
import { useHelpRecordStore } from '@/stores/help/helpRecordStore';
import { storeToRefs } from 'pinia';
const helpRecordStore = useHelpRecordStore()
const { helpRecord } = storeToRefs(helpRecordStore)
const task = ref()
const getTask = async () => {
    const res = await getVolunteerNowTask()
    helpRecord.value.helpId = res[0]?.helpId
    task.value = res[0]
    console.log(task.value)
}
onMounted(async () => {
    await getTask()

})
</script>