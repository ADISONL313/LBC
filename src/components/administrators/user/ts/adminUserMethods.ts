import { post } from "@/axiosConfig"
import { getNowTime } from "@/components/universal/user/tsMethods/tsMethods"
import { ElMessage } from "element-plus"

export const passVoluntter = async (mail: any) => {
    const nowTime = getNowTime()
    const res = await post('/volunteer/passVolunteer', { mail: mail, volunteerTime: nowTime })
    if (res.message === '成功') {
        return true
    } else {
        ElMessage.error("通过失败")
        return false
    }
}
export const rejectionVoluntter = async (rejectReason: any,mail:any) => {
    if (rejectReason === '' || rejectReason === null) {
        ElMessage.error("请填写驳回原因")
        return false
    } else {
        const res = await post('/volunteer/rejectionVolunteer', { rejectReason: rejectReason,mail:mail })
        if (res.message === '成功') {
            return true
        } else {
            ElMessage.error("驳回失败")
            return false
        }
    }
}