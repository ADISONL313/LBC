<template>
    <div class="hv-container">
        <div class="title">救助记录</div>
        <div v-for="item in helpRecordList" :key="item.helpRecordId" class="record-item">
            <div class="record-info">
                <span>记录志愿者：{{ item?.mail }}</span>
            </div>
            <div class="record-info">
                <span>记录时间：{{ item?.recordDate }}</span>
            </div>
            <div class="record-info">
                <span>{{ item?.recordDescription }}</span>
            </div>
            <div class="record-image">
                <el-image v-if="item.recordImage != ''" style="width: 100px; height: 100px"
                    :src="baseUrl + item.helpRecordId + '/' + item.recordImage.split(';')[0]" :zoom-rate="1.2"
                    :max-scale="7" :min-scale="0.2"
                    :preview-src-list="(item.recordImage.split(';').slice(0, -1)).map((total: string) => baseUrl + item.helpRecordId + '/' + total)"
                    show-progress :initial-index="4" fit="cover" />
                <div v-else style="background-color:rgb(148.6, 212.3, 117.1);color: white;height: 100px;width: 100px; display: flex; align-items: center; justify-content: center;">
                    未上传图片
                </div>
            </div>
        </div>
    </div>
</template>
<script lang="ts" setup>
const baseUrl = 'http://47.108.29.91:9000/helprecord/'
import { useRoute } from 'vue-router';
import { getHelpRecordList } from './ts/helpTsMethods';
import { onMounted, ref } from 'vue';
const props=defineProps({
    helpData:Object
})
const route = useRoute()
const helpRecordList = ref()
const getlHelpRecord = async () => {
    const res = await getHelpRecordList(sessionStorage.getItem('mail')==='admin'?null:sessionStorage.getItem('mail'), typeof(route.query.helpId)==='undefined'?props.helpData?.helpId:route.query.helpId)
    console.log(res)
    helpRecordList.value = res
}
onMounted(async () => {
    console.log(route.query.helpId)
    await getlHelpRecord()
})
</script>
<style scoped>
.hv-container {
    display: flex;
    flex-direction: column;
    gap: 20px;
    padding: 25px;
    background-color: white;
    border-radius: 12px;
    box-shadow: 0 0 20px rgba(148.6, 212.3, 117.1, 0.8);
    margin-left: 2vh ;
    margin-right: 2vh;
}

.title {
    font-size: 24px;
    font-weight: bold;
    color: #333;
}

.record-item {
    border: 1px solid #e0e0e0;
    border-radius: 8px;
    padding: 15px;
    display: flex;
    flex-direction: column;
    gap: 10px;
    transition: box-shadow 0.3s ease;
}

.record-item:hover {
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.record-info {
    font-size: 16px;
    color: #666;
}

.record-image {
    margin-top: 10px;
}
</style>    