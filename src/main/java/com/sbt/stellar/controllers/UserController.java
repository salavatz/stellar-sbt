package com.sbt.stellar.controllers;

import com.sbt.stellar.configs.JwtTokenProvider;
import com.sbt.stellar.entities.StellarAccount;
import com.sbt.stellar.entities.Transaction;
import com.sbt.stellar.entities.User;
import com.sbt.stellar.exceptions.UserAlreadyExistsException;
import com.sbt.stellar.repositories.TransactionRepository;
import com.sbt.stellar.services.StellarAccountService;
import com.sbt.stellar.services.UserService;
import com.sbt.stellar.utils.Account;
import com.sbt.stellar.utils.Balance;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.stellar.sdk.AssetTypeNative;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.responses.AccountResponse;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private StellarAccountService stellarAccountService;

    private TransactionRepository transactionRepository;

    public UserController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @GetMapping(value = "/users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/users")
    public void addUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    @RequestMapping("/login")
    public ResponseEntity login(@RequestBody AuthBody user) {
        try {
            String username = user.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, user.getPassword()));
            String token = jwtTokenProvider.createToken(username, userService.getUserByEmail(username).getRoles());
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Username or password is incorrect");
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User user) {
        User userExists = userService.getUserByEmail(user.getEmail());
        if (userExists != null) {
            throw new UserAlreadyExistsException("User with username: " + user.getEmail() + " already exists");
        }
        userService.saveUser(user);
        Map<Object, Object> model = new HashMap<>();
        model.put("message", "User registered successfully");
        return ok(model);
    }

    @RequestMapping("/user")
    public Principal user(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization")
                .substring("Basic".length()).trim();
        return () -> new String(Base64.getDecoder().decode(authToken)).split(":")[0];
    }

    @GetMapping(value = "/transactions")
    public List<User> gets() {
        return userService.getAllUsers();
    }

    @PostMapping("/transactions")
    public void sendTransaction(@RequestBody Transaction transaction) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        StellarAccount stellarAccount = stellarAccountService.getStellarAccountByEmail(auth.getName());
        transactionRepository.save(transaction);
        KeyPair sender = KeyPair.fromSecretSeed(stellarAccount.getSecretKey());
        KeyPair receiver  = KeyPair.fromAccountId(transaction.getReceiver());
        stellarAccountService.sendTransaction(new AssetTypeNative(), sender, receiver, transaction.getAmount(), "description");
    }

    @GetMapping("/user/account")
    public Account getAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        StellarAccount stellarAccount = stellarAccountService.getStellarAccountByEmail(auth.getName());
        if (stellarAccount == null) {
            return null;
        }
        List<Balance> balance = stellarAccountService.getAccountBalance(stellarAccount.getPublicKey());
        //List<Balance> stub = new ArrayList<>();
        //stub.add(new Balance("1","2","3"));
        return new Account(stellarAccount.getPublicKey(), stellarAccount.getSecretKey(), balance);
    }

    @GetMapping("/user/create")
    public void createAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        StellarAccount stellarAccount = stellarAccountService.createAccount();
        stellarAccount.setEmail(email);
        stellarAccountService.updateStellarAccount(stellarAccount);
    }

    @PostMapping("/user/add_account")
    public void addAccount(@RequestBody StellarAccount stellarAccount) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        stellarAccount.setEmail(email);
        stellarAccountService.updateStellarAccount(stellarAccount);
    }

}
