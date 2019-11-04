package com.sbt.stellar.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Account {
    private String publicKey;
    private String secretKey;
    private List<Balance> balance;
}
