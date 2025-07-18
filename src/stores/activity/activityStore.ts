// stores/countdown.ts
import { defineStore } from 'pinia'
 
export const useActivityStore = defineStore('activityStore',{
  state: () => ({
    activity:{
        activityId:'',
        activityName:'',
        activityStartTime:'',
        activityEndTime:'',
        activityContent:'',
        address:'',
        participantsNumber:0,
        activityImg:'',
        registrationRejectionReason:''
    }
  }),
  actions: {
    clear(){
        this.activity.activityName=''
        this.activity.activityContent=''
        this.activity.activityEndTime=''
        this.activity.activityImg=''
        this.activity.activityStartTime=''
        this.activity.address=''
        this.activity.participantsNumber=0
        this.activity.activityId=''
        this.activity.registrationRejectionReason=''
    }
  }
});