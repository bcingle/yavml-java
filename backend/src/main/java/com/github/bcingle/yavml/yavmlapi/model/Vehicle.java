package com.github.bcingle.yavml.yavmlapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Vehicle {

    @Id
    @GeneratedValue
    private Long id;

    private String name, make, model, trim, vin;

    @ElementCollection
    private List<String> options = new ArrayList<>();

    @ManyToOne
    @JsonBackReference
    private Fleet fleet;

    @ElementCollection
    private List<Document> documents = new ArrayList<>();

    @OneToMany(mappedBy = "vehicle")
    @JsonManagedReference
    private List<Maintenance> maintenanceLog = new ArrayList<>();

    @OneToMany(mappedBy = "vehicle")
    @JsonManagedReference
    private List<Fillup> fillupLog = new ArrayList<>();
}
