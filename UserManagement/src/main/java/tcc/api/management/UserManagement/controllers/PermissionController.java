package tcc.api.management.UserManagement.controllers;

import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tcc.api.management.UserManagement.entities.Permission;
import tcc.api.management.UserManagement.entities.Role;
import tcc.api.management.UserManagement.repository.PermissionRepository;
import tcc.api.management.UserManagement.repository.RoleRepository;
import tcc.api.management.UserManagement.types.PermissionEnum;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class PermissionController {

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    RoleRepository roleRepository;
    @RequestMapping(value="roles/{id}/permissions", method = RequestMethod.POST)
    public ResponseEntity<Role> createPermission(@PathVariable int id, @RequestBody Permission permission) {
        try {
            Role role = roleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Role doesn't exist"));
            Permission existingPermission = permissionRepository.findByPermission(permission.getPermission());
            if (existingPermission == null) {
                role.addPermission(permission);
            } else {
                role.addPermission(existingPermission);
            }
            Role roleWithAddedPermission = roleRepository.save(role);
            return new ResponseEntity<>(roleWithAddedPermission, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            String message = new ArrayList<>(e.getConstraintViolations()).get(0).getMessage();
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, message);
        }
    }

        @RequestMapping(value="roles/{id}/permissions", method = RequestMethod.GET)
        public ResponseEntity<Set<PermissionEnum>> readAllPermissions(@PathVariable int id){
            Role role = roleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Role doesn't exist"));
            Set<PermissionEnum> permissions = role.getPermissions().stream().map(Permission::getPermission).collect(Collectors.toSet());
            return new ResponseEntity<>(permissions, HttpStatus.OK);
        }

    @RequestMapping(value="permissions/{permissionId}", method = RequestMethod.GET)
    public ResponseEntity<Permission> readPermission(@PathVariable int permissionId){
        Permission permission = permissionRepository.findById(permissionId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Permission doesn't exist"));
        return new ResponseEntity<>(permission, HttpStatus.OK);
    }

    @RequestMapping(value="permissions/{permissionId}", method = RequestMethod.PUT)
    public ResponseEntity<Permission> updatePermission(@PathVariable int permissionId, @RequestBody Permission permission){
        Permission existingPermission = permissionRepository.findById(permissionId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Permission doesn't exist"));
        existingPermission.setPermission(permission.getPermission());
        Permission updatedPermission = permissionRepository.save(existingPermission);
        return new ResponseEntity<>(updatedPermission,HttpStatus.OK);
    }

    @RequestMapping(value="roles/{id}/permissions/{permissionId}", method = RequestMethod.DELETE)
    public ResponseEntity<Role> deleteRolePermission(@PathVariable int id,@PathVariable int permissionId){
        Role role = roleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Role doesn't exist"));
        Permission permission = permissionRepository.findById(permissionId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Permission doesn't exist"));
        role.removePermission(permission);
        roleRepository.save(role);
        return new ResponseEntity<>(role,HttpStatus.OK);
    }
}
