//package com._4s_.clients.dao;
//
//import java.util.Optional;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import com._4s_.clients.model.Tenant;
//
//@Repository(value = "tenantRepository")
//public interface TenantRepository extends JpaRepository<Tenant, Long> {
//    Optional<Tenant> findBySlug(String slug);
//}
package com._4s_.clients.dao;

import java.util.Optional;

import com._4s_.clients.model.Tenant;

public interface TenantRepository {

    Optional<Tenant> findBySlug(String slug);
    
    Optional<Tenant> findById(Long id);

    Tenant save(Tenant tenant);

    void delete(Tenant tenant);
}
