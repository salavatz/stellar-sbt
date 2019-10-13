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
@Table(name = "stellarAccounts")
public class StellarAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "publicKey")
    private String publicKey;

    @Column(name = "privateKey")
    private String privateKey;

    @Column(name = "email")
    private String email;

    @Column(name = "balance")
    private double balance;
}
