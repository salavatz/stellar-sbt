package com.sbt.stellar.services;

import com.sbt.stellar.entities.StellarAccount;
import org.stellar.sdk.Asset;
import org.stellar.sdk.KeyPair;

import java.util.Map;

public interface StellarAccountService {
    StellarAccount getStellarAccountByEmail(String email);
    Map<String, Object> generateKeyPair();
    Map<String, Object> requestFreeLumen(String accountId);
    void sendTransaction(Asset asset, KeyPair source, KeyPair destination, String amount, String transactionMemo) ;
}
