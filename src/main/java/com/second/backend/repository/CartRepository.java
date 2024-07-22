package com.second.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.second.backend.model.Carts;

public interface CartRepository  extends JpaRepository<Carts, Integer>{
    Carts findByUsersId(Integer userId);
}
