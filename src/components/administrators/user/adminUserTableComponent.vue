<template>
    <div class="tb-card">
        <search-user-component/>
        <el-table :data="userList.slice((currentPage - 1) * 10, currentPage * 10)" style="width: 100%">
            <el-table-column label="邮箱">
                <template #default="scope">
                    {{ scope.row.mail }}
                </template>
            </el-table-column>
            <el-table-column label="身份">
                <template #default="scope">
                    {{ scope.row.userRole===1?"普通用户":"志愿者" }}
                </template>
            </el-table-column>
            <el-table-column label="联系电话">
                <template #default="scope">
                    {{ scope.row.phoneNumber }}
                </template>
            </el-table-column>
            <el-table-column label="昵称" width="180">
                <template #default="scope">
                    {{ scope.row.nickName }}
                </template>
            </el-table-column>
            <el-table-column label="撰稿权限" width="180">
                <template #default="scope">
                    {{ scope.row.reportPermission === 0 ? "审核中" : scope.row.reportPermission === 1 ? "审核通过" : "审核失败" }}
                </template>
            </el-table-column>
            <el-table-column label="捐赠权限" width="180">
                <template #default="scope">
                    {{ scope.row.donationPermission === 0 ? "审核中" : scope.row.donationPermission === 1 ? "审核通过" : "审核失败"
                    }}
                </template>
            </el-table-column>
            <el-table-column label="操作" width="180">
                <template #default="scope">
                    <el-button class="cool-button" @click="showDetail(scope.row)">查看</el-button>
                </template>
            </el-table-column>
        </el-table>
        <paging-component :pageNum="Math.ceil(userList.length / 10)" />
    </div>
    <n-drawer v-model:show="visibleDrawer" :on-after-leave="handleClose" :default-width="700" placement="left"
        resizable>
        <n-drawer-content show-mask="false" :title="realName">
            <div class="head-img">
                <h3>头像</h3>
                <img :src="headImg">
            </div>
            <user-center-detail-info-component />
            <rejection-user-info-component/>
            <volunteer-review-component :mail="userMail" />
        </n-drawer-content>
    </n-drawer>
</template>

<script lang="ts" setup>
import volunteerReviewComponent from './volunteerReviewComponent.vue';
import searchUserComponent from './searchUserComponent.vue';
import rejectionUserInfoComponent from './rejectionUserInfoComponent.vue';
import userCenterDetailInfoComponent from '@/components/ordinaryUsers/user/userCenterDetailInfoComponent.vue';
//导入pinia中的查询数据
import { useSearchStore } from '@/stores/universal/searchStore';
const searchStore = useSearchStore()
const { currentPage,selMail,selRealName } = storeToRefs(searchStore)
//导入分页组件
import { storeToRefs } from 'pinia'
import pagingComponent from '@/components/universal/pagingComponent.vue'
import { get } from '@/axiosConfig'
import { onMounted, ref, watch } from 'vue'
const userList = ref([])
//获取新闻列表方法
const getUserList = (mail: string, realName: string) => {
    get('/user/getUser', { mail: mail, realName: realName }).then((res) => {
        console.log(res.data)
        userList.value = res.data
    })
}
onMounted(() => {
    //页面挂载时执行一次查询方法
    getUserList('', '')
})
//导入pinia用户对象
import { useUserDetailStore } from '@/stores/user/userDetailStore';
const userDetailStore = useUserDetailStore()
const { userDetail } = storeToRefs(userDetailStore)
//将新闻对象转为响应式对象
const realName = ref()
const visibleDrawer = ref(false)
//头像
const headImg = ref()
const userMail=ref('')
const showDetail = (item: any) => {
    userMail.value=item.mail
    visibleDrawer.value = true
    realName.value = item.realName + '的详细信息'
    userDetail.value = item
    headImg.value = 'http://47.108.29.91:9000/user/' + item.userDetailId + '/' + item.idPhoto
}
//naive ui 抽屉关闭回调
const handleClose = () => {
    getUserList('','')
}
//用watch钩子函数来监听查询数据变化，改变时查询
watch([selMail,selRealName],()=>{
    getUserList(selMail.value,selRealName.value)
})
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

.head-img {
    text-align: left;
    position: relative;
    display: flex;
    flex-direction: column;
    align-items: center;
    border-radius: 15px;
    background-color: white;
    box-shadow: 0 0 20px rgba(148.6, 212.3, 117.1, 0.6);
    overflow: hidden;
    transition: all 0.3s ease;
    padding: 15px;
}

.head-img:hover {
    box-shadow: 0 0 30px rgba(148.6, 212.3, 117.1, 0.8);
    transform: scale(1.05);
}

.head-img h3 {
    text-align: center;
    width: 100%;
    color: rgb(148.6, 212.3, 117.1);
    font-size: 20px;
    margin-top: 10px;
    text-shadow: 0 0 5px rgba(0, 0, 0, 0.3);
    z-index: 1;
    border-bottom: 1px solid rgb(148.6, 212.3, 117.1);
}

.head-img img {
    width: 150px;
    height: 150px;
    object-fit: cover;
    border: 5px solid rgba(148.6, 212.3, 117.1, 0.6);
    box-shadow: 0 0 15px rgba(148.6, 212.3, 117.1, 0.5);
    transition: all 0.3s ease;
}

.head-img img:hover {
    transform: scale(1.1);
    box-shadow: 0 0 20px rgba(148.6, 212.3, 117.1, 0.7);
}
</style>