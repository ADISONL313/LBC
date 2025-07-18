<template>
    <el-popconfirm placement="bottom" width="400" :icon="InfoFilled" icon-color="#95D475" title="我需要帮助"
        @cancel="onCancel">
        <template #reference>
            <div class="help-btn">
                <p class="help-tip">寻求帮助</p>
                <img class="cool-img" src="../../../assets/user/hands-helping.png" alt="按钮图片">

            </div>
        </template>
        <template #actions="{ confirm, cancel }">
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
            </el-form>
            <el-button size="small" @click="cancel">取消</el-button>
            <el-button type="success" size="small" @click="sendHelp">
                求助
            </el-button>
        </template>
    </el-popconfirm>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { InfoFilled } from '@element-plus/icons-vue'
import { addHelp, locationAddress } from './ts/helpTsMethods'

const help = ref({
    helpTitle: '',
    helpContent: '',
    phone: '',
    emergencyPhone: '',
    helpStatus: 0,
    address: '',
    release_time: ''
})

const clicked = ref(false)
function onCancel() {
    clicked.value = true
}
//获取当前ip地址
const getIpAddress = async () => {
    const location = await locationAddress()
    help.value.address = (await location.json()).regeocode.formatted_address
}
import { inject } from 'vue'
const setMessage = inject('setMessage')

import { useSearchStore } from "@/stores/universal/searchStore"
import { storeToRefs } from "pinia"
import { ElMessage } from 'element-plus'
const searchStore = useSearchStore()
const { freshHelp } = storeToRefs(searchStore)

const sendHelp = async () => {
    const res = await addHelp(help.value)
    if (res === true) {
        freshHelp.value = true
        ElMessage.success('求助成功')
    }else {
        ElMessage.error('求助失败')
    }
}
</script>
<style scoped>
.help-btn {
    position: fixed;
    top: 10%;
    left: 5%;
}

.cool-img {
    width: 74px;
    height: 74px;
    filter: drop-shadow(0 0 15px rgba(148.6, 212.3, 117.1, 0.9)) hue-rotate(0deg);
    transform: scale(1) rotate(0deg);
    transition: all 0.4s cubic-bezier(0.68, -0.55, 0.27, 1.55);
    /* 增加动画时长，让旋转变慢 */
}

.cool-img:hover {
    filter: drop-shadow(0 0 30px rgba(148.6, 212.3, 117.1, 1)) hue-rotate(0deg);
    transform: scale(1.5) rotate(180deg);
    animation: none;
}

.help-tip {
    font-size: 20px;
    font-weight: bolder;
    /* 修正颜色值，rgba 取值范围 0 - 255 */
    color: rgba(148, 212, 117, 0.9);
    /* 添加文字阴影 */
    text-shadow: 0 0 5px rgba(148, 212, 117, 0.5),
        0 0 10px rgba(148, 212, 117, 0.4),
        0 0 15px rgba(148, 212, 117, 0.3),
        0 0 20px rgba(148, 212, 117, 0.2);
    /* 添加动态光影效果 */
    animation: glow 1.5s ease-in-out infinite alternate;
}

@keyframes glow {
    from {
        text-shadow: 0 0 5px rgba(148, 212, 117, 0.5),
            0 0 10px rgba(148, 212, 117, 0.4),
            0 0 15px rgba(148, 212, 117, 0.3),
            0 0 20px rgba(148, 212, 117, 0.2);
    }

    to {
        text-shadow: 0 0 10px rgba(148, 212, 117, 0.7),
            0 0 20px rgba(148, 212, 117, 0.6),
            0 0 30px rgba(148, 212, 117, 0.5),
            0 0 40px rgba(148, 212, 117, 0.4);
    }
}
</style>