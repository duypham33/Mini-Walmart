import { IInvoiceItem } from "./invoice-item.model"
import { IOrderItem } from "./order-item.model"

export interface IOrder{
    id: number,
    orderNumber: string,
    orderingUser?: {
        id: number,
        username: string
    },
    placedDate: string,
    total: number,
    status: string,
    items: IOrderItem[]
}

export interface IOrderDetail{
    id: number,
    orderNumber: string,
    orderingUser?: {
        id: number,
        username: string
    },
    placedDate: string,
    total: number,
    status: string,
    savings?: string,
    shipping: {
        id: number,
        estimatedDate: string,
        status: string,
        trackCode: string
    },
    invoice: IInvoiceItem[],
    items: IOrderItem[],
    recordedLog?: string[]
}

// export const dummyOrder: IOrderDetail = {
//     id: 1,
//     orderNumber: "32d187b4-a9e8-11ef-ac56-bdaf34f83f51",
//     orderingUser: {
//         id: 3,
//         username: "larrypham"
//     },
//     placedDate: "25/11/2024",
//     total: 62.42,
//     status: "PR",
//     savings: "You saved $12.0 with your PREMIUM subscription",
//     "shipping": {
//             "id": 1,
//             "estimatedDate": "24/11/2024",
//             "status": "DELIVERED",
//             "trackCode": "32cfb36c-a9e8-11ef-ac56-bdaf34f83f51"
//     },
//     items: [
//         {
//             "orderItemId": 1,
//             "productId": 1,
//             "productName": "Marketside Skin-On Atlantic Salmon Fillet, 2.0-3.0 lb",
//             productImgPath: "https://i5.walmartimages.com/seo/Fresh-Blueberries-11-oz-or-1-Pint-Container_58566f1b-31b1-4e76-b0c2-39403a93c0c7.c8b5bd91935856cbdb15b7587d8c031e.jpeg?odnHeight=784&odnWidth=580&odnBg=FFFFFF",
//             "purchasedPrice": 21.55,
//             "quantity": 2
//         },
//         {
//             "orderItemId": 2,
//             "productId": 1,
//             "productName": "Great Value Frozen Cooked Medium Peeled & Deveined Tail-on Shrimp, 12 oz Bag (41-60 Count per lb)",
//             productImgPath: "https://i5.walmartimages.com/seo/Great-Value-Frozen-Cooked-Medium-Peeled-Deveined-Tail-on-Shrimp-12-oz-Bag-41-60-Count-per-lb_14c58556-515f-4420-b1a6-517437f3db08.4c5376359ce156a1f6c952b8972dfebb.jpeg?odnHeight=784&odnWidth=580&odnBg=FFFFFF",
//             "purchasedPrice": 5.47,
//             "quantity": 3
//         },
//         {
//             "orderItemId": 3,
//             "productId": 1,
//             "productName": "Marketside Fresh Shredded Iceberg Lettuce, 8 oz Bag, Fresh",
//             "purchasedPrice": 1.97,
//             "quantity": 3
//         }
//     ],
//     "invoice": [
//             {
//                 "detail": "2  x ($21.55) Marketside Skin-On Atlantic Salmon Fillet, 2.0-3.0 lb",
//                 "subtotal": 43.1
//             },
//             {
//                 "detail": "3  x ($5.47) Great Value Frozen Cooked Medium Peeled & Deveined Tail-on Shrimp, 12 oz Bag (41-60 Count per lb)",
//                 "subtotal": 16.41
//             },
//             {
//                 "detail": "3  x ($1.97) Marketside Fresh Shredded Iceberg Lettuce, 8 oz Bag, Fresh",
//                 "subtotal": 5.91
//             },
//             {
//                 "detail": "Shipping: $9.00",
//                 "subtotal": 9.0
//             },
//             {
//                 "detail": "Free Shipping: -$9.00",
//                 "subtotal": -9.0
//             },
//             {
//                 "detail": "5% discount: -$3.00",
//                 "subtotal": -3.0
//             }
//         ],
//         "recordedLog": [
//             "Applied FREE shipping of $9.00 for PREMIUM subscription",
//             "Applied 5% discount (= $3.00) for PREMIUM subscription",
//             "Saved $12.0 with your PREMIUM subscription",
//             "Order completed"
//         ]
// }

