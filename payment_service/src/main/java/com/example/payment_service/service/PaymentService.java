package com.example.payment_service.service;

import com.example.payment_service.domain.OrderState;
import com.example.payment_service.domain.order.Order;
import com.example.payment_service.domain.order.request.OrderCreateReq;
import com.example.payment_service.domain.payment.PaymentDto;
import com.example.payment_service.domain.product.Product;
import com.example.payment_service.domain.stock.Stock;
import com.example.payment_service.repository.OrderRepository;
import com.example.payment_service.repository.ProductRepository;
import com.example.payment_service.repository.ProductStockRepository;
import com.example.payment_service.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final OrderService orderService;
    private final StockRepository stockRepository;

    private final OrderRepository orderRepository;
    private final ProductStockRepository productStockRepository;

    private final ProductRepository productRepository;

//    public void purchase(double prob, OrderCreateReq orderCreateReq) {
//
//        System.out.println("실제 결제를 하는 API 호출 완료");
//
//        String productNumber = orderCreateReq.getProductNumber();
//
//        if (0.2 < prob) {
//            // todo : 여기서 결제 완료 처리
//            // 결제가 완료되면 redis 의 값과 db에 값을 변경
//            // order 정보 db에 저장.
//
//            updateStock(productNumber);
//            createOrder(orderCreateReq);
//
//        }
//
//        // redis 에 수량 +1
//        updateStockInCache(productNumber);
//
//        throw new IllegalArgumentException("결제 실패");
//
//    }

    public void purchase(double prob) {

        System.out.println("실제 결제를 하는 API 호출 완료");

    //    String productNumber = orderCreateReq.getProductNumber();

        if (0.2 < prob) {
            // todo : 여기서 결제 완료 처리
            // 결제가 완료되면 redis 의 값과 db에 값을 변경
            // order 정보 db에 저장.
            productStockRepository.decreaseStock("001");
         //   updateStock("001");
            createOrder();


        }else{
            // redis 에 수량 +1
      //      updateStockInCache("001");

            throw new IllegalArgumentException("결제 실패");

        }


    }

    private void updateStock(String productNum){
        Stock stock = stockRepository.findByProductNumber(productNum)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 상품 아이디 입니다."));
        stock.decreaseStock(1); // db에 있는 재고 한개 감소

    }

    private void updateStockInCache(String productNumber){
        productStockRepository.increaseStock(productNumber);
    }


//    private void createOrder(OrderCreateReq orderRequest){
//        Order.builder()
//                .userId(orderRequest.getUserId())
//                .productId(orderRequest.getProductNumber())
//                .orderState(OrderState.COMPLETE)
//                .build();
//
//    }

    private void createOrder(){
        productStockRepository.buyerIncrease();
        System.out.println("결제 성공!");
    }
}
