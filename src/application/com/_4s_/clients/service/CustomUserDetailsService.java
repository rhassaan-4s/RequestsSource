package com._4s_.clients.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com._4s_.clients.dao.UserRepository;
import com._4s_.clients.model.CustomUserDetails;
import com._4s_.common.dao.TenantContext;
import com._4s_.security.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	@Qualifier("userRepository")
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String tenant = TenantContext.getTenant();
        System.out.println("Loading user by username: " + username + " for tenant: " + tenant);

        if (tenant != null) {
            return loadUser(username);
        } else { 
			throw new UsernameNotFoundException(username +
                    "' was not found.");
        }
//        else {
//        	Role role = ADMINISTRATOR;
//            return loadGeneralAdmin(username,role);
//        }
    }

    private UserDetails loadUser(String username) {
//    	System.out.println("Loading user for tenant: " + tenant);
        User user = userRepository.findUser(username);
        String tenant = TenantContext.getTenant();
//        System.out.println("User found: " + user.getEmail() + ", Role: " + user.getRole().getRoleName());
        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        auths.addAll(user.getAuthorities());
        return new CustomUserDetails(username, user.getPassword(), user.getId(), auths);
    }

//    private UserDetails loadGeneralAdmin(String email, Role role) {
//        User admin = userRepository.findGeneralAdmin(email,role).orElseThrow(
//                () -> new UsernameNotFoundException(
//                        "'" + email + "' was not found as a general admin."));
//        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
//        auths.add(new SimpleGrantedAuthority(ADMINISTRATOR.getRoleName()));
//        return new CustomUserDetails(admin.getEmail(), admin.getPassword(), admin.getId(), null,
//                auths);
//    }
}
