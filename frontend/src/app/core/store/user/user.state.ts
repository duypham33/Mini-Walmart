import { Auth } from "../../models/user/auth.model"
import { ICartItem } from "../../models/user/cart-item.model"
import { User } from "../../models/user/user.model"

export type UserState = {
    user?: User,
    auth: Auth,
    cart: ICartItem[],
    watchList: number[]
}

export const initUserState: UserState = {
    auth: {}, cart: [], watchList: []
}