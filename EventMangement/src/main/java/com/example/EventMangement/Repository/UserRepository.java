package com.example.EventMangement.Repository;

import com.example.EventMangement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>{
}

