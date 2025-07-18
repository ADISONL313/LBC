import { deleteRemove, get, post } from "@/axiosConfig"
import { ElMessage } from "element-plus"

export const getActivityRecord=async(activityId:any)=>{
    const res=await get('/activityRecord/getRecord',{activityId:activityId})
    return res.data
}
export const beginRecord=async(activityId:any)=>{
    const res=await post('/activityRecord/startRecord',{activityId:activityId})
    if(res.message==='成功'){
        return true
    }else{
        ElMessage.error("开始失败")
        return false
    }
}
export const deleteThisFile=(fileId:any,fileName:any)=>{
    deleteRemove(`/activityRecord/delFile?fileId=${fileId}&fileName=${fileName}`).then((res) => {
        ElMessage(res.message)
    })
}
export const sendSign=async(activityId:any)=>{
    const res=await post('/activitySignIn/sendSignIn',{activityId:activityId})
    if(res.message==="成功"){
        return true
    }else{
        ElMessage.error(res.message)
    }
}
export const getSignIn=async(activityId:any,mail:any)=>{
    const res=await get('/activitySignIn/getActivitySignIn',{activityId:activityId,mail:mail})
    return res.data
}
export const signInfor=async(signInId:any)=>{
    const res=await post('/activitySignIn/signFor',{signInId:signInId})
    if(res.message==='成功'){
        return true
    }else{
        ElMessage.error(res.message)
        return false
    }
}