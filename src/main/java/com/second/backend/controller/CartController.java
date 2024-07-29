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

    @GetMapping("/items")
    public ResponseEntity<List<CartResponse>> getCartItems(@RequestParam Integer userId) {
        try {
            List<CartResponse> cartItems = cartService.getCartItemsByUserId(userId);
            if (cartItems != null) {
                return ResponseEntity.ok(cartItems);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            e.printStackTrace(); // 오류 로그 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
