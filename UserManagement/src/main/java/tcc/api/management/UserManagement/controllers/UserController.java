package tcc.api.management.UserManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import tcc.api.management.UserManagement.entities.User;
import tcc.api.management.UserManagement.repository.UserRepository;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value="user", method = RequestMethod.POST)
    public void createUser(@RequestParam String username){
        try{
            User u = new User(username,"pass");
            userRepository.save(u);
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Username already exists");
        }
    }

    @RequestMapping(value="user", method = RequestMethod.GET)
    public User readUser(@RequestParam String username){
        return userRepository.findByUsername(username);
    }

    @RequestMapping(value="user", method = RequestMethod.PUT)
    public void updateUser(@RequestParam String username, @RequestParam String newUsername){
        User user = userRepository.findByUsername(username);
        user.setUsername(newUsername);
        userRepository.save(user);
    }

    @RequestMapping(value="user", method = RequestMethod.DELETE)
    public void deleteUser(@RequestParam String username){
        userRepository.delete(readUser(username));
    }
}
