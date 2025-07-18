// stores/countdown.ts
import { defineStore } from 'pinia'
 
export const useDonationStore = defineStore('donationStore',{
  state: () => ({
    donation:{
        donationId:'',
        mail:'',
        itemName:'',
        itemType:'',
        expressId:'',
        itemImg:'',
        rejectionReason:'',
        auditStatus:0,
        address:''
    }
  }),
  actions: {
    clear(){
        this.donation.expressId=''
        this.donation.auditStatus=0
        this.donation.donationId=''
        this.donation.itemImg=''
        this.donation.itemName=''
        this.donation.mail=''
        this.donation.rejectionReason=''
        this.donation.itemType=''
        this.donation.address=''
    }
  }
});