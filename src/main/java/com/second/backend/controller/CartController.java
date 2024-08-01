package com.second.backend.controller;

import com.second.backend.dto.CartItemUpdateRequest;
import com.second.backend.dto.CartResponse;
import com.second.backend.dto.CartRequest;
import com.second.backend.model.CartItems;
import com.second.backend.service.CartItemsService;
import com.second.backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartItemsService cartItemsService;
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartRequest cartRequest) {
        String response = cartService.addToCart(cartRequest);
        if ("장바구니에 추가되었습니다.".equals(response)) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/items") //주은추가
    public ResponseEntity<?> getCartItems(@RequestParam Integer userId) {
        try {
            List<CartResponse> cartItems = cartService.getCartItemsByUserId(userId);
            if (cartItems != null && !cartItems.isEmpty()) {
                return ResponseEntity.ok(cartItems);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No cart items found for user ID: " + userId);
            }
        } catch (Exception e) {
            e.printStackTrace(); // 로그에 자세한 예외 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching cart items: " + e.getMessage());
        }
    }



    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCartItems(@RequestBody Map<String, List<Integer>> body) {
        List<Integer> ids = body.get("ids");
        cartItemsService.deleteItemsByIds(ids);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/updateQuantity")
    public ResponseEntity<CartItems> updateQuantity(@RequestBody CartItemUpdateRequest request) {
        CartItems updatedCartItem = cartService.updateCartItemQuantity(request);
        if (updatedCartItem != null) {
            return ResponseEntity.ok(updatedCartItem);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
