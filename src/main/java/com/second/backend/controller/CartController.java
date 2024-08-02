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

    @GetMapping("/items/{id}")//지영 추가
    public ResponseEntity<List<CartDTO>> getCartItems(@PathVariable Integer id) {
        List<CartDTO> cartDTOs = cartService.getCartItems(id);
            return ResponseEntity.ok(cartDTOs);

    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCartItems(@RequestBody List<CartDTO> deleteCartDTO) {
        return cartService.deleteCartItems(deleteCartDTO);
    }

    @PutMapping("/quantity")
    public ResponseEntity<String> updateQuantity(@RequestBody CartDTO cartDTO) {
        String result = cartService.updateQuantity(cartDTO.getId(), cartDTO.getItemQuantity());
        if (result.equals("Quantity updated successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}
