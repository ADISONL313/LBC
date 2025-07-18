// stores/countdown.ts
import { defineStore } from 'pinia'
 
export const useDonationStateStore = defineStore('donationStateStore',{
  state: () => ({
    donationState:{
         stateId:'',
         donationId:'',
         logisticsState:0,
         logisticsTime:'',
         logisticsImg:'',
         logisticsText:'',
         transportState:0,
         transportTime:'',
         transportAddress:'',
         transportText:'',
         transportImg:'',
         arrivalState:0,
         arrivalTime:'',
         arrivalText:'',
         arrivalImg:'',
         distributeState:0,
         distributeTime:'',
         distributeText:'',
         distributeImg:'',
    }
  }),
  actions: {
    clear(){
        this.donationState.stateId=''
        this.donationState.donationId=''
        this.donationState.logisticsState=0
        this.donationState.logisticsTime=''
        this.donationState.logisticsImg=''
        this.donationState.logisticsText=''
        this.donationState.transportState=0
        this.donationState.transportTime=''
        this.donationState.transportAddress=''
        this.donationState.transportText=''
        this.donationState.transportImg=''
        this.donationState.arrivalState=0
        this.donationState.arrivalTime=''
        this.donationState.arrivalText=''
        this.donationState.arrivalImg=''
        this.donationState.distributeState=0
        this.donationState.distributeTime=''
        this.donationState.distributeText=''
        this.donationState.distributeImg=''
    }
  }
});