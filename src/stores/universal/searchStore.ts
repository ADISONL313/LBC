// stores/countdown.ts
import { fa } from 'element-plus/es/locales.mjs';
import { defineStore } from 'pinia'
 
export const useSearchStore = defineStore('searchStore',{
  state: () => ({
    selKey:'',
    selNewTime:'',
    currentPage:1,
    selMail:'',
    selRealName:'',
    selActivityName:'',
    selActivityStartTime:'',
    selActivityEndTime:'',
    btnStatus:true,
    freshHelp:false,
    freshRecord:false,
    freshValue:false
  }),
  actions: {
    clear(){
        this.selKey=''
        this.selNewTime=''
        this.selMail=''
        this.selRealName=''
        this.selActivityEndTime=''
        this.selActivityName=''
        this.selActivityStartTime=''
        this.btnStatus=true
        this.freshHelp=false
        this.freshRecord=false
        this.freshValue=false
    }
  }
});