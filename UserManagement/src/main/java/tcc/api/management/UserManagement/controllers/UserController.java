package tcc.api.management.UserManagement.controllers;

import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tcc.api.management.UserManagement.entities.User;
import tcc.api.management.UserManagement.repository.UserRepository;

import java.util.ArrayList;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;


    @RequestMapping(value="user", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user){
        try{
            if (userRepository.findByUsername(user.getUsername()) == null) {
                User createdUser = userRepository.save(user);
                return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
            }else{
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Username already exists");
            }
        }catch (ConstraintViolationException e) {
            String message = new ArrayList<>(e.getConstraintViolations()).get(0).getMessage();
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, message);
        }
    }

    @RequestMapping(value="user/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> readUser(@PathVariable int id){
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "User doesn't exist"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value="user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user){
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "User doesn't exist"));
        existingUser.setUsername(user.getUsername());
        existingUser.setPass(user.getPass());
        User newUser = userRepository.save(existingUser);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @RequestMapping(value="user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable int id){
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "User doesn't exist"));
        userRepository.delete(user);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
