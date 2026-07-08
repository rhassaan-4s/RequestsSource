package com._4s_.clients.dao;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com._4s_.clients.model.Tenant;

@Repository("tenantRepository")
@Transactional
public class TenantRepositoryImpl implements TenantRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Tenant> findBySlug(String slug) {

        String hql =
                "from Tenant t " +
                "where t.slug = :slug";

        Tenant tenant = getCurrentSession()
                .createQuery(hql, Tenant.class)
                .setParameter("slug", slug)
                .uniqueResult();

        return Optional.ofNullable(tenant);
    }

    @Override
    public Optional<Tenant> findById(Long id) {
        return Optional.ofNullable(
            (Tenant) getCurrentSession().get(Tenant.class, id)
        );
    }
    
    @Override
    public Tenant save(Tenant tenant) {

        getCurrentSession().saveOrUpdate(tenant);

        return tenant;
    }

    @Override
    public void delete(Tenant tenant) {

        getCurrentSession().delete(tenant);
    }
}