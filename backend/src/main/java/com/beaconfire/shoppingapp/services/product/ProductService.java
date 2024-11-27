package com.beaconfire.shoppingapp.services.product;

import com.beaconfire.shoppingapp.daos.product.ProductDao;
import com.beaconfire.shoppingapp.dtos.ResponseDto;
import com.beaconfire.shoppingapp.dtos.product.*;
import com.beaconfire.shoppingapp.dtos.queryFeature.QueryPage;
import com.beaconfire.shoppingapp.entities.product.Brand;
import com.beaconfire.shoppingapp.entities.product.Category;
import com.beaconfire.shoppingapp.entities.product.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao){
        this.productDao = productDao;
    }

    public ResponseDto<ProductDetailDto> findProductDetail(Long productId, Boolean isAdmin){
        var product = productDao.findProductById(productId);
        return ResponseDto.get(
                product == null ? null : (isAdmin ? ProductDetailDto.fullInfo(product) : ProductDetailDto.fromProduct(product)),
                product != null ? null : "Not found product with id " + productId,
                product != null ? null : HttpStatus.NOT_FOUND
        );
    }

    public ResponseDto<List<ProductDto>> searchProducts(
            Integer page, Integer pageSize,
            String search, Long categoryId, List<Integer> brands, Double maxPrice, Boolean onlyInStock
    ){
        List<ProductDto> products = productDao.getProducts(
                QueryPage.builder().page(page).pageSize(pageSize).build(),
                search, categoryId, brands, maxPrice, onlyInStock
        ).stream().map(onlyInStock ? ProductDto::fromProduct : ProductDto::fullInfo).toList();

        String message = !products.isEmpty() ? String.format("Found %d products for page %d", products.size(), page)
                : "No records found!";
        return ResponseDto.get(products, message);
    }


    public ResponseDto<ProductsSearchDto> searchProducts2(
            Integer page, Integer pageSize,
            String search, Long categoryId, List<Integer> brands, Double maxPrice, Boolean onlyInStock
    ){
        List<Product> products = productDao.getProducts(
                QueryPage.builder().page(page).pageSize(pageSize).build(),
                search, categoryId, brands, maxPrice, onlyInStock
        );

        String message = !products.isEmpty() ? String.format("Found %d products for page %d", products.size(), page)
                : "No records found!";

        var res = products.stream().map(onlyInStock ? ProductDto::fromProduct : ProductDto::fullInfo).toList();
        if(search == null || products.isEmpty()){
            return ResponseDto.get(
                    ProductsSearchDto.builder()
                            .products(res)
                            .build(),
                    message
            );
        }

        CategoryDto categoryDto = CategoryDto.fromCategory(products.get(0).getCategory());
//        System.out.println(categoryDto);
        Set<BrandDto> bs = new HashSet<>();
        for(Product p : products){
            bs.add(BrandDto.fromBrand(p.getBrand()));
        }

//        System.out.println(bs);
        return ResponseDto.get(
                ProductsSearchDto.builder()
                        .products(res).category(categoryDto).brands(bs)
                        .build(),
                message
        );
    }

    public ResponseDto<Product> findClosestProduct(Long productId){
        var product = productDao.findProductById(productId);
        return ResponseDto.get(productDao.findClosestProduct(product));
    }

    public ResponseDto<ProductDetailDto> createProduct(CreateProductRequestDto requestDto){
        Category category = productDao.findCategoryById(requestDto.getCategoryId());
        if(category == null) return ResponseDto.get(null, "Category Not Found!", HttpStatus.NOT_FOUND);
        Brand brand = productDao.findBrandById(requestDto.getBrandId());
        if(brand == null) return ResponseDto.get(null, "Brand Not Found!", HttpStatus.NOT_FOUND);
        Product product = Product.builder()
                .name(requestDto.getName()).description(requestDto.getDescription())
                .quantity(requestDto.getQuantity())
                .retailPrice(requestDto.getRetailPrice())
                .wholesalePrice(requestDto.getWholesalePrice())
                .numRatings(0)
                .build();

        product = productDao.createProduct(product, category, brand);
        return ResponseDto.get(ProductDetailDto.fullInfo(product), "The product was created successfully!");
    }

    public ResponseDto<ProductDetailDto> updateProduct(Long productId, UpdateProductRequestDto requestDto){
        Product product = productDao.findProductById(productId);
        if(product == null) return ResponseDto.get(null, "Product Not Found!", HttpStatus.NOT_FOUND);
        product = productDao.updateProduct(product, requestDto);
        return ResponseDto.get(ProductDetailDto.fullInfo(product), "The product was updated successfully!");
    }

    public ResponseDto<List<CategoryDto>> searchCategoryByKeyWord(String keyword){
        return ResponseDto.get(productDao.searchCategoryByKeyWord(keyword).stream()
                .map(CategoryDto::fromCategory).toList());
    }

    public ResponseDto<List<CategoryDto>> getRootCategories(){
        return ResponseDto.get(productDao.getRootCategories().stream()
                .map(CategoryDto::getWithNameFromCategory).toList());
    }

    public ResponseDto<List<CategoryFamilyDto>> getCategoryFamilies(Long rootId){
        List<CategoryFamilyDto> parents = productDao.getParentCategories(rootId).stream()
                .map(c -> CategoryFamilyDto.builder().id(c.getId()).name(c.getName()).build()).toList();
        parents.forEach(c -> {
            c.setChildren(productDao.getSubCategories(c.getId()).stream()
                    .map(CategoryDto::getWithNameFromCategory).toList());
        });
        return ResponseDto.get(parents);
    }

    public ResponseDto<List<BrandDto>> searchBrandByKeyWord(String keyword){
        return ResponseDto.get(productDao.searchBrandByKeyWord(keyword).stream()
                .map(BrandDto::fromBrand).toList());
    }
}
