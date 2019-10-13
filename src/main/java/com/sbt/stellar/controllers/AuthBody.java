package com.sbt.stellar.controllers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthBody {
    private String email;
    private String password;
}
