import { IInvoiceItem } from "./invoice-item.model"

export interface ICheckout{
    shipping: {
        estimatedDate: string,
        fee: number
    },
    finalDueAmount: number,
    saving?: string,
    invoiceItems: IInvoiceItem[]
}

// export const dummyCheckout: ICheckout = {
//     "shipping": {
//         "estimatedDate": "25/11/2025",
//         "fee": 9.0
//     },
//     "finalDueAmount": 59.92,
//     "saving": "You will save $12.0 with your PREMIUM subscription",
//     "invoiceItems": [
//         {
//             "detail": "2  x ($21.55) Marketside Skin-On Atlantic Salmon Fillet, 2.0-3.0 lb",
//             "subtotal": 43.1
//         },
//         {
//             "detail": "3  x ($5.47) Great Value Frozen Cooked Medium Peeled & Deveined Tail-on Shrimp, 12 oz Bag (41-60 Count per lb)",
//             "subtotal": 16.41
//         },
//         {
//             "detail": "3  x ($1.97) Marketside Fresh Shredded Iceberg Lettuce, 8 oz Bag, Fresh",
//             "subtotal": 5.91
//         },
//         {
//             "detail": "Shipping: $9.00",
//             "subtotal": 9.0
//         },
//         {
//             "detail": "Free Shipping: -$9.00",
//             "subtotal": -9.0
//         },
//         {
//             "detail": "5% discount: -$3.00",
//             "subtotal": -3.0
//         },
//         {
//             "detail": "E Cash: -$2.50",
//             "subtotal": -2.5
//         }
//     ]
// }

// export const dummyCheckout2: ICheckout = {
//     "shipping": {
//         "estimatedDate": "25/11/2025",
//         "fee": 9.0
//     },
//     "finalDueAmount": 59.92,
//     "saving": "You will save $12.0 with your PREMIUM subscription",
//     "invoiceItems": [
//         {
//             "detail": "2  x ($21.55) Marketside Skin-On Atlantic Salmon Fillet, 2.0-3.0 lb",
//             "subtotal": 43.1
//         },
//         {
//             "detail": "3  x ($5.47) Great Value Frozen Cooked Medium Peeled & Deveined Tail-on Shrimp, 12 oz Bag (41-60 Count per lb)",
//             "subtotal": 16.41
//         },
//         {
//             "detail": "3  x ($1.97) Marketside Fresh Shredded Iceberg Lettuce, 8 oz Bag, Fresh",
//             "subtotal": 5.91
//         },
//         {
//             "detail": "Shipping: $9.00",
//             "subtotal": 9.0
//         },
//         {
//             "detail": "Free Shipping: -$9.00",
//             "subtotal": -9.0
//         },
//         {
//             "detail": "5% discount: -$3.00",
//             "subtotal": -3.0
//         }
//     ]
// }

// export const dummyCheckout3: ICheckout = {
//     "shipping": {
//         "estimatedDate": "25/11/2025",
//         "fee": 9.0
//     },
//     "finalDueAmount": 59.92,
//     "saving": "You will save $12.0 with your PREMIUM subscription",
//     "invoiceItems": [
//         {
//             "detail": "2  x ($21.55) Marketside Skin-On Atlantic Salmon Fillet, 2.0-3.0 lb",
//             "subtotal": 43.1
//         },
//         {
//             "detail": "3  x ($5.47) Great Value Frozen Cooked Medium Peeled & Deveined Tail-on Shrimp, 12 oz Bag (41-60 Count per lb)",
//             "subtotal": 16.41
//         },
//         {
//             "detail": "3  x ($1.97) Marketside Fresh Shredded Iceberg Lettuce, 8 oz Bag, Fresh",
//             "subtotal": 5.91
//         },
//         {
//             "detail": "Shipping: $9.00",
//             "subtotal": 9.0
//         },
//         {
//             "detail": "5% discount: -$3.00",
//             "subtotal": -3.0
//         },
//         {
//             "detail": "E Cash: -$2.50",
//             "subtotal": -2.5
//         }
//     ]
// }

// export const dummyCheckout4: ICheckout = {
//     "shipping": {
//         "estimatedDate": "25/11/2025",
//         "fee": 9.0
//     },
//     "finalDueAmount": 59.92,
//     "saving": "You will save $12.0 with your PREMIUM subscription",
//     "invoiceItems": [
//         {
//             "detail": "2  x ($21.55) Marketside Skin-On Atlantic Salmon Fillet, 2.0-3.0 lb",
//             "subtotal": 43.1
//         },
//         {
//             "detail": "3  x ($5.47) Great Value Frozen Cooked Medium Peeled & Deveined Tail-on Shrimp, 12 oz Bag (41-60 Count per lb)",
//             "subtotal": 16.41
//         },
//         {
//             "detail": "3  x ($1.97) Marketside Fresh Shredded Iceberg Lettuce, 8 oz Bag, Fresh",
//             "subtotal": 5.91
//         },
//         {
//             "detail": "Shipping: $9.00",
//             "subtotal": 9.0
//         },
//         {
//             "detail": "5% discount: -$3.00",
//             "subtotal": -3.0
//         }
//     ]
// }

// export const dummyCheckout5: ICheckout = {
//     "shipping": {
//         "estimatedDate": "25/11/2025",
//         "fee": 9.0
//     },
//     "finalDueAmount": 59.92,
//     "saving": "You will save $12.0 with your PREMIUM subscription",
//     "invoiceItems": [
//         {
//             "detail": "2  x ($21.55) Marketside Skin-On Atlantic Salmon Fillet, 2.0-3.0 lb",
//             "subtotal": 43.1
//         },
//         {
//             "detail": "3  x ($5.47) Great Value Frozen Cooked Medium Peeled & Deveined Tail-on Shrimp, 12 oz Bag (41-60 Count per lb)",
//             "subtotal": 16.41
//         },
//         {
//             "detail": "3  x ($1.97) Marketside Fresh Shredded Iceberg Lettuce, 8 oz Bag, Fresh",
//             "subtotal": 5.91
//         },
//         {
//             "detail": "Shipping: $9.00",
//             "subtotal": 9.0
//         },
//         {
//             "detail": "E Cash: -$2.50",
//             "subtotal": -2.5
//         }
//     ]
// }

// export const dummyCheckout6: ICheckout = {
//     "shipping": {
//         "estimatedDate": "25/11/2025",
//         "fee": 9.0
//     },
//     "finalDueAmount": 59.92,
//     "saving": "You will save $12.0 with your PREMIUM subscription",
//     "invoiceItems": [
//         {
//             "detail": "2  x ($21.55) Marketside Skin-On Atlantic Salmon Fillet, 2.0-3.0 lb",
//             "subtotal": 43.1
//         },
//         {
//             "detail": "3  x ($5.47) Great Value Frozen Cooked Medium Peeled & Deveined Tail-on Shrimp, 12 oz Bag (41-60 Count per lb)",
//             "subtotal": 16.41
//         },
//         {
//             "detail": "3  x ($1.97) Marketside Fresh Shredded Iceberg Lettuce, 8 oz Bag, Fresh",
//             "subtotal": 5.91
//         },
//         {
//             "detail": "Shipping: $9.00",
//             "subtotal": 9.0
//         }
//     ]
// }