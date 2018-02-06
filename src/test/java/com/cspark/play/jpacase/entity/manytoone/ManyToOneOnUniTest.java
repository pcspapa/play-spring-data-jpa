package com.cspark.play.jpacase.entity.manytoone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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

        // @ManyToOne
        // private Person person;
        // select phone0_.id as id1_1_, phone0_.number as number2_1_, phone0_.person_id as person_i3_1_ from mto_phone_uni phone0_
        // select person0_.id as id1_0_0_, person0_.name as name2_0_0_ from mto_person_uni person0_ where person0_.id=1
    }

    @Test
    public void selectPhoneAndTouchPerson() {
        List<Phone> phones = (List<Phone>) phoneRepository.findAll();
        assertThat(phones.size(), is(1));

        Phone phone = phoneRepository.findOne(1L);
        assertThat(phone.getId(), is(1L));
        assertThat(phone.getPerson().getId(), is(1L));

        // @ManyToOne
        // private Person person;
        // select phone0_.id as id1_1_, phone0_.number as number2_1_, phone0_.person_id as person_i3_1_ from mto_phone_uni phone0_
        // select person0_.id as id1_0_0_, person0_.name as name2_0_0_ from mto_person_uni person0_ where person0_.id=1
    }

    @Test
    public void selectPhonesByPerson() {
        List<Phone> phones = phoneRepository.findByPerson(new Person(1L));
        assertThat(phones.size(), is(2));

        // @ManyToOne
        // private Person person;
        // select phone0_.id as id1_4_, phone0_.number as number2_4_, phone0_.person_id as person_i3_4_ from mto_phone_uni phone0_ left outer join mto_person_uni person1_ on phone0_.person_id=person1_.id where person1_.id=1
        // select person0_.id as id1_3_0_, person0_.name as name2_3_0_ from mto_person_uni person0_ where person0_.id=1
    }


    @Test
    public void selectPhonesOnPageByPerson() {
        List<Phone> phones = phoneRepository.findByPerson(new Person(1L), new PageRequest(0, 1));
        assertThat(phones.size(), is(1));

        // @ManyToOne
        // private Person person;
        // select phone0_.id as id1_4_, phone0_.number as number2_4_, phone0_.person_id as person_i3_4_ from mto_phone_uni phone0_ left outer join mto_person_uni person1_ on phone0_.person_id=person1_.id where person1_.id=1 limit ?
        // select person0_.id as id1_3_0_, person0_.name as name2_3_0_ from mto_person_uni person0_ where person0_.id=1
    }

    @Test
    public void selectPhonesByPersonId() {
        List<Phone> phones = phoneRepository.findByPersonId(1L);
        assertThat(phones.size(), is(2));

        // @ManyToOne
        // private Person person;

        // @Query("select c from mto_phone_uni c join fetch c.person p where p.id = :personId")
        // select phone0_.id as id1_4_0_, person1_.id as id1_3_1_, phone0_.number as number2_4_0_, phone0_.person_id as person_i3_4_0_, person1_.name as name2_3_1_ from mto_phone_uni phone0_ inner join mto_person_uni person1_ on phone0_.person_id=person1_.id where person1_.id=1

        // @Query("select c from mto_phone_uni c join c.person p where p.id = :personId")
        // select phone0_.id as id1_4_, phone0_.number as number2_4_, phone0_.person_id as person_i3_4_ from mto_phone_uni phone0_ inner join mto_person_uni person1_ on phone0_.person_id=person1_.id where person1_.id=1
        // select person0_.id as id1_3_0_, person0_.name as name2_3_0_ from mto_person_uni person0_ where person0_.id=1

        // @Query("select c from mto_phone_uni c where c.person.id = :personId")
        // select phone0_.id as id1_4_, phone0_.number as number2_4_, phone0_.person_id as person_i3_4_ from mto_phone_uni phone0_ where phone0_.person_id=1
        // select person0_.id as id1_3_0_, person0_.name as name2_3_0_ from mto_person_uni person0_ where person0_.id=1
    }

    @Test
    public void selectPhonesOnPageByPersonId() {
        List<Phone> phones = phoneRepository.findByPersonId(1L, new PageRequest(0, 1));
        assertThat(phones.size(), is(1));

        // @ManyToOne
        // private Person person;

        // @Query("select c from mto_phone_uni c join fetch c.person p where p.id = :personId")
        // select phone0_.id as id1_4_0_, person1_.id as id1_3_1_, phone0_.number as number2_4_0_, phone0_.person_id as person_i3_4_0_, person1_.name as name2_3_1_ from mto_phone_uni phone0_ inner join mto_person_uni person1_ on phone0_.person_id=person1_.id where person1_.id=1 limit ?
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
    public void savePhone() {
        Phone phone = new Phone("345-678-9012");
        phoneRepository.save(phone);

        // @ManyToOne : cascade 관계 없음.
        // private Person person;
        // insert into mto_phone_uni (id, number, person_id) values (null, '345-678-9012', null)
    }

    @Test
    public void savePhoneWithPersonId() {
        Phone phone = new Phone("345-678-9012");
        phone.setPerson(new Person(1L));
        phoneRepository.save(phone);

        // @ManyToOne
        // @ManyToOne(cascade = CascadeType.MERGE)
        // @ManyToOne(cascade = CascadeType.REMOVE)
        // private Person person;
        // insert into mto_phone_uni (id, number, person_id) values (null, ''234-567-8901, 1)

        // @ManyToOne(cascade = CascadeType.ALL)
        // @ManyToOne(cascade = CascadeType.PERSIST)
        // private Person person;
        // Exception
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

    @Test
    public void deletePhone() {
        phoneRepository.delete(2L);

        // @ManyToOne
        // private Person person;
        // select phone0_.id as id1_4_0_, phone0_.number as number2_4_0_, phone0_.person_id as person_i3_4_0_, person1_.id as id1_3_1_, person1_.name as name2_3_1_ from mto_phone_uni phone0_ left outer join mto_person_uni person1_ on phone0_.person_id=person1_.id where phone0_.id=2
        // delete from mto_phone_uni where id=2
    }

    @Test
    public void deletePhonesByPerson() {
        phoneRepository.deletePhonesByPerson(new Person(1L));

        // @ManyToOne
        // private Person person;
        // select phone0_.id as id1_4_, phone0_.number as number2_4_, phone0_.person_id as person_i3_4_ from mto_phone_uni phone0_ left outer join mto_person_uni person1_ on phone0_.person_id=person1_.id where person1_.id=1
        // select person0_.id as id1_3_0_, person0_.name as name2_3_0_ from mto_person_uni person0_ where person0_.id=1
        // delete from mto_phone_uni where id=1
        // delete from mto_phone_uni where id=2
    }

    @Test
    public void deleteAllByPerson() {
        phoneRepository.deleteAllByPerson(new Person(1L));

        // @ManyToOne
        // private Person person;
        // select phone0_.id as id1_4_, phone0_.number as number2_4_, phone0_.person_id as person_i3_4_ from mto_phone_uni phone0_ left outer join mto_person_uni person1_ on phone0_.person_id=person1_.id where person1_.id=1
        // select person0_.id as id1_3_0_, person0_.name as name2_3_0_ from mto_person_uni person0_ where person0_.id=1
        // delete from mto_phone_uni where id=1
        // delete from mto_phone_uni where id=2
    }

    @Test
    public void removePerson() {
        phoneRepository.removePhonesByPerson(new Person(1L));

        // @ManyToOne
        // private Person person;
        // select phone0_.id as id1_4_, phone0_.number as number2_4_, phone0_.person_id as person_i3_4_ from mto_phone_uni phone0_ left outer join mto_person_uni person1_ on phone0_.person_id=person1_.id where person1_.id=1
        // select person0_.id as id1_3_0_, person0_.name as name2_3_0_ from mto_person_uni person0_ where person0_.id=1
        // delete from mto_phone_uni where id=1
        // delete from mto_phone_uni where id=2
    }

    @Test
    public void selectAndDeleteAllByPersonId() {
        List<Phone> phones = phoneRepository.findByPersonId(1L);
        phoneRepository.delete(phones);

        // select phone0_.id as id1_4_0_, person1_.id as id1_3_1_, phone0_.number as number2_4_0_, phone0_.person_id as person_i3_4_0_, person1_.name as name2_3_1_ from mto_phone_uni phone0_ inner join mto_person_uni person1_ on phone0_.person_id=person1_.id where person1_.id=?
        // delete from mto_phone_uni where id=?
        // delete from mto_phone_uni where id=?
    }

    @Test
    public void deleteAllByPersonId() {
        phoneRepository.deleteAllByPersonId(1L);
        personRepository.delete(1L);

        // @ManyToOne
        // private Person person;
        // delete from mto_phone_uni where person_id=?
        // select person0_.id as id1_3_0_, person0_.name as name2_3_0_ from mto_person_uni person0_ where person0_.id=1
        // delete from mto_person_uni where id=1
    }

    @Test
    public void deleteAllAndPersonByPersonId() {
        phoneRepository.deleteAllByPersonId(1L);
        personRepository.deleteAllById(1L);

        // @ManyToOne
        // private Person person;
        // delete from mto_phone_uni where person_id=?
        // delete from mto_person_uni where id=1
    }
}