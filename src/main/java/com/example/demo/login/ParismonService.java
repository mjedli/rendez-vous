package com.example.demo.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.model.*;

/**
 * @author mjedli
 */
@Service
public class ParismonService {

    ParismonRepository parismonRepository;

    public ParismonService(ParismonRepository parismonRepository) {
        this.parismonRepository = parismonRepository;
    }

    public LoginPojo findParismonByMail(String mail) {
        return parismonRepository.findByMail(mail);
    }

    public LoginPojo findParismonByActiveToken(String id) {
        return parismonRepository.findParismonByActiveToken(id);
    }

    public void updateParismon(LoginPojo parismon) {
        parismonRepository.updateParismon(parismon);
    }

    public LoginPojo insertParismon(LoginPojo parismon) {
        return parismonRepository.insert(parismon);
    }

}
