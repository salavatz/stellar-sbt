package com.sbt.stellar.services;

import com.sbt.stellar.entities.StellarAccount;
import com.sbt.stellar.repositories.StellarAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stellar.sdk.*;
import org.stellar.sdk.responses.AccountResponse;

import java.io.IOException;
import java.util.Map;

@Service
public class StellarAccountServiceImpl implements StellarAccountService {
    private String network = "https://horizon-testnet.stellar.org";

    @Autowired
    private StellarAccountRepository stellarAccountRepository;

    public StellarAccount getStellarAccountByEmail(String email) {
        return stellarAccountRepository.findByEmail(email);
    }

    @Override
    public Map<String, Object> generateKeyPair() {
        return null;
    }

    @Override
    public Map<String, Object> requestFreeLumen(String accountId) {
        return null;
    }

    @Override
    public void sendTransaction(Asset asset, KeyPair sourceKeyPair, KeyPair destinationKeyPair, String amount, String transactionMemo) {
        Server server = new Server(network);
        AccountResponse sourceAccount = null;
        try {
            server.accounts().account(destinationKeyPair);
            sourceAccount = server.accounts().account(sourceKeyPair);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Transaction transaction = new Transaction.Builder(sourceAccount, Network.TESTNET)
                .addOperation(new PaymentOperation.Builder(destinationKeyPair, asset, amount).build())
                .setOperationFee(120)
                .addMemo(Memo.text(transactionMemo)).setTimeout(1000).build();
        transaction.sign(sourceKeyPair);

        try {
            server.submitTransaction(transaction);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
