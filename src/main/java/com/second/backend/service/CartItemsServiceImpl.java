package com.second.backend.service;
import com.second.backend.repository.CartItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemsServiceImpl implements CartItemsService {

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Override
    public void deleteItemsByIds(List<Integer> ids) {
        cartItemsRepository.deleteAllById(ids);
    }
}