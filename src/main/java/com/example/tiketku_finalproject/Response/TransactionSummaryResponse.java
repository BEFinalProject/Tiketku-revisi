package com.example.tiketku_finalproject.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionSummaryResponse {
    private Long Total_Price;
    private int Total_Passanger;
}
