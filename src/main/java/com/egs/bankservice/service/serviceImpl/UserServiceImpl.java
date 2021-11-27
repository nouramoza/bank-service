package com.egs.bankservice.service.serviceImpl;

import com.egs.bankservice.service.service.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = new User("username","", null);
        if(user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("User with username = " + s + " Not Found");
        }
    }
}
