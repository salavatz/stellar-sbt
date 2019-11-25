package com.sbt.stellar.utils;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Offer {
    private String selling;
    private String buying;
    private String amount;
    private String price;
    private String memo;
    private Date date;
}
