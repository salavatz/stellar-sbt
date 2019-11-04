package com.sbt.stellar.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Balance {
    private String assetType;
    private String assetCode;
    private String balance;
}
