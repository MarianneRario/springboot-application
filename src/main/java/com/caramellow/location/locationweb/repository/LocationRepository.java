package com.caramellow.location.locationweb.repository;

import com.caramellow.location.locationweb.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository
        extends JpaRepository<Location, Integer> {
}
