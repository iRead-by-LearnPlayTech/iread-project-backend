package com.iread.backend.project.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("""
    SELECT t FROM Token t INNER JOIN Teacher u ON t.teacher.id = u.id
    WHERE u.id = :userId AND (t.expired = false OR t.revoked = false)
""")
    List<Token> findAllValidTokensBy(Long userId);

    Optional<Token> findByToken(String token);
}
