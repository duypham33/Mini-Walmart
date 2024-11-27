package com.beaconfire.shoppingapp.controllers.product;

import com.beaconfire.shoppingapp.dtos.ResponseDto;
import com.beaconfire.shoppingapp.dtos.product.*;
import com.beaconfire.shoppingapp.services.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("admin/products")
public class AdminProductController {
    private final ProductService productService;

    public AdminProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<ProductDto>>> getAllProducts(
            @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer pageSize
    ){
        return productService.searchProducts(page, pageSize, null, null, null, null, false).toResponseEntity();
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDto<ProductsSearchDto>> searchProducts(
            @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(required = false) String search, @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) List<Integer> brands,
            @RequestParam(required = false) Double maxPrice
    ){
        return productService.searchProducts2(page, pageSize, search, categoryId, brands, maxPrice, false).toResponseEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<ProductDetailDto>> getProductDetail(@PathVariable Long id){
        return productService.findProductDetail(id, true).toResponseEntity();
    }

    @PostMapping
    private ResponseEntity<ResponseDto<ProductDetailDto>> createProduct(@RequestBody CreateProductRequestDto requestDto){
        return productService.createProduct(requestDto).toResponseEntity();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<ProductDetailDto>> updateProduct(@PathVariable Long id, @RequestBody UpdateProductRequestDto requestDto){
        return productService.updateProduct(id, requestDto).toResponseEntity();
    }
}
