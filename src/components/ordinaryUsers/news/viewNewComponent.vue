<template>
    <div class="news-detail-container">
        <header class="news-header">
            {{ detailItem.newTitle }}
        </header>
        <div class="news-info">
            <span>发布时间：{{ detailItem.newTime }}</span>
            <span>发布地址：{{ detailItem.address }}</span>
            <span>撰稿人：{{ detailItem.reporter }}</span>
            <span>点击量：{{ detailItem.hits }}</span>
        </div>
        <div class="news-main-img">
            <img v-if="detailItem.newImg !== ''" :src="baseUrl + detailItem.newId + '/' + detailItem.newImg.split(/;/)[0]"
                alt="Main News Image">
        </div>
        <div v-html="detailItem.newContent" class="news-content">
        </div>
        <div class="news-additional-imgs" v-if="detailItem.newImg!==''">
            <div v-for="i in detailItem.newImg.split(/;/).slice(1,-1)" :key="i">
                <img :src="baseUrl+detailItem.newId+'/'+i" alt="Additional News Image">
            </div>
        </div>
        <footer class="news-footer">
            <logoLBC/>
        </footer>
    </div>
</template>

<script lang="ts" setup>
import logoLBC from '@/components/logoLBC.vue';
const baseUrl = 'http://47.108.29.91:9000/news/'
import { get } from '@/axiosConfig';
import { onBeforeMount, onMounted, ref } from 'vue';
const detailItem = ref({
    newId: '',
    newTitle: '',
    newContent: '',
    newTime: '',
    reporter: '',
    newImg: '',
    hits: 0,
    address: '',
    mail: '',
    process: 0,
    rejectionReason: ''
})
//根据新闻id查询新闻详细信息
const getNewDetail = (newId: string) => {
    get('/news/userGetNews', { mail: '', newId: newId, newTime: '', selKey: '' }).then((res) => {
        detailItem.value = res.data[0]
    })
}
//引入vueroute
import { useRoute } from 'vue-router';
const route = useRoute()
onMounted(() => {
    getNewDetail(String(route.query.newId))
})
</script>

<style scoped>
.news-detail-container {
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    padding: 20px;
    margin: 20px auto;
    max-width: 900px;
}

.news-header {
    font-family: 'Arial Black', sans-serif;
    font-size: 36px;
    color: black;
    text-align: left;
    margin-bottom: 20px;
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
    white-space:pre-wrap;
}

.news-info {
    display: flex;
    justify-content: space-around;
    font-size: 14px;
    color: #777;
    margin-bottom: 20px;
    border-bottom: 1px solid #eee;
    padding-bottom: 10px;
}

.news-main-img {
    text-align: center;
    margin-bottom: 20px;
}

.news-main-img img {
    max-width: 100%;
    height: auto;
    border-radius: 5px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease;
}

.news-main-img img:hover {
    transform: scale(1.05);
}

.news-content {
    font-size: 16px;
    line-height: 1.6;
    color: #555;
    margin-bottom: 20px;
    white-space:pre-wrap;
}

.news-additional-imgs {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: 10px;
}

.news-additional-imgs img {
    max-width: 100%;
    height: auto;
    border-radius: 5px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease;
}

.news-additional-imgs img:hover {
    transform: scale(1.05);
}

.news-footer {
    text-align: center;
    font-size: 12px;
    color: #999;
    border-top: 1px solid #eee;
    margin-top: 20px;
    padding-top: 10px;
}

/* 使用主色调强调部分元素 */
.news-header {
    border-left: 5px solid rgb(148.6, 212.3, 117.1);
    padding-left: 15px;
}

.news-info span {
    transition: color 0.3s ease;
}

.news-info span:hover {
    color: rgb(148.6, 212.3, 117.1);
}
</style>