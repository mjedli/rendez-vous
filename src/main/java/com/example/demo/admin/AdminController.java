package com.example.demo.admin;

import com.example.demo.admin.model.RendezVous;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8080"})
public class AdminController {

    AdminService adminService;

    private static final Logger logger =
            LoggerFactory.getLogger(AdminController.class);

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/admin/rendezvous/create")
    public String creationRendezvous(Authentication authentication, ModelMap modelMap) {

        String username = authentication.getName(); // email ou identifiant
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

        modelMap.addAttribute("username", username);
        modelMap.addAttribute("roles", roles);

        return "admin/creation";
    }

    @PostMapping("/admin/rendezvous/add")
    @ResponseBody
    public String addRendezvous(@RequestBody RendezVous rendezvous, Authentication authentication, ModelMap modelMap) {

        try {

            if (adminService.addRendezvous(rendezvous).getHeure().equals("exist")) {
                return "false";
            }

            return "true";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "false";
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

            return "redirect:/home";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error";
        }
    }

    @DeleteMapping("/admin/rendezvous/delete/{id}")
    @ResponseBody
    public String deleteRendezvous(@PathVariable String id, Authentication authentication, ModelMap modelMap) {
        try {
/*
            String username = authentication.getName(); // email ou identifiant
            Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

            modelMap.addAttribute("username", username);
            modelMap.addAttribute("roles", roles);
*/
            adminService.deleteRendezVousId(id);

            return "true";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "false";
        }
    }

    @GetMapping("/admin/rendezvous/list/venir")
    @ResponseBody
    public List<RendezVous> listRendezvousVenir(Authentication authentication, ModelMap modelMap) {

        try {

            List<RendezVous> liste = adminService.getListRendezVousVenir();

            logger.info(liste.toString());

            String username = authentication.getName(); // email ou identifiant
            Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

            //modelMap.addAttribute("username", username);
            //modelMap.addAttribute("roles", roles);

            liste.sort(Comparator
                    .comparing((RendezVous r) -> LocalDate.parse(r.getDate()))
                    .thenComparing(r -> LocalTime.parse(r.getHeure()))
                    .reversed());

            //modelMap.addAttribute("listrendezvous", liste);
            return liste;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    @GetMapping("/admin/rendezvous/list/depasser")
    @ResponseBody
    public List<RendezVous> listRendezvousDepassers(Authentication authentication, ModelMap modelMap) {

        try {

            List<RendezVous> liste = adminService.getListRendezVousDepasser();

            liste.sort(Comparator
                    .comparing((RendezVous r) -> LocalDate.parse(r.getDate()))
                    .thenComparing(r -> LocalTime.parse(r.getHeure()))
                    .reversed());

            return liste;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }


}
