package com.github.bcingle.yavml.yavmlapi.repository;

import com.github.bcingle.yavml.yavmlapi.model.YavmlUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends CrudRepository<YavmlUser, Long> {

    @Override
    @Query("select u from YavmlUser u where id = :id and u.username like ?#{ hasRole('USER') ? '%' : principal.username }")
    Optional<YavmlUser> findById(@Param("id") Long id);

    @Override
    @Query("select u from YavmlUser u where u.username like ?#{ hasRole('USER') ? '%' : principal.username }")
    Iterable<YavmlUser> findAll();

    Optional<YavmlUser> findByUsername(@Param("username") String username);

    Optional<YavmlUser> findByEmail(@Param("email") String email);

}