// export const dummyOrders: IOrder[] = [
//     {
//         id: 1,
//         orderNumber: "32d187b4-a9e8-11ef-ac56-bdaf34f83f51",
//         orderingUser: {
//             id: 3,
//             username: "larrypham"
//         },
//         placedDate: "25/11/2024",
//         total: 62.42,
//         status: "COMPLETED",
//         items: [
//             {
//                 "orderItemId": 1,
//                 "productId": 1,
//                 "productName": "Marketside Skin-On Atlantic Salmon Fillet, 2.0-3.0 lb",
//                 productImgPath: "https://i5.walmartimages.com/seo/Fresh-Blueberries-11-oz-or-1-Pint-Container_58566f1b-31b1-4e76-b0c2-39403a93c0c7.c8b5bd91935856cbdb15b7587d8c031e.jpeg?odnHeight=784&odnWidth=580&odnBg=FFFFFF",
//                 "purchasedPrice": 21.55,
//                 "quantity": 2
//             },
//             {
//                 "orderItemId": 2,
//                 "productId": 1,
//                 "productName": "Great Value Frozen Cooked Medium Peeled & Deveined Tail-on Shrimp, 12 oz Bag (41-60 Count per lb)",
//                 productImgPath: "https://i5.walmartimages.com/seo/Great-Value-Frozen-Cooked-Medium-Peeled-Deveined-Tail-on-Shrimp-12-oz-Bag-41-60-Count-per-lb_14c58556-515f-4420-b1a6-517437f3db08.4c5376359ce156a1f6c952b8972dfebb.jpeg?odnHeight=784&odnWidth=580&odnBg=FFFFFF",
//                 "purchasedPrice": 5.47,
//                 "quantity": 3
//             },
//             {
//                 "orderItemId": 3,
//                 "productId": 1,
//                 "productName": "Marketside Fresh Shredded Iceberg Lettuce, 8 oz Bag, Fresh",
//                 "purchasedPrice": 1.97,
//                 "quantity": 3
//             }
//         ]
//     },
//     {
//         id: 1,
//         orderNumber: "32d187b4-a9e8-11ef-ac56-bdaf34f83f51",
//         // orderingUser: {
//         //     id: 3,
//         //     username: "larrypham"
//         // },
//         placedDate: "25/11/2024",
//         total: 62.42,
//         status: "CANCEL",
//         items: [
//             {
//                 "orderItemId": 1,
//                 "productId": 1,
//                 "productName": "Marketside Skin-On Atlantic Salmon Fillet, 2.0-3.0 lb",
//                 productImgPath: "https://i5.walmartimages.com/seo/Fresh-Blueberries-11-oz-or-1-Pint-Container_58566f1b-31b1-4e76-b0c2-39403a93c0c7.c8b5bd91935856cbdb15b7587d8c031e.jpeg?odnHeight=784&odnWidth=580&odnBg=FFFFFF",
//                 "purchasedPrice": 21.55,
//                 "quantity": 2
//             },
//             {
//                 "orderItemId": 2,
//                 "productId": 1,
//                 "productName": "Great Value Frozen Cooked Medium Peeled & Deveined Tail-on Shrimp, 12 oz Bag (41-60 Count per lb)",
//                 productImgPath: "https://i5.walmartimages.com/seo/Great-Value-Frozen-Cooked-Medium-Peeled-Deveined-Tail-on-Shrimp-12-oz-Bag-41-60-Count-per-lb_14c58556-515f-4420-b1a6-517437f3db08.4c5376359ce156a1f6c952b8972dfebb.jpeg?odnHeight=784&odnWidth=580&odnBg=FFFFFF",
//                 "purchasedPrice": 5.47,
//                 "quantity": 3
//             },
//             {
//                 "orderItemId": 3,
//                 "productId": 1,
//                 "productName": "Marketside Fresh Shredded Iceberg Lettuce, 8 oz Bag, Fresh",
//                 "purchasedPrice": 1.97,
//                 "quantity": 3
//             }
//         ]
//     },
//     {
//         id: 1,
//         orderNumber: "32d187b4-a9e8-11ef-ac56-bdaf34f83f51",
//         orderingUser: {
//             id: 3,
//             username: "larrypham"
//         },
//         placedDate: "25/11/2024",
//         total: 62.42,
//         status: "PREPARING",
//         items: [
//             {
//                 "orderItemId": 1,
//                 "productId": 1,
//                 "productName": "Marketside Skin-On Atlantic Salmon Fillet, 2.0-3.0 lb",
//                 productImgPath: "https://i5.walmartimages.com/seo/Fresh-Blueberries-11-oz-or-1-Pint-Container_58566f1b-31b1-4e76-b0c2-39403a93c0c7.c8b5bd91935856cbdb15b7587d8c031e.jpeg?odnHeight=784&odnWidth=580&odnBg=FFFFFF",
//                 "purchasedPrice": 21.55,
//                 "quantity": 2
//             },
//             {
//                 "orderItemId": 2,
//                 "productId": 1,
//                 "productName": "Great Value Frozen Cooked Medium Peeled & Deveined Tail-on Shrimp, 12 oz Bag (41-60 Count per lb)",
//                 productImgPath: "https://i5.walmartimages.com/seo/Great-Value-Frozen-Cooked-Medium-Peeled-Deveined-Tail-on-Shrimp-12-oz-Bag-41-60-Count-per-lb_14c58556-515f-4420-b1a6-517437f3db08.4c5376359ce156a1f6c952b8972dfebb.jpeg?odnHeight=784&odnWidth=580&odnBg=FFFFFF",
//                 "purchasedPrice": 5.47,
//                 "quantity": 3
//             },
//             {
//                 "orderItemId": 3,
//                 "productId": 1,
//                 "productName": "Marketside Fresh Shredded Iceberg Lettuce, 8 oz Bag, Fresh",
//                 "purchasedPrice": 1.97,
//                 "quantity": 3
//             },
//             {
//                 "orderItemId": 1,
//                 "productId": 1,
//                 "productName": "Marketside Skin-On Atlantic Salmon Fillet, 2.0-3.0 lb",
//                 productImgPath: "https://i5.walmartimages.com/seo/Fresh-Blueberries-11-oz-or-1-Pint-Container_58566f1b-31b1-4e76-b0c2-39403a93c0c7.c8b5bd91935856cbdb15b7587d8c031e.jpeg?odnHeight=784&odnWidth=580&odnBg=FFFFFF",
//                 "purchasedPrice": 21.55,
//                 "quantity": 2
//             },
//             {
//                 "orderItemId": 2,
//                 "productId": 1,
//                 "productName": "Great Value Frozen Cooked Medium Peeled & Deveined Tail-on Shrimp, 12 oz Bag (41-60 Count per lb)",
//                 productImgPath: "https://i5.walmartimages.com/seo/Great-Value-Frozen-Cooked-Medium-Peeled-Deveined-Tail-on-Shrimp-12-oz-Bag-41-60-Count-per-lb_14c58556-515f-4420-b1a6-517437f3db08.4c5376359ce156a1f6c952b8972dfebb.jpeg?odnHeight=784&odnWidth=580&odnBg=FFFFFF",
//                 "purchasedPrice": 5.47,
//                 "quantity": 3
//             },
//             {
//                 "orderItemId": 3,
//                 "productId": 1,
//                 "productName": "Marketside Fresh Shredded Iceberg Lettuce, 8 oz Bag, Fresh",
//                 "purchasedPrice": 1.97,
//                 "quantity": 3
//             },
//             {
//                 "orderItemId": 1,
//                 "productId": 1,
//                 "productName": "Marketside Skin-On Atlantic Salmon Fillet, 2.0-3.0 lb",
//                 productImgPath: "https://i5.walmartimages.com/seo/Fresh-Blueberries-11-oz-or-1-Pint-Container_58566f1b-31b1-4e76-b0c2-39403a93c0c7.c8b5bd91935856cbdb15b7587d8c031e.jpeg?odnHeight=784&odnWidth=580&odnBg=FFFFFF",
//                 "purchasedPrice": 21.55,
//                 "quantity": 2
//             },
//             {
//                 "orderItemId": 2,
//                 "productId": 1,
//                 "productName": "Great Value Frozen Cooked Medium Peeled & Deveined Tail-on Shrimp, 12 oz Bag (41-60 Count per lb)",
//                 productImgPath: "https://i5.walmartimages.com/seo/Great-Value-Frozen-Cooked-Medium-Peeled-Deveined-Tail-on-Shrimp-12-oz-Bag-41-60-Count-per-lb_14c58556-515f-4420-b1a6-517437f3db08.4c5376359ce156a1f6c952b8972dfebb.jpeg?odnHeight=784&odnWidth=580&odnBg=FFFFFF",
//                 "purchasedPrice": 5.47,
//                 "quantity": 3
//             },
//             {
//                 "orderItemId": 3,
//                 "productId": 1,
//                 "productName": "Marketside Fresh Shredded Iceberg Lettuce, 8 oz Bag, Fresh",
//                 "purchasedPrice": 1.97,
//                 "quantity": 3
//             },
//             {
//                 "orderItemId": 1,
//                 "productId": 1,
//                 "productName": "Marketside Skin-On Atlantic Salmon Fillet, 2.0-3.0 lb",
//                 productImgPath: "https://i5.walmartimages.com/seo/Fresh-Blueberries-11-oz-or-1-Pint-Container_58566f1b-31b1-4e76-b0c2-39403a93c0c7.c8b5bd91935856cbdb15b7587d8c031e.jpeg?odnHeight=784&odnWidth=580&odnBg=FFFFFF",
//                 "purchasedPrice": 21.55,
//                 "quantity": 2
//             },
//             {
//                 "orderItemId": 2,
//                 "productId": 1,
//                 "productName": "Great Value Frozen Cooked Medium Peeled & Deveined Tail-on Shrimp, 12 oz Bag (41-60 Count per lb)",
//                 productImgPath: "https://i5.walmartimages.com/seo/Great-Value-Frozen-Cooked-Medium-Peeled-Deveined-Tail-on-Shrimp-12-oz-Bag-41-60-Count-per-lb_14c58556-515f-4420-b1a6-517437f3db08.4c5376359ce156a1f6c952b8972dfebb.jpeg?odnHeight=784&odnWidth=580&odnBg=FFFFFF",
//                 "purchasedPrice": 5.47,
//                 "quantity": 3
//             },
//             {
//                 "orderItemId": 3,
//                 "productId": 1,
//                 "productName": "Marketside Fresh Shredded Iceberg Lettuce, 8 oz Bag, Fresh",
//                 "purchasedPrice": 1.97,
//                 "quantity": 3
//             },
//             {
//                 "orderItemId": 1,
//                 "productId": 1,
//                 "productName": "Marketside Skin-On Atlantic Salmon Fillet, 2.0-3.0 lb",
//                 productImgPath: "https://i5.walmartimages.com/seo/Fresh-Blueberries-11-oz-or-1-Pint-Container_58566f1b-31b1-4e76-b0c2-39403a93c0c7.c8b5bd91935856cbdb15b7587d8c031e.jpeg?odnHeight=784&odnWidth=580&odnBg=FFFFFF",
//                 "purchasedPrice": 21.55,
//                 "quantity": 2
//             },
//             {
//                 "orderItemId": 2,
//                 "productId": 1,
//                 "productName": "Great Value Frozen Cooked Medium Peeled & Deveined Tail-on Shrimp, 12 oz Bag (41-60 Count per lb)",
//                 productImgPath: "https://i5.walmartimages.com/seo/Great-Value-Frozen-Cooked-Medium-Peeled-Deveined-Tail-on-Shrimp-12-oz-Bag-41-60-Count-per-lb_14c58556-515f-4420-b1a6-517437f3db08.4c5376359ce156a1f6c952b8972dfebb.jpeg?odnHeight=784&odnWidth=580&odnBg=FFFFFF",
//                 "purchasedPrice": 5.47,
//                 "quantity": 3
//             },
//             {
//                 "orderItemId": 3,
//                 "productId": 1,
//                 "productName": "Marketside Fresh Shredded Iceberg Lettuce, 8 oz Bag, Fresh",
//                 "purchasedPrice": 1.97,
//                 "quantity": 3
//             }
//         ]
//     }
// ];