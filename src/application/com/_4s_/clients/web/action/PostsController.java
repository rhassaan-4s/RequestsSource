package com._4s_.clients.web.action;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com._4s_.clients.service.PostService;
import com._4s_.clients.web.util.TenantId;

@RestController
@RequestMapping("/clients/posts")
public class PostsController {
    private final PostService postService;

    public PostsController(PostService postService) {
        this.postService = postService;
    }

    @DeleteMapping("{postId}")
    @PreAuthorize("hasRole('TENANT_ADMIN')")
    public ResponseEntity<Void> deletePost(@PathVariable long postId, @TenantId long tenantId) {
        // In a real application we would want to do a soft delete
        // and/or keep track of which tenant administrator deleted
        // the post.

        // It's important to pass _both_ post ID and tenant ID.
        // The service must ensure that the post being deleted is owned by
        // the tenant that the user is authenticated against.
        if (postService.removePost(postId, tenantId)) {
            return new ResponseEntity<>(NO_CONTENT);
        } else {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }
}
