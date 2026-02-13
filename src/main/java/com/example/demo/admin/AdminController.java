package com.example.demo.admin;

import com.example.demo.admin.model.RendezVous;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class AdminController {

    AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/admin/rendezvous/create")
    public String creationRendezvous(Authentication authentication, ModelMap modelMap){

        String username = authentication.getName(); // email ou identifiant
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

        modelMap.addAttribute("username", username);
        modelMap.addAttribute("roles", roles);

        return "admin/creation";
    }

    @PostMapping("/admin/rendezvous/add")
    public String addRendezvous(@ModelAttribute RendezVous rendezvous, Authentication authentication, ModelMap modelMap) {

        try {

            String username = authentication.getName(); // email ou identifiant
            Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

            modelMap.addAttribute("username", username);
            modelMap.addAttribute("roles", roles);

            if(adminService.addRendezvous(rendezvous).getHeure().equals("exist")) {
                return "redirect:/admin/rendezvous/create?error";
            }

            return "redirect:/admin/rendezvous/create?sucess";

        } catch (Exception e) {
            return "error";
        }
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
