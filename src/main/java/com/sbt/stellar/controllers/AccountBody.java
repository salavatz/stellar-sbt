package com.sbt.stellar.controllers;

import com.sbt.stellar.utils.Balance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.stellar.sdk.responses.AccountResponse;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountBody {
    private String publicKey;
    private String secretKey;
    private List<Balance> balance;
}
