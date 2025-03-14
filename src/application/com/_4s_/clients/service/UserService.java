package com._4s_.clients.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com._4s_.clients.dao.TenantRepository;
import com._4s_.clients.dao.UserRepository;
import com._4s_.clients.model.Role;
import com._4s_.clients.model.Tenant;
import com._4s_.clients.model.User;
import com._4s_.clients.web.exception.TenantNotFoundException;

@Service
public class UserService {
	@Autowired
    private final TenantRepository tenantRepository;
	@Autowired
    private final UserRepository userRepository;
	@Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(TenantRepository tenantRepository,
                       UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.tenantRepository = tenantRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createNewUser(String email, String password, long tenantId, Role role) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new TenantNotFoundException(
                        "Tenant " + tenantId + " not found."));
        userRepository.save(new User(tenant, email, passwordEncoder.encode(password), role));
    }
}
