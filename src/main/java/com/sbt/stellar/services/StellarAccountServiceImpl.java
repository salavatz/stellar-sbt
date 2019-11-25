package com.sbt.stellar.services;

import com.sbt.stellar.entities.StellarAccount;
import com.sbt.stellar.repositories.StellarAccountRepository;
import com.sbt.stellar.utils.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stellar.sdk.*;
import org.stellar.sdk.responses.AccountResponse;
import org.stellar.sdk.responses.Response;
import org.stellar.sdk.xdr.XdrDataInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

@Service
public class StellarAccountServiceImpl implements StellarAccountService {
    private String network = "https://horizon-testnet.stellar.org";

    @Autowired
    private StellarAccountRepository stellarAccountRepository;

    @Override
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
    public List<Balance> getAccountBalance(String publicKey) {
        Server server = new Server(network);
        List<AccountResponse.Balance> balances = new ArrayList<>();
        AccountResponse sourceAccount = null;
        KeyPair accountKeyPair = KeyPair.fromAccountId(publicKey);
        try {
            sourceAccount = server.accounts().account(accountKeyPair);
            balances.addAll(Arrays.asList(sourceAccount.getBalances()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Balance> resultBalances = new ArrayList<>();
        for (AccountResponse.Balance b: balances) {
            resultBalances.add(new Balance(b.getAssetType(), b.getAssetCode(), b.getBalance()));
        }
        return resultBalances;
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

    @Override
    public void updateStellarAccount(StellarAccount stellarAccount) {
        stellarAccountRepository.save(stellarAccount);
    }

    @Override
    public StellarAccount createAccount() {
        KeyPair pair = KeyPair.random();
        String friendbotUrl = String.format("https://friendbot.stellar.org/?addr=%s", pair.getAccountId());
        try {
            InputStream response = new URL(friendbotUrl).openStream();
            String body = new Scanner(response, "UTF-8").useDelimiter("\\A").next();
            System.out.println("SUCCESS! You have a new account :)\n" + body);
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StellarAccount resultStellarAccount = new StellarAccount();
        resultStellarAccount.setPublicKey(pair.getAccountId());
        resultStellarAccount.setSecretKey(String.valueOf(pair.getSecretSeed()));
        return resultStellarAccount;
    }

    //1 selling = price buying,
    @Override
    public Map<String, String> createSellOffer(KeyPair sourceKeyPair, Asset selling, Asset buying, String amountSell, String price, String transactionMemo) {
        Map<String, String> status = new HashMap<>();
        Server server = new Server(network);
        AccountResponse sourceAccount = null;
        try {
            sourceAccount = server.accounts().account(sourceKeyPair);
        } catch (IOException e) {
            status.put("status", e.getMessage());
            return status;
        }
        Transaction transaction = new Transaction.Builder(sourceAccount, Network.TESTNET)
                .addOperation(new ManageSellOfferOperation.Builder(selling, buying, amountSell, price)
                        .setOfferId(0).build())
                .setOperationFee(100)
                .addMemo(Memo.text(transactionMemo)).setTimeout(1000).build();
        transaction.sign(sourceKeyPair);
        try {
            Response response = server.submitTransaction(transaction);
            System.out.println(response);
            status.put("status", "success");
        } catch (IOException e) {
            status.put("status", e.getMessage());
            return status;
        }
        return status;
    }

}
