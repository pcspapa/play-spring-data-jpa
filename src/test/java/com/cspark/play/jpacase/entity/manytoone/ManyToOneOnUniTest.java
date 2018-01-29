package com.cspark.play.jpacase.entity.manytoone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Rollback(false)
public class ManyToOneOnUniTest {

    @Autowired
    private UniMtoPersonRepository personRepository;

    @Autowired
    private UniMtoPhoneRepository phoneRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void selectPerson() {
        List<Person> people = (List<Person>) personRepository.findAll();
        assertThat(people.size(), is(1));

        Person person = personRepository.findOne(1L);
        assertThat(person.getId(), is(1L));

//         select person0_.id as id1_0_, person0_.name as name2_0_ from mto_person_uni person0_
    }

    @Test
    public void selectPhone() {
        List<Phone> phones = (List<Phone>) phoneRepository.findAll();
        assertThat(phones.size(), is(1));

        Phone phone = phoneRepository.findOne(1L);
        assertThat(phone.getId(), is(1L));

//        select phone0_.id as id1_1_, phone0_.number as number2_1_, phone0_.person_id as person_i3_1_ from mto_phone_uni phone0_
//        select person0_.id as id1_0_0_, person0_.name as name2_0_0_ from mto_person_uni person0_ where person0_.id=?
    }

    @Test
    public void selectPhoneAndTouchPerson() {
        List<Phone> phones = (List<Phone>) phoneRepository.findAll();
        assertThat(phones.size(), is(1));

        Phone phone = phoneRepository.findOne(1L);
        assertThat(phone.getId(), is(1L));
        assertThat(phone.getPerson().getId(), is(1L));

//        select phone0_.id as id1_1_, phone0_.number as number2_1_, phone0_.person_id as person_i3_1_ from mto_phone_uni phone0_
//        select person0_.id as id1_0_0_, person0_.name as name2_0_0_ from mto_person_uni person0_ where person0_.id=?
    }

    @Test
    public void save() {
        Phone phone = new Phone("234-567-8901");
        phone.setPerson(new Person("name"));
        phoneRepository.save(phone);

        // @ManyToOne
        // @ManyToOne(cascade = CascadeType.MERGE)
        // @ManyToOne(cascade = CascadeType.REMOVE)
        // private Person person;
        // Exception

        // @ManyToOne(cascade = CascadeType.ALL)
        // @ManyToOne(cascade = CascadeType.PERSIST)
        // @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        // @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
        // private Person person;
//        Hibernate: insert into mto_person_uni (id, name) values (null, ?)
//        Hibernate: insert into mto_phone_uni (id, number, person_id) values (null, ?, ?)

    }

    @Test
    public void updatePerson() {
        Person person = new Person(1L, "edit name");
        personRepository.save(person);

        // @ManyToOne : cascade 관계 없음.
        // private Person person;
//        update mto_person_uni set name=? where id=?
    }

    @Test
    public void updatePhone() {
        Phone phone = new Phone(1L, "234-567-8901");
        phoneRepository.save(phone);

        // @ManyToOne
        // @ManyToOne(cascade = CascadeType.PERSIST)
        // @ManyToOne(cascade = CascadeType.REMOVE)
        // @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
        // private Person person;
//        select phone0_.id as id1_1_0_, phone0_.number as number2_1_0_, phone0_.person_id as person_i3_1_0_ from mto_phone_uni phone0_ where phone0_.id=1
//        select person0_.id as id1_0_0_, person0_.name as name2_0_0_ from mto_person_uni person0_ where person0_.id=1
//        update mto_phone_uni set number='234-567-8901', person_id=null where id=1

        // @ManyToOne(cascade = CascadeType.ALL)
        // @ManyToOne(cascade = CascadeType.MERGE)
        // @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
        // @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        // private Person person;
//        select phone0_.id as id1_1_1_, phone0_.number as number2_1_1_, phone0_.person_id as person_i3_1_1_, person1_.id as id1_0_0_, person1_.name as name2_0_0_ from mto_phone_uni phone0_ left outer join mto_person_uni person1_ on phone0_.person_id=person1_.id where phone0_.id=1
//        update mto_phone_uni set number='234-567-8901', person_id=null where id=1
    }

    @Test
    public void updatePhoneWithPersonId() {
        Phone phone = new Phone(1L, "234-567-8901");
        phone.setPerson(new Person(1L));
        phoneRepository.save(phone);

        // @ManyToOne
        // @ManyToOne(cascade = CascadeType.PERSIST)
        // @ManyToOne(cascade = CascadeType.REMOVE)
        // @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
        // private Person person;
//        select phone0_.id as id1_1_0_, phone0_.number as number2_1_0_, phone0_.person_id as person_i3_1_0_ from mto_phone_uni phone0_ where phone0_.id=1
//        select person0_.id as id1_0_0_, person0_.name as name2_0_0_ from mto_person_uni person0_ where person0_.id=1
//        update mto_phone_uni set number='234-567-8901', person_id=1 where id=1

        // @ManyToOne(cascade = CascadeType.ALL)
        // @ManyToOne(cascade = CascadeType.MERGE)
        // @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
        // @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
        // private Person person;
//        select phone0_.id as id1_1_1_, phone0_.number as number2_1_1_, phone0_.person_id as person_i3_1_1_, person1_.id as id1_0_0_, person1_.name as name2_0_0_ from mto_phone_uni phone0_ left outer join mto_person_uni person1_ on phone0_.person_id=person1_.id where phone0_.id=1
//        update mto_person_uni set name=null where id=1
//        update mto_phone_uni set number='234-567-8901', person_id=1 where id=1
    }
}