<template>
    <!-- el container布局 -->
    <el-container>
        <el-header>
            <div v-if="token!==null&&mail==='admin'">
                <adminMenuComponent/>
            </div>
            <div v-if="token!==null&&mail!=='admin'">
                <ordinaryMenuComponent/>
            </div>
            <div v-if="token===null&&mail===null">
                <noLoginMenuComponent/>
            </div>
        </el-header>
        <el-main>
            <RouterView />
        </el-main>
    </el-container>
</template>
<script lang="ts" setup>
import adminMenuComponent from '@/components/universal/user/menu/adminMenuComponent.vue';
import ordinaryMenuComponent from '@/components/universal/user/menu/ordinaryMenuComponent.vue';
import noLoginMenuComponent from '@/components/universal/user/menu/noLoginMenuComponent.vue';
const token=sessionStorage.getItem('token')
const mail=sessionStorage.getItem('mail')
</script>
<style scoped>
.el-header {
    padding: 0px;
    margin: 0px;
    border: none;
}
div{
    margin: 0px;
    padding: 0px;
}
.el-main{
    padding: 0px;
    margin: 0px;
}
</style>