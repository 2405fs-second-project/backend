package com.second.backend.service;

import com.second.backend.dto.*;
import com.second.backend.model.*;

import com.second.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final UsersRepository usersRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final CartItemsRepository cartItemsRepository;
    private final ProductRepository productRepository;
    private final ProductSizesRepository productSizesRepository;
    private final UsersService usersService;

    @Transactional(readOnly = true)
    public UserOrderInfoResponse getUserOrderInfo(Integer userId) {
        Users user = usersService.findById(userId);
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return new UserOrderInfoResponse(
                user.getName() != null ? user.getName() : "",
                user.getPhoneNum() != null ? user.getPhoneNum() : "",
                user.getUpdateName() != null ? user.getUpdateName() : "",
                user.getUpdatePhone() != null ? user.getUpdatePhone() : "",
                user.getUpdateAddress() != null ? user.getUpdateAddress() : "",
                user.getShippingInfo() != null ? user.getShippingInfo() : ""
        );
    }


    @Transactional(readOnly = true)
    public List<CartItemsResponse> getCartItems(Integer userId) {//장바구니에서 데이터를 가져오는 경우

        Users users = usersService.findById(userId); //유저 연결로 코드추가(지영)

        // 장바구니 조회
        List<CartItems> cartItems = cartItemsRepository.findByUsers(users);

        // DTO로 변환 및 로그 추가 : 엔티티 데이터를 DTO 구조로 변환하여 외부로 전달합니다.
        List<CartItemsResponse> cartItemsResponse = cartItems.stream().map(item -> {
            Product product = item.getProduct();
            ProductSizes productSize = item.getProductSizes();

            String filePath = product.getFileUrl().replace("/img/", ""); // "/img/" 제거

            // 변환 데이터 확인용 로그
            System.out.println("Product ID: " + product.getId());
            System.out.println("Product Name: " + product.getName());
            System.out.println("Product Image URL: " + product.getFileUrl());
            System.out.println("Product Color: " + product.getColor());
            System.out.println("Size: " + productSize.getSize());
            System.out.println("Quantity: " + item.getQuantity());
            System.out.println("Price: " + product.getPrice());

            return new CartItemsResponse(
                    product.getId(),
                    product.getName(),
                    filePath,
                    product.getColor(),
                    productSize.getSize(),
                    item.getQuantity(),
                    product.getPrice()
            );
        }).collect(Collectors.toList());

        // 최종 응답 로그
        System.out.println("CartItemsResponse: " + cartItemsResponse);

        return cartItemsResponse;
    }

    @Transactional //객체 변경으로 수정(지영)
    public Orders createOrder(Integer userId) { //주문 결제 후 order_items 의 값을 넣는 경우


        // 사용자 조회
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // 장바구니 아이템 조회 *수정* 유저연결로 객체변환(findByUserID -> findByUsers)
        List<CartItems> cartItems = cartItemsRepository.findByUsers(user);
        if (cartItems.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty");
        }

        // 총 가격 계산
        int totalPrice = cartItems.stream()
                .mapToInt(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        // 주문 번호 생성
        String orderNumber = UUID.randomUUID().toString();

        // 주문 생성
        Orders order = Orders.builder()
                .user(user)
                .totalPrice(totalPrice)
                .orderDate(LocalDateTime.now())
                .orderNumber(orderNumber)
                .build();

        Orders savedOrder = orderRepository.save(order);

        // 주문 아이템 생성 및 저장
        for (CartItems cartItem : cartItems) {
            OrderItems orderItem = OrderItems.builder()
                    .order(savedOrder)
                    .product(cartItem.getProduct())
                    .quantity(cartItem.getQuantity())
                    .payState("결제완료")
                    .build();

            orderItemsRepository.save(orderItem);
        }

        // 장바구니 비우기 -> 유저연결로 객체변환(지영)
        cartRepository.deleteByUsers(user);

        return savedOrder;
    }

    @Transactional
    public Users updateUser(Integer id, String updateName, String updateAddress, String updatePhone, String shippingInfo) {
        return usersRepository.findById(id)
                .map(user -> {
                    Users updatedUser = Users.builder()
                            .id(user.getId())
                            .email(user.getEmail())  // 기존 이메일 유지
                            .name(user.getName()) // 기존 이름 유지
                            .password(user.getPassword()) // 기존 이름 유지
                            .phoneNum(user.getPhoneNum()) // 기존 이름 유지
                            .gender(user.getGender()) // 기존 성별 유지
                            .updateName(updateName != null ? updateName : user.getUpdateName())
                            .updateAddress(updateAddress != null ? updateAddress : user.getUpdateAddress())
                            .updatePhone(updatePhone != null ? updatePhone : user.getUpdatePhone())
                            .shippingInfo(shippingInfo != null ? shippingInfo : user.getShippingInfo())
                            .profilePictureUrl(user.getProfilePictureUrl())
                            .build();
                    return usersRepository.save(updatedUser);
                })
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }


    @Transactional(readOnly = true)
    public BuyOrderRequest getProductById(Integer productId, String size) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            // 사이즈 정보 조회
            List<ProductSizes> sizes = productSizesRepository.findByProductIdAndSize(productId, size);
            String productSize = sizes.stream()
                    .findFirst()
                    .map(ProductSizes::getSize)
                    .orElse("N/A"); // 사이즈 정보가 없으면 "N/A"로 설정

            // 파일 경로 수정
            String filePath = product.getFileUrl().replace("/img/", "");

            return new BuyOrderRequest(
                    product.getId(),
                    product.getName(),
                    product.getColor(),
                    filePath,
                    product.getPrice(),
                    // 실제 값은 구매 수량으로 설정해야 합니다.
                    1, // 예시로 1로 설정
                    productSize, // 사이즈 정보 추가
                    product.getListedDate()
            );
        } else {
            return null; // 상품이 없는 경우 null 반환
        }
    }


    @Transactional
    public Orders createBuyOrder(Integer userId, CreateOrderRequest request) {
        // 사용자 조회
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // 상품 사이즈 조회
        List<ProductSizes> productSizes = productSizesRepository.findByProductIdAndSize(request.getProductId(), request.getSize());
        if (productSizes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product size not found");
        }
        ProductSizes productSize = productSizes.get(0); // 사이즈가 존재할 경우 첫 번째 사이즈 선택

        // 총 가격 계산
        int totalPrice = request.getQuantity() * productSize.getProduct().getPrice();

        // 주문 번호 생성
        String orderNumber = UUID.randomUUID().toString();

        // 주문 생성
        Orders order = Orders.builder()
                .user(user)
                .totalPrice(totalPrice)
                .orderDate(LocalDateTime.now())
                .orderNumber(orderNumber)
                .build();

        Orders savedOrder = orderRepository.save(order);

        // 주문 아이템 생성 및 저장
        OrderItems orderItem = OrderItems.builder()
                .order(savedOrder)
                .product(productSize.getProduct())
                .quantity(request.getQuantity())
                .payState("결제완료")
                .build();

        orderItemsRepository.save(orderItem);

        return savedOrder;
    }


}