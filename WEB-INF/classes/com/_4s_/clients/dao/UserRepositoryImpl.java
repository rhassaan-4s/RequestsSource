package com._4s_.clients.dao;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com._4s_.clients.model.Role;
import com._4s_.clients.model.User;

@Repository("userRepository")
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUser(String email, String slug) {

        String hql =
                "select u " +
                "from User u " +
                "join u.tenant t " +
                "where u.email = :email " +
                "and t.slug = :slug";

        User user = getCurrentSession()
                .createQuery(hql, User.class)
                .setParameter("email", email)
                .setParameter("slug", slug)
                .uniqueResult();

        return Optional.ofNullable(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findGeneralAdmin(String email, Role role) {

        String hql =
                "from User u " +
                "where u.tenant is null " +
                "and u.email = :email " +
                "and u.role = :role";

        User user = getCurrentSession()
                .createQuery(hql, User.class)
                .setParameter("email", email)
                .setParameter("role", role)
                .uniqueResult();

        return Optional.ofNullable(user);
    }
    
    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(
            (User) getCurrentSession().get(User.class, id)
        );
    }

    @Override
    public User save(User user) {

        getCurrentSession().saveOrUpdate(user);

        return user;
    }
}