package com.arj.tiketkufinalproject.Security;

import com.arj.tiketkufinalproject.Model.UsersEntity;
import com.arj.tiketkufinalproject.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersRepository ui;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsersEntity> userinfo = ui.findByEmail(username);
        return userinfo.map(UserInfoUserDetails::new).orElseThrow(()->new UsernameNotFoundException("Username Doesn't Exsist" + username));

    }
}
