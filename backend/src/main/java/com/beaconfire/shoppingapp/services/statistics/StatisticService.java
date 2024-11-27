package com.beaconfire.shoppingapp.services.statistics;

import com.beaconfire.shoppingapp.daos.product.ProductDao;
import com.beaconfire.shoppingapp.daos.statistic.StatisticDao;
import com.beaconfire.shoppingapp.dtos.ResponseDto;
import com.beaconfire.shoppingapp.dtos.product.ProductDto;
import com.beaconfire.shoppingapp.dtos.statistics.AdminStatisticDto;
import com.beaconfire.shoppingapp.dtos.statistics.SimpleStatisticDto;
import com.beaconfire.shoppingapp.dtos.statistics.TopProduct;
import com.beaconfire.shoppingapp.dtos.statistics.UserStatisticDto;
import com.beaconfire.shoppingapp.utils.StatisticUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticService {
    private final StatisticDao statisticDao;
    private final ProductDao productDao;

    public StatisticService(StatisticDao statisticDao, ProductDao productDao){
        this.statisticDao = statisticDao;
        this.productDao = productDao;
    }

    public ResponseDto<List<SimpleStatisticDto>> getAllUserStatistics(Long userId){
        var list = StatisticUtil.prepareUserStatistics(userId);
        for(var s : list){
            var res = statisticDao.callStoredProcedure(s.getProcedureName(), s.getInputs());
            if(res.size() > 1) s.setResults(res);
            else if(res.size() == 1) s.setResult(res.get(0));
            else s.setResult("None");
        }

        return ResponseDto.get(list);
    }

    public ResponseDto<UserStatisticDto> getAllUserStatistics2(Long userId){
        List<Long> inputs = List.of(userId);
        return ResponseDto.get(
                UserStatisticDto.builder()
                        .subscription(parseResults(statisticDao.callStoredProcedure("subscription", inputs)))
                        .savings(parseResults(statisticDao.callStoredProcedure("savings", inputs)))
                        .eCash(parseResults(statisticDao.callStoredProcedure("ecash", inputs)))
                        .completedOrders(parseResults(statisticDao.callStoredProcedure("completedOrdersNum", inputs)))
                        .cancelOrders(parseResults(statisticDao.callStoredProcedure("canceledOrdersNum", inputs)))
                        .frequentProducts(topProductsForUserStatistic("frequentlyProducts", userId, 3L))
                        .recentProducts(topProductsForUserStatistic("recentlyProducts", userId, 3L))
                        .build()
        );
    }

    private String parseResults(List<String> results){
        return results.isEmpty() ? "None" : results.get(0);
    }

    public ResponseDto<List<String>> mostFrequentlyPurchasedProducts(Long userId, Integer limit){
        return ResponseDto.get(statisticDao.mostPurchasedProducts("frequentlyProducts", userId, limit));
    }

    private List<TopProduct> topProductsForUserStatistic(String procedureName, Long userId, Long limit){
        List<String> textResults = statisticDao.callStoredProcedure(procedureName, List.of(userId, limit));
        List<TopProduct> results = new ArrayList<>();
        for(String res : textResults){
            String[] tokens = res.split(" - ");
            Long productId = Long.valueOf(tokens[1]);
            results.add(TopProduct.builder()
                    .product(ProductDto.fromProduct(productDao.findProductById(productId)))
                    .metric(tokens[0])
                    .build());
        }
        return results;
    }

    public ResponseDto<List<String>> mostRecentlyPurchasedProducts(Long userId, Integer limit){
        return ResponseDto.get(statisticDao.mostPurchasedProducts("recentlyProducts", userId, limit));
    }

    public ResponseDto<List<SimpleStatisticDto>> getAllSellerStatistics(){
        var list = StatisticUtil.prepareSellerStatistics();
        for(var s : list){
            var res = statisticDao.callStoredProcedure(s.getProcedureName(), s.getInputs());
            if(res.size() > 1) s.setResults(res);
            else if(res.size() == 1) s.setResult(res.get(0));
            else s.setResult("None");
        }

        return ResponseDto.get(list);
    }

    public ResponseDto<AdminStatisticDto> getAllSellerStatistics2(){
        List<Long> inputs = List.of();
        return ResponseDto.get(
                AdminStatisticDto.builder()
                        .userTotal(parseResults(statisticDao.callStoredProcedure("usersNum", inputs)))
                        .subscriptions(parseResults(statisticDao.callStoredProcedure("subscriptionNums", inputs)))
                        .orderTotal(parseResults(statisticDao.callStoredProcedure("ordersByStatus", inputs)))
                        .subscriptionProfit(parseResults(statisticDao.callStoredProcedure("subscriptionProfit", inputs)))
                        .sellingProfit(parseResults(statisticDao.callStoredProcedure("sellingProfit", inputs)))
                        .categoryTotal(parseResults(statisticDao.callStoredProcedure("categoriesNum", inputs)))
                        .brandTotal(parseResults(statisticDao.callStoredProcedure("brandsNum", inputs)))
                        .productTotal(parseResults(statisticDao.callStoredProcedure("productsNum", inputs)))
                        .soldProductTotal(parseResults(statisticDao.callStoredProcedure("soldProducts", inputs)))
                        .profitProducts(topProductsForAdminStatistic("topProfit", 3L))
                        .popularProducts(topProductsForAdminStatistic("topPopular", 3L))
                        .build()
        );
    }

    private List<TopProduct> topProductsForAdminStatistic(String procedureName, Long limit){
        List<String> textResults = statisticDao.callStoredProcedure(procedureName, List.of(limit));
        List<TopProduct> results = new ArrayList<>();
        for(String res : textResults){
            String[] tokens = res.split(" - ");
            Long productId = Long.valueOf(tokens[1]);
            results.add(TopProduct.builder()
                    .product(ProductDto.fullInfo(productDao.findProductById(productId)))
                    .metric(tokens[0])
                    .build());
        }
        return results;
    }


    public ResponseDto<List<String>> mostProfitableProduct(Integer limit){
        return ResponseDto.get(statisticDao.mostPurchasedProductsForSeller("topProfit", limit));
    }

    public ResponseDto<List<String>> mostPopularProduct(Integer limit){
        return ResponseDto.get(statisticDao.mostPurchasedProductsForSeller("topPopular", limit));
    }
}
