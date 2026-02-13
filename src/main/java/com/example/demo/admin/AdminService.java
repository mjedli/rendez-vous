package com.example.demo.admin;

import com.example.demo.admin.model.RendezVous;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public RendezVous addRendezvous(RendezVous rendezvous) {
        return adminRepository.addRendezvous(rendezvous);
    }

    public List<RendezVous> getListRendezVous() {
        return adminRepository.getListRendezVous();
    }
}
