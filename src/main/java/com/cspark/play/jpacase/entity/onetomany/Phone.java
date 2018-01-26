package com.cspark.play.jpacase.entity.onetomany;

import com.cspark.play.jpacase.entity.PhoneBase;

import javax.persistence.Entity;

@Entity(name = "otm_phone_uni")
public class Phone extends PhoneBase {

    public Phone() {
    }

    public Phone(Long id) {
        super(id);
    }

    public Phone(String number) {
        super(number);
    }

    public Phone(Long id, String number) {
        super(id, number);
    }
}
