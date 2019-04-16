package com.github.bcingle.yavml.yavmlapi.repository;

import com.github.bcingle.yavml.yavmlapi.model.MaintenanceItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface MaintenanceItemRepository extends CrudRepository<MaintenanceItem, Long> {

}
