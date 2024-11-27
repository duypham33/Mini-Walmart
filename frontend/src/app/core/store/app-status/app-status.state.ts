import { APP_STATUS } from "../../constants/app-status.constants"

export type AppStatusState = {
    status: APP_STATUS,
    info?: string,
    error?: {
        message: string,
        code?: number,
        status?: string
    }
}

export const initAppStatusState: AppStatusState = {
    status: APP_STATUS.OK
}