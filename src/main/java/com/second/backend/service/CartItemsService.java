package com.second.backend.service;
import java.util.List;

public interface CartItemsService {
    void deleteItemsByIds(List<Integer> ids);
}