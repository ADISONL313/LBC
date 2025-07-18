import { deleteRemove } from "@/axiosConfig"
import { ElMessage } from "element-plus"

export const delStateImg=async(stateId:string,fileName:string,whatImg:string)=>{
    const res:any=await deleteRemove(`/donationState/delStateImg?stateId=${stateId}&fileName=${fileName}&whatImg=${whatImg}`)
    return res
}

export const verifyTransportStateData=(transportState:any)=>{
    if(transportState.transportText===''||transportState.transportText===null||transportState.transportAddress===''||transportState.transportAddress===null||transportState.transportTime===''||transportState.transportTime===null){
        ElMessage.error("请完善表单信息")
        return false
    }else{
        return true
    }
}

export const verifyarrivalStateData=(arrivalState:any)=>{
    if(arrivalState.arrivalTime===''||arrivalState.arrivalTime===null||arrivalState.arrivalText===''||arrivalState.arrivalText===null){
        ElMessage.error("请完成表单信息")
        return false
    }
}

export const verifyDistributeData=(distributeState:any)=>{
    console.log(distributeState.distributeTime)
    if(distributeState.distributeTime===''||distributeState.distributeTime===null||distributeState.distributeText===''||distributeState.distributeText===null){
        console.log(distributeState)
        ElMessage.error("请完成表单信息")
        return false
    }else{
        return true
    }
}