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
public class Fleet {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JsonBackReference
    private YavmlUser owner;

    @OneToMany(mappedBy = "fleet")
    @JsonManagedReference
    private List<Vehicle> vehicles = new ArrayList<>();

    @ElementCollection
    @OneToMany
    private List<Document> documents = new ArrayList<>();
}
