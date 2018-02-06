package com.cspark.play.jpacase.entity.manytoone;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UniMtoPhoneRepository extends CrudRepository<Phone, Long> {
    List<Phone> findByPerson(Person person);

    List<Phone> findByPerson(Person person, Pageable pageable);

    @Query("select c from mto_phone_uni c join fetch c.person p where p.id = :personId")
    List<Phone> findByPersonId(@Param("personId") Long personId);

    @Query("select c from mto_phone_uni c join fetch c.person p where p.id = :personId")
    List<Phone> findByPersonId(@Param("personId") Long personId, Pageable pageable);

    void deletePhonesByPerson(Person person);

    void removePhonesByPerson(Person person);

    void deleteAllByPerson(Person person);

    @Modifying
    @Query("delete from mto_phone_uni c where c.person.id = :personId")
    void deleteAllByPersonId(@Param("personId") Long personId);
    
}
