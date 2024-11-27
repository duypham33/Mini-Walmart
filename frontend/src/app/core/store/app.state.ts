import { AppStatusState } from "./app-status/app-status.state"
import { UserState } from "./user/user.state"

export type AppState = {
    appStatus: AppStatusState,
    user: UserState
}