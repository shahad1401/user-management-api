package tcc.api.management.UserManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcc.api.management.UserManagement.entities.Role;
import tcc.api.management.UserManagement.types.RoleEnum;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    public Role findByRole(RoleEnum role);
}
