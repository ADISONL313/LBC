<template>
  <RouterView />
</template>

<script setup lang="ts">
import lingDongIsland from './components/universal/lingDongIsland.vue';
import { RouterView, useRouter } from 'vue-router'
const router = useRouter()
</script>