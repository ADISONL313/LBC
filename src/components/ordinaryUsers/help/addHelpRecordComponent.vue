<template>
    <div class="v-card">
        <el-form>
            <el-form-item>
                <el-input placeholder="记录内容" v-model="helpRecord.recordDescription" type="textarea"></el-input>
            </el-form-item>
            <fileUploadComponent :uploadKey="'helprecord'" />
        </el-form>
    </div>
</template>
<script lang="ts" setup>
import fileUploadComponent from '@/components/universal/fileUploadComponent.vue';
const props = defineProps({
    nowTask: Object
})
import { storeToRefs } from 'pinia';
import { useHelpRecordStore } from '@/stores/help/helpRecordStore'
const helpRecordStore=useHelpRecordStore()
const {helpRecord}=storeToRefs(helpRecordStore)

</script>
<style scoped>
.v-card {
    margin: 0 10% 2vh 10%;
    padding: 30px;
    border-radius: 15px;
    box-shadow: 0 0 20px rgba(148.6, 212.3, 117.1, 0.8);
    position: relative;
    overflow: hidden;
    animation: fadeInUp 0.5s ease;
    background-color: white;
    border: 1px solid #e0e0e0;
    transition: all 0.3s ease;
}

/* 鼠标悬停效果 */
.v-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 0 30px rgba(148.6, 212.3, 117.1, 1);
}
</style>