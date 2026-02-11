package com.example.demo.admin;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class AdminController {

    @GetMapping("/admin/rendezvous/create")
    public String creationRendezvous(Authentication authentication, ModelMap modelMap){

        String username = authentication.getName(); // email ou identifiant
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

        modelMap.addAttribute("username", username);
        modelMap.addAttribute("roles", roles);

        return "admin/creation";
    }

    @GetMapping("/admin/rendezvous/list")
    public String listRendezvous(Authentication authentication, ModelMap modelMap){

        String username = authentication.getName(); // email ou identifiant
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

        modelMap.addAttribute("username", username);
        modelMap.addAttribute("roles", roles);
        return "admin/list";
    }


}
