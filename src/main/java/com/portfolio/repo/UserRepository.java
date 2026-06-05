package com.portfolio.repo;

import com.portfolio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String Email);

    boolean existsById(Long AuthorId);

    Optional<Object> findByUsername(String username);

}
