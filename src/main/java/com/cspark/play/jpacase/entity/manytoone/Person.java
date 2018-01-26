package com.cspark.play.jpacase.entity.manytoone;

import com.cspark.play.jpacase.entity.PersonBase;

import javax.persistence.Entity;

@Entity(name = "mto_person_uni")
public class Person extends PersonBase {
    public Person() {
        super();
    }

    public Person(Long id) {
        super(id);
    }

    public Person(String name) {
        super(name);
    }

    public Person(Long id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Person{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
