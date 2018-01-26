package com.cspark.play.jpacase.entity.manytoone;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniMtoPersonRepository extends CrudRepository<Person, Long> {
}
