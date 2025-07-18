<template>
    <iframe ref="Geo" style="height: 400px;width: 400px;"
        src="https://m.amap.com/picker/?key=e9417ad15f42bee9edce9a08f08bb922&jscode=f2f6202df04429228226af759917cf27">
    </iframe>
</template>
<script lang="ts" setup>
import { storeToRefs } from 'pinia'
import { useActivityStore } from '@/stores/activity/activityStore';
const activityStore=useActivityStore()
const {activity}=storeToRefs(activityStore)
import { onMounted, onUnmounted, ref } from 'vue';

import { defineEmits } from 'vue';
const emits=defineEmits(['send-data'])
// 定义 iframe 的 ref
const Geo = ref<HTMLIFrameElement | null>(null);
onMounted(() => {

    if (Geo.value) {
        // 监听 iframe 加载完成事件
        Geo.value.onload = () => {
            const iframeWindow = Geo.value?.contentWindow;
            if (iframeWindow) {
                // 向 iframe 发送消息
                iframeWindow.postMessage('hello', 'https://m.amap.com/picker/');
            }
        };

        // 监听窗口的 message 事件
        const handleMessage = (e: MessageEvent) => {
            const data = e.data as { name: string; location: string };
            emits('send-data',data.name+";"+data.location)
        };

        window.addEventListener('message', handleMessage, false);

        // 在组件卸载时移除事件监听器，避免内存泄漏
        onUnmounted(() => {
            window.removeEventListener('message', handleMessage);
        });
    }
});
</script>