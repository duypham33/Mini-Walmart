package com.beaconfire.shoppingapp.daos.product;

import com.beaconfire.shoppingapp.daos.AbstractHibernateDao;
import com.beaconfire.shoppingapp.dtos.product.UpdateProductRequestDto;
import com.beaconfire.shoppingapp.dtos.queryFeature.QueryPage;
import com.beaconfire.shoppingapp.entities.product.Brand;
import com.beaconfire.shoppingapp.entities.product.Category;
import com.beaconfire.shoppingapp.entities.product.Product;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class ProductDao extends AbstractHibernateDao {
    public Product findProductById(Long id){
        return findById(id, Product.class);
    }

    public Category findCategoryById(Long id){
        return findById(id, Category.class);
    }

    public Brand findBrandById(Long id){
        return findById(id, Brand.class);
    }

    public List<Product> getProducts(
            QueryPage queryPage, String search, Long categoryId, List<Integer> brands, Double maxPrice, Boolean onlyInStock
    ){
        var session = getCurrentSession();
        var criteriaBuilder = session.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);

        Predicate predicate = onlyInStock ? criteriaBuilder.greaterThan(productRoot.get("quantity"), 0L) : null;
        Join<Product, Category> categoryJoin = null;
        if(search != null){
            categoryJoin = productRoot.join("category", JoinType.INNER);
            search = "%" + search.toLowerCase() + "%";
            var p = criteriaBuilder.like(criteriaBuilder.lower(categoryJoin.get("categoryLookup")), search);
            p = criteriaBuilder.or(p, criteriaBuilder.like(criteriaBuilder.lower(productRoot.get("name")), search));
            predicate = predicate == null ? p : criteriaBuilder.and(predicate, p);
        }

        if(categoryId != null){
            if(categoryJoin == null) categoryJoin = productRoot.join("category", JoinType.INNER);
            var p = criteriaBuilder.equal(categoryJoin.get("id"), categoryId);
            predicate = predicate == null ? p : criteriaBuilder.and(predicate, p);
        }

        if(maxPrice != null){
            var p = criteriaBuilder.lessThanOrEqualTo(productRoot.get("retailPrice"), maxPrice);
            predicate = predicate == null ? p : criteriaBuilder.and(predicate, p);
        }

        if(brands != null && !brands.isEmpty()){
            Join<Product, Brand> brandJoin = productRoot.join("brand", JoinType.INNER);
            var p = brandJoin.get("id").in(brands);
            predicate = predicate == null ? p : criteriaBuilder.and(predicate, p);
        }

        if(predicate != null) criteriaQuery.where(predicate);
        if(maxPrice != null) criteriaQuery.orderBy(criteriaBuilder.asc(productRoot.get("retailPrice")));
        Query<Product> query = session.createQuery(criteriaQuery);
        query.setFirstResult(queryPage == null ? 0 : queryPage.getSkip());
        query.setMaxResults(queryPage == null ? 20 : queryPage.getPageSize());
        return query.getResultList();
    }

    public Category getCategoryByProductId(Long productId){
        var session = getCurrentSession();
        final String hql = """
                select p from Product p join fetch p.category where p.id = :productId
                """;
        var query = session.createQuery(hql, Product.class);
        query.setParameter("productId", productId);
        Category c = query.uniqueResult().getCategory();
        System.out.println(c);
        return c;
    }

    public List<Category> searchCategoryByKeyWord(String keyword){
        var session = getCurrentSession();
        final String hql = """
                from Category c where lower(c.categoryLookup) like :kw
                """;
        var query = session.createQuery(hql, Category.class);
        query.setParameter("kw", "%" + keyword.toLowerCase() + "%");
        query.setMaxResults(30);
        return query.getResultList();
    }

    public List<Category> getRootCategories(){
        var session = getCurrentSession();
        final String hql = """
                from Category c where c.parentCategory is null and c.categoryLookup is null
                """;
        var query = session.createQuery(hql, Category.class);
        return query.getResultList();
    }

    public List<Category> getParentCategories(Long rootId){
        var session = getCurrentSession();
        final String hql = """
                from Category c where c.parentCategory.id = :rootId and c.categoryLookup is null
                """;
        var query = session.createQuery(hql, Category.class);
        query.setParameter("rootId", rootId);
        return query.getResultList();
    }

    public List<Category> getSubCategories(Long parentId){
        var session = getCurrentSession();
        final String hql = """
                from Category c where c.parentCategory.id = :parentId and c.categoryLookup is not null
                """;
        var query = session.createQuery(hql, Category.class);
        query.setParameter("parentId", parentId);
        return query.getResultList();
    }

    public Brand getBrandByProductId(Long productId){
        var session = getCurrentSession();
        final String hql = """
                select p from Product p join fetch p.brand where p.id = :productId
                """;
        var query = session.createQuery(hql, Product.class);
        query.setParameter("productId", productId);
        Brand b = query.uniqueResult().getBrand();
        System.out.println(b);
        return b;
    }

    public List<Brand> searchBrandByKeyWord(String keyword){
        var session = getCurrentSession();
        final String hql = """
                from Brand b where lower(b.name) like :kw
                """;
        var query = session.createQuery(hql, Brand.class);
        query.setParameter("kw", "%" + keyword.toLowerCase() + "%");
        query.setMaxResults(30);
        return query.getResultList();
    }

    public void updateProductQuantity(Long productId, int quantity){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            final String hql = """
                update Product set quantity = :quantity where id = :productId
            """;
            Query query = session.createQuery(hql);
            query.setParameter("productId", productId);
            query.setParameter("quantity", quantity);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public Product findClosestProduct(Product product){
        var session = getCurrentSession();
        var criteriaBuilder = session.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);

        Join<Product, Category> categoryJoin = productRoot.join("category", JoinType.INNER);
        Predicate categoryPredicate = criteriaBuilder.equal(categoryJoin.get("id"), product.getCategory().getId());
        Predicate idPredicate = criteriaBuilder.notEqual(productRoot.get("id"), product.getId());
        Predicate quantityPredicate = criteriaBuilder.greaterThan(productRoot.get("quantity"), 0);
        // Combine the conditions with AND
        Predicate finalPredicate = criteriaBuilder.and(categoryPredicate, idPredicate, quantityPredicate);
        criteriaQuery.where(finalPredicate);

        criteriaQuery.orderBy(criteriaBuilder.asc(criteriaBuilder.abs(
                criteriaBuilder.diff(productRoot.get("retailPrice"), product.getRetailPrice())
        )));
        Query<Product> query = session.createQuery(criteriaQuery);
        query.setMaxResults(1);
        return query.uniqueResult();
    }

    public Product createProduct(Product product, Category category, Brand brand){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            product.setCategory(category);
            product.setBrand(brand);
            session.save(product);
            tx.commit();

            return product;
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    public Product updateProduct(Product product, UpdateProductRequestDto requestDto){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            product.setName(requestDto.getName());
            product.setDescription(requestDto.getDescription());
            product.setQuantity(requestDto.getQuantity());
            product.setRetailPrice(requestDto.getRetailPrice());
            product.setWholesalePrice(requestDto.getWholesalePrice());
            session.merge(product);
            tx.commit();

            return product;
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }
}
