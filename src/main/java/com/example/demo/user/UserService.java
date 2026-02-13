package com.example.demo.user;

import com.example.demo.admin.model.RendezVous;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository repositoryService) {
        this.userRepository = repositoryService;
    }

    public List<RendezVous> getListRendezVous() {
        return userRepository.getListRendezVous();
    }

    public void selectRendezvous(RendezVous rendezVous, String username) {
        userRepository.select(rendezVous, username);
    }

    public void annulerRendezvous(RendezVous rendezVous) {
        userRepository.annulerRendezVous(rendezVous);
    }

    public List<RendezVous> getListRendezVousByUser(String username) {
        return userRepository.getListRendezVousByUser(username);
    }
}
