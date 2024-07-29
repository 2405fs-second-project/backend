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
import java.util.List;
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
    private final ProductSizesRepository productSizesRepository;

    @Transactional(readOnly = true)
    public UserOrderInfoResponse getUserOrderInfo(Integer userId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return new UserOrderInfoResponse(
                user.getName(),
                user.getPhoneNum(),
                user.getUpdateName(),
                user.getUpdatePhone(),
                user.getUpdateAddress(),
                user.getShippingInfo()
        );
    }

    @Transactional(readOnly = true)
    public List<CartItemsResponse> getCartItems(Integer userId) {
        // 장바구니 조회
        List<CartItems> cartItems = cartItemsRepository.findByUserId(userId);

        // DTO로 변환 및 로그 추가
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

    @Transactional
    public Orders createOrder(Integer userId) {
        // 사용자 조회
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // 장바구니 아이템 조회
        List<CartItems> cartItems = cartItemsRepository.findByUserId(userId);
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

        // 장바구니 비우기
        cartRepository.deleteByUserId(userId);

        return savedOrder;
    }

    @Transactional
    public Users updateUser(Integer id, String updateName, String updateAddress, String updatePhone, String shippingInfo) {
        return usersRepository.findById(id)
                .map(user -> {
                    Users updatedUser = Users.builder()
                            .id(user.getId())
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

}

