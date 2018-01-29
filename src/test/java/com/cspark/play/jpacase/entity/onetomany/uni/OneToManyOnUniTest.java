package com.cspark.play.jpacase.entity.onetomany.uni;

import com.cspark.play.jpacase.entity.onetomany.uni.Person;
import com.cspark.play.jpacase.entity.onetomany.uni.Phone;
import com.cspark.play.jpacase.entity.onetomany.uni.UniOtmPersonRepository;
import com.cspark.play.jpacase.entity.onetomany.uni.UniOtmPhoneRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Rollback(false)
public class OneToManyOnUniTest {

    @Autowired
    private UniOtmPersonRepository personRepository;

    @Autowired
    private UniOtmPhoneRepository phoneRepository;

    @Test
    public void selectPerson() {
        List<Person> people = (List<Person>) personRepository.findAll();
        assertThat(people.size(), is(1));

        Person person = personRepository.findOne(1L);
        assertThat(person.getId(), is(1L));

        // @OneToMany
        // private List<Phone> phones = new ArrayList<>();
        // select person0_.id as id1_2_, person0_.name as name2_2_ from otm_person_uni person0_
    }

    @Test
    public void selectPersonAndTouchPhone() {
        List<Person> people = (List<Person>) personRepository.findAll();
        assertThat(people.size(), is(1));

        Person person = personRepository.findOne(1L);
        assertThat(person.getId(), is(1L));
        assertThat(person.getPhones().size(), is(1));

        // @OneToMany
        // @JoinColumn(name = "person_id")
        // private List<Phone> phones = new ArrayList<>();
        // select person0_.id as id1_2_, person0_.name as name2_2_ from otm_person_uni person0_
        // select phones0_.person_id as person_i3_3_0_, phones0_.id as id1_3_0_, phones0_.id as id1_3_1_, phones0_.number as number2_3_1_ from otm_phone_uni phones0_ where phones0_.person_id=1
    }

    @Test
    public void selectPhone() {
        List<Phone> phones = (List<Phone>) phoneRepository.findAll();
        assertThat(phones.size(), is(1));

        Phone phone = phoneRepository.findOne(1L);
        assertThat(phone.getId(), is(1L));

        // @OneToMany
        // @JoinColumn(name = "person_id")
        // private List<Phone> phones = new ArrayList<>();
        // select phone0_.id as id1_3_, phone0_.number as number2_3_ from otm_phone_uni phone0_
    }

    @Test
    public void save() {
        Person person = new Person("name");
        person.getPhones().add(new Phone("234-567-8901"));

        personRepository.save(person);
        assertThat(person.getId(), is(notNullValue()));
        assertThat(person.getPhones().get(0).getId(), is(notNullValue()));

        // @OneToMany
        // @JoinColumn(name = "person_id")
        // Exception

        // @OneToMany(cascade = CascadeType.MERGE)
        // @OneToMany(cascade = CascadeType.REMOVE)
        // @JoinColumn(name = "person_id")
        // insert into otm_person_uni (id, name) values (null, 'name')
        // update otm_phone_uni set person_id=2 where id=?
        // Exception

        // @OneToMany(cascade = CascadeType.ALL)
        // @OneToMany(cascade = CascadeType.PERSIST)
        // @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        // @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
        // @JoinColumn(name = "person_id")
        // insert into otm_person_uni (id, name) values (null, 'name')
        // insert into otm_phone_uni (id, number) values (null, '234-567-8901')
        // update otm_phone_uni set person_id=2 where id=2
    }

