package com.cspark.play.jpacase.entity.onetomany.bi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Rollback(false)
public class OneToManyOnBiTest {

    @Autowired
    private BiOtmPersonRepository personRepository;

    @Autowired
    private BiOtmPhoneRepository phoneRepository;

    @Test
    public void select() {
        List<Person> people = (List<Person>) personRepository.findAll();
        assertThat(people.size(), is(1));

        Person person = personRepository.findOne(1L);
        assertThat(person.getId(), is(1L));

        // @OneToMany(mappedBy = "person")
        // private List<Phone> phones = new ArrayList<>();
        // @ManyToOne
        // private Person person;
        // select person0_.id as id1_5_, person0_.name as name2_5_ from otm_person_bi person0_
    }

    @Test
    public void selectPersonAndTouchPhone() {
        List<Person> people = (List<Person>) personRepository.findAll();
        assertThat(people.size(), is(1));

        Person person = personRepository.findOne(1L);
        assertThat(person.getId(), is(1L));
        assertThat(person.getPhones().size(), is(1));

        // @OneToMany(mappedBy = "person")
        // private List<Phone> phones = new ArrayList<>();
        // @ManyToOne
        // private Person person;
        // select person0_.id as id1_5_, person0_.name as name2_5_ from otm_person_bi person0_
    }

    @Test
    public void selectPhone() {
        List<Phone> phones = (List<Phone>) phoneRepository.findAll();
        assertThat(phones.size(), is(1));

        Phone phone = phoneRepository.findOne(1L);
        assertThat(phone.getId(), is(1L));

        // @OneToMany(mappedBy = "person")
        // private List<Phone> phones = new ArrayList<>();
        // @ManyToOne
        // private Person person;
        // select phone0_.id as id1_7_, phone0_.number as number2_7_, phone0_.person_id as person_i3_7_ from otm_phone_bi phone0_
        // select person0_.id as id1_5_0_, person0_.name as name2_5_0_ from otm_person_bi person0_ where person0_.id=?
    }

    @Test
    public void save() {
        Person person = new Person("name");
        person.addPhone(new Phone("234-567-8901"));

        personRepository.save(person);
        assertThat(person.getId(), is(notNullValue()));
        assertThat(person.getPhones().get(0).getId(), is(notNullValue()));

        // @OneToMany(mappedBy = "person")
        // @OneToMany(mappedBy = "person", cascade = CascadeType.MERGE)
        // @OneToMany(mappedBy = "person", cascade = CascadeType.REMOVE)
        // private List<Phone> phones = new ArrayList<>();
        // @ManyToOne
        // private Person person;
        // insert into otm_person_bi (id, name) values (null, 'name')
        // Exception

        // @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
        // @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST)
        // private List<Phone> phones = new ArrayList<>();
        // @ManyToOne
        // private Person person;
        // insert into otm_person_bi (id, name) values (null, 'name')
        // insert into otm_phone_bi (id, number, person_id) values (null, '234-567-8901', 2)
    }

    @Test
    public void updatePerson() {
        Person person = new Person(1L, "edit name");
        personRepository.save(person);

        // @OneToMany(mappedBy = "person")
        // @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
        // @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST)
        // @OneToMany(mappedBy = "person", cascade = CascadeType.MERGE)
        // @OneToMany(mappedBy = "person", cascade = CascadeType.REMOVE)
        // private List<Phone> phones = new ArrayList<>();
        // @ManyToOne
        // private Person person;
        // select person0_.id as id1_5_0_, person0_.name as name2_5_0_ from otm_person_bi person0_ where person0_.id=1
        // update otm_person_bi set name='edit name' where id=1
    }

    @Test
    public void updatePersonWithPhone() {
        Person person = new Person(1L, "edit name");
        person.addPhone(new Phone(1L, "234-567-8901"));
        personRepository.save(person);

        // @OneToMany(mappedBy = "person")
        // @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST)
        // @OneToMany(mappedBy = "person", cascade = CascadeType.REMOVE)
        // private List<Phone> phones = new ArrayList<>();
        // @ManyToOne
        // private Person person;
        // select person0_.id as id1_5_0_, person0_.name as name2_5_0_ from otm_person_bi person0_ where person0_.id=1
        // select phone0_.id as id1_7_0_, phone0_.number as number2_7_0_, phone0_.person_id as person_i3_7_0_, person1_.id as id1_5_1_, person1_.name as name2_5_1_ from otm_phone_bi phone0_ left outer join otm_person_bi person1_ on phone0_.person_id=person1_.id where phone0_.id=1
        // update otm_person_bi set name='edit name' where id=1

        // @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
        // @OneToMany(mappedBy = "person", cascade = CascadeType.MERGE)
        // private List<Phone> phones = new ArrayList<>();
        // @ManyToOne
        // private Person person;
        // select person0_.id as id1_5_1_, person0_.name as name2_5_1_, phones1_.person_id as person_i3_7_3_, phones1_.id as id1_7_3_, phones1_.id as id1_7_0_, phones1_.number as number2_7_0_, phones1_.person_id as person_i3_7_0_ from otm_person_bi person0_ left outer join otm_phone_bi phones1_ on person0_.id=phones1_.person_id where person0_.id=1
        // update otm_phone_bi set number='234-567-8901', person_id=1 where id=1
        // update otm_person_bi set name='edit name' where id=1
    }

