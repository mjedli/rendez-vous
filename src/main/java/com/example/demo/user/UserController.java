package com.example.demo.user;

import com.example.demo.admin.AdminRepository;
import com.example.demo.admin.model.RendezVous;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Controller
public class UserController {


    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/rendezvous/choisir")
    public String creationRendezvous(Authentication authentication, ModelMap modelMap){

        try {

            List<RendezVous> liste = userService.getListRendezVous();

            String username = authentication.getName(); // email ou identifiant
            Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

            modelMap.addAttribute("username", username);
            modelMap.addAttribute("roles", roles);

            liste.sort(Comparator
                    .comparing((RendezVous r) -> LocalDate.parse(r.getDate()))
                    .thenComparing(r -> LocalTime.parse(r.getHeure()))
                    .reversed());

            modelMap.addAttribute("listrendezvous", liste);
            return "user/creation";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error";
        }
    }

    @PostMapping("/user/rendezvous/select")
    public String selectRendezvous(@ModelAttribute RendezVous rendezVous, Authentication authentication, ModelMap modelMap){

        try {

            String username = authentication.getName(); // email ou identifiant
            Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

            modelMap.addAttribute("username", username);
            modelMap.addAttribute("roles", roles);

            userService.selectRendezvous(rendezVous, username);

            return "redirect:/user/rendezvous/choisir";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error";
        }

    }

    @PostMapping("/user/rendezvous/annuler")
    public String annulerRendezVous(@ModelAttribute RendezVous rendezVous, Authentication authentication, ModelMap modelMap){

        try {

            String username = authentication.getName(); // email ou identifiant
            Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

            modelMap.addAttribute("username", username);
            modelMap.addAttribute("roles", roles);

            userService.annulerRendezvous(rendezVous);

            return "redirect:/user/rendezvous/choisir";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error";
        }

    }

    @GetMapping("/user/rendezvous/list")
    public String listRendezvous(Authentication authentication, ModelMap modelMap){

        try {

            String username = authentication.getName(); // email ou identifiant
            Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

            modelMap.addAttribute("username", username);
            modelMap.addAttribute("roles", roles);

            List<RendezVous> liste = userService.getListRendezVousByUser(username);

            liste.sort(Comparator
                    .comparing((RendezVous r) -> LocalDate.parse(r.getDate()))
                    .thenComparing(r -> LocalTime.parse(r.getHeure()))
                    .reversed());

            modelMap.addAttribute("listrendezvous", liste);
            return "user/list";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error";
        }
    }

    @PostMapping("/user/rendezvous/annuler/{id}")
    public String annulerRendezvous(@PathVariable("id") String id, @ModelAttribute RendezVous rendezVous, Authentication authentication, ModelMap modelMap) {
        try {
            String username = authentication.getName();
            Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

            modelMap.addAttribute("username", username);
            modelMap.addAttribute("roles", roles);

            userService.annulerRendezvous(rendezVous);

            return "redirect:/user/rendezvous/list";
        } catch (Exception e) {
            System.out.println("Erreur lors de l'annulation du rendez-vous : " + e.getMessage());
            return "error";
        }
    }



}
