package com.second.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.second.backend.model.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByName(String name);
}
