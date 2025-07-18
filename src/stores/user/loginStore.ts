// stores/countdown.ts
import { defineStore } from 'pinia'
 
export const useLoginStore = defineStore('loginStore',{
  state: () => ({
    count: 60, // 倒计时的初始值，如60秒
    intervalId: 0, // 存储interval的ID
    verifyCode:'',
    mail:''
  }),
  actions: {
    startCountdown() {
      if (this.intervalId) return; // 确保只有一个倒计时实例
 
      this.intervalId = window.setInterval(() => {
        if (this.count > 0) {
          this.count -= 1;
        } else {
          this.stopCountdown();
        }
      }, 1000);
    },
    stopCountdown() {
      if (this.intervalId) {
        window.clearInterval(this.intervalId);
        this.intervalId = 0;
      }
      this.count = 60; // 重置倒计时
    }
  }
});