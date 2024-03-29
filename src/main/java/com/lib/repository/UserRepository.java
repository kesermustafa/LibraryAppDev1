package com.lib.repository;

import com.lib.domain.Role;
import com.lib.domain.User;
import com.lib.domain.enums.RoleType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    boolean existsByEmail(String email);

    Optional<User> findUserById(Long id);
    @EntityGraph(attributePaths = "roles")
    Optional<User> findByEmail(String email);


    @EntityGraph(attributePaths = "roles") // 2 query
    List<User> findAll();  // 4 query

}
