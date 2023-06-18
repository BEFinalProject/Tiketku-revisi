package com.example.tiketku_finalproject.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutTransactionResponse {
    private UUID uuid_transaction;
    private String title;
    private String full_name;
    private String given_name;
    private Date birth_date;
    private String id_card;
}
