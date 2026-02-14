package com.example.demo.login;

import com.example.demo.login.model.HomeObject;
import com.example.demo.login.model.LoginObject;
import com.example.demo.login.model.LoginPojo;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Random;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login/login"; // correspond à login.html
    }

    private static final String HREF_BASE = "/paybay";

    ParismonService parismonService;

    public LoginController(ParismonService parismonService, JavaMailSender emailSender, PasswordEncoder passwordEncoder) {
        this.parismonService = parismonService;
        this.emailSender = emailSender;
        this.passwordEncoder = passwordEncoder;
    }

    @Value("${server.address}")
    String server;

    @Value("${server.port}")
    String port;

    private JavaMailSender emailSender;

    private PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @GetMapping(value = "/")
    private String start() {
        return "paybuy/index";
    }

    @GetMapping(value = "/home")
    private String login(HttpServletResponse response, Authentication authentication, Principal principal, Model modelMap) {
        if (principal == null) {
            return HREF_BASE + "/login";
        }

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        modelMap.addAttribute("username", principal.getName());

        String username = authentication.getName(); // email ou identifiant
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

        modelMap.addAttribute("username", username);
        modelMap.addAttribute("roles", roles);

        //String idBarber = parismonService.findParismonByMail(principal.getName()).getId();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM, dd yyyy");

        HomeObject homeObject = new HomeObject();

        modelMap.addAttribute("homeObject", homeObject);

        // Exemple : redirection selon le rôle
        if (roles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
            return "admin-home";
        } else if (roles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_USER"))) {
            return "user-home";
        }

        return "access-denied";
    }


    @GetMapping(value = HREF_BASE + "/regisration/{id}")
    private String findBarberByActiveToken(@PathVariable String id) {
        LoginPojo parismon = parismonService.findParismonByActiveToken(id);
        parismon.setActive(true);
        parismonService.updateParismon(parismon);
        return HREF_BASE + "/login";
    }

    @PostMapping(value = HREF_BASE + "/model/insert")
    private String insertBarberModel(@ModelAttribute LoginObject barber1, Model modelMap) {
        try {

            if (!barber1.getPassword().equals(barber1.getRepassword())) {
                modelMap.addAttribute("passworderror", "passworderror");
                return "/login/registration";
            }

            LoginPojo barber = new LoginPojo();
            barber.setEmail(barber1.getEmail());
            barber.setPassword(passwordEncoder.encode(barber1.getPassword()));

            barber.setRole(barber1.getRole());

            // TODO Bouchon to remove
            barber.setActive(true);

            jakarta.mail.internet.MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "utf-8");

            message.setFrom("noreply@barber.com");
            message.setTo(barber.getEmail());
            message.setSubject("Validation email");
            message.setReplyTo("noreply@barber.com");


            int leftLimit = 97; // letter 'a'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 50;
            Random random = new Random();
            StringBuilder buffer = new StringBuilder(targetStringLength);
            for (int i = 0; i < targetStringLength; i++) {
                int randomLimitedInt = leftLimit + (int)
                        (random.nextFloat() * (rightLimit - leftLimit + 1));
                buffer.append((char) randomLimitedInt);
            }

            String generatedString = buffer.toString();
            barber.setActiveMailToken(generatedString);

            StringBuilder b = new StringBuilder();
            b.append("<a href='http://");
            b.append(server);
            b.append(":");
            b.append(port);
            b.append("/login/regisration/");
            b.append(generatedString);
            b.append("'>");
            b.append("Active my barber a count!");
            b.append("</a>");
            message.setText(b.toString(), true);

            LoginPojo barberTemp = parismonService.insertParismon(barber);

            if ("exist".equals(barberTemp.getEmail())) {
                modelMap.addAttribute("exist", "exist");
                return "/login/registration";
            } else {
                // TODO reactive the mail sender
                //emailSender.send(mimeMessage);
            }

            return "active";

        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = HREF_BASE + "/forgetpassword/{id}")
    private String forgetPassword(@PathVariable String id, ModelMap modelMap) {
        LoginPojo barber = parismonService.findParismonByActiveToken(id);
        modelMap.addAttribute("barber", barber);
        if (barber != null && id.equals(barber.getActiveMailToken())) {
            return HREF_BASE + "/password";
        } else {
            return HREF_BASE + "/forget";
        }
    }

    @PostMapping(value = HREF_BASE + "/update/password")
    private String updatePassword(@ModelAttribute LoginPojo barber) {
        LoginPojo barber1 = parismonService.findParismonByMail(barber.getEmail());
        if (barber1 != null && barber1.getActiveMailToken().equals(barber.getActiveMailToken())) {
            barber1.setPassword(passwordEncoder.encode(barber.getPassword()));
            barber1.setActiveMailToken("");
            parismonService.updateParismon(barber1);
            return HREF_BASE + "/login";
        } else {
            return HREF_BASE + "/forget";
        }
    }

    @PostMapping(value = HREF_BASE + "/forget/password")
    private String forgetPassword(@ModelAttribute LoginPojo barber) throws MailException {
        try {

            LoginPojo barber1 = parismonService.findParismonByMail(barber.getEmail());

            if (barber1 != null && barber1.getEmail().equals(barber.getEmail())) {

                MimeMessage mimeMessage = emailSender.createMimeMessage();
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "utf-8");

                int leftLimit = 97; // letter 'a'
                int rightLimit = 122; // letter 'z'
                int targetStringLength = 50;
                Random random = new Random();
                StringBuilder buffer = new StringBuilder(targetStringLength);
                for (int i = 0; i < targetStringLength; i++) {
                    int randomLimitedInt = leftLimit + (int)
                            (random.nextFloat() * (rightLimit - leftLimit + 1));
                    buffer.append((char) randomLimitedInt);
                }

                String generatedString = buffer.toString();
                barber.setActiveMailToken(generatedString);

                StringBuilder b = new StringBuilder();
                b.append("<a href='http://");
                b.append(server);
                b.append(":");
                b.append(port);
                b.append(HREF_BASE + "/forgetpassword/");
                b.append(generatedString);
                b.append("'>");
                b.append("Active my barber a count!");
                b.append("</a>");
                message.setText(b.toString(), true);

                message.setFrom("noreply@barber.com");
                message.setTo(barber.getEmail());
                message.setSubject("Validation email");
                message.setReplyTo("noreply@barber.com");

                emailSender.send(mimeMessage);

                barber1.setActiveMailToken(generatedString);

                parismonService.updateParismon(barber1);

                return "active";

            } else
                return HREF_BASE + "/forget";
        } catch (Exception e) {
            return null;
        }
    }
}
