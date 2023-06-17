package com.example.tiketku_finalproject.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TempAddTransactionResponse {
    private UUID uuid_user;
    private UUID schedule_uid;
    private int seats_id;
}
