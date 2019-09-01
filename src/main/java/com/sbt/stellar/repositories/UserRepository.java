package com.sbt.stellar.repositories;

import com.sbt.stellar.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}