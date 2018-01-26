package com.cspark.play.jpacase.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class PersonBase {

    @Id
    @GeneratedValue
    protected Long id;

    protected String name;

    public PersonBase() {
    }

    public PersonBase(Long id) {
        this.id = id;
    }

    public PersonBase(String name) {
        this.name = name;
    }

    public PersonBase(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
