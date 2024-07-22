package com.github.userpage.repository;

import com.github.userpage.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByName(String name);
}
