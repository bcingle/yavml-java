package com.github.bcingle.yavml.yavmlapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Maintenance {

    @Id
    @GeneratedValue
    private Long id;

    @ElementCollection
    private List<String> parts = new ArrayList<>();

    @ElementCollection
    private List<String> services = new ArrayList<>();

    private long mileage;

    @ManyToOne
    @JsonBackReference
    private Vehicle vehicle;

    @OneToMany(mappedBy = "maintenance")
    @JsonManagedReference
    private List<MaintenanceItem> maintenanceItems = new ArrayList<>();

    @ElementCollection
    @OneToMany
    private List<Document> documents = new ArrayList<>();

    private ZonedDateTime timestamp;
}
