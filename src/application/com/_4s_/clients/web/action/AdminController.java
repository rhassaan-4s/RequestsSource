package com._4s_.clients.web.action;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("top_secret")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public String showTopSecretPage() {
        return "top_secret";
    }
}
