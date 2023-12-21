package tcc.api.management.UserManagement.controllers;

import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public void createUser(@RequestBody User user){
        try{
            if (userRepository.findByUsername(user.getUsername()) == null) {
                userRepository.save(user);
            }else{
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Username already exists");
            }
        }catch (ConstraintViolationException e){
            String message = new ArrayList<>(e.getConstraintViolations()).get(0).getMessage();
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, message);
        }
    }

    @RequestMapping(value="user", method = RequestMethod.GET)
    public User readUser(@RequestParam String username){
        User u = userRepository.findByUsername(username);
        if (u != null){
            return userRepository.findByUsername(username);
        }
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "User doesn't exist");
    }

    @RequestMapping(value="user", method = RequestMethod.PUT)
    public void updateUser(@RequestParam String username, @RequestParam String newUsername){
        User user = readUser(username);
        user.setUsername(newUsername);
        userRepository.save(user);
    }

    @RequestMapping(value="user", method = RequestMethod.DELETE)
    public void deleteUser(@RequestParam String username){
        userRepository.delete(readUser(username));
    }
}
