package tcc.api.management.UserManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcc.api.management.UserManagement.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUsername(String username);
}
