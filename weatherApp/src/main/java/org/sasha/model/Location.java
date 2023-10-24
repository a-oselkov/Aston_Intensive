package org.sasha.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
@Entity
@Table(name = "labels")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String region;
    private String country;
    //private String localTime;

//    @CreationTimestamp
//    @Temporal(TIMESTAMP)
//    private Date createdAt;
}
