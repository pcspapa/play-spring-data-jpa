package com.cspark.play.jpacase.entity.manytoone;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UniMtoPersonRepository extends CrudRepository<Person, Long> {

    @Modifying
    @Query("delete from mto_person_uni p where p.id = :id")
    void deleteAllById(@Param("id") Long id);

}
