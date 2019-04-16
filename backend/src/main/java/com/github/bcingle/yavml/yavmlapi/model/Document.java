package com.github.bcingle.yavml.yavmlapi.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.net.URI;

@Data
@Entity
public class Document {

    @Id
    @GeneratedValue
    private Long id;

    private String name, mimeType;

    private URI data;
}
