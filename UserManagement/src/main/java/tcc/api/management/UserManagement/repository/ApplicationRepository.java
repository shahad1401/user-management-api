package tcc.api.management.UserManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcc.api.management.UserManagement.entities.Application;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
}
