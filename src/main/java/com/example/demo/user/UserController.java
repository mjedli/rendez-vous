package com.example.demo.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class UserController {

    @GetMapping("/user/rendezvous/choisir")
    public String creationRendezvous(Authentication authentication, ModelMap modelMap){

        String username = authentication.getName(); // email ou identifiant
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

        modelMap.addAttribute("username", username);
        modelMap.addAttribute("roles", roles);

        return "user/creation";
    }

    @GetMapping("/user/rendezvous/list")
    public String listRendezvous(Authentication authentication, ModelMap modelMap){

        String username = authentication.getName(); // email ou identifiant
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

        modelMap.addAttribute("username", username);
        modelMap.addAttribute("roles", roles);
        return "user/list";
    }


}
