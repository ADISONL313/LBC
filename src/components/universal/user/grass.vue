<template>
    <div class="w-full max-w-4xl bg-white rounded-xl shadow-lg p-6">
    <h2 class="text-2xl font-bold text-gray-800 mb-6">绿色滚动动画演示</h2>
    
    <!-- 动画容器 -->
    <div class="scroll-container h-24 bg-gray-50 rounded-lg border border-gray-200 mb-8">
      <!-- 滚动元素 - 可以替换为任何内容 -->
      <div class="animate-scroll inline-flex items-center space-x-4 p-4">
        <!-- 绿色操作图标示例 -->
        <div class="bg-primary rounded-full w-12 h-12 flex items-center justify-center shadow-lg">
          <i class="fa fa-check text-white text-xl"></i>
        </div>
        
        <!-- 绿色文本标签示例 -->
        <div class="bg-primary text-white px-6 py-3 rounded-full font-medium shadow-lg">
          <i class="fa fa-refresh mr-2"></i>正在处理中...
        </div>
        
        <!-- 绿色进度条示例 -->
        <div class="bg-white rounded-full h-12 px-4 py-2 shadow-lg w-64">
          <div class="bg-primary h-full rounded-full" style="width: 75%"></div>
        </div>
        
        <!-- 重复元素以增强视觉效果 -->
        <div class="bg-primary rounded-full w-12 h-12 flex items-center justify-center shadow-lg">
          <i class="fa fa-check text-white text-xl"></i>
        </div>
        
        <div class="bg-primary text-white px-6 py-3 rounded-full font-medium shadow-lg">
          <i class="fa fa-refresh mr-2"></i>正在处理中...
        </div>
      </div>
    </div>
    
    <div class="text-gray-600">
      <p>这个动画展示了一个绿色操作元素向右滚动的效果。</p>
      <p>你可以根据需要修改动画速度、元素样式或添加更多内容。</p>
    </div>
  </div>
</template>
<style  scoped>
    @layer utilities {
      .animate-scroll {
        animation: scrollRight 8s linear infinite;
      }
      
      .scroll-container {
        overflow: hidden;
        position: relative;
      }
    }
    
    @keyframes scrollRight {
      0% {
        transform: translateX(-100%);
      }
      100% {
        transform: translateX(calc(100vw + 100%));
      }
    }
  </style>