    @Test
    public void updatePhone() {
        Phone phone = new Phone(1L, "234-567-8901");
        phoneRepository.save(phone);

        // @OneToMany(mappedBy = "person")
        // private List<Phone> phones = new ArrayList<>();
        // @ManyToOne
        // @ManyToOne(cascade = CascadeType.PERSIST)
        // @ManyToOne(cascade = CascadeType.REMOVE)
        // private Person person;
        // select phone0_.id as id1_7_0_, phone0_.number as number2_7_0_, phone0_.person_id as person_i3_7_0_ from otm_phone_bi phone0_ where phone0_.id=1
        // select person0_.id as id1_5_0_, person0_.name as name2_5_0_ from otm_person_bi person0_ where person0_.id=1
        // update otm_phone_bi set number='234-567-8901', person_id=null where id=1

        // @OneToMany(mappedBy = "person")
        // private List<Phone> phones = new ArrayList<>();
        // @ManyToOne(cascade = CascadeType.ALL)
        // @ManyToOne(cascade = CascadeType.MERGE)
        // private Person person;
        // select phone0_.id as id1_7_1_, phone0_.number as number2_7_1_, phone0_.person_id as person_i3_7_1_, person1_.id as id1_5_0_, person1_.name as name2_5_0_ from otm_phone_bi phone0_ left outer join otm_person_bi person1_ on phone0_.person_id=person1_.id where phone0_.id=1
        // update otm_phone_bi set number='234-567-8901', person_id=null where id=1
    }

    @Test
    public void updatePhoneWithPersonId() {
        Phone phone = new Phone(1L, "234-567-8901");
        phone.setPerson(new Person(1L));
        phoneRepository.save(phone);

        // @OneToMany(mappedBy = "person")
        // private List<Phone> phones = new ArrayList<>();
        // @ManyToOne
        // @ManyToOne(cascade = CascadeType.PERSIST)
        // @ManyToOne(cascade = CascadeType.REMOVE)
        // private Person person;
        // select phone0_.id as id1_7_0_, phone0_.number as number2_7_0_, phone0_.person_id as person_i3_7_0_ from otm_phone_bi phone0_ where phone0_.id=1
        // select person0_.id as id1_5_0_, person0_.name as name2_5_0_ from otm_person_bi person0_ where person0_.id=1
        // update otm_phone_bi set number='234-567-8901', person_id=1 where id=1

        // @OneToMany(mappedBy = "person")
        // private List<Phone> phones = new ArrayList<>();
        // @ManyToOne(cascade = CascadeType.ALL)
        // @ManyToOne(cascade = CascadeType.MERGE)
        // private Person person;
        // select phone0_.id as id1_7_1_, phone0_.number as number2_7_1_, phone0_.person_id as person_i3_7_1_, person1_.id as id1_5_0_, person1_.name as name2_5_0_ from otm_phone_bi phone0_ left outer join otm_person_bi person1_ on phone0_.person_id=person1_.id where phone0_.id=1
        // update otm_person_bi set name=null where id=1
        // update otm_phone_bi set number='234-567-8901', person_id=1 where id=1
    }

    @Test
    public void addPhoneSetPerson() {
        Phone phone = new Phone("234-567-8901");
        phone.setPerson(new Person(1L));

        phoneRepository.save(phone);

        // @OneToMany(mappedBy = "person")
        // private List<Phone> phones = new ArrayList<>();
        // @ManyToOne
        // @ManyToOne(cascade = CascadeType.MERGE)
        // @ManyToOne(cascade = CascadeType.REMOVE)
        // private Person person;
        // insert into otm_phone_bi (id, number, person_id) values (null, '234-567-8901', 1)

        // @OneToMany(mappedBy = "person")
        // private List<Phone> phones = new ArrayList<>();
        // @ManyToOne(cascade = CascadeType.ALL)
        // @ManyToOne(cascade = CascadeType.PERSIST)
        // private Person person;
        // Exception
    }

    @Test
    public void personWithIdAddPhone() {
        Person person = new Person(1L);
        person.addPhone(new Phone("234-567-8901"));

        personRepository.save(person);
        // TODO
    }
}