package com.baloch.auth.repository;

import com.baloch.auth.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<UserCredentials, String> {
    UserCredentials findByUsername(String username);
}
