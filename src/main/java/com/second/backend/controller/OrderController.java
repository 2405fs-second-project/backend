package com.second.backend.controller;

import com.second.backend.dto.*;
import com.second.backend.model.Orders;
import com.second.backend.model.Users;
import com.second.backend.service.OrderService;
import com.second.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/user-info/{userId}")
    public ResponseEntity<UserOrderInfoResponse> getUserOrderInfo(@PathVariable Integer userId) {
        UserOrderInfoResponse userOrderInfo = orderService.getUserOrderInfo(userId);
        return ResponseEntity.ok(userOrderInfo);
    }

    @GetMapping("/cart-items/{userId}") //장바구니에서 구매할 경우
    public ResponseEntity<List<CartItemsResponse>> getCartItems(@PathVariable Integer userId) {
        List<CartItemsResponse> cartItems = orderService.getCartItems(userId);
        if (cartItems.isEmpty()) {
            return ResponseEntity.noContent().build(); // 데이터가 없을 때 204 No Content 응답
        }
        return ResponseEntity.ok(cartItems); // 정상적으로 데이터를 반환
    }

    @GetMapping("/buy-item/{productId}")
    public ResponseEntity<BuyOrderRequest> getProduct(
            @PathVariable Integer productId,
            @RequestParam(required = false) String size) {
        System.out.println("Received size parameter: " + size); // 로그 추가
        BuyOrderRequest product = orderService.getProductById(productId, size);

        if (size == null || size.equals("undefined")) {
            return ResponseEntity.badRequest().body(null); // 적절한 에러 응답을 반환
        }

        return ResponseEntity.ok(product);
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<ViewOrderResponse> createOrder(@PathVariable Integer userId) {
        try {
            Orders order = orderService.createOrder(userId);
            ViewOrderResponse viewOrderResponse = new ViewOrderResponse(
                    order.getUser().getId(),
                    order.getTotalPrice(),
                    order.getOrderNumber(),
                    order.getOrderDate()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(viewOrderResponse);
        } catch (ResponseStatusException e) {
            // 예외가 발생한 경우 상태 코드와 메시지를 직접 설정
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 기본 상태 코드
            if (e.getReason() != null) {
                // 예외 이유를 기반으로 상태 코드를 설정
                status = HttpStatus.BAD_REQUEST;
            }
            return ResponseEntity.status(status).body(null);
        }
    }

    @PostMapping("/direct/{userId}")
    public ResponseEntity<ViewOrderResponse> createDirectOrder(
            @PathVariable Integer userId,
            @RequestBody CreateOrderRequest request) {
        try {
            Orders order = orderService.createBuyOrder(userId, request);
            ViewOrderResponse viewOrderResponse = new ViewOrderResponse(
                    order.getUser().getId(),
                    order.getTotalPrice(),
                    order.getOrderNumber(),
                    order.getOrderDate()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(viewOrderResponse);
        } catch (ResponseStatusException e) {
            System.err.println("Error creating order: " + e.getMessage());
            // BAD_REQUEST 상태 코드로 응답
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            // INTERNAL_SERVER_ERROR 상태 코드로 응답
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PostMapping("/{id}/shipping")
    public ResponseEntity<Users> updateUser(@PathVariable Integer id, @RequestBody UpdateUserRequest request) {
        try {
            Users user = orderService.updateUser(
                    id,
                    request.getUpdateName(),
                    request.getUpdateAddress(),
                    request.getUpdatePhone(),
                    request.getShippingInfo()
            );
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (NullPointerException e) {
            System.err.println("NullPointerException: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (DataAccessException e) {
            System.err.println("Database access error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            e.printStackTrace(); // 예외 스택 트레이스를 출력하여 문제를 파악
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}