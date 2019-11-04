package com.sbt.stellar.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stellar_accounts")
public class StellarAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "email")
    private String email;

    @Column(name = "publicKey", unique = true, nullable = false)
    private String publicKey;

    @Column(name = "secretKey", unique = true, nullable = false)
    private String secretKey;

}
