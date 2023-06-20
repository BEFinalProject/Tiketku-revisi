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
//@Table(name = "terminals")
//public class TerminalsEntity {
//    @Id
//    private int terminal_id;
//    private String terminal_name;
//    @JsonIgnore
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime createdAt;
//    @JsonIgnore
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @UpdateTimestamp
//    private LocalDateTime modifiedAt;
//}
