import { IBrand, IBrandForm } from "./brand.model";

export interface IProduct{
    id: number,
    name: string,
    description?: string,
    retailPrice: number,
    wholesalePrice?: number,
    quantity?: number,
    imgPath?: string,
    rating?: number,
    category?: {
        id: number,
        categoryPath: string
    },
    brand?: IBrand
}

export interface IProductForm{
    id: number,
    name?: string,
    description?: string,
    retailPrice?: number,
    wholesalePrice?: number,
    quantity?: number,
    category: {
        id: number,
        categoryPath?: string
    },
    brand: IBrandForm
}

export const EMPTY_PRODUCT: IProductForm = {
    id: 0,
    category: {
        id: 0
    },
    brand: {
        id: 0
    }
} 

// export const dummyDetailProduct: IProduct = {
//     "category": {
//         "id": 3,
//         "categoryPath": "Grocery - Fresh Produce - Fresh Fruit"
//     },
//     "brand": {
//         "id": 1,
//         "name": "Fresh Produce"
//     },
//     "id": 1,
//     "name": "Fresh Blueberries, 1 Pint Container",
//     "description": "Fresh Blueberries, 1 Pint Container",
//     "quantity": 22,
//     "retailPrice": 2.87,
//     "wholesalePrice": 0.92,
//     "imgPath": "https://i5.walmartimages.com/seo/Fresh-Blueberries-11-oz-or-1-Pint-Container_58566f1b-31b1-4e76-b0c2-39403a93c0c7.c8b5bd91935856cbdb15b7587d8c031e.jpeg?odnHeight=784&odnWidth=580&odnBg=FFFFFF",
//     "rating": 3.0
// }

// export const dummyProducts: IProduct[] = [
//     {
//         id: 49,
//         name: "Great Value Frozen Peeled Tail on Extra Large Shrimp, 12 oz (26-30 Count per lb)",
//         quantity: 10,
//         retailPrice: 5.68,
//         wholesalePrice: 2.71,
//         imgPath: "https://i5.walmartimages.com/seo/Great-Value-Frozen-Peeled-Tail-on-Extra-Large-Shrimp-12-oz-26-30-Count-per-lb_8f6a9394-9329-4b0d-91d6-c4377e91b993.4ba127b5110694ff4a534eac7a9747ec.jpeg?odnHeight=784&odnWidth=580&odnBg=FFFFFF",
//         rating: 5.0
//     },
//     {
//         id: 50,
//         name: "Great Value Frozen Cooked Medium Peeled & Deveined Tail-on Shrimp, 12 oz Bag (41-60 Count per lb)",
//         quantity: 72,
//         retailPrice: 5.47,
//         wholesalePrice: 3.04,
//         imgPath: "https://i5.walmartimages.com/seo/Great-Value-Frozen-Cooked-Medium-Peeled-Deveined-Tail-on-Shrimp-12-oz-Bag-41-60-Count-per-lb_14c58556-515f-4420-b1a6-517437f3db08.4c5376359ce156a1f6c952b8972dfebb.jpeg?odnHeight=784&odnWidth=580&odnBg=FFFFFF",
//         rating: 3.5
//     },
//     {
//         "id": 37,
//         "name": "Jennie-O Frozen Turkey Oven Ready Breast, Bone-In, 5-7Ibs",
//         "quantity": 70,
//         "retailPrice": 23.22,
//         "wholesalePrice": 11.17,
//         "rating": 1.0
//     },
//     {
//         "id": 38,
//         "name": "Jennie-O Frozen Whole Hen Turkey 10-16 lb",
//         "retailPrice": 12.13,
//         "imgPath": "https://i5.walmartimages.com/seo/Jennie-O-Turkey-Frozen-Whole-Turkey-10-16-lb_b920e3b2-cb73-4fad-950c-fb290d1a39d0.167417a9b9b17d1cf70e7d620788142d.jpeg?odnHeight=784&odnWidth=580&odnBg=FFFFFF"
//     },
//     {
//         "id": 39,
//         "name": "JENNIE-O OVEN READY Boneless Turkey Breast, Frozen, 2 - 3 lb Plastic Bag",
//         "quantity": 49,
//         "retailPrice": 14.96,
//         "wholesalePrice": 10.05,
//         "imgPath": "https://i5.walmartimages.com/seo/JENNIE-O-OVEN-READY-Boneless-Turkey-Breast-Frozen-2-3-lb-Plastic-Bag_e0e0400e-7662-4f51-8f5b-d94481dcf72a.aa5f976a4d02b86eead25b8fbbe04c10.jpeg?odnHeight=784&odnWidth=580&odnBg=FFFFFF"
//     }
// ];