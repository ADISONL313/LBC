import { deleteRemove, get, post } from "@/axiosConfig"
import { getNowTime } from "@/components/universal/user/tsMethods/tsMethods"
import { ElMessage } from "element-plus"
export const locationAddress = async () => {
    // const res = await fetch('https://ifconfig.me/ip')
    // const ip=await res.text()
    const location = await fetch(`https://restapi.amap.com/v3/ip?output=json&key=290a01347d23fa36957fcb8b0c9ceca3`)
    console.log(location)
    const jw = (await location.json()).rectangle.split(';')[1]
    const ad = await fetch(`https://restapi.amap.com/v3/geocode/regeo?output=json&location=${jw}&key=290a01347d23fa36957fcb8b0c9ceca3&radius=1000&extensions=all `)
    console.log(ad)
    return ad
}
export const addHelp = async (help: any) => {
    //|| help.address === '' || help.address === null
    if (help.helpTitle === '' || help.helpTitle === null || help.helpContent === '' || help.helpContent === null || help.phone === '' || help.phone === null || help.emergencyPhone === '' || help.emergencyPhone === null) {
        ElMessage.error("请完善表单信息")
        return
    }
    help.mail = sessionStorage.getItem('mail')
    help.releaseTime = getNowTime()
    const res = await post('/help/addHelp', help)
    if (res.message === '成功') {
        return true
    } else {
        ElMessage.error('求助失败')
        return false
    }
}
export const getMyHelp = async (help: any) => {
    const res = await post('help/getHelp', help)
    console.log(res.data)
    return res.data
}
export const cancelHelpTs = async (helpId: string, helpStatus: number) => {
    if (helpStatus === 1) {
        ElMessage.error("工作人员已前往救助");
        return false
    } else {
        const res = await deleteRemove(`/help/cancelHelp?helpId=${helpId}`)
        if (res.message === "成功") {
            return true
        } else {
            ElMessage.error(res.message)
            return false
        }
    }
}
export const updateHelpData = async (help: any) => {
    if (help.helpStatus === 1 || help.helpStatus === 1) {
        ElMessage.error("求助已被接取，无法修改")
        return false
    } else if (help.helpTitle === '' || help.helpTitle === null || help.helpContent === '' || help.helpContent === null || help.phone === '' || help.phone === null || help.emergencyPhone === '' || help.emergencyPhone === null) {
        ElMessage.error("请完善表单信息")
        return false
    } else {
        const res = await post('/help/updateHelp', help)
        if (res.message === '成功') {
            ElMessage.success("修改成功")
            return true
        } else {
            ElMessage.success("修改失败")
            return false
        }
    }
}
export const getVolunteerNowTask = async () => {
    const res = await get('/volunteerHelp/getVolunteerNowTask', { mail: sessionStorage.getItem('mail') })
    return res.data
}
export const getHelpRecordList = async (mail: any, helpId: any) => {
    const res = await get("/helpRecord/getHelpRecord", { mail: mail, helpId: helpId })
    return res.data
}
export const finishThisHelp = (volunteerHelpId: any) => {
    post('/volunteerHelp/volunteerFinishHelp', { volunteerHelpId: volunteerHelpId }).then((res) => {
        ElMessage(res.message)
    })
}
export const getHelpHistory = async () => {
    const res = await get('/volunteerHelp/getHelpHistory', { mail: sessionStorage.getItem('mail') })
    return res.data
}