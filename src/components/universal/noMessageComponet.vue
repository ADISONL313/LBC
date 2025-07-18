<template>

    <div ref="autoMation" style="padding: 5%;height: 65vh;">
    </div>
</template>
<script lang="ts" setup>
import Lottie from 'lottie-web';
import noMessage from '@/assets/noMessage.json'
import { onMounted, ref } from 'vue';
const autoMation = ref();
onMounted(() => {
    Lottie.loadAnimation({
        container: autoMation.value,
        renderer: 'svg',
        loop: true,
        autoplay: true,
        animationData: noMessage
    })
})
</script>