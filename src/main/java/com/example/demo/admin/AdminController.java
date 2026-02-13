package com.example.demo.admin;

import com.example.demo.admin.model.RendezVous;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

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
            System.out.println(e.getMessage());
            return "error";
        }
    }

    @PostMapping("/admin/rendezvous/delete")
    public String deleteRendezvous(@ModelAttribute RendezVous rendezvous, Authentication authentication, ModelMap modelMap) {

        try {

            String username = authentication.getName(); // email ou identifiant
            Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

            modelMap.addAttribute("username", username);
            modelMap.addAttribute("roles", roles);

            adminService.deleteRendezVous(rendezvous);

            return "redirect:/admin/rendezvous/list";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error";
        }
    }

    @GetMapping("/admin/rendezvous/list")
    public String listRendezvous(Authentication authentication, ModelMap modelMap){

        try {

            List<RendezVous> liste = adminService.getListRendezVous();

            String username = authentication.getName(); // email ou identifiant
            Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

            modelMap.addAttribute("username", username);
            modelMap.addAttribute("roles", roles);

           liste.sort(Comparator
                    .comparing((RendezVous r) -> LocalDate.parse(r.getDate()))
                    .thenComparing(r -> LocalTime.parse(r.getHeure()))
                    .reversed());

            modelMap.addAttribute("listrendezvous", liste);
            return "admin/list";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error";
        }

    }


}
