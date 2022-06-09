package com.caramellow.location.locationweb.repository;

import com.caramellow.location.locationweb.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationRepository
        extends JpaRepository<Location, Integer> {


    // this query is hql/jpql (Hibernate Query Language) against our entities (location)
    // this is not going to database
    // hibernate will internally convert this to a database query
    @Query("select type, count(type) from location group by type")
    public List<Object[]> findTypeCount(); // return a list of object array or a bean
}