    @Test
    public void updatePerson() {
        Person person = new Person(1L, "edit name");
        personRepository.save(person);

        // @OneToMany
        // @OneToMany(cascade = CascadeType.ALL)
        // @OneToMany(cascade = CascadeType.PERSIST)
        // @OneToMany(cascade = CascadeType.REMOVE)
        // @JoinColumn(name = "person_id")
        // select person0_.id as id1_2_0_, person0_.name as name2_2_0_ from otm_person_uni person0_ where person0_.id=1
        // select phones0_.person_id as person_i3_3_0_, phones0_.id as id1_3_0_, phones0_.id as id1_3_1_, phones0_.number as number2_3_1_ from otm_phone_uni phones0_ where phones0_.person_id=1
        // update otm_person_uni set name='edit name' where id=1
        // update otm_phone_uni set person_id=null where person_id=1

        // @OneToMany(cascade = CascadeType.MERGE)
        // @JoinColumn(name = "person_id")
        // select person0_.id as id1_2_1_, person0_.name as name2_2_1_, phones1_.person_id as person_i3_3_3_, phones1_.id as id1_3_3_, phones1_.id as id1_3_0_, phones1_.number as number2_3_0_ from otm_person_uni person0_ left outer join otm_phone_uni phones1_ on person0_.id=phones1_.person_id where person0_.id=1
        // update otm_person_uni set name=? where id=1
        // update otm_phone_uni set person_id=null where person_id=1
    }

    @Test
    public void updatePersonWithPhoneId() {
        Person person = new Person(1L, "edit name");
        person.getPhones().add(new Phone(1L));
        personRepository.save(person);

        // @OneToMany
        // @OneToMany(cascade = CascadeType.PERSIST)
        // @OneToMany(cascade = CascadeType.REMOVE)
        // @JoinColumn(name = "person_id")
        // select person0_.id as id1_2_0_, person0_.name as name2_2_0_ from otm_person_uni person0_ where person0_.id=1
        // select phones0_.person_id as person_i3_3_0_, phones0_.id as id1_3_0_, phones0_.id as id1_3_1_, phones0_.number as number2_3_1_ from otm_phone_uni phones0_ where phones0_.person_id=1
        // update otm_person_uni set name='edit name' where id=1


        // @OneToMany(cascade = CascadeType.ALL)
        // @OneToMany(cascade = CascadeType.MERGE)
        // @JoinColumn(name = "person_id")
        // select person0_.id as id1_2_1_, person0_.name as name2_2_1_, phones1_.person_id as person_i3_3_3_, phones1_.id as id1_3_3_, phones1_.id as id1_3_0_, phones1_.number as number2_3_0_ from otm_person_uni person0_ left outer join otm_phone_uni phones1_ on person0_.id=phones1_.person_id where person0_.id=1
        // update otm_phone_uni set number=null where id=1
        // update otm_person_uni set name='edit name' where id=1

    }

    @Test
    public void updatePhone() {
        Phone phone = new Phone(1L, "234-567-8901");
        phoneRepository.save(phone);

        // @OneToMany : cascade 관계 없음
        // @JoinColumn(name = "person_id")
        // select phone0_.id as id1_3_0_, phone0_.number as number2_3_0_ from otm_phone_uni phone0_ where phone0_.id=1
        // update otm_phone_uni set number='234-567-8901' where id=1
    }

    @Test
    public void addPhone() {
        Phone phone = new Phone("234-567-8901");
        phoneRepository.save(phone);

        // @OneToMany : cascade 관계 없음
        // @JoinColumn(name = "person_id")
        // insert into otm_phone_uni (id, number) values (null, '234-567-8901)
    }

