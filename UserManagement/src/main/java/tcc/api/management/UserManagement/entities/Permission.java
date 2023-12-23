package tcc.api.management.UserManagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import tcc.api.management.UserManagement.types.PermissionEnum;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="PERMISSION")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="PermissionType")
    @Enumerated(EnumType.STRING)
    private PermissionEnum permission;

    @ManyToMany(mappedBy = "permissions")
    @JsonIgnoreProperties(value = "roles")
    private Set<Role> roles = new HashSet<>();

    public Permission(){
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PermissionEnum getPermission() {
        return permission;
    }

    public void setPermission(PermissionEnum permission) {
        this.permission = permission;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
