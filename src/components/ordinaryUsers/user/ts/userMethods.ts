import { get, post } from "@/axiosConfig"
import { ElMessage } from "element-plus"

export const volunteerRepply = (value1: string, value2: string) => {
    if (value1 !== value2) {
        ElMessage.error("内容不一致")
        return
    }
    console.log(111)
    post('/volunteer/replyFor',{mail:sessionStorage.getItem('mail')}).then((res)=>{
        ElMessage(res.message)
    })
}
export const getVolunteer=async(mail:string)=>{
    const res=await get('/volunteer/getVolunteer',{mail});
    return res.data
}
export const donationStatistics=async()=>{
    const res=await get('/donation/donationStatistics',{mail:sessionStorage.getItem('mail')})
    return res.data
}