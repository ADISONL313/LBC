// stores/countdown.ts
import { defineStore } from 'pinia'
 
export const useActivityRecordStore = defineStore('activityRecordStore',{
  state: () => ({
    activityRecord:{
        activityRecordId:'',
        recordTime:'',
        recordContent:'',
        recordImage:'',
        activityId:''
    }
  }),
  actions: {
    clear(){
        this.activityRecord.activityRecordId
        this.activityRecord.recordTime
        this.activityRecord.recordContent
        this.activityRecord.recordImage
        this.activityRecord.activityId
    }
  }
});