<template>
    <div class="tb-card">
        <el-form>
            <el-form-item >
                <el-input style="width: 30vh;" v-model="mail" placeholder="用户邮箱"></el-input>
                <el-divider direction="vertical" />
                <el-select style="width: 20vh;" v-model="itemType" placeholder="物资类型" clearable>
                    <el-option label="衣物" value="衣物">衣物</el-option>
                    <el-option label="书籍" value="书籍">书籍</el-option>
                </el-select>
                <el-divider direction="vertical" />
                <el-button type="success" class="search-btn" @click="search" style="width: 100px;">搜索</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="donationList.slice((currentPage - 1) * 10, currentPage * 10)" style="width: 100%">
            <el-table-column label="捐赠用户">
                <template #default="scope">
                    {{ scope.row.mail }}
                </template>
            </el-table-column>
            <el-table-column label="物品名称">
                <template #default="scope">
                    {{ scope.row.itemName }}
                </template>
            </el-table-column>
            <el-table-column label="物品类型">
                <template #default="scope">
                    {{ scope.row.itemType }}
                </template>
            </el-table-column>
            <el-table-column label="审核状态" width="90">
                <template #default="scope">
                    {{ scope.row.auditStatus === 0 ? "待审核" : scope.row.auditStatus === 1 ? "已通过" : "已驳回" }}
                </template>
            </el-table-column>
            <el-table-column label="操作" width="180">
                <template #default="scope">
                    <el-button class="cool-button" @click="goView(scope.row)">查看</el-button>
                    <el-button type="danger" @click="delDonation(scope.row.donationId)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <paging-component :pageNum="Math.ceil(donationList.length / 10)" />
    </div>
</template>

<script lang="ts" setup>
//导入分页组件
import { deleteRemove, get } from '@/axiosConfig';
import pagingComponent from '@/components/universal/pagingComponent.vue'
import { onMounted, ref } from 'vue';

import { useDonationStore } from '@/stores/donation/donationStore';
import { storeToRefs } from 'pinia';
const donationStore = useDonationStore()
const { donation } = storeToRefs(donationStore)

import { useSearchStore } from '@/stores/universal/searchStore';
import { ElMessage } from 'element-plus';
const searchStore = useSearchStore()
const { currentPage } = storeToRefs(searchStore)

const donationList = ref([])
const mail = ref('')
const itemType = ref('')
const getDonationItem = () => {
    get('/donation/getItem', { mail: mail.value, itemType: itemType.value }).then((res) => {
        donationList.value = res.data;
    });
};
onMounted(() => {
    getDonationItem()
})

const delDonation = (donationId: string) => {
    deleteRemove(`/donation/cancelItem?donationId=${donationId}`).then((res) => {
        ElMessage.success(res.message)
        getDonationItem()
    })
}

import { useRouter } from 'vue-router';
const router = useRouter()
import { getDonationState } from '@/components/universal/user/tsMethods/tsMethods';
import { useDonationStateStore } from '@/stores/donation/donationStateStore';
const donationStateStore = useDonationStateStore()
const { donationState } = storeToRefs(donationStateStore)
const goView = async (item: any) => {
    donation.value = item
    const res: any = await getDonationState(item.donationId)
    donationState.value = res.data[0]
    router.push('donationState')
}

const search=()=>{
    getDonationItem()
}
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

.el-form {
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 20px;
    animation: fadeIn 0.5s ease-out;
    margin-top: 2vh;
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

/* 输入框样式 */
.el-input__inner {
    border: 2px solid black;
    border-radius: 8px;
    padding: 10px 15px;
    transition: all 0.3s ease;
}

.el-input__inner:hover,
.el-input__inner:focus {
    border-color: rgba(0, 0, 0, 0.7);
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

/* 日期选择器样式 */
.el-date-picker__editor {
    border: 2px solid black;
    border-radius: 8px;
    padding: 10px 15px;
    transition: all 0.3s ease;
}

.el-date-picker__editor:hover,
.el-date-picker__editor:focus {
    border-color: rgba(0, 0, 0, 0.7);
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

/* 分割线样式 */
.el-divider--vertical {
    background-color: black;
}

/* 搜索按钮样式 */
.search-btn {
    background-color: black;
    color: rgb(148.6, 212.3, 117.1);
    border: none;
    border-radius: 8px;
    padding: 10px 15px;
    font-weight: bold;
    text-transform: uppercase;
    letter-spacing: 1px;
    transition: all 0.3s ease;
}

.search-btn:hover {
    background-color: rgb(148.6, 212.3, 117.1);
    transform: scale(1.05);
    color: black;
    box-shadow: 0 0 15px rgba(0, 0, 0, 0.3);
}
</style>