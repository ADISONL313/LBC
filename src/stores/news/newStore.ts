// stores/countdown.ts
import { defineStore } from 'pinia'
 
export const useNewStore = defineStore('newStore',{
  state: () => ({
    news:{
        newId:'',
        newTitle:'',
        newContent:'',
        newTime:'',
        reporter:'',
        newImg:'',
        hits:0,
        address:'',
        mail:'',
        process:0,
        rejectionReason:''
    }
  }),
  actions: {
    clear(){
        this.news.address=''
        this.news.hits=0
        this.news.mail=''
        this.news.newContent=''
        this.news.newId=''
        this.news.newTime=''
        this.news.newTitle=''
        this.news.newImg=''
        this.news.process=0
        this.news.reporter=''
        this.news.rejectionReason=''
    }
  }
});