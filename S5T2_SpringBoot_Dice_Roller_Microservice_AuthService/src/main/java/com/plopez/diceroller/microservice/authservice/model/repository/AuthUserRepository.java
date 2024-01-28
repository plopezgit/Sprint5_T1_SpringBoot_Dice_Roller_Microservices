package com.plopez.diceroller.microservice.authservice.model.repository;

import com.plopez.diceroller.microservice.authservice.model.entity.AuthUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@SuppressWarnings("unused")
@Repository
public interface AuthUserRepository extends MongoRepository<AuthUser, Integer> {
    Optional<AuthUser> findByUserName(String username);
}
