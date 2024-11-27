import { ICheckout } from "../models/order/checkout.model";
import { IInvoiceDetail } from "../models/order/invoice-detail.model";
import { IInvoiceItem } from "../models/order/invoice-item.model";
import { IOrderItem } from "../models/order/order-item.model";
import { IOrderDetail } from "../models/order/order.model";
import { ICartItem } from "../models/user/cart-item.model"
import { IShoppingItem } from "../models/user/shopping-item.model";

export const getTotalItems = (items: ICartItem[]): number => {
    if(items.length === 0) return 0;
    return items.map(i => i.quantity).reduce((l, r) => l + r);
}

export const getTotalItemsCost = (items: ICartItem[]): number => {
    if(items.length === 0) return 0.0;
    return items.map(i => i.quantity * i.product.retailPrice).reduce((l, r) => l + r);
}

export const getTotalItemsForOrder = (items: IOrderItem[]): number => {
    if(items.length === 0) return 0;
    return items.map(i => i.quantity).reduce((l, r) => l + r);
}

export const parseInvoice = (checkout: ICheckout): IInvoiceDetail => {
    const subtotal = checkout.invoiceItems
    .filter(i => i.subtotal > 0 && !i.detail.toLowerCase().includes("shipping"))
    .map(i => i.subtotal).reduce((l, r) => l + r);

    const shipping = checkout.invoiceItems
    .filter(i => i.subtotal > 0 && i.detail.toLowerCase().includes("shipping"))
    .map(i => i.subtotal).reduce((l, r) => l + r);

    const deducts: IInvoiceItem[] = checkout.invoiceItems.filter(i => i.subtotal < 0)
    .map(i => ({...i, detail: i.detail.split(":")[0]}));

    return {
        charges: {
            subtotal,
            shipping: shipping > 0 ? shipping : undefined
        },
        chargeTotal: subtotal + shipping,
        estimatedDate: checkout.shipping.estimatedDate.split("T")[0],
        deducts: {
            shipping: deducts.find(i => i.detail.toLowerCase().includes("shipping")),
            discount: deducts.find(i => i.detail.toLowerCase().includes("discount")),
            eCash: deducts.find(i => i.detail.toLowerCase().includes("cash"))
        },
        saving: checkout.saving,
        finalDueAmount: checkout.finalDueAmount
    }
}

export const parseInvoiceForOrder = (order: IOrderDetail): IInvoiceDetail => {
    const subtotal = order.invoice
    .filter(i => i.subtotal > 0 && !i.detail.toLowerCase().includes("shipping"))
    .map(i => i.subtotal).reduce((l, r) => l + r);

    const shipping = order.invoice
    .filter(i => i.subtotal > 0 && i.detail.toLowerCase().includes("shipping"))
    .map(i => i.subtotal).reduce((l, r) => l + r);

    const deducts: IInvoiceItem[] = order.invoice.filter(i => i.subtotal < 0)
    .map(i => ({...i, detail: i.detail.split(":")[0]}));

    return {
        charges: {
            subtotal,
            shipping: shipping > 0 ? shipping : undefined
        },
        chargeTotal: subtotal + shipping,
        estimatedDate: order.shipping.estimatedDate,
        deducts: {
            shipping: deducts.find(i => i.detail.toLowerCase().includes("shipping")),
            discount: deducts.find(i => i.detail.toLowerCase().includes("discount")),
            eCash: deducts.find(i => i.detail.toLowerCase().includes("cash"))
        },
        saving: order.savings,
        finalDueAmount: order.invoice.map(i => i.subtotal).reduce((l, r) => l + r)
    }
}

export const convertCartItemToShoppingItem = (cartItem: ICartItem): IShoppingItem => {
    return cartItem;
}

export const convertOrderItemToShoppingItem = (orderItem: IOrderItem): IShoppingItem => {
    return {
        quantity: orderItem.quantity,
        product: {
            id: orderItem.productId,
            name: orderItem.productName,
            retailPrice: orderItem.purchasedPrice,
            imgPath: orderItem.productImgPath
        }
    }
}

export const getOrderStatus = (status: string): string => {
    if(status === "COMPLETED") return "Completed";
    if(status === "CANCEL") return "Canceled";
    return "On Going";
}

export const getOrderStatusIconPath = (status: string): string => {
    if(status === "COMPLETED") return "assets/icons/general/order_complete_ic.svg";
    if(status === "CANCEL") return "assets/icons/general/order_cancel_ic.svg";
    return "assets/icons/general/order_inprogress_ic.svg";
}

export const shortOrderNum = (orderNumber: string): string => {
    return orderNumber.substring(0, 5) + "..." + orderNumber.substring(orderNumber.length - 4);
}

export const isOrderProcessing = (status: string): boolean => {
    if(status === "COMPLETED" || status === "CANCEL") return false;
    return true;
}