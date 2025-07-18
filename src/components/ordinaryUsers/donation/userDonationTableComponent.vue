<template>
    <div class="donation-box">
        <div class="d-item" v-for="item in donationList.slice((currentPage - 1) * 8, currentPage * 8)">
            <div class="d-name">
                <el-row>
                    <el-col :span="12">
                        名称：{{ item.itemName.substring(0,5)+"......" }}
                    </el-col>
                    <el-col style="text-align: right;" :span="12">
                        类型：{{ item.itemType }}
                    </el-col>
                </el-row>
            </div>
            <div class="d-img">
                <el-image v-if="item.itemImg != ''" style="width: 100px; height: 100px"
                    :src="baseUrl + item.donationId + '/' + item.itemImg.split(';')[0]" :zoom-rate="1.2" :max-scale="7"
                    :min-scale="0.2"
                    :preview-src-list="(item.itemImg.split(';').slice(0, -1)).map((total: string) => baseUrl + item.donationId + '/' + total)"
                    show-progress :initial-index="4" fit="cover" />
                <div v-else style="background-color:rgb(148.6, 212.3, 117.1);color: white;height: 100px;width: 100px;">
                    未上传图片
                </div>
            </div>
            <p>
                {{ item.auditStatus === 0 ? "待审核" : item.auditStatus === 1 ? "已通过" : "已驳回" }}
            </p>
            <div class="d-option">
                <el-row>
                    <el-col :span="12">
                        <el-button type="danger" @click="cancelDonarion(item.donationId)">取消捐赠</el-button>
                    </el-col>
                    <el-col :span="12" style="text-align: right;">
                        <el-button type="success" @click="goView(item)">详情</el-button>
                    </el-col>
                </el-row>
            </div>
        </div>
    </div>
    <paging-component :pageNum="Math.ceil(donationList.length / 8)" />
</template>
<script lang="ts" setup>
import pagingComponent from '@/components/universal/pagingComponent.vue';
import { deleteRemove, get } from '@/axiosConfig';
import { onMounted, ref } from 'vue';
const baseUrl = 'http://47.108.29.91:9000/donation/';
const donationList = ref([{
    donationId: '',
    mail: '',
    itemName: '',
    itemType: '',
    expressId: '',
    itemImg: '',
    rejectionReason: '',
    auditStatus: 0,
}]);
const getDonationItem = (itemtype: any) => {
    get('/donation/getItem', { mail: sessionStorage.getItem('mail'), itemtype: itemtype }).then((res) => {
        donationList.value = res.data;
    });
};
onMounted(() => {
    getDonationItem('');
});

//导入pinia中的查询数据
import { useSearchStore } from '@/stores/universal/searchStore';
import { storeToRefs } from 'pinia';
import { ElMessage } from 'element-plus';
const searchStore = useSearchStore()
const { currentPage } = storeToRefs(searchStore)

const cancelDonarion=(donationId:string)=>{
    deleteRemove(`/donation/cancelItem?donationId=${donationId}`).then((res)=>{
        ElMessage.success(res.message)
        getDonationItem('')
    })
}
import { useRouter } from 'vue-router';

import { useDonationStore } from '@/stores/donation/donationStore';
const donationStore=useDonationStore()
const {donation}=storeToRefs(donationStore)
const router=useRouter()
const goView=(item:any)=>{
    donation.value=item
    router.push('donationDetail')
}
</script>
<style scoped>
.donation-box {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 40px;
    justify-content: center;
    padding: 1%;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.1) inset;
    /* 内阴影 */
}

.d-item {
    background: linear-gradient(135deg, rgba(255, 255, 255, 0.9) 0%, rgba(255, 255, 255, 0.8) 100%);
    /* 渐变背景 */
    border-radius: 15px;
    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.15), 0 6px 6px rgba(0, 0, 0, 0.1);
    /* 多层阴影 */
    overflow: hidden;
    transition: transform 0.3s ease, box-shadow 0.3s ease;
    /* 过渡效果 */
}

.d-item:hover {
    box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2), 0 8px 8px rgba(0, 0, 0, 0.15);
    /* 悬停时更明显的阴影 */
}

.d-name {
    font-size: 1.2rem;
    font-weight: bold;
    border-bottom: 3px solid rgba(148.6, 212.3, 117.1, 0.5);
    /* 加深分割线颜色 */
    padding: 15px;
    background: linear-gradient(to right, rgba(148.6, 212.3, 117.1, 0.2), rgba(148.6, 212.3, 117.1, 0));
    /* 主题色渐变背景 */
    text-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
    /* 文字阴影 */
}

p {
    font-weight: bold;
    font-size: 1rem;
    padding: 10px 15px;
    color: #333;
    text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
    /* 文字小阴影 */
}

.d-img {
    padding: 15px;
}

