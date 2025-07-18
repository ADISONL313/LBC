<template>
    <div style="display: grid;grid-template-columns: repeat(4,1fr);gap: 40px; padding: 40px;place-items: center;">
        <div class="new-box" v-for="item in (newsList.slice((currentPage - 1) * 8, currentPage * 8))" :key="(Object(item)).newId">
            <div @click="viewTheNew(item)" style="height: 100%;">
                <div class="new-img">
                    <img v-if="(Object(item)).newImg !== ''" :src="baseUrl + (Object(item)).newId + '/' + (Object(item)).newImg.split(/;/)[0]" />
                    <div v-if="(Object(item)).newImg === ''" class="no-img-placeholder">
                        Left - Behind children 为了儿童
                    </div>
                </div>
                <div class="new-time">
                    <el-row>
                        <el-col :span="18">
                            <el-icon>
                                <DocumentChecked />
                            </el-icon>{{ (Object(item)).newTime.split(' ')[0] }}
                        </el-col>
                        <el-col :span="6" style="text-align: right;">
                            <el-icon>
                                <Pointer />
                            </el-icon>{{ (Object(item)).hits }}
                        </el-col>
                    </el-row>
                </div>
                <p class="new-title">
                    {{ (Object(item)).newTitle }}
                </p>
                <p class="reporter">
                    发稿人：{{ (Object(item)).reporter }}
                </p>
            </div>
        </div>
    </div>
    <paging-component :pageNum="Math.ceil(newsList.length / 8)" />
</template>

<script lang="ts" setup>
import pagingComponent from '@/components/universal/pagingComponent.vue';
//服务器存储新闻图片的基础地址
const baseUrl = 'http://47.108.29.91:9000/news/'
//导入封装好的get方法
import { get, post } from '@/axiosConfig';
//导入vue响应式函数
import { onMounted, ref } from 'vue';
const newsList = ref([])
//获取新闻列表方法
const props = defineProps({
    listNum:{
        type:Boolean,
        default: true
    }
})
import { useSearchStore } from '@/stores/universal/searchStore';
const searchStore = useSearchStore()
const { currentPage  } = storeToRefs(searchStore)
const getNewsList = (mail: string, newId: string, newTime: string, selKey: string) => {
    get('/news/userGetNews', { mail: mail, newId: newId, newTime: newTime, selKey: selKey }).then((res) => {
        console.log(props.listNum)
        if (props.listNum === false) {
            if (res.data.length >= 9) {
                newsList.value = res.data.slice(0, 8)
            } else {
                newsList.value = res.data
            }
        }else if(props.listNum===true){
            newsList.value = res.data
        }else{
            newsList.value = res.data
        }
    })
}
//使用vue钩子函数，在页面挂载完成后请求一次接口
onMounted(() => {
    getNewsList('', '', '', '')
})
//导入路由
import { useRouter } from 'vue-router';
import { storeToRefs } from 'pinia';
const router = useRouter()
//查看新闻详情
const viewTheNew = (item: any) => {
    //获取token，只有登录的用户才能增加点击量
    const token = sessionStorage.getItem('token')
    if (token !== null) {
        item.hits = item.hits + 1
        post('/news/addHit', item)
    }
    //登录后才能浏览新闻内容，路由守卫会自动拦截
    router.push({
        name: 'viewNew',
        query: {
            newId: item.newId
        }
    })
}
</script>

<style scoped>
/* 全局重置样式 */
* {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
}

/* 新闻盒子样式 */
.new-box {

    background: rgba(255, 255, 255, 0.1);
    border-radius: 15px;
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
    height: 500px;
    width: 250px;
    position: relative;
    overflow: hidden;
    transition: all 0.3s ease;
    backdrop-filter: blur(5px);
    cursor: pointer;
}

.new-box:hover {
    transform: translateY(-15px);
    box-shadow: 0 12px 24px rgba(0, 0, 0, 0.3);
    background: rgba(255, 255, 255, 0.2);
}

/* 新闻图片样式 */
.new-img {
    height: 175px;
    width: 100%;
    overflow: hidden;
}

.new-img img {
    height: 100%;
    width: 100%;
    object-fit: cover;
    transition: transform 0.3s ease;
}

.new-box:hover .new-img img {
    transform: scale(1.1);
}

/* 无图片占位符样式 */
.no-img-placeholder {
    height: 175px;
    width: 100%;
    background: linear-gradient(50deg, #67C23A, rgb(148.6, 212.3, 117.1));
    color: white;
    text-align: center;
    font-weight: bold;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 18px;
}

/* 新闻时间样式 */
.new-time {
    padding: 10px;
    font-size: small;
    color: #ccc;
}

/* 新闻标题样式 */
.new-title {
    padding: 10px;
    font-weight: bold;
    font-size: 18px;
    line-height: 1.5;
    text-indent: 3ch;
    color: black;
}

/* 发稿人样式 */
.reporter {
    position: absolute;
    bottom: 10px;
    right: 10px;
    font-size: 14px;
    color: #ccc;
}
</style>