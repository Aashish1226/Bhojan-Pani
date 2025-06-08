package Food.FoodDelivery.project.service;

import Food.FoodDelivery.project.Entity.*;
import Food.FoodDelivery.project.Repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmailAndIsActiveTrue(email)
                .orElseThrow(() -> new UsernameNotFoundException("Active user not found with email: " + email));

        List<GrantedAuthority> authorities = getAuthorities(user);
        return new User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    private List<GrantedAuthority> getAuthorities(Users user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Add role (Spring expects roles to be prefixed with "ROLE_")
        String roleName = user.getRole().getName().toUpperCase();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + roleName));

        // Add permissions
        if (user.getRole().getPermissions() != null) {
            for (Permission permission : user.getRole().getPermissions()) {
                authorities.add(new SimpleGrantedAuthority(permission.getName()));
            }
        }

        return authorities;
    }


}
