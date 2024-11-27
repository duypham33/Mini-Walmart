package com.beaconfire.shoppingapp.controllers.product;

import com.beaconfire.shoppingapp.dtos.ResponseDto;
import com.beaconfire.shoppingapp.dtos.product.*;
import com.beaconfire.shoppingapp.services.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private static ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<ProductDto>>> getInStockProducts(
            @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer pageSize
    ){
        return productService.searchProducts(page, pageSize, null, null, null, null, true).toResponseEntity();
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDto<ProductsSearchDto>> searchInStockProducts(
            @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(required = false) String search, @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) List<Integer> brands,
            @RequestParam(required = false) Double maxPrice
    ){
        return productService.searchProducts2(page, pageSize, search, categoryId, brands, maxPrice, true).toResponseEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<ProductDetailDto>> getProductDetail(@PathVariable Long id){
        return productService.findProductDetail(id, false).toResponseEntity();
    }

    @GetMapping("/category-search")
    public ResponseEntity<ResponseDto<List<CategoryDto>>> searchCategoryByKeyWord(
            @RequestParam(required = false) String kw
    ){
        return productService.searchCategoryByKeyWord(kw).toResponseEntity();
    }

    @GetMapping("/category-root")
    public ResponseEntity<ResponseDto<List<CategoryDto>>> getRootCategories(){
        return productService.getRootCategories().toResponseEntity();
    }

    @GetMapping("/category-families/{rootId}")
    public ResponseEntity<ResponseDto<List<CategoryFamilyDto>>> getCategoryFamilies(@PathVariable Long rootId){
        return productService.getCategoryFamilies(rootId).toResponseEntity();
    }

    @GetMapping("/brand-search")
    public ResponseEntity<ResponseDto<List<BrandDto>>> searchBrandByKeyWord(
            @RequestParam(required = false) String kw
    ){
        return productService.searchBrandByKeyWord(kw).toResponseEntity();
    }
}
