package com.cspark.play.jpacase.entity.onetomany.uni;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniOtmPersonRepository extends CrudRepository<Person, Long> {
}
