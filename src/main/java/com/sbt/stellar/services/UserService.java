package com.sbt.stellar.services;

import com.sbt.stellar.entities.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserByEmail(String email);
    void saveUser(User user);
    void updateUser(String email, String publicKey, String secretKey);
}
