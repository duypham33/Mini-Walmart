import { IInvoiceItem } from "./invoice-item.model"

export interface IInvoiceDetail{
    charges:{
        subtotal: number,
        shipping?: number
    },
    chargeTotal: number,
    estimatedDate: string,
    deducts: {
        shipping?: IInvoiceItem,
        discount?: IInvoiceItem,
        eCash?: IInvoiceItem
    },
    saving?: string,
    finalDueAmount: number
}