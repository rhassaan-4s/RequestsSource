package com._4s_.clients.web.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com._4s_.clients.model.Role;
import com._4s_.clients.service.UserService;
import com._4s_.clients.web.cmd.NewUserViewModel;
import com._4s_.clients.web.util.TenantId;

@Controller
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("register")
    public String registerNewUser(@Valid NewUserViewModel userViewModel,
                                  @TenantId long tenantId,
                                  HttpServletRequest request)
            throws ServletException {
        userService.createNewUser(
                userViewModel.getUsername(),
                userViewModel.getPassword1(),
                tenantId,
                Role.USER);
        request.login(userViewModel.getUsername(), userViewModel.getPassword1());

        return "redirect:/";
    }
}
