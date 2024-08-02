package com.second.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.second.backend.dto.*;
import com.second.backend.model.*;
import com.second.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemsRepository cartItemsRepository;
    private final ProductRepository productRepository;
    private final ProductSizesRepository productSizesRepository;
    private final UsersService usersService;
    private final UsersRepository usersRepository;
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);
    //1. 장바구니 추가 메서드
    public void addToCart(CartDTO cartDTO) {
        Integer UserId = cartDTO.getUsersid();
        Integer ProductSizeId = cartDTO.getItemSizeid();

        //1.Carts테이블에서 userid값 찾기
        Carts carts = cartRepository.findByUsers_Id(UserId);
        Users users = usersService.findById(UserId);
        if (carts == null) {

            carts = Carts.builder()
                    .users(users)
                    .build();
            cartRepository.save(carts);
        }
        // 2. productSizes테이블에서 prodocutid값 찾기
        ProductSizes productSizes = productSizesRepository.findProductSizesById(ProductSizeId);

        // 3. 찾은 값에서 productid값 추출
        Integer productId = productSizes.getProduct().getId();
        Product product = productRepository.findProductById(productId);


// 4. Check if CartItem exists
        List<CartItems> cartItems = cartItemsRepository.findByCartId(carts.getId());

        boolean itemFound = false;

        for (CartItems item : cartItems) {
            if (item.getProduct().getId().equals(product.getId()) &&
                    item.getProductSizes().getId().equals(productSizes.getId())) {
                item.setQuantity(item.getQuantity() + 1);
                cartItemsRepository.save(item);
                itemFound = true;
                break;
            }
        }
        if (!itemFound) {
            CartItems newCartItem = CartItems.builder()
                    .cart(carts)
                    .product(product)
                    .quantity(1)
                    .productSizes(productSizes)
                    .users(users)
                    .build();
            cartItemsRepository.save(newCartItem);
        }
    }

    //2. 장바구니 조회 메서드
    public List<CartDTO> getCartItems(Integer userId) {
        {
            // 1. 사용자를 기반으로 장바구니를 조회
            Users users = usersService.findById(userId);
            Optional<Carts> cartsList = cartRepository.findByUsers(users);

            if (cartsList.isEmpty()) {
                return Collections.emptyList(); // 장바구니가 없는 경우 빈 리스트 반환
            }

            // 첫 번째 장바구니를 가져옵니다 (여러 개일 수 있으므로 필요에 따라 조정)
            Carts cart = cartsList.get();

            // 2. 장바구니 아이템을 조회
            List<CartItems> cartItemsList = cartItemsRepository.findByCart(cart);

            // 3. 각 CartItem을 CartDTO로 변환
            return cartItemsList.stream().map(cartItem -> {
                Product product = cartItem.getProduct();
                ProductSizes productSizes = cartItem.getProductSizes();

                // CartDTO 빌더 패턴을 사용하여 생성
                return CartDTO.builder()
                        .id(cartItem.getId())
                        .itemSizeid(productSizes.getId())
                        .usersid(cartItem.getUsers().getId())
                        .cartid(cartItem.getCart().getId())
                        .itemid(product.getId())
                        .itemUrl(product.getFileUrl())
                        .itemName(product.getName())
                        .itemColor(product.getColor())
                        .itemPrice(product.getPrice())
                        .itemQuantity(cartItem.getQuantity())
                        .itemSize(productSizes.getSize())
                        .build();
            }).collect(Collectors.toList());
        }

    }

    //3. 장바구니 삭제 메서드
    public ResponseEntity<String> deleteCartItems (List<CartDTO> deleteCartDTO) {
        // 1. deleteCartDTO에서 id 값 추출
        List<Integer> idsToDelete = deleteCartDTO.stream()
                .map(CartDTO::getId) // CartDTO에서 itemid 추출
                .collect(Collectors.toList());

        logger.info("IDs to delete: " + idsToDelete);

        // 2. cartItemsRepository에서 해당 아이템들을 찾아 삭제
        if (!idsToDelete.isEmpty()) {
            List<CartItems> itemsToDelete = cartItemsRepository.findAllById(idsToDelete);
            logger.info("Items to delete: " + itemsToDelete);
            if (!itemsToDelete.isEmpty()) {
                cartItemsRepository.deleteAll(itemsToDelete);
                logger.info("Items deleted successfully.");
                return ResponseEntity.ok("삭제되었습니다.");
            } else {
                logger.warn("No items found for given IDs.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제할 항목이 없습니다.");
            }
        } else {
            logger.warn("No IDs found to delete.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제할 항목이 없습니다.");
        }

    }


    //2. 장바구니 수량 수정 메서드
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

}

