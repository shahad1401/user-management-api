package tcc.api.management.UserManagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@Entity
public class API implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "API name is empty")
    private String name;
    @NotBlank(message = "URL is empty")
    private String URL;
    @NotBlank(message = "Method is empty")
    private String Method;
    @NotNull(message = "secure is empty")
    private boolean isSecure;
    @NotBlank(message = "Authorization Method is empty")
    private String AuthorizationMethod;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "application_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = "api")
    private Application application;

    public API() {
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

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getMethod() {
        return Method;
    }

    public void setMethod(String method) {
        Method = method;
    }

    public boolean isSecure() {
        return isSecure;
    }

    public void setSecure(boolean secure) {
        isSecure = secure;
    }

    public String getAuthorizationMethod() {
        return AuthorizationMethod;
    }

    public void setAuthorizationMethod(String authorizationMethod) {
        AuthorizationMethod = authorizationMethod;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
