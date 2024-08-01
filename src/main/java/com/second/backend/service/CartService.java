package com.second.backend.service;


import com.second.backend.dto.*;
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
    public String addToCart(CartDTO cartDTO) {
      Integer usersid = cartDTO.getUsersid();
      Integer productsizeid = cartDTO.getItemSizeid();

        Carts carts = cartRepository.findByCartUserId(usersid);
        if(carts == null){
            carts = new Carts();
            carts.setUserId(usersid);
            carts = cartRepository.save(carts);
        }
        Integer cartid = carts.getId();
        cartDTO = CartDTO.builder()
                .usersid(usersid)
                .itemSizeid(productsizeid)
                .cartid(cartid)
                .build();

        ProductSizes productSizes = productSizesRepository.findByCartSizeIde(productsizeid);

        Integer productid = productSizes.getProduct().getId();
        String productsize = productSizes.getSize();

        //Product product = productRepository.findById(productid);

        CartItems cartItems = cartItemsRepository.findByCartIdAndProductIdAndItemSizeId(cartid, productid, productsizeid);
        if(cartItems == null){
            cartItems = CartItems.builder()
                    .cart(cartid)
                    .product(productid)
                    .productSizes(productsizeid)
                    .quantity(1) // 초기 수량은 1
                    .build();
        }else {
            cartItems.setQuantity(cartItems.getQuantity() + 1); // 수량 증가
        }
        cartItemsRepository.save(cartItems)

        cartDTO = CartDTO.builder()
                .usersid(userid)
                .itemSizeId(productsizeid)
                .cartId(cartid)
                .productId(productid)
                .itemSize(productsize)
                .fileUrl(product.getFileUrl())
                .name(product.getName())
                .color(product.getColor())
                .price(product.getPrice())
                .quantity(cartItem.getQuantity())
                .build();
    }

    }

    @Transactional //주은추가
    public List<CartResponse> getCartItemsByUserId(Integer userId) {
        try {
            Optional<Carts> optionalCart = cartRepository.findByUserId(userId); // 장바구니 조회
            if (optionalCart.isEmpty()) {
                return Collections.emptyList();
            }
            Carts cart = optionalCart.get();

            List<CartItems> cartItemsList = cartItemsRepository.findByCart(cart);

            List<CartResponse> responses = new ArrayList<>(); // 아이템 정보 구성
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
        } catch (Exception e) {
            e.printStackTrace(); // 로그에 자세한 예외 출력
            throw new RuntimeException("Error fetching cart items", e);
        }
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
