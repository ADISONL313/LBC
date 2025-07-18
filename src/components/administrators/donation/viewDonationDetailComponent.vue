<template>
    <div class="d-container">
        <div class="d-name">
            <strong>物品名称：</strong>
            <span>
                {{ donation.itemName }}
            </span>
            <strong>物品类型：</strong>
            <span>{{ donation.itemType }}</span>
        </div>
        <div class="d-img-container">
            <el-image :src="imageArry[0]" :zoom-rate="1.2" :max-scale="7" :min-scale="0.2" :preview-src-list="imageArry"
                show-progress :initial-index="4" fit="cover" />
        </div>
        <div class="d-express">
            <strong>快递单号：</strong>
            <span>{{ donation.expressId }}</span>
        </div>
        <div>
            <strong>审核状态：</strong>
            <span>
                {{ donation.auditStatus === 0 ? "待审核" : donation.auditStatus === 1 ? "已通过" : "已驳回" }}
            </span>
        </div>
        <div class="d-pass">
            <el-input :disabled="isDisable" v-model="donation.address" placeholder="收货地址">
                <template #append>
                    <el-button :disabled="isDisable" type="success" @click="pass">通过</el-button>
                </template>
            </el-input>
        </div>
        <div class="d-rejection">
            <el-input :disabled="isDisable" v-model="donation.rejectionReason" placeholder="驳回原因">
                <template #append>
                    <el-button :disabled="isDisable" type="danger" @click="rejectionDonation">驳回</el-button>
                </template>
            </el-input>
        </div>
        <div class="d-logo">
            <logoComponent/>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { post } from '@/axiosConfig';
import { useDonationStore } from '@/stores/donation/donationStore';
import { ElMessage } from 'element-plus';
import { storeToRefs } from 'pinia';
import { onMounted, ref } from 'vue';
const donationStore = useDonationStore()
const { donation } = storeToRefs(donationStore)

const baseUrl = `http://47.108.29.91:9000/donation/${donation.value.donationId}/`
const imageArry = ref(donation.value.itemImg.split(';').slice(0, -1).map((img: string) => (baseUrl + img)))

const isDisable=ref(true)
onMounted(()=>{
    if(donation.value.expressId===''||donation.value.expressId===null){
        isDisable.value=false
    }
    console.log(donation.value.expressId)
})

const pass=()=>{
    if(donation.value.address===''||donation.value.address===null){
        ElMessage.error("请填写收货地址")
        return
    }
    donation.value.rejectionReason=''
    donation.value.auditStatus=1
    post('/donation/passDonation',donation.value).then((res)=>{
        ElMessage(res.message)
    })
}

const rejectionDonation=()=>{
    if(donation.value.rejectionReason===''||donation.value.rejectionReason===null){
        ElMessage.error("请填写驳回原因")
        return
    }
    donation.value.address=''
    donation.value.auditStatus=2
    post('/donation/rejection',donation.value).then((res)=>{
        ElMessage(res.message)
    })
}
</script>

<style scoped>
/* 整体容器样式 */
.d-container {
    background-color: white;
    border-radius: 12px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
    padding: 32px;
    color: black;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    margin: 30px auto;
    max-width: 800px;
    animation: fadeIn 0.6s ease-out;
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

/* 名称和类型部分样式 */
.d-name {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    margin-bottom: 24px;
    font-size: 18px;
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    padding-bottom: 16px;
}

.d-name strong {
    color: rgb(148.6, 212.3, 117.1);
    font-weight: 700;
}

/* 图片容器样式 */
.d-img-container {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    margin-bottom: 24px;
    justify-content: center;
}

.d-img-container .el-image {
    max-width: 250px;
    height: auto;
    border-radius: 10px;
    box-shadow: 0 6px 18px rgba(0, 0, 0, 0.12);
}

.d-img-container .el-image:hover {
    box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
}

/* 快递单号部分样式 */
.d-express {
    margin-bottom: 24px;
    font-size: 18px;
}

.d-express strong {
    color: rgb(148.6, 212.3, 117.1);
    font-weight: 700;
}

/* 审核状态部分样式 */
div>strong {
    color: rgb(148.6, 212.3, 117.1);
    font-weight: 700;
    font-size: 18px;
}

/* 通过输入框和按钮样式 */
.d-pass .el-input {
    width: 100%;
    margin-bottom: 20px;
}

.d-pass .el-button {
    background-color: rgb(148.6, 212.3, 117.1);
    border: none;
    color: white;
    font-weight: 700;
    padding: 10px 20px;
    border-top-right-radius: 8px;
    border-bottom-right-radius: 8px;
    transition: all 0.3s ease;
    box-shadow: 0 4px 10px rgba(148.6, 212.3, 117.1, 0.3);
}

.d-pass .el-button:hover {
    background-color: rgba(148.6, 212.3, 117.1, 0.8);
    box-shadow: 0 6px 15px rgba(148.6, 212.3, 117.1, 0.4);
}

/* 驳回输入框和按钮样式 */
.d-rejection .el-input {
    width: 100%;
}

.d-rejection .el-button {
    background-color: #F56C6C;
    border: none;
    color: white;
    font-weight: 700;
    padding: 10px 20px;
    border-top-right-radius: 8px;
    border-bottom-right-radius: 8px;
    transition: all 0.3s ease;
    box-shadow: 0 4px 10px rgba(245, 108, 108, 0.3);
}

.d-rejection .el-button:hover {
    background-color: #f35050;
    box-shadow: 0 6px 15px rgba(245, 108, 108, 0.4);
}
.d-logo{
    gap: 20px;
    margin-top: 24px;
    border-top: 1px solid rgba(0, 0, 0, 0.1);
    padding-top: 16px;
}
</style>