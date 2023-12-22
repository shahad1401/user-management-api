package tcc.api.management.UserManagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tcc.api.management.UserManagement.types.RoleEnum;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="ROLE")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "role is empty")
    private RoleEnum role;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnoreProperties(value = "roles")
    private Set<User> users= new HashSet<>();

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name="ROLE_PERMISSION",
            joinColumns = @JoinColumn(name="role_id"),
            inverseJoinColumns = @JoinColumn(name="permission_id")
    )
    @JsonIgnoreProperties(value = "roles")
    private Set<Permission> permissions = new HashSet<>();

    public Role() {
        super();
    }

    public Role(RoleEnum role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
    public void addPermission(Permission permission){
        this.permissions.add(permission);
        permission.getRoles().add(this);
    }
    public void removePermission(Permission permission){
        this.permissions.remove(permission);
        permission.getRoles().remove(this);
    }
}


