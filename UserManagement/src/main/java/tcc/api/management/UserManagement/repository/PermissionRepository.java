package tcc.api.management.UserManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcc.api.management.UserManagement.entities.Permission;
import tcc.api.management.UserManagement.types.PermissionEnum;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    public Permission findByPermission(PermissionEnum permission);
}
