<template>
    <el-form class="appliance-card">
        <el-form-item>
            <el-input :disabled="isDisable" placeholder="物品名称" v-model="donation.itemName"></el-input>
        </el-form-item>
        <el-form-item>
            <el-select v-model="donation.itemType" placeholder="物品类型" clearable>
                <el-option label="衣服" value="衣服"></el-option>
                <el-option label="书籍" value="书籍">书籍</el-option>
            </el-select>
        </el-form-item>
        <el-form-item>
            审核状态：{{ donation.auditStatus === 0? "待审核" : donation.auditStatus === 1? "已通过" : "已驳回" }}
        </el-form-item>
        <el-form-item v-if="donation.auditStatus===2">
            驳回原因：{{ donation.rejectionReason }}
        </el-form-item>
        <el-form-item>
            邮寄到：{{ donation.address }}
        </el-form-item>
        <el-form-item v-if="donation.auditStatus===1">
            <el-input placeholder="快递单号" v-model="donation.expressId"></el-input>
        </el-form-item>
        <processFilesComponent :fileType="'donation'" />
    </el-form>
</template>

<script lang="ts" setup>
import processFilesComponent from '@/components/universal/processFilesComponent.vue';
import { storeToRefs } from 'pinia';
import { useDonationStore } from '@/stores/donation/donationStore';
import { onMounted, ref } from 'vue';
const donationStore = useDonationStore()
const { donation } = storeToRefs(donationStore)
const isDisable = ref(true)
onMounted(() => {
    console.log(donation.value.expressId)
    if (donation.value.expressId === null || donation.value.expressId === '') {
        isDisable.value = false
    }
})
</script>

<style scoped>
/* 整体表单容器样式 */
.appliance-card {
    margin-top: 1%;
    margin-left: 10%;
    margin-right: 10%;
    margin-bottom: 1%;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* 更柔和的阴影 */
    border-radius: 10px; /* 圆角 */
    background-color: #fff; /* 白色背景 */
    padding: 20px; /* 增加内边距 */
    transition: all 0.3s ease; /* 过渡效果 */
}

.appliance-card:hover {
    box-shadow: 0 0 15px rgba(0, 0, 0, 0.2); /* 鼠标悬停时加深阴影 */
}

/* 表单项样式 */
.el-form-item {
    margin-bottom: 15px; /* 增加表单项之间的间距 */
}

/* 输入框样式 */
.el-input {
    border: none; /* 去掉边框 */
    border-radius: 0; /* 去掉圆角 */
    padding: 10px 0; /* 调整内边距，只保留上下内边距 */
    background-color: transparent; /* 透明背景 */
    transition: border-bottom-color 0.3s ease; /* 底部边框颜色过渡效果 */
}
/* 选择框样式 */
.el-select {
    border-radius: 5px; /* 选择框圆角 */
}


/* 文字样式 */
.el-form-item__label {
    font-weight: 500; /* 加粗标签文字 */
    color: #333; /* 标签文字颜色 */
}

/* 审核状态、驳回原因等文字样式 */
.el-form-item__content {
    color: #666; /* 内容文字颜色 */
    font-size: 14px; /* 文字大小 */
}
</style>