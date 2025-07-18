<template>
    <el-form>
        <el-form-item>
            <el-input v-model="help.helpTitle" placeholder="标题"></el-input>
        </el-form-item>
        <el-form-item>
            <el-input v-model="help.helpContent" placeholder="内容" :rows="2" type="textarea"></el-input>
        </el-form-item>
        <el-form-item>
            <el-input v-model="help.phone" maxlength="11" placeholder="电话"></el-input>
        </el-form-item>
        <el-form-item>
            <el-input v-model="help.emergencyPhone" maxlength="11" placeholder="紧急电话"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button title="定位" @click="getIpAddress"><el-icon>
                    <LocationFilled />
                </el-icon></el-button><el-divider direction="vertical" />
            {{ help.address }}
        </el-form-item>
        <el-form-item>
            <el-button type="success" @click="updateData">修改</el-button>
        </el-form-item>
    </el-form>
</template>
<script lang="ts" setup>
import { onMounted, ref } from 'vue'
import { locationAddress, updateHelpData } from './ts/helpTsMethods'
import { ElMessage } from 'element-plus'
const help=ref({
    helpId:'',
    mail:'',
    helpTitle:'',
    helpContent:'',
    phone:'',
    emergencyPhone:'',
    helpStatus:0,
    address:'',
    helpTime:'',
    releaseTime:''
})
const props = defineProps({
    helpData: Object
})
onMounted(()=>{
    if (props.helpData) {
        help.value.helpId = props.helpData.helpId || ''
        help.value.mail = props.helpData.mail || ''
        help.value.helpTitle = props.helpData.helpTitle || ''
        help.value.helpContent = props.helpData.helpContent || ''
        help.value.phone = props.helpData.phone || ''
        help.value.emergencyPhone = props.helpData.emergencyPhone || ''
        help.value.helpStatus = props.helpData.helpStatus ?? 0
        help.value.address = props.helpData.address || ''
        help.value.helpTime = props.helpData.helpTime || ''
        help.value.releaseTime = props.helpData.releaseTime || ''
    }
})
const getIpAddress = async () => {
    const location = await locationAddress()
    help.value.address = (await location.json()).regeocode.formatted_address
}
const updateData=async()=>{
    const res=await updateHelpData(help.value)
}
</script>