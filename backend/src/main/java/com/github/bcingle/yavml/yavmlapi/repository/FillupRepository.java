package com.github.bcingle.yavml.yavmlapi.repository;

import com.github.bcingle.yavml.yavmlapi.model.Fillup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.persistence.QueryHint;
import java.util.Optional;

@RepositoryRestResource
public interface FillupRepository extends CrudRepository<Fillup, Long> {

    @Override
    @Query("select f from Fillup f where f.id = :id and f.vehicle.fleet.owner.id = ?#{ principal?.id }")
    Optional<Fillup> findById(@Param("id") Long id);

    @Override
    @Query("select f from Fillup f where f.vehicle.fleet.owner.id = ?#{ principal?.id }")
    Iterable<Fillup> findAll();

}
