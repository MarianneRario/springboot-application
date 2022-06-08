package com.caramellow.location.locationweb.service;

import com.caramellow.location.locationweb.model.Location;

import java.util.List;

public interface LocationService {
    /**
     * this service interface will host 5 different methods 1. save location 2.
     * update location 3. delete location 4. get location 5. get all location
     */

    Location saveLocation(Location location); // location as return type

    Location updateLocation(Location location); // location as return type

    void deleteLocation(Location location); // void as return type

    Location getLocationById(int id); // location as a return type

    List<Location> getAllLocations(); // list of location as return type
}
