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
//
//@Entity
//@Data
//@Table(name = "gates")
//public class GatesEntity {
//    @Id
//    private int gate_id;
//    private String gate_name;
//    @JsonIgnore
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime createdAt;
//    @JsonIgnore
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @UpdateTimestamp
//    private LocalDateTime modifiedAt;
//    private int terminal_id;
//}
