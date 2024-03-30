## 예약 구매 프로젝트
### 💡 프로젝트 소개
해당 프로젝트는 크게 두가지 주제로 나누어져 있습니다. 

**첫번째 주제는** Spring Data JPA를 사용한 crud 기능의 게시판 형태의 서비스를 제공합니다.  
사용자는 jwt 토큰 방식의 인증/인가를 통해 프로필, 글, 댓글 crud 등의 기능과  
팔로우 기능 다앙한 조건으로 사용자들의 활동을 조회할수 있는 기능을 제공합니다.

**두번째 주제는** 대용량 트래픽 발생시 일어날 수 있는 문제들을 가정하고 이를 처리하는 내용입니다.  
특정 시간 트래픽이 몰리는 예약 구매 상품 결제를 시뮬레이션하면서 데이터 동시성 문제를 해결하고,  
성능 향상을 위해 캐싱을 도입했습니다.

- 두 프로젝트 모두 도메인과 주요 기능을 중심으로 마이크로 서비스로 분리를 하였습니다.  

---
### ⚙️ 개발환경
- Java 17 , Gradle 8.5 , SpringBoot 3.2.2 , MySQL 8.0 , Redis , Docker

---
### ⚒️ 프로젝트 아키텍처
- 첫번째
  <img width="1384" alt="스크린샷 2024-03-28 오후 8 39 26" src="https://github.com/preorder-shop/purchase-service/assets/74480236/81c02d6e-7fe1-4ab4-a22c-426b81faedd7">

---

- 두번째
  <img width="1417" alt="스크린샷 2024-03-28 오후 8 59 27" src="https://github.com/preorder-shop/purchase-service/assets/74480236/b95ed56a-95e6-4c6e-ab3a-acfc442c530f">
---

### 📁 디렉토리 구조
- 첫번째 주제
```bash
├── user_service
├── activity_service
└── newsfeed_service
```
- 두번째 주제
```bash
├── product_service
├── stock_service
├── payment_service
└── purchase_service
```
- 각 서비스 소개 & 링크

| Service	                                                                                                                                             | Description	                     | Port   |
|:-----------------------------------------------------------------------------------------------------------------------------------------------------|:---------------------------------|:-------|
| [`User`](https://github.com/preorder-shop/user-service)                                                                                              | 유저 정보 관리 (회원가입, 로그인, 로그아웃 등)     | `8081` |
| [`Activity`](https://github.com/preorder-shop/activity-service)                                                                                      | 유저 활동 관리 (게시글 작성, 팔로우, 좋아요 기능 등) | `8082` |
| [`Newsfeed`](https://github.com/preorder-shop/newsfeed-service)                                                                                      | 피드 기능 제공                         | `8083` |
| [`Product`](https://github.com/preorder-shop/product-service)                                                                                        | 상품 관리 (일반 상품, 예약 상품)             | `8084` |
| [`Stock`](https://github.com/preorder-shop/stock-service)                                                                                            | 실시간 재고 관리                        | `8085` |
| [`Payment`](https://github.com/preorder-shop/payment-service)                                                                                        | 결제 진입 관리                         | `8086` |
| [`Purchase`](https://github.com/preorder-shop/purchase-service)                                                                                      | 결제 완료 관리                         | `8087` |
| [`API Gateway`](https://github.com/preorder-shop/gateway-service)                                                                                    | 게이트웨이 역할, jwt 토큰 검사              | `8080` |
| [`Eureka`](https://github.com/preorder-shop/eureka-server)                                                                                           | 서비스 등록 및 검색 (discovery 서버 역할)    | `8761` |
| [`Config`](https://github.com/preorder-shop/config-service)                                                                                          | application.yml 등의 설정 정보 관리      | `9000` |
---
### 🗂️ ERD 구조

<img width="1006" alt="pre order erd" src="https://github.com/preorder-shop/purchase-service/assets/74480236/774458d8-4b28-4177-aa5d-8cce851c1a6e">
---

### 💻 실행 환경 구축
-  msa service 가 사용하는 database 실행 환경 구축 (MySQL, Redis)


    docker-compose up -d

---

### 📜 Docs

[API 명세서](https://documenter.getpostman.com/view/18311817/2sA35D5iGf)

