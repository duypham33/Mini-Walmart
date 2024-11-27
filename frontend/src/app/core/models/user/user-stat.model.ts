import { IProduct } from "../product/product.model"

export interface IUserStats{
    subscription: string,
    savings: string,
    ecash: string,
    completedOrders: string,
    cancelOrders: string,
    frequentProducts: {
        product: IProduct,
        metric?: string
    }[],
    recentProducts: {
        product: IProduct,
        metric?: string
    }[]
}

export interface IAdminStats{
    userTotal: string,
    subscriptions: string,
    orderTotal: string,
    subscriptionProfit: string,
    sellingProfit: string,
    categoryTotal: string,
    brandTotal: string,
    productTotal: string,
    soldProductTotal: string,
    profitProducts: {
        product: IProduct,
        metric?: string
    }[],
    popularProducts: {
        product: IProduct,
        metric?: string
    }[]
}

export interface IProductPrize {
    position: number,
    value: string
}

// export const dummyUserStat: IUserStats = {
//     subscription: "PREMIUM",
//     savings: "$80.50",
//     ecash: "$2.50",
//     completedOrders: "10 orders",
//     cancelOrders: "3 orders",
//     frequentProducts: [
//         {
//             product: dummyProducts[0],
//             metric: "4 orders"
//         },
//         {
//             product: dummyProducts[1],
//             metric: "3 orders"
//         },
//         {
//             product: dummyProducts[2],
//             metric: "3 orders"
//         }
//     ],
//     recentProducts: [
//         {
//             product: dummyProducts[0],
//             metric: "0 days ago"
//         },
//         {
//             product: dummyProducts[1],
//             metric: "1 days ago"
//         },
//         {
//             product: dummyProducts[2],
//             metric: "4 days ago"
//         }
//     ]
// }

// export const dummyAdminStats: IAdminStats = {
//     userTotal: "10",
//     subscriptions: "1 PREMIUM and 0 BASIC",
//     orderTotal: "10 COMPLETED and 5 CANCELED",
//     subscriptionProfit: "$200",
//     sellingProfit: "$1500",
//     categoryTotal: "295 categories",
//     brandTotal: "254 brands",
//     productTotal: "885 in stock and 5 out stock",
//     soldProductTotal: "24 sold products, rate 3%",
//     profitProducts: [
//         {
//             product: dummyProducts[0],
//             metric: "$500"
//         },
//         {
//             product: dummyProducts[1],
//             metric: "$300"
//         },
//         {
//             product: dummyProducts[2],
//             metric: "$200"
//         }
//     ],
//     popularProducts: [
//         {
//             product: dummyProducts[0],
//             metric: "20 quantity"
//         },
//         {
//             product: dummyProducts[1],
//             metric: "12 quantity"
//         },
//         {
//             product: dummyProducts[2],
//             metric: "10 quantity"
//         }
//     ]
// }