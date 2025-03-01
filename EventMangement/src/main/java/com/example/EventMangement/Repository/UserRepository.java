package com.example.EventMangement.Repository;

import com.example.EventMangement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findByName(String name);
}

