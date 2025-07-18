import { deleteRemove, get, post } from "@/axiosConfig"
import { ElMessage } from "element-plus"

export const idleVolunteer = async () => {
    const res = await get('/volunteer/getIdleVolunteer')
    console.log(await res.data)
    return res.data
}
export const assign = async(item:any, helpId: any) => {
    const res=await post('/volunteerHelp/assignVolunteer', {volunteerId:item.volunteerId,mail:item.mail,helpId:helpId})
    if(res.message==='成功'){
        return true
    }else{
        ElMessage.error("指派失败")
        return false
    }
}
export const getAssign = async (helpId: any) => {
    const res = await get('/volunteerHelp/getAssignVolunteer', { helpId: helpId })
    return res.data
}
export const cancelAssign=async(volunteerId:any,mail:any,helpId:any)=>{
    const res=await deleteRemove(`/volunteerHelp/cancelAssignVolunteer?volunteerHelpId=${volunteerId}&mail=${mail}&helpId=${helpId}`)
    if(res.message==='成功'){
        return true
    }else{
        ElMessage.error("取消失败")
        return false
    }
}
export const finishHelp=(helpId:any)=>{
    post('/help/finishHelp',{helpId:helpId}).then((res)=>{
        ElMessage(res.message)
    })
}