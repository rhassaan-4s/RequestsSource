package com._4s_.clients.web.action;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.clients.model.CustomUserDetails;
import com._4s_.clients.service.PostService;
import com._4s_.clients.web.cmd.NewPostViewModel;
import com._4s_.clients.web.cmd.PostViewModel;
import com._4s_.clients.web.util.TenantId;

@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("posts")
    public ModelAndView showPosts(@TenantId long tenantId) {
    	ModelAndView mav = new ModelAndView();
        mav.setViewName("posts");
        mav.getModel().put("posts", postService.getPostsOfTenantWithAuthor(tenantId).stream()
                .map(post -> new PostViewModel(post.getId(), post.getText(), post.getAuthor().getEmail())));
        return mav;
    }

    @GetMapping("add_post")
    @PreAuthorize("isAuthenticated() && !hasRole('ADMINISTRATOR')")
    public ModelAndView addPost() {
    	ModelAndView mav = new ModelAndView();
        mav.setViewName("add_post");
        mav.getModel().put("post", new NewPostViewModel());
        return mav;
    }

    @PostMapping("add_post")
    @PreAuthorize("isAuthenticated() && !hasRole('ADMINISTRATOR')")
    public String addPost(@TenantId long tenantId,
                          @AuthenticationPrincipal CustomUserDetails user,
                          @Valid NewPostViewModel postVm) {
        postService.addPost(user.getUserId(), tenantId, postVm.getText());
        return "redirect:/posts";
    }
}
