package com._4s_.clients.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com._4s_.clients.model.Post;

@Repository(value = "postRepository")
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT post FROM Post post INNER JOIN FETCH post.author WHERE post.tenant.id = :tenantId")
    List<Post> findByTenantIdWithAuthor(@Param("tenantId") long tenantId);

    Optional<Post> findByIdAndTenantId(@Param("postId") long postId, @Param("tenantId") long tenantId);
}
