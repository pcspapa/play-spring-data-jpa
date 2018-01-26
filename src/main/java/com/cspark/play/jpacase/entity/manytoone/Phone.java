package com.cspark.play.jpacase.entity.manytoone;

import com.cspark.play.jpacase.entity.PhoneBase;

import javax.persistence.*;

@Entity(name = "mto_phone_uni")
public class Phone extends PhoneBase {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "person_id",
            foreignKey = @ForeignKey(name = "OTM_PERSON_UNI_ID_FK")
    )
    private Person person;

    public Phone() {
        super();
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
    public String toString() {
        final StringBuffer sb = new StringBuffer("Phone{");
        sb.append("person=").append(person);
        sb.append(", id=").append(id);
        sb.append(", number='").append(number).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
