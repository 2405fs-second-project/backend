package com.second.backend.controller;

import com.second.backend.dto.CartDTO;
import com.second.backend.dto.CartItemUpdateRequest;
import com.second.backend.dto.CartResponse;
import com.second.backend.model.CartItems;
import com.second.backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart") //api 추가(지영)
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartDTO cartDTO) {
        cartService.addToCart(cartDTO);
            return ResponseEntity.ok("장바구니 등록에 성공하였습니다!");

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
