<template>
  <n-tabs type="segment" animated>
    <n-tab-pane name="chap1" style="padding:0;" tab="浏览新闻">
      <div style=" display: flex; flex-direction: column;align-items: center;">
        <news-list-component :listNum="true" />
      </div>
    </n-tab-pane>
    <n-tab-pane name="chap2" style="padding: 0%;" tab="发布新闻">
      <release-new-component/>
    </n-tab-pane>
    <n-tab-pane name="chap3" style="padding: 0%;" tab="我的新闻">
      <news-table-component/>
    </n-tab-pane>
  </n-tabs>
</template>
<script lang="ts" setup>
import newsListComponent from '@/components/ordinaryUsers/news/newsListComponent.vue';
import releaseNewComponent from '@/components/administrators/news/releaseNewComponent.vue';
import newsTableComponent from '@/components/administrators/news/newsTableComponent.vue';
</script>