    @Test
    public void addPhoneWithPersonId() {
        Person person = new Person(1L);
        person.getPhones().add(new Phone("234-567-8901"));

        personRepository.save(person);
        assertThat(person.getPhones().size(), is(2));


        // @OneToMany
        // @OneToMany(cascade = CascadeType.REMOVE)
        // @JoinColumn(name = "person_id")
        // select person0_.id as id1_2_0_, person0_.name as name2_2_0_ from otm_person_uni person0_ where person0_.id=?
        // select phones0_.person_id as person_i3_3_0_, phones0_.id as id1_3_0_, phones0_.id as id1_3_1_, phones0_.number as number2_3_1_ from otm_phone_uni phones0_ where phones0_.person_id=?
        // update otm_person_uni set name=null where id=1
        // update otm_phone_uni set person_id=null where person_id=1 and id=1
        // update otm_phone_uni set person_id=1 where id=?
        // Exception

        // @OneToMany(cascade = CascadeType.ALL)
        // @OneToMany(cascade = CascadeType.MERGE)
        // @JoinColumn(name = "person_id")
        // select person0_.id as id1_2_1_, person0_.name as name2_2_1_, phones1_.person_id as person_i3_3_3_, phones1_.id as id1_3_3_, phones1_.id as id1_3_0_, phones1_.number as number2_3_0_ from otm_person_uni person0_ left outer join otm_phone_uni phones1_ on person0_.id=phones1_.person_id where person0_.id=1
        // insert into otm_phone_uni (id, number) values (null, '234-567-8901')
        // update otm_person_uni set name=null where id=1
        // update otm_phone_uni set person_id=null where person_id=1 and id=1
        // update otm_phone_uni set person_id=1 where id=2

        // @OneToMany(cascade = CascadeType.PERSIST)
        // @JoinColumn(name = "person_id")
        // select person0_.id as id1_2_0_, person0_.name as name2_2_0_ from otm_person_uni person0_ where person0_.id=?
        // select phones0_.person_id as person_i3_3_0_, phones0_.id as id1_3_0_, phones0_.id as id1_3_1_, phones0_.number as number2_3_1_ from otm_phone_uni phones0_ where phones0_.person_id=?
        // insert into otm_phone_uni (id, number) values (null, null)
        // update otm_person_uni set name=null where id=1
        // update otm_phone_uni set person_id=null where person_id=1 and id=1
        // update otm_phone_uni set person_id=1 where id=2
    }

    @Test
    public void selectPersonAndAddPhone() {
        Person person = personRepository.findOne(1L);
        person.getPhones().add(new Phone("234-567-8901"));

        personRepository.save(person);
        assertThat(person.getPhones().size(), is(2));
        assertThat(person.getPhones().get(1).getNumber(), is("234-567-8901"));

        // @OneToMany
        // @OneToMany(cascade = CascadeType.REMOVE)
        // @JoinColumn(name = "person_id")
        // select person0_.id as id1_2_0_, person0_.name as name2_2_0_ from otm_person_uni person0_ where person0_.id=?
        // select phones0_.person_id as person_i3_3_0_, phones0_.id as id1_3_0_, phones0_.id as id1_3_1_, phones0_.number as number2_3_1_ from otm_phone_uni phones0_ where phones0_.person_id=?
        // update otm_phone_uni set person_id=1 where id=?
        // Exception

        // @OneToMany(cascade = CascadeType.ALL)
        // @OneToMany(cascade = CascadeType.MERGE)
        // @JoinColumn(name = "person_id")
        // select person0_.id as id1_2_0_, person0_.name as name2_2_0_ from otm_person_uni person0_ where person0_.id=1
        // select phones0_.person_id as person_i3_3_0_, phones0_.id as id1_3_0_, phones0_.id as id1_3_1_, phones0_.number as number2_3_1_ from otm_phone_uni phones0_ where phones0_.person_id=1
        // insert into otm_phone_uni (id, number) values (null, '234-567-8901')
        // update otm_phone_uni set person_id=1 where id=2


        // @OneToMany(cascade = CascadeType.PERSIST)
        // @JoinColumn(name = "person_id")
        // select person0_.id as id1_2_0_, person0_.name as name2_2_0_ from otm_person_uni person0_ where person0_.id=1
        // select phones0_.person_id as person_i3_3_0_, phones0_.id as id1_3_0_, phones0_.id as id1_3_1_, phones0_.number as number2_3_1_ from otm_phone_uni phones0_ where phones0_.person_id=1
        // insert into otm_phone_uni (id, number) values (null, null)
        // update otm_phone_uni set person_id=1 where id=2
    }
}