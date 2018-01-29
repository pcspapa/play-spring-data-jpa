package com.cspark.play.jpacase.entity.onetomany.bi;

import com.cspark.play.jpacase.entity.PhoneBase;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "otm_phone_bi")
public class Phone extends PhoneBase {

    @ManyToOne
    @JoinColumn(name = "person_id",
            foreignKey = @ForeignKey(name = "OTM_PERSON_BI_ID_FK")
    )
    private Person person;

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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        Phone phone = (Phone) o;
        return Objects.equals( number, phone.number );
    }

    @Override
    public int hashCode() {
        return Objects.hash( number );
    }
}
