<template>
    <n-tabs type="segment" animated placement="right" style="margin: 0%;">
        <n-tab-pane name="chap1" style="padding:0"  tab="发布新闻">

            <releaseNewComponent />
        </n-tab-pane>
        <n-tab-pane name="chap2" style="padding:0" tab="管理新闻">
            <newsTableComponent />
        </n-tab-pane>
    </n-tabs>
</template>
<script lang="ts" setup>
import releaseNewComponent from '@/components/administrators/news/releaseNewComponent.vue';
import newsTableComponent from '@/components/administrators/news/newsTableComponent.vue';
</script>
<style >
.n-tabs .n-tabs-rail .n-tabs-tab-wrapper .n-tabs-tab.n-tabs-tab--active {
    font-weight:bold;
    font-size: medium;
    color:rgb(148.6, 212.3, 117.1);
}
.n-tabs .n-tabs-rail .n-tabs-capsule {
    border-radius: var(--n-tab-border-radius);
    position: absolute;
    pointer-events: none;
    background-color: #000000;
    box-shadow: 0 1px 3px 0 rgba(0, 0, 0, .08);
    transition: transform 0.3s var(--n-bezier);
}
</style>