package com.example.tiketku_finalproject.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchTicketRequest {
    @JsonProperty("departure_city")
    private String departureCity;

    @JsonProperty("arrival_city")
    private String arrivalCity;

    @JsonProperty("departure_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date departureDate;

    private int passenger;
}
