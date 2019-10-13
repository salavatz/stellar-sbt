package com.sbt.stellar.controllers;

import com.sbt.stellar.configs.JwtTokenProvider;
import com.sbt.stellar.entities.Transaction;
import com.sbt.stellar.entities.User;
import com.sbt.stellar.repositories.TransactionRepository;
import com.sbt.stellar.services.StellarAccountService;
import com.sbt.stellar.services.UserService;
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

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            throw new BadCredentialsException("Invalid email/password supplied");
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User user) {
        User userExists = userService.getUserByEmail(user.getEmail());
        if (userExists != null) {
            throw new BadCredentialsException("User with username: " + user.getEmail() + " already exists");
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
        System.out.println(10123);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        System.out.println("name = " + auth.getName());
        transactionRepository.save(transaction);
        KeyPair recipient  = KeyPair.fromAccountId(transaction.getRecipient());
        KeyPair sender = KeyPair.fromSecretSeed("SBGVAT5JYGEQ2LWPLOCNNIU27LNBC7N24RXGYHGNBAXNDPRINBQSRDOI");
        //stellarAccountService.getStellarAccountByEmail(auth.getName()).getKeypair()
        stellarAccountService.sendTransaction(new AssetTypeNative(), sender, recipient, transaction.getAmount(), "description");
    }

    @GetMapping("/user/account")
    public AccountBody getAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        return new AccountBody(user.getPublicKey(), user.getSecretKey());
    }

    @PostMapping("/user/add_account")
    public void addAccount(@RequestBody AccountBody accountBody) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        userService.updateUser(email, accountBody.getPublicKey(), accountBody.getSecretKey());
    }

}
