export interface ICartItem{
    quantity: number,
    product: {
        id: number,
        name: string,
        retailPrice: number,
        imgPath?: string
    }
}

// export const dummyCartItems: ICartItem[] = [
//     {
//         "product": {
//             "id": 64,
//             "name": "Marketside Skin-On Atlantic Salmon Fillet, 2.0-3.0 lb",
//             "retailPrice": 21.55,
//             "imgPath": "https://i5.walmartimages.com/seo/Fresh-Atlantic-Salmon-Fillets-2-00-3-00-lb-Whole-Salmon-Side-240-Calories-per-3-oz-Serving-Certifications-BAP-Certified_3163f38e-a693-4846-a49c-f2c87f9e633f.877131d6a6459f4b613c9757a140b8eb.jpeg?odnHeight=784&odnWidth=580&odnBg=FFFFFF"
//         },
//         "quantity": 2
//     },
//     {
//         "product": {
//             "id": 50,
//             "name": "Great Value Frozen Cooked Medium Peeled & Deveined Tail-on Shrimp, 12 oz Bag (41-60 Count per lb)",
//             "retailPrice": 5.47,
//             "imgPath": "https://i5.walmartimages.com/seo/Great-Value-Frozen-Cooked-Medium-Peeled-Deveined-Tail-on-Shrimp-12-oz-Bag-41-60-Count-per-lb_14c58556-515f-4420-b1a6-517437f3db08.4c5376359ce156a1f6c952b8972dfebb.jpeg?odnHeight=784&odnWidth=580&odnBg=FFFFFF"
//         },
//         "quantity": 3
//     },
//     {
//         "product": {
//             "id": 24,
//             "name": "Marketside Fresh Shredded Iceberg Lettuce, 8 oz Bag, Fresh",
//             "retailPrice": 1.97,
//             "imgPath": "https://i5.walmartimages.com/seo/Marketside-Fresh-Shredded-Iceberg-Lettuce-8-oz-Bag-Fresh_c806e522-b09e-4078-a6cf-c49fa64b8bac_1.184fccfd6599719f0852c4a66b9a1e0e.jpeg?odnHeight=784&odnWidth=580&odnBg=FFFFFF"
//         },
//         "quantity": 3
//     }
// ];