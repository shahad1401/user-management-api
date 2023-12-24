package tcc.api.management.UserManagement.Security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tcc.api.management.UserManagement.entities.Role;
import tcc.api.management.UserManagement.entities.User;

import java.util.*;
import java.util.stream.Collectors;

public class APIUserDetails implements UserDetails {
    private String userName;
    private String password;
    private List<GrantedAuthority> authorities;

    public APIUserDetails(User user, List<Role> roles){
        this.userName = user.getUsername();
        this.password = user.getPass();
        this.authorities = roles.stream().map(role -> role.getRole().name())
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public APIUserDetails(){
        super();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
