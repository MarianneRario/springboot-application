package com.caramellow.location.locationweb.controllers;

import com.caramellow.location.locationweb.model.Location;
import com.caramellow.location.locationweb.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LocationController {

    // reference of the service file
    @Autowired
    private LocationService service;

    /**
     * @return createLocation.html
     */
    @RequestMapping(path = "/createLocation")
    public String showCreate() {
        return "createLocation";
    }

    /**
     * @param location
     *
     * @return createLocation.html
     *
     * @ModelAttribute("") -> will map to the model obj
     *                     -> handle the request
     *
     * @ModelMap           -> handle the response
     *                     -> key value pairs
     */
    @RequestMapping(path = "/saveLoc") // dapat yung path is parehas sa nakalagay sa form action
    public String saveLocation(@ModelAttribute("location")Location location, ModelMap modelMap){
        Location savedLocation = service.saveLocation(location);
        String msg = "Location saved with id " + savedLocation.getId();
        modelMap.addAttribute("message", msg); // this can be access by the thymeleaf(createLocation.html)
        return "createLocation";
    }

    // will retrieve all the locations in the database
    @RequestMapping(path = "/displayLocations") // dapat yung path same sa nakalagay sa href sa createLocation.html
    public String displayLocation(ModelMap modelMap){
        List<Location> locationsList = service.getAllLocations();
        // To send it as a response, we need a modelMap. So that in the thymeleaf, we can access the list of locations
        modelMap.addAttribute("locations", locationsList);
        return "displayLocations";
    }

    /**
     * @param id should be mapped to from the request parameter
     * @RequestParam -> this tells the spring that it should retrieve the parameter from the url
     * deleteLocation?id -> we can see here that we have used "id"
     * "/deleteLocation" -> came from href in displayLocation.html
     * <td><a th:href="@{'deleteLocation?id='+${location.id}}">Delete</a> </td>
     */
    @RequestMapping(path = "/deleteLocation")
    public String deleteLocation(@RequestParam("id") int id, ModelMap modelMap){
        Location location = service.getLocationById(id); // get first the location
        service.deleteLocation(location); // once get, delete it

        // delete response
        // we're deleting it, and we're sending the list of locations again as a response
        List<Location> locationsList = service.getAllLocations();
        modelMap.addAttribute("locations", locationsList);
        // then the modelMap will have location list that will then be mapped in our thymeleaf file (displayLocations.html)
        // kasi pag wala un, wala tayong marereturn dito sa displayLocations (empty lang ung table)
        return "displayLocations";
    }

    @RequestMapping(path = "/editLocation")
    public String editLocation(@RequestParam("id") int id, ModelMap modelMap){
        Location location = service.getLocationById(id);
        modelMap.addAttribute("location", location);
        return "editLocation";  // will render this page (.html)
    }

    @RequestMapping("/updateLoc") // url that we need to handle in the form
    public String updateLocation(@ModelAttribute("location")Location location, ModelMap modelMap){
        Location updatedLocation = service.updateLocation(location);
        List<Location> locationsList = service.getAllLocations();
        modelMap.addAttribute("locations", locationsList);
        return "displayLocations";
    }


}
