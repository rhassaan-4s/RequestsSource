package com._4s_.clients.service;

import static com._4s_.clients.model.Role.ADMINISTRATOR;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com._4s_.clients.dao.UserRepository;
import com._4s_.clients.model.CustomUserDetails;
import com._4s_.clients.model.Role;
import com._4s_.clients.model.User;
import com._4s_.common.dao.TenantContext;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String tenant = TenantContext.getTenant();

        if (tenant != null) {
            return loadUser(email, tenant);
        } else {
        	Role role = ADMINISTRATOR;
            return loadGeneralAdmin(email,role);
        }
    }

    private UserDetails loadUser(String email, String tenant) {
        User user =
                userRepository.findUser(email, tenant)
                        .orElseThrow(
                                () -> new UsernameNotFoundException(
                                        "'" + email + "' / '" + tenant +
                                                "' was not found."));

        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        auths.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));
        return new CustomUserDetails(user.getEmail(), user.getPassword(), user.getId(),
                user.getTenant().getId(), auths);
    }

    private UserDetails loadGeneralAdmin(String email, Role role) {
        User admin = userRepository.findGeneralAdmin(email,role).orElseThrow(
                () -> new UsernameNotFoundException(
                        "'" + email + "' was not found as a general admin."));
        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        auths.add(new SimpleGrantedAuthority(ADMINISTRATOR.getRoleName()));
        return new CustomUserDetails(admin.getEmail(), admin.getPassword(), admin.getId(), null,
                auths);
    }
}
