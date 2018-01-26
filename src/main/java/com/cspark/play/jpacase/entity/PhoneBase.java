package com.cspark.play.jpacase.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class PhoneBase {

    @Id
    @GeneratedValue
    protected Long id;

    protected String number;

    public PhoneBase() {
    }

    public PhoneBase(Long id) {
        this.id = id;
    }

    public PhoneBase(String number) {
        this.number = number;
    }

    public PhoneBase(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

}
