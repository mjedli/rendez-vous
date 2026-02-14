package com.example.demo.login;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.example.demo.login.model.*;

/**
 * @author mjedli
 */
@Service
public class UserServiceForLogin implements UserDetailsService {

    ParismonRepository parismonRepository;

    public UserServiceForLogin(ParismonRepository parismonRepository) {
        this.parismonRepository = parismonRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LoginPojo barber = parismonRepository.findByMail(username);

        final UserDetails userDetails = new UserDetails() {

            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isEnabled() {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            public boolean isAccountNonExpired() {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            public String getUsername() {
                // TODO Auto-generated method stub
                if (barber != null && barber.getActive() == true) {
                    return barber.getEmail();
                }

                return "";
            }

            @Override
            public String getPassword() {
                // TODO Auto-generated method stub
                if (barber != null && barber.getActive() == true) {
                    return barber.getPassword();
                }

                return "";
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                if (barber != null && barber.getActive()) {
                    return List.of(new SimpleGrantedAuthority("ROLE_" + barber.getRole()));
                }
                return List.of();
            }

        };


        return userDetails;
    }


}