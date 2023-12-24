package tcc.api.management.UserManagement.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import tcc.api.management.UserManagement.entities.Role;
import tcc.api.management.UserManagement.entities.User;
import tcc.api.management.UserManagement.repository.RoleRepository;
import tcc.api.management.UserManagement.repository.UserRepository;

import java.util.List;

@Component
public class APIUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u =userRepository.findByUsername(username);
        List<Role> roles = roleRepository.findRolesByUsersId(u.getId());
        if(u ==null) throw new UsernameNotFoundException("user does not exist");
        return new APIUserDetails(u,roles);
    }
}