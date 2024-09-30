package com.demo.authentication_service.repository;

import com.demo.authentication_service.entity.UserCredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredentialsEntity,Integer> {

    Optional<UserCredentialsEntity> findByName(String name);
}
