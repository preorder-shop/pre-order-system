package com.example.payment_service.service;

import com.example.payment_service.client.PaymentServiceClient;
import com.example.payment_service.domain.ProductType;
import com.example.payment_service.domain.order.Order;
import com.example.payment_service.domain.order.request.OrderCreateReq;
import com.example.payment_service.domain.product.Product;
import com.example.payment_service.repository.ProductRepository;
import com.example.payment_service.repository.ProductStockRepository;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final ProductStockRepository productStockRepository;

    private final PaymentServiceClient paymentServiceClient;


//    public void createOrder(OrderCreateReq request, double probability) {
//
//        String productNumber = request.getProductNumber();
//        productStockRepository.decreaseStock(productNumber); // 결제 프로세스에 들어오면 -1
//
//        if (0.2 < probability) {
//            paymentServiceClient.purchaseProduct(request);
//     //       productStockRepository.decreaseStock(productNumber);
//        }
//
//        // 결제 실패
//        productStockRepository.increaseStock(productNumber); // 중간에 실패하면 다시 +1
//        throw new IllegalArgumentException("결제를 취소합니다.");
//
//    }

    public void createOrder(double probability) {


   //     productStockRepository.decreaseStock("001"); // 결제 프로세스에 들어오면 -1

        if (0.2 < probability) {
            paymentServiceClient.purchaseProduct();
            //       productStockRepository.decreaseStock(productNumber);
        }else{
            // 결제 실패
       //     productStockRepository.increaseStock("001"); // 중간에 실패하면 다시 +1
            throw new IllegalArgumentException("결제를 취소합니다.");
        }



    }
    public void initStockInCache() {
        productStockRepository.initStockInfo();
    }


}
