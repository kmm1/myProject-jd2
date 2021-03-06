package com.itacademy.controller;

import com.itacademy.entity.Category;
import com.itacademy.entity.EnumFlashmobType;
import com.itacademy.entity.Event;
import com.itacademy.entity.SystemUser;
import com.itacademy.service.CategoryService;
import com.itacademy.service.FlashmobService;
import com.itacademy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final FlashmobService flashmobService;
    private final CategoryService categoryService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, FlashmobService flashmobService,
                          CategoryService categoryService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.flashmobService = flashmobService;
        this.categoryService = categoryService;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute("user")
    public SystemUser user() {
        return new SystemUser();
    }

    @ModelAttribute("allFlashmobTypes")
    public List<EnumFlashmobType> enumFlashmob() {
        return Arrays.asList(EnumFlashmobType.values());
    }

    @ModelAttribute("allEvents")
    public List<Event> allEvents() {
        return flashmobService.findAllEvents();
    }

    @ModelAttribute("allCategories")
    public List<Category> categories() {
        return categoryService.findAll();
    }

    @ModelAttribute("roles")
    public Collection<? extends GrantedAuthority> roles() {
        Collection<? extends GrantedAuthority> roles = SecurityContextHolder.getContext()
                .getAuthentication().getAuthorities();
        return roles;
    }

    @ModelAttribute("userName")
    public String userName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = ((UserDetails) principal).getUsername();
        return userName;
    }


    @GetMapping("/")
    public String showHomePage() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        return "main-page-user";
    }

    @GetMapping(path = "/mainPageUser")
    public String mainPageUser() {
        return "main-page-user";
    }

    @GetMapping(path = "/admin")
    public String mainPageAdmin() {
        return "main-page-admin";
    }
}
