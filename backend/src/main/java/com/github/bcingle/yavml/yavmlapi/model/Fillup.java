package com.github.bcingle.yavml.yavmlapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Fillup {

    @Id
    @GeneratedValue
    private Long id;

    private String location;

    private long mileage;

    /**
     * Number of gallons or liters
     */
    private int quantity;

    /**
     * price perr gallon or liter
     */
    private long unitPrice;

    @ManyToOne
    @JsonBackReference
    private Vehicle vehicle;

    private ZonedDateTime timestamp;

    @ElementCollection
    @OneToMany
    private List<Document> documents = new ArrayList<>();
}
