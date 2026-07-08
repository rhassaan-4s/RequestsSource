package com._4s_.clients.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._4s_.clients.dao.PostRepository;
import com._4s_.clients.dao.TenantRepository;
import com._4s_.clients.dao.UserRepository;
import com._4s_.clients.model.Post;
import com._4s_.clients.model.Tenant;
import com._4s_.clients.model.User;
import com._4s_.clients.web.exception.TenantNotFoundException;
import com._4s_.clients.web.exception.UserNotFoundException;

@Service
public class PostService {
	@Autowired
    private final PostRepository postRepository;
	@Autowired
    private final UserRepository userRepository;
	@Autowired
    private final TenantRepository tenantRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository,
                       TenantRepository tenantRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
    }

    public void addPost(long userId, long tenantId, String text) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User " + userId + " not found."));
        Tenant tenant =
                tenantRepository.findById(tenantId).orElseThrow(() -> new TenantNotFoundException(
                        "Tenant " + tenantId + " not found."));
        postRepository.save(new Post(text, tenant, user));
    }

    public List<Post> getPostsOfTenantWithAuthor(long tenantId) {
        return postRepository.findByTenantIdWithAuthor(tenantId);
    }

    public boolean removePost(long postId, long tenantId) {
        Optional<Post> post = postRepository.findByIdAndTenantId(postId, tenantId);
        if (post.isPresent()) {
            return false;
        }
        postRepository.delete(post.get());
        return true;
    }
}
