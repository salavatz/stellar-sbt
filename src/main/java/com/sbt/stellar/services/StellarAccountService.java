package com.sbt.stellar.services;

import com.sbt.stellar.entities.StellarAccount;
import com.sbt.stellar.utils.Balance;
import org.stellar.sdk.Asset;
import org.stellar.sdk.KeyPair;

import java.util.List;
import java.util.Map;

public interface StellarAccountService {
    Map<String, Object> generateKeyPair();
    Map<String, Object> requestFreeLumen(String accountId);
    List<Balance> getAccountBalance(String publicKey);
    StellarAccount getStellarAccountByEmail(String email);
    void sendTransaction(Asset asset, KeyPair source, KeyPair destination, String amount, String transactionMemo);
    void updateStellarAccount(StellarAccount stellarAccount);
    StellarAccount createAccount();
}
