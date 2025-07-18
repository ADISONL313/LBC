import { get } from "@/axiosConfig"
import { ElMessage } from "element-plus"
export const loginFormVerify = (value1: string, value2: string) => {
    if (value1 === '' || value2 === '') {
        ElMessage('完善表单信息')
        return false
    }
}
export const forgetPwdFormVerify = (value1: string, value2: string, value3: string) => {
    if (value1 === '' || value2 === '' || value3 === '') {
        ElMessage('完善表单信息')
        return false
    }
}
export const verifyNewData = (news: any) => {
    if (news.newId === '' || news.newTitle === '' || news.newContent === '' || news.reporter === '' || news.newTime === '' || news.address === '') {
        ElMessage('完善表单信息')
        return false
    }
}
export const userDetailVerify = (thisUserDetail: any) => {
    if (thisUserDetail.realName === '' || thisUserDetail.gender === '' || thisUserDetail.phoneNumber === '' || thisUserDetail.contactAddress === '' || thisUserDetail.idCard === '') {
        ElMessage("完善表单信息")
        return false
    }
}
export const verifyActivityData = (activity: any) => {
    if (activity.activityName === '' || activity.activityStartTime === '' || activity.activityEndTime === '' || activity.activityContent === '' || activity.address === '' || activity.participantsNumber === 0 || activity.participantsNumber === '' || activity.participantsNumber === null) {
        ElMessage("完善表单信息")
        return false
    }
}
export const getNowTime = () => {
    const currentDate: Date = new Date();

    // 获取年份
    const year: string = currentDate.getFullYear() + '-';

    // 获取月份（注意：月份从 0 开始计数，所以要加 1）
    const month: string = currentDate.getMonth() + 1 + '-';

    // 获取日期
    const day: string = currentDate.getDate() + ' ';

    // 获取小时
    const hours: string = currentDate.getHours() + ':';

    // 获取分钟
    const minutes: string = currentDate.getMinutes() + ':';

    // 获取秒数
    const seconds: string = currentDate.getSeconds() + '';
    const nowTime = year + month + day + hours + minutes + seconds;
    return nowTime;
}
export const getYMD=()=>{
    const currentDate: Date = new Date();

    // 获取年份
    const year: string = currentDate.getFullYear() + '-';

    // 获取月份（注意：月份从 0 开始计数，所以要加 1）
    const month: string = currentDate.getMonth() + 1 + '-';

    // 获取日期
    const day: string = currentDate.getDate() + ' ';
    
    const nowTime = year + month + day;
    return nowTime
}
export const verifyDonationData = (donation: any) => {
    if (donation.itemName === '' || donation.itemName === null || donation.itemType === '' || donation.itemType === null) {
        ElMessage.error("请完善表单信息")
        return false
    }else{
        return true
    }
}
export const logisticsStateVerify = (logisticsState: any) => {
    if (logisticsState.logisticsText === null || logisticsState.logisticsText === '' || logisticsState.logisticsTime === null || logisticsState.logisticsTime === '') {
        ElMessage.error("请完善表单信息")
        return false
    }
}

export interface DonationStateType  {
    stateId: string,
    donationId: string,
    logisticsState: number,
    logisticsTime: string,
    logisticsImg: string,
    logisticsText: string,
    transportState: number,
    transportTime: string,
    transportAddress: string,
    transportText: string,
    transportImg: string,
    arrivalState: number,
    arrivalTime: string,
    arrivalText: string,
    arrivalImg: string,
    distributeState: number,
    distributeTime: string,
    distributeText: string,
    distributeImg: string,
}

export const getDonationState=async(donationId:string)=>{
    const res:any=await get('/donationState/getDonationState',{donationId:donationId})
    return res
}
export const verifyActivityRecord=(activityRecord:any)=>{
    if(activityRecord.recordContent===''||activityRecord.recordTime===''||activityRecord.recordContent===null||activityRecord.recordTime===null){
        ElMessage.error("请完善表单信息")
        return false
    }else{
        return true
    }
}
export const verifyHelpRecordData=(helpRecord:any)=>{
    if(helpRecord.recordDescription===''||helpRecord.recordDescription===null){
        ElMessage.error("请完善表单信息")
        return false
    }else{
        return true
    }
}