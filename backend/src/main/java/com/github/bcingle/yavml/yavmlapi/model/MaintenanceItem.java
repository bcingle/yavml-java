package com.github.bcingle.yavml.yavmlapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class MaintenanceItem {

    @Id
    @GeneratedValue
    private Long id;

    private String description;

    private long basePrice;

    private int quantity;

    @ManyToOne
    @JsonBackReference
    private Maintenance maintenance;
}
