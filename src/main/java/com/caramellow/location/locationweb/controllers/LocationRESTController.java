package com.caramellow.location.locationweb.controllers;

import com.caramellow.location.locationweb.model.Location;
import com.caramellow.location.locationweb.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/locations")
public class LocationRESTController {

    @Autowired
    private LocationRepository repository;

    // return a list of locations
    @GetMapping
    public List<Location> getLocations(){
        return repository.findAll();
    }

    @PostMapping(path = "/createNewLocation")
    public Location createNewLocation(@RequestBody Location location){
        return repository.save(location);
    }

    @PutMapping(path = "/updateLocation")
    public Location updateLocation(@RequestBody Location location){
        return repository.save(location);
    }

    @DeleteMapping(path = "/deleteLocation/{id}")
    public void deleteLocation(@PathVariable("id") int id){
        repository.deleteById(id);
    }

    @GetMapping(path = "/{id}")
    public Optional<Location> getLocation(@PathVariable("id") int id) throws Exception {
        return repository.findById(id);
    }



}
