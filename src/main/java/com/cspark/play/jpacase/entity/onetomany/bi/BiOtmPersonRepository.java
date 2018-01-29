package com.cspark.play.jpacase.entity.onetomany.bi;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BiOtmPersonRepository extends CrudRepository<Person, Long> {
}
