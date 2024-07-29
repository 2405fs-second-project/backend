package com.second.backend.service;

import com.second.backend.dto.CartItemUpdateRequest;
import com.second.backend.dto.CartRequest;
import com.second.backend.dto.CartResponse;
import com.second.backend.model.*;
import com.second.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class CartService { //장바구니 기능 제공

    private final UsersRepository usersRepository;
    private final CartRepository cartRepository;
    private final CartItemsRepository cartItemsRepository;
    private final ProductRepository productRepository;
    private final ProductSizesRepository productSizesRepository;

    @Transactional
    public String addToCart(CartRequest cartRequest) {
        Optional<Users> optionalUser = usersRepository.findById(cartRequest.getUserId()); //사용자 확인
        if (optionalUser.isEmpty()) {
            return "사용자가 없습니다.";
        }
        Users user = optionalUser.get();

        Optional<Carts> optionalCart = cartRepository.findByUserId(cartRequest.getUserId()); //장바구니 확인 및 생성
        Carts cart = optionalCart.orElseGet(() -> {
            Carts newCart = new Carts();
            newCart.setUserId(user.getId());
            return cartRepository.save(newCart);
        });

        Optional<Product> optionalProduct = productRepository.findById(cartRequest.getProductId()); //상품 확인
        if (optionalProduct.isEmpty()) {
            return "판매되는 상품이 아닙니다.";
        }
        Product product = optionalProduct.get();

        ProductSizes productSizes = productSizesRepository.findByProductIdAndSize( //상품사이즈 확인
                cartRequest.getProductId(),
                cartRequest.getSize()
        );
        if (productSizes == null) {
            return "해당 사이즈의 상품이 없습니다.";
        }

        CartItems existingCartItem = cartItemsRepository.findByCartAndProductAndProductSizes( //장바구니 아이템 업데이트
                cart, product, productSizes
        );

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartRequest.getQuantity());
            cartItemsRepository.save(existingCartItem);
        } else {
            CartItems newCartItem = new CartItems();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setProductSizes(productSizes);
            newCartItem.setQuantity(cartRequest.getQuantity());
            newCartItem.setUserId(user.getId());
            cartItemsRepository.save(newCartItem);
        }

        return "장바구니에 추가되었습니다.";
    }

    public List<CartResponse> getCartItemsByUserId(Integer userId) {
        Optional<Carts> optionalCart = cartRepository.findByUserId(userId); //장바구니 조회
        if (optionalCart.isEmpty()) {
            return Collections.emptyList();
        }
        Carts cart = optionalCart.get();

        List<CartItems> cartItemsList = cartItemsRepository.findByCart(cart);

        List<CartResponse> responses = new ArrayList<>(); //아이템 정보구성
        for (CartItems cartItems : cartItemsList) {
            Product product = cartItems.getProduct();
            ProductSizes productSizes = cartItems.getProductSizes();

            CartResponse response = new CartResponse();
            response.setId(cartItems.getId());
            response.setName(product.getName());
            response.setColor(product.getColor());
            response.setSize(productSizes.getSize());
            response.setQuantity(cartItems.getQuantity());
            response.setPrice(product.getPrice());

            String filePath = product.getFileUrl().replace("/img/", "");
            response.setFileUrl(filePath);

            responses.add(response);
        }

        return responses;
    }

    @Transactional
    public CartItems updateCartItemQuantity(CartItemUpdateRequest request) { //장바구니 아이템의 수량 업데이트
        Optional<CartItems> cartItemOptional = cartItemsRepository.findById(request.getItemId());

        if (cartItemOptional.isPresent()) {
            CartItems cartItem = cartItemOptional.get();
            cartItem.setQuantity(request.getQuantity());
            cartItemsRepository.save(cartItem);
            return cartItem; // 업데이트된 장바구니 아이템을 반환
        } else {
            return null; // 아이템을 찾지 못했을 경우 null 반환
        }
    }

    @Transactional
    public String transferCartForLoggedInUser(Integer previousUserId, Integer newUserId) { //로그인 전 비회원의 장바구니 새로운 사용자 계정으로 이전
        Optional<Carts> optionalPreviousCart = cartRepository.findByUserId(previousUserId);
        if (optionalPreviousCart.isEmpty()) {
            return "이전 사용자 장바구니가 없습니다.";
        }
        Carts previousCart = optionalPreviousCart.get();

        Optional<Carts> optionalNewCart = cartRepository.findByUserId(newUserId);
        Carts newCart = optionalNewCart.orElseGet(() -> { //orElseGet은 optional 클래스에서 제공하는 메서드로 비었을때 대체 값을 생성하거나 반환하는데 사용
            Carts cart = new Carts();
            cart.setUserId(newUserId);
            return cartRepository.save(cart);
        });

        List<CartItems> cartItems = previousCart.getCartItems();
        for (CartItems item : cartItems) {
            item.setCart(newCart);
            item.setUserId(newUserId);
        }

        cartItemsRepository.saveAll(cartItems);
        cartRepository.delete(previousCart);

        return "장바구니가 새로운 사용자 계정으로 이전되었습니다.";
    }
}
