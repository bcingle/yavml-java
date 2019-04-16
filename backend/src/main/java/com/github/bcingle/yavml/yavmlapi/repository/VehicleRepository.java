package com.github.bcingle.yavml.yavmlapi.repository;

import com.github.bcingle.yavml.yavmlapi.model.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface VehicleRepository extends CrudRepository<Vehicle, Long> {

}
