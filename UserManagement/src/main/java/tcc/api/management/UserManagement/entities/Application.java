package tcc.api.management.UserManagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

@Entity
public class Application implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "application name is empty")
    public String name;
    @NotBlank(message = "application status is empty")
    public String status;
    @OneToOne(mappedBy = "application")
    @JsonIgnoreProperties(value = "application")
    private API api;

    public Application() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public API getApi() {
        return api;
    }

    public void setApi(API api) {
        this.api = api;
    }
}
