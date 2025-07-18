// stores/countdown.ts
import { defineStore } from 'pinia'
 
export const useHelpRecordStore = defineStore('helpRecord',{
  state: () => ({
    helpRecord:{
        helpRecordId:'',
        mail:'',
        recordDescription:'',
        recordImage:'',
        recordDate:'',
        helpId:''
    }
  }),
  actions: {
    clear(){
        this.helpRecord.helpRecordId=''
        this.helpRecord.mail=''
        this.helpRecord.recordDate=''
        this.helpRecord.recordDescription=''
        this.helpRecord.recordImage=''
    }
  }
});