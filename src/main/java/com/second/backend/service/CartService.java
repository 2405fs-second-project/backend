package com.second.backend.service;
import com.second.backend.dto.CartReponse;
import com.second.backend.model.Product;
import com.second.backend.model.Users;
import com.second.backend.dto.CartRequest;
import com.second.backend.model.Carts;
import com.second.backend.model.CartItems;
import com.second.backend.model.ProductSizes;
import com.second.backend.repository.CartItemsRepository;
import com.second.backend.repository.CartRepository;
import com.second.backend.repository.ProductRepository;
import com.second.backend.repository.UsersRepository;
import com.second.backend.repository.ProductSizesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CancellationException;

@Service
public class CartService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductSizesRepository productSizesRepository;

    public String addToCart(CartRequest cartRequest) {
        // 사용자 조회
        Optional<Users> optionalUser = usersRepository.findById(cartRequest.getUserId());
        if (optionalUser.isEmpty()) {
            return "사용자가 없습니다.";
        }
        Users users = optionalUser.get();

        // 장바구니 조회 또는 생성
        Carts carts = cartRepository.findByUsersId(cartRequest.getUserId());
        if (carts == null) {
            carts = new Carts();
            carts.setUser(users);
            cartRepository.save(carts);
        }

        // 상품 검증
        Optional<Product> optionalProduct = productRepository.findById(cartRequest.getProductId());
        if (optionalProduct.isEmpty()) {
            return "판매되는 상품이 아닙니다.";
        }
        Product product = optionalProduct.get();

        // 사이즈 검증
        ProductSizes productSizes = productSizesRepository.findByProductIdAndSize(
                cartRequest.getProductId(),
                cartRequest.getSize()
        );
        if (productSizes == null) {
            return "해당 사이즈의 상품이 없습니다.";
        }

        // 장바구니 아이템 검색
        CartItems existingCartItem = cartItemsRepository.findByCartsAndProductAndProductSizes(
                carts, product, productSizes
        );

        if (existingCartItem != null) {
            // 기존 아이템이 있으면 수량 업데이트
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartRequest.getQuantity());
            cartItemsRepository.save(existingCartItem);
        } else {
            // 새 아이템 추가
            CartItems newCartItem = new CartItems();
            newCartItem.setCarts(carts);
            newCartItem.setUserId(users.getId());
            newCartItem.setProduct(product);
            newCartItem.setProductSizes(productSizes);  // 수정된 부분
            newCartItem.setQuantity(cartRequest.getQuantity());
            cartItemsRepository.save(newCartItem);
        }

        return "장바구니에 추가되었습니다.";}

    public List<CartReponse> getCartItemsByUserId(Integer userId) {
        // 1. 사용자 ID를 통해 카트를 조회하여 카트 ID를 얻는다
        Carts cart = cartRepository.findByUsersId(userId);
        if (cart == null) {
            return Collections.emptyList(); // 카트가 없는 경우 빈 리스트 반환
        }

        // 2. 카트 ID를 통해 카트 아이템을 조회한다
        List<CartItems> cartItemsList = cartItemsRepository.findByCarts(cart); // 수정된 부분

        List<CartReponse> responses = new ArrayList<>();
        for (CartItems cartItems : cartItemsList) {
            Product product = cartItems.getProduct();
            ProductSizes productSizes = cartItems.getProductSizes(); // 수정된 부분

            CartReponse response = new CartReponse();
            response.setId(cartItems.getId());
            response.setName(product.getName());
            response.setColor(product.getColor());
            response.setSize(productSizes.getSize()); // 수정된 부분
            response.setQuantity(cartItems.getQuantity());
            response.setPrice(product.getPrice());

            // 파일 경로 수정
            String filePath = product.getFileUrl().replace("/img/", "");
            response.setFileUrl(filePath);

            responses.add(response);
        }

        return responses;
    }

}
