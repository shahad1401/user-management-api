package tcc.api.management.UserManagement.controllers;

import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tcc.api.management.UserManagement.entities.API;
import tcc.api.management.UserManagement.entities.User;
import tcc.api.management.UserManagement.repository.APIRepository;

import java.util.ArrayList;

@RestController
public class APIController {

    @Autowired
    APIRepository apiRepository;

    @RequestMapping(value="api", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<API> createAPI(@RequestBody API api) {
        try{
            API savedAPI = apiRepository.save(api);
            return new ResponseEntity<>(savedAPI, HttpStatus.CREATED);
        }catch (ConstraintViolationException e) {
            String message = new ArrayList<>(e.getConstraintViolations()).get(0).getMessage();
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, message);
        }

    }

    @RequestMapping(value="api/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public ResponseEntity<API> readAPI(@PathVariable int id) {
        API api = apiRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "API doesn't exist"));
        return new ResponseEntity<>(api, HttpStatus.OK);
    }

    @RequestMapping(value="api/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<API> updateAPI(@PathVariable int id, @RequestBody API api){
            API existingApi = apiRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "API doesn't exist"));
            existingApi.setMethod(api.getMethod());
            existingApi.setName(api.getName());
            existingApi.setURL(api.getURL());
            existingApi.setSecure(api.isSecure());
            existingApi.setAuthorizationMethod(api.getAuthorizationMethod());
            API newApi= apiRepository.save(existingApi);
            return new ResponseEntity<>(newApi, HttpStatus.OK);
    }
    @RequestMapping(value="api/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> deleteAPI(@PathVariable int id){
        API api = apiRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "API doesn't exist"));
        apiRepository.delete(api);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
