<template>
    <div style="width: 100%;justify-items: center;margin-top: 2vh;">
        <el-pagination :page-size="10" :page-count="props.pageNum"  @current-change="handlePageChange" background layout="prev, pager, next" :total="1000" />
    </div>
</template>
<script lang="ts" setup>
import { defineProps } from 'vue';
const props = defineProps({
    pageNum: Number
})
import { useSearchStore } from '@/stores/universal/searchStore';
import { storeToRefs } from 'pinia';
const searchStore = useSearchStore()
const { currentPage } = storeToRefs(searchStore)
const handlePageChange = (page: number) => {
    currentPage.value = page
}
</script>

<style>
.el-pagination.is-background .btn-next.is-active, .el-pagination.is-background .btn-prev.is-active, .el-pagination.is-background .el-pager li.is-active {
    background-color:rgb(148.6, 212.3, 117.1);
    color: var(--el-color-white);
}
/* 分页组件外层容器样式 */
.el-pagination {
    display: flex;
    justify-content: center;
    border-radius: 15px;
    padding: 15px;
    animation: slideIn 0.5s ease-out;
}

@keyframes slideIn {
    from {
        opacity: 0;
        transform: translateX(-100%);
    }
    to {
        opacity: 1;
        transform: translateX(0);
    }
}
</style>