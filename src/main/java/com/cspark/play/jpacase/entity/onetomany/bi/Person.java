package com.cspark.play.jpacase.entity.onetomany.bi;

import com.cspark.play.jpacase.entity.PersonBase;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "otm_person_bi")
public class Person extends PersonBase {

    @OneToMany(mappedBy = "person", cascade = CascadeType.REMOVE)
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

    public void addPhone(Phone phone) {
        phones.add( phone );
        phone.setPerson(this );
    }

    public void removePhone(Phone phone) {
        phones.remove( phone );
        phone.setPerson( null );
    }


}
