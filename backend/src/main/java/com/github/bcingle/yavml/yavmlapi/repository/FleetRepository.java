package com.github.bcingle.yavml.yavmlapi.repository;

import com.github.bcingle.yavml.yavmlapi.model.Fleet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface FleetRepository  extends CrudRepository<Fleet, Long> {

}
