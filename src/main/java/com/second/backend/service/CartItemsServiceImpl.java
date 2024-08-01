package com.second.backend.service;

import com.second.backend.repository.CartItemsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemsServiceImpl implements CartItemsService {

    private final CartItemsRepository cartItemsRepository;

    public CartItemsServiceImpl(CartItemsRepository cartItemsRepository) {
        this.cartItemsRepository = cartItemsRepository;
    }

    @Override
    public void deleteItemsByIds(List<Integer> ids) {
        cartItemsRepository.deleteAllById(ids);
    }
}
