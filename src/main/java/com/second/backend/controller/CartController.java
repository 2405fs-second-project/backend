package com.second.backend.controller;
import com.second.backend.dto.CartReponse;
import com.second.backend.dto.CartRequest;
import com.second.backend.service.CartItemsService;
import com.second.backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartItemsService cartItemsService;
    @Autowired
    private CartService cartService;


    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartRequest cartRequest) {
        String response = cartService.addToCart(cartRequest);
        if (response.equals("장바구니에 추가되었습니다.")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/items")
    public List<CartReponse> getCartItems(@RequestParam Integer userId) {
        return cartService.getCartItemsByUserId(userId);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCartItems(@RequestBody Map<String, List<Integer>> body) {
        List<Integer> ids = body.get("ids");
        cartItemsService.deleteItemsByIds(ids);
        return ResponseEntity.noContent().build();
    }
}