.d-option {
    padding: 15px;
}

.d-option.el-button {
    border-radius: 8px;
    padding: 10px 20px;
    font-size: 1rem;
    box-shadow: 0 3px 6px rgba(0, 0, 0, 0.15);
    /* 按钮阴影 */
    transition: background-color 0.3s ease, transform 0.3s ease;
    /* 过渡效果 */
}

.d-option.el-button--danger {
    background-color: #F56C6C;
    border-color: #F56C6C;
}

.d-option.el-button--danger:hover {
    background-color: #e74c3c;
    transform: translateY(-2px);
    /* 按钮悬停时向上移动 */
}

.d-option.el-button--success {
    background-color: rgb(148.6, 212.3, 117.1);
    border-color: rgb(148.6, 212.3, 117.1);
}

.d-option.el-button--success:hover {
    background-color: rgb(120, 180, 90);
    transform: translateY(-2px);
    /* 按钮悬停时向上移动 */
}
</style>
<!-- <template>
    <div class="tb-card">
        <el-table :data="donationList.slice((currentPage - 1) * 10, currentPage * 10)" style="width: 100%">
            <el-table-column label="物品名称">
                <template #default="scope">
                    {{ scope.row.itemName }}
                </template>
            </el-table-column>
            <el-table-column label="捐赠类型" width="180">
                <template #default="scope">
                    {{ scope.row.itemType }}
                </template>
            </el-table-column>
            <el-table-column label="物品图片">
                <template #default="scope">
                    <el-image v-if="scope.row.itemImg!=''" style="width: 100px; height: 100px"
                        :src="baseUrl + scope.row.donationId + '/' + scope.row.itemImg.split(';')[0]" :zoom-rate="1.2"
                        :max-scale="7" :min-scale="0.2"
                        :preview-src-list="(scope.row.itemImg.split(';').slice(0, -1)).map((total: string) => baseUrl + scope.row.donationId + '/' + total)"
                        show-progress :initial-index="4" fit="cover" />
                </template>
            </el-table-column>
            <el-table-column label="捐赠状态">
                <template #default="scope">
                    {{ scope.row.auditStatus === 0 ? "待审核" : scope.row.auditStatus === 1 ? "审核通过" : "已驳回" }}
                </template>
            </el-table-column>
            <el-table-column label="操作" width="220">
                <template #default="scope">
                    <el-button type="danger" @click="">取消捐赠</el-button>
                </template>
            </el-table-column>
        </el-table>
        <paging-component :pageNum="Math.ceil(donationList.length / 10)" />
    </div>
</template>

<script lang="ts" setup>
//导入分页组件
import pagingComponent from '@/components/universal/pagingComponent.vue'
import { deleteRemove, get, post } from '@/axiosConfig'
import { ElMessage } from 'element-plus'
import { onMounted, ref, watch } from 'vue'
const baseUrl = 'http://47.108.29.91:9000/donation/'
import { storeToRefs } from 'pinia'
const donationList = ref([])
//获取个人报名活动
const getDonationItem = (itemtype: any) => {
    get('/donation/getItem', { mail: sessionStorage.getItem('mail'), itemtype: itemtype }).then((res) => {
        donationList.value = res.data
        console.log(res.data)
    })
}
onMounted(() => {
    //页面挂载时执行一次查询方法
    getDonationItem(null)
})
//导入pinia中的查询数据
import { useSearchStore } from '@/stores/universal/searchStore';
const searchStore = useSearchStore()
const { currentPage } = storeToRefs(searchStore)

//用watch钩子函数来监听查询数据变化，改变时查询

</script>

<style scoped>
/* 表格样式 */
.tb-card {
    overflow: hidden;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
    animation: fadeIn 0.5s ease-out;
    margin-bottom: 2vh;
    padding-bottom: 2vh;
}

@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(-20px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.el-table__header th {
    background-color: black;
    color: rgb(148.6, 212.3, 117.1);
    font-weight: bold;
    text-transform: uppercase;
    letter-spacing: 1px;
}

.el-table__body tr:nth-child(even) {
    background-color: rgba(148.6, 212.3, 117.1, 0.1);
}

.el-table__body tr:hover {
    background-color: rgba(148.6, 212.3, 117.1, 0.3);
    transform: scale(1.01);
    transition: all 0.3s ease;
}

/* 按钮样式 */
.cool-button {
    background-color: black;
    color: rgb(148.6, 212.3, 117.1);
    border: none;
    border-radius: 5px;
    padding: 8px 16px;
    cursor: pointer;
    transition: all 0.3s ease;
}

.cool-button:hover {
    background-color: rgb(148.6, 212.3, 117.1);
    color: black;
    transform: scale(1.1);
    box-shadow: 0 0 10px rgba(148.6, 212.3, 117.1, 0.5);
}

</style> -->