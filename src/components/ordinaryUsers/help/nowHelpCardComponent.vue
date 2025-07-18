<template>
    <div class="v-card">
        <div class="current-task">
            <el-row :gutter="0">
              <el-col :span="12">当前任务</el-col>
              <el-col :span="12"><el-button type="success" @click="finishThisHelp(props.nowTask?.volunteerHelpId)">完成</el-button></el-col>
            </el-row>
        </div>
        <div class="help-title">
            标题：{{ props.nowTask?.helpTitle }}
        </div>
        <div class="help-content">
            内容：{{ props.nowTask?.helpContent }}
        </div>
        <div class="help-ad-t">
            <span>求救地址：{{ props.nowTask?.address }}</span>
            <span>发布时间：{{ props.nowTask?.releaseTime }}</span>
        </div>
        <div class="help-info">
            <span>求救用户：{{ props.nowTask?.mail }}</span>
            <span>联系电话：{{ props.nowTask?.phone }}</span>
            <span>紧急电话：{{ props.nowTask?.emergencyPhone }}</span>
        </div>
    </div>
</template>
<script lang="ts" setup>
import { finishThisHelp } from './ts/helpTsMethods';

const props=defineProps({
    nowTask:Object
})
</script>
<style scoped>
/* 设置全局字体 */
body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

/* 卡片整体样式 */
.v-card {
    margin: 0 10% 2vh 10%;
    padding: 30px;
    border-radius: 15px;
    box-shadow: 0 0 20px rgba(148.6, 212.3, 117.1, 0.8);
    position: relative;
    overflow: hidden;
    animation: fadeInUp 0.5s ease;
    background-color: white;
    border: 1px solid #e0e0e0;
    transition: all 0.3s ease;
}

/* 鼠标悬停效果 */
.v-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 0 30px rgba(148.6, 212.3, 117.1, 1);
}

/* 当前任务样式 */
.current-task {
    font-size: 20px;
    font-weight: 600;
    color: #555;
    margin-bottom: 20px;
    text-align: center;
    text-transform: uppercase;
    letter-spacing: 1px;
}

/* 标题样式 */
.help-title {
    font-size: 24px;
    font-weight: bold;
    color: #333;
    margin-bottom: 15px;
    border-bottom: 1px solid #e0e0e0;
    padding-bottom: 10px;
}

/* 内容样式 */
.help-content {
    font-size: 16px;
    color: #666;
    margin-bottom: 20px;
    line-height: 1.6;
    text-align: justify;
}

/* 地址和时间样式 */
.help-ad-t {
    display: flex;
    justify-content: space-between;
    font-size: 14px;
    color: #888;
    margin-bottom: 15px;
    border-top: 1px solid #e0e0e0;
    padding-top: 10px;
}

/* 信息样式 */
.help-info {
    display: flex;
    justify-content: space-between;
    font-size: 14px;
    color: #888;
    border-top: 1px solid #e0e0e0;
    padding-top: 10px;
}

/* 动画效果 */
@keyframes fadeInUp {
    from {
        opacity: 0;
        transform: translateY(20px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
}

/* 响应式设计 */
@media (max-width: 768px) {
    .v-card {
        margin: 0 5% 2vh 5%;
        padding: 20px;
    }

    .help-title {
        font-size: 20px;
    }

    .help-content {
        font-size: 14px;
    }

    .help-ad-t,
    .help-info {
        flex-direction: column;
    }

    .help-ad-t span,
    .help-info span {
        margin-bottom: 5px;
    }
}
</style>