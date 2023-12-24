package tcc.api.management.UserManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcc.api.management.UserManagement.entities.API;

public interface APIRepository extends JpaRepository<API, Integer> {
}
