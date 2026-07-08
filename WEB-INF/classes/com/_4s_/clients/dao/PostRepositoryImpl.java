package com._4s_.clients.dao;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com._4s_.clients.model.Post;

@Repository("postRepository")
@Transactional
public class PostRepositoryImpl implements PostRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> findByTenantIdWithAuthor(long tenantId) {

        String hql =
                "select distinct p " +
                "from Post p " +
                "join fetch p.author " +
                "where p.tenant.id = :tenantId";

        return getCurrentSession()
                .createQuery(hql, Post.class)
                .setParameter("tenantId", tenantId)
                .list();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Post> findByIdAndTenantId(long postId, long tenantId) {

        String hql =
                "from Post p " +
                "where p.id = :postId " +
                "and p.tenant.id = :tenantId";

        Post post = getCurrentSession()
                .createQuery(hql, Post.class)
                .setParameter("postId", postId)
                .setParameter("tenantId", tenantId)
                .uniqueResult();

        return Optional.ofNullable(post);
    }

    @Override
    public Post save(Post post) {

        getCurrentSession().saveOrUpdate(post);

        return post;
    }

    @Override
    public void delete(Post post) {

        getCurrentSession().delete(post);
    }
}