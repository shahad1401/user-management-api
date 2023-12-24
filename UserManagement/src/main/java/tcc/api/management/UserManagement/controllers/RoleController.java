package tcc.api.management.UserManagement.controllers;

import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tcc.api.management.UserManagement.entities.Role;
import tcc.api.management.UserManagement.entities.User;
import tcc.api.management.UserManagement.repository.RoleRepository;
import tcc.api.management.UserManagement.repository.UserRepository;
import tcc.api.management.UserManagement.types.RoleEnum;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value="user/{id}/roles", method = RequestMethod.POST)
    public ResponseEntity<User> createRole(@PathVariable int id, @RequestBody Role role){
        try{
            User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "User doesn't exist"));
            Role existingRole= roleRepository.findByRole(role.getRole());
            if ( existingRole == null) {
                user.addRole(role);
            }else{
                user.addRole(existingRole);
            }
            User userWithAddedRole = userRepository.save(user);
            return new ResponseEntity<>(userWithAddedRole, HttpStatus.CREATED);
        }catch (ConstraintViolationException e) {
            String message = new ArrayList<>(e.getConstraintViolations()).get(0).getMessage();
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, message);
        }
    }

    @RequestMapping(value="user/{id}/roles", method = RequestMethod.GET)
    public ResponseEntity<Set<RoleEnum>> readAllRoles(@PathVariable int id){
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "User doesn't exist"));
        Set<RoleEnum> roles = user.getRoles().stream().map(Role::getRole).collect(Collectors.toSet());
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @RequestMapping(value="roles/{id}", method = RequestMethod.GET)
    public ResponseEntity<Role> readRole(@PathVariable int id){
        Role role = roleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Role doesn't exist"));
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @RequestMapping(value="roles/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Role> updateRole(@PathVariable int id, @RequestBody Role role){
        Role existingRole = roleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Role doesn't exist"));
        existingRole.setRole(role.getRole());
        Role updatedRole = roleRepository.save(existingRole);
        return new ResponseEntity<>(updatedRole,HttpStatus.OK);
    }

    @RequestMapping(value="user/{id}/roles/{roleId}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUserRole(@PathVariable int id,@PathVariable int roleId){
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "User doesn't exist"));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Role doesn't exist"));
        user.removeRole(role);
        userRepository.save(user);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @RequestMapping(value="roles/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Role> deleteRole(@PathVariable int id){
        Role role = roleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Role doesn't exist"));
        roleRepository.delete(role);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
