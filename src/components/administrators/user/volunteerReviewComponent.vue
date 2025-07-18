<template>
    <div class="v-r">
        <h3>
            志愿者申请
        </h3>
        <div style="padding-bottom: 10px;padding-left: 5px;font-size: 16px;font-weight: bolder;"
            v-if="volunteerData?.exStatus === 0">
            用户已提交申请：<el-button type="success" @click="pass">通过</el-button>
        </div>
        <div v-if="volunteerData?.exStatus === 1">
            用户已获得志愿者身份
        </div>
        <div>
            <el-form>
                <el-form-item>
                    <el-input placeholder="驳回原因" v-model="rejectReason" type="textarea"></el-input>
                </el-form-item>
                <div>

                    <el-button type="danger" @click="rejection">
                        {{ volunteerData?.exStatus===0?"驳回":volunteerData?.exStatus===1?"收回志愿者权限":"驳回" }}
                    </el-button>
                </div>
            </el-form>

        </div>
    </div>
</template>
<script lang="ts" setup>
import { getVolunteer } from '@/components/ordinaryUsers/user/ts/userMethods';
import { onMounted, ref } from 'vue';
import { passVoluntter, rejectionVoluntter } from './ts/adminUserMethods';

const props = defineProps({
    mail: String
})
const rejectReason = ref('')
const volunteerData = ref()
onMounted(async () => {
    console.log(props.mail)
    const res = await getVolunteer(props.mail + '')
    volunteerData.value = res
    rejectReason.value=res.rejectReason
})

const pass=async()=>{
    const res=await passVoluntter(props.mail)
    if(res===true){
        volunteerData.value.exStatus=1
    }
}

const rejection=async()=>{
    const res=await rejectionVoluntter(rejectReason.value,props.mail)
    if(res===true){
        volunteerData.value.exStatus=2
    }
}
</script>
<style scoped>
.v-r {
    flex-direction: column;
    align-items: center;
    padding: 20px;
    border-radius: 15px;
    background-color: white;
    box-shadow: 0 0 20px rgba(148.6, 212.3, 117.1, 0.6);
    transition: all 0.3s ease;
    perspective: 1000px;
    margin-top: 2vh;
}

.v-r:hover {
    box-shadow: 0 0 30px rgba(148.6, 212.3, 117.1, 0.8);
    transform: scale(1.05);
    filter: brightness(1.1);
}

h3 {
    text-align: center;
    width: 100%;
    color: rgb(148.6, 212.3, 117.1);
    font-size: 20px;
    margin-top: 10px;
    text-shadow: 0 0 5px rgba(0, 0, 0, 0.3);
    z-index: 1;
    border-bottom: 1px solid rgb(148.6, 212.3, 117.1);
}
</style>