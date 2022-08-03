package com.springjwt.repo;

import com.springjwt.entity.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken,Long> {
    @Query(value = "SELECT * FROM users u JOIN reset_password_token r where u._id = r._id and u.username=?1",nativeQuery = true)
    Optional<ResetPasswordToken> findByUser(String username);

    Optional<ResetPasswordToken> findByToken(String token);
}
