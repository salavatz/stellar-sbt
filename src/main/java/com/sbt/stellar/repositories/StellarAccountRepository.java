package com.sbt.stellar.repositories;

import com.sbt.stellar.entities.StellarAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StellarAccountRepository extends JpaRepository<StellarAccount, Long> {
    StellarAccount findByEmail(String email);
}
