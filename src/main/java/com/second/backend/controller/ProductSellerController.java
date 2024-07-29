package com.second.backend.controller;

import com.second.backend.dto.ProductSellerDTO;
import com.second.backend.model.Users;
import com.second.backend.service.ProductSellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sale/{sellerid}")
public class ProductSellerController {
    private final ProductSellerService productSellerService;

    @GetMapping
    public ResponseEntity<List<ProductSellerDTO>> getProductSeller(@PathVariable("sellerid") Integer sellerId) {
        // 판매자 ID를 기반으로 제품 정보를 가져옵니다.
        List<ProductSellerDTO> products = productSellerService.findProductsBySellerId(sellerId);
        // 결과를 ResponseEntity로 반환합니다.
        return ResponseEntity.ok(products);
    }

    @PostMapping("/insert")
    public ResponseEntity<String> InsertProduct(
            @PathVariable Integer sellerid,
            @RequestBody ProductSellerDTO productSellerDTO) {

        productSellerService.saveProductWithSizes(sellerid, productSellerDTO);
        return ResponseEntity.ok("성공적으로 등록되었습니다.");
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeProductAndSizes(@RequestBody ProductSellerDTO productSellerDTO) {
        String responseMessage = productSellerService.removeProductAndSizes(productSellerDTO);
        return ResponseEntity.ok(responseMessage);
    }

    @PutMapping("/revision")
    public ResponseEntity<String> updateProductAndSizes(@RequestBody ProductSellerDTO productSellerDTO){
        String responseMessage =productSellerService.updateProductAndSizes(productSellerDTO);
        return ResponseEntity.ok(responseMessage);
    }
}
