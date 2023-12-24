package tcc.api.management.UserManagement.controllers;

import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tcc.api.management.UserManagement.entities.API;
import tcc.api.management.UserManagement.entities.Application;
import tcc.api.management.UserManagement.entities.User;
import tcc.api.management.UserManagement.repository.APIRepository;
import tcc.api.management.UserManagement.repository.ApplicationRepository;

import java.util.ArrayList;

@RestController
public class ApplicationController {

    @Autowired
    ApplicationRepository appRepository;

    @Autowired
    APIRepository apiRepository;

    @RequestMapping(value="api/{id}/application", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<API> createApplication(@PathVariable int id, @RequestBody Application application) {
        try{
            API api = apiRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "API doesn't exist"));
            appRepository.save(application);
            api.setApplication(application);
            apiRepository.save(api);
            return new ResponseEntity<>(api, HttpStatus.CREATED);
        }catch (ConstraintViolationException e) {
            String message = new ArrayList<>(e.getConstraintViolations()).get(0).getMessage();
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, message);
        }

    }
    @RequestMapping(value="application/{appid}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public ResponseEntity<Application> readApplication(@PathVariable int appid) {
        Application application = appRepository.findById(appid).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Application doesn't exist"));
        return new ResponseEntity<>(application, HttpStatus.OK);
    }

    @RequestMapping(value="application/{appid}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Application> updateApplication(@PathVariable int appid, @RequestBody Application application){
            Application existingApplication = appRepository.findById(appid).orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Application doesn't exist"));
            existingApplication.setName(application.getName());
            existingApplication.setStatus(application.getStatus());
            Application newApp= appRepository.save(existingApplication);
            return new ResponseEntity<>(newApp, HttpStatus.OK);
    }
    @RequestMapping(value="application/{appid}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> deleteApplication(@PathVariable int appid){
        Application application = appRepository.findById(appid).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Application doesn't exist"));
        appRepository.delete(application);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
