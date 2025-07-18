<template>
    <n-tabs type="segment" animated>
        <n-tab-pane name="chap1" style="padding:0;" tab="动态">
            111
        </n-tab-pane>
        <n-tab-pane name="chap2" style="padding: 0%;" tab="声名">
            <agreement />
        </n-tab-pane>
    </n-tabs>
</template>
<script lang="ts" setup>
import agreement from '@/components/universal/agreement.vue';
</script>