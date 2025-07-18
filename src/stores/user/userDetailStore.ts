// stores/countdown.ts
import { defineStore } from 'pinia'
export const useUserDetailStore = defineStore('userDetailStore',{
  state: () => ({
    userDetail:{
        nickName:'',
        userDetailId:'',
        mail:'',
        realName:'',
        phoneNumber:'',
        gender:'',
        idCard:'',
        contactAddress:'',
        idPhoto:'',
        introduction:'',
        donationPermission:0,
        reportPermission:0,
        rejectionReason:'',
        userRole:1 
    },
    userCenterState:'详细信息',
    showWitchPage:{
      userDetail:true,
      certificate:false,
      userDetailUpdate:false,
      becomevolunteer:false
    }
  }),
  actions: {
    clear(){
        this.userDetail.userDetailId=''
        this.userDetail.mail=''
        this.userDetail.realName=''
        this.userDetail.phoneNumber=''
        this.userDetail.gender=''
        this.userDetail.idCard=''
        this.userDetail.contactAddress=''
        this.userDetail.idPhoto=''
        this.userDetail.introduction=''
        this.userDetail.donationPermission=0
        this.userDetail.donationPermission=0
        this.userDetail.rejectionReason=''
        this.userCenterState=''
        this.showWitchPage.userDetail=true,
        this.showWitchPage.certificate=false
        this.showWitchPage.userDetailUpdate=false
        this.showWitchPage.becomevolunteer=false
    },
    clearUserDetail(){
      this.userDetail.userDetailId=''
      this.userDetail.mail=''
      this.userDetail.realName=''
      this.userDetail.phoneNumber=''
      this.userDetail.gender=''
      this.userDetail.idCard=''
      this.userDetail.contactAddress=''
      this.userDetail.idPhoto=''
      this.userDetail.introduction=''
      this.userDetail.donationPermission=0
      this.userDetail.donationPermission=0
      this.userDetail.rejectionReason=''
      this.userDetail.userRole=1
    }
  },
  persist: {
    key: 'userDetailStore', //存储名称
    storage: sessionStorage, // 存储方式
    paths: ['userDetail'],//指定 state 中哪些数据需要被持久化。[] 表示不持久化任何状态，undefined 或 null 表示持久化整个 state
}
});