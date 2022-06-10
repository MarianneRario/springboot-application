package com.caramellow.location.locationweb.controllers;

import com.caramellow.location.locationweb.model.Location;
import com.caramellow.location.locationweb.repository.LocationRepository;
import com.caramellow.location.locationweb.service.LocationService;
import com.caramellow.location.locationweb.util.EmailUtil;
import com.caramellow.location.locationweb.util.ReportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import java.util.List;

@Controller
public class LocationController {

    // reference of the service file (has LocationRepository injected)
    @Autowired
    private LocationService service;

    // reference to the email util interface
    @Autowired
    private EmailUtil email;

    // reference to the location repository
    @Autowired
    private LocationRepository repository;

    // reference to the reportUtil class
    @Autowired
    private ReportUtil reportUtil;

    // have a reference to the servlet context (inject it here)
    @Autowired
    private ServletContext servletContext;


    /**
     * @return createLocation.html
     */
    @RequestMapping(path = "/createLocation")
    public String showCreate() {
        return "createLocation"; // return a view (createLocation.html)
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
     *
     * Add'l:
     *      On every data saved in the db, you must send an email on the email that we have set up
     */
    @RequestMapping(path = "/saveLoc") // dapat yung path is parehas sa nakalagay sa form action (<form action="saveLoc" method="post">) -> in createLocation.html
    public String saveLocation(@ModelAttribute("location")Location location, ModelMap modelMap){
        Location savedLocation = service.saveLocation(location);
        String msg = "Location saved with id " + savedLocation.getId();
        modelMap.addAttribute("message", msg); // this can be access by the thymeleaf(createLocation.html)
        // send an email before saving to the db
        email.sendEmail(
                "xpringboot@gmail.com",
                "Location Saved",
                "Location saved successfully and about to return a response");

        return "createLocation"; // will return a createLocation.html view
    }

    // will retrieve all the locations in the database
    @RequestMapping(path = "/displayLocations") // dapat yung path same sa nakalagay sa href sa createLocation.html (<a href="displayLocations">View All</a>)
    public String displayLocation(ModelMap modelMap){
        List<Location> locationsList = service.getAllLocations();
        // To send it as a response, we need a modelMap. So that in the thymeleaf, we can access the list of locations
        modelMap.addAttribute("locations", locationsList);
        return "displayLocations"; // will return displayLocations.html view
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
        return "displayLocations"; // return the displayLocations.html view
    }

    @RequestMapping(path = "/editLocation") // url came from displayLocations.html (<td><a th:href="@{'editLocation?id='+${location.id}}">Edit</a> </td>)
    public String editLocation(@RequestParam("id") int id, ModelMap modelMap){
        Location location = service.getLocationById(id);
        modelMap.addAttribute("location", location);
        return "editLocation";  // will render this page (editLocation.html)
    }

    @RequestMapping("/updateLoc") // url that we need to handle in the form (<form action="updateLoc" method="post">) editLocation.html
    public String updateLocation(@ModelAttribute("location")Location location, ModelMap modelMap){
        Location updatedLocation = service.updateLocation(location);
        List<Location> locationsList = service.getAllLocations();
        modelMap.addAttribute("locations", locationsList);
        return "displayLocations"; // return the displayLocations.html view
    }

    // allow the end users to get the report back
    @RequestMapping("/generateReport")
    public String generateReport(){
        String path = servletContext.getRealPath("/");

        // first, we need to get the data from the database (to do that, we need to inject the repository(LocationRepository))
        List<Object[]> data = repository.findTypeCount(); // findTypeCount() is only available in LocationRepository and not in Location Impl that is why we can't use "service"
        reportUtil.generatePieChart(path, data);
        return "report";
    }



}
