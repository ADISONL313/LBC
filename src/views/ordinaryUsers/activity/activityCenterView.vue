<template>
    <n-tabs type="segment" animated>
      <n-tab-pane name="chap1" style="padding:0;" tab="浏览活动">
        <div style=" display: flex; flex-direction: column;align-items: center;">
            <activityListComponent />
        </div>
      </n-tab-pane>
      <n-tab-pane name="chap2" style="padding: 0%;" tab="我的报名">
        <activityRegistrationTableComponent/>
      </n-tab-pane>
    </n-tabs>
  </template>
  <script lang="ts" setup>
  import activityListComponent from '@/components/ordinaryUsers/activity/activityListComponent.vue';
  import activityRegistrationTableComponent from '@/components/ordinaryUsers/activity/activityRegistrationTableComponent.vue';
  </script>