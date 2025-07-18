<template>
    <div style="justify-items: center;align-items: center;margin-top: 20vh;">
        <el-card>
            <template #header>
                <logoLBC/>
            </template>
            <forgetPwdComponent />
        </el-card>
    </div>
</template>
<script lang="ts" setup>
import logoLBC from '@/components/logoLBC.vue';
import forgetPwdComponent from '@/components/universal/user/forgetPwdComponent.vue';
</script>
<style scoped>
.el-card__header {
    padding: 0px;
    border-bottom: 1px solid #67C23A;
}

.el-card {
    background-color: rgb(148.6, 212.3, 117.1);
    width: 500px;
}
</style>