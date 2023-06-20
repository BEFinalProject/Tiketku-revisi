//package com.arj.tiketkufinalproject.Model;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.Data;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Entity
//@Data
//@Table(name = "routes")
//public class RoutesEntity {
//    @Id
//    private UUID route_uid;
//    private String departure_city;
//    private String arrival_city;
//    private String departure_airport;
//    private String arrival_airport;
//    private String departure_terminal;
//    private String arrival_terminal;
//    @JsonIgnore
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime createdAt;
//    @JsonIgnore
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @UpdateTimestamp
//    private LocalDateTime modifiedAt;
//}
