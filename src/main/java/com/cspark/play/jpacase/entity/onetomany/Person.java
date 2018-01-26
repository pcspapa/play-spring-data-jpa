package com.cspark.play.jpacase.entity.onetomany;

import com.cspark.play.jpacase.entity.PersonBase;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "otm_person_uni")
public class Person extends PersonBase {

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "person_id")
    private List<Phone> phones = new ArrayList<>();

    public Person() {
    }

    public Person(String name) {
        super(name);
    }

    public Person(Long id) {
        super(id);
    }

    public Person(Long id, String name) {
        super(id, name);
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Person{");
        sb.append("phones=").append(phones);
        sb.append(", id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
