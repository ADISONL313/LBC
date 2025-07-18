<template>
    <div style="margin-top: 2vh;">
        <el-form>
            <el-form-item>
                <el-input style="width: 700px;" v-model="mail" placeholder="用户账号"></el-input>
                <el-divider direction="vertical" />
                <el-button type="success" class="search-btn" @click="search" style="width: 100px;">搜索</el-button>
            </el-form-item>
        </el-form>
    </div>
    <el-table :data="helpList.slice((currentPage - 1) * 10, currentPage * 10)" style="width: 100%">
        <el-table-column label="求助用户">
            <template #default="scope">
                {{ scope.row.mail }}
            </template>
        </el-table-column>
        <el-table-column label="求助状态">
            <template #default="scope">
                {{ scope.row.helpStatus === 0 ? "等待救援" : scope.row.helpStatus === 1 ? "救助中" : "救助完成" }}
            </template>
        </el-table-column>
        <el-table-column label="发布时间" width="180">
            <template #default="scope">
                {{ scope.row.releaseTime }}
            </template>
        </el-table-column>
        <el-table-column label="救助时间" width="180">
            <template #default="scope">
                {{ scope.row.helpTime }}
            </template>
        </el-table-column>
        <el-table-column label="救助地址">
            <template #default="scope">
                {{ scope.row.address }}
            </template>
        </el-table-column>
        <el-table-column label="联系电话">
            <template #default="scope">
                {{ scope.row.phone }}
            </template>
        </el-table-column>
        <el-table-column label="操作" width="300">
            <template #default="scope">
                <el-button class="cool-button" type="success" @click="goView(scope.row)">查看</el-button>
            </template>
        </el-table-column>
    </el-table>
    <paging-component :pageNum="Math.ceil(helpList.length / 10)" />

    <n-drawer v-model:show="helpActive" :default-width="1004" placement="left" resizable>
        <n-drawer-content :title="'求助详情'">
            <helpDetailCardComponent :helpData="help" />
            <assignVolunteerComponent :helpData="help" />
            <helpRecordListComponent :helpData="help" />
        </n-drawer-content>
    </n-drawer>
</template>
<script lang="ts" setup>
import helpRecordListComponent from '@/components/ordinaryUsers/help/helpRecordListComponent.vue';
import assignVolunteerComponent from './assignVolunteerComponent.vue';
import helpDetailCardComponent from './helpDetailCardComponent.vue';
import { getMyHelp } from '@/components/ordinaryUsers/help/ts/helpTsMethods';
import pagingComponent from '@/components/universal/pagingComponent.vue';
import { useSearchStore } from '@/stores/universal/searchStore';
import { storeToRefs } from 'pinia';
import { onMounted, ref } from 'vue';
const searchStore = useSearchStore()
const { currentPage } = storeToRefs(searchStore)
const mail=ref(null)
const helpList = ref([])

onMounted(async () => {
    helpList.value = await getMyHelp({})
})
const help = ref()
const helpActive = ref(false)
const goView = (item: any) => {
    help.value = item
    helpActive.value = true
}
const search=async()=>{
    console.log(111)
    helpList.value=await getMyHelp({mail:mail.value})
}
</script>
<style scoped>
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