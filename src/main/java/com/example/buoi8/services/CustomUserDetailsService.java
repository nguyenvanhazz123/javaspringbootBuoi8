package com.example.buoi8.services;

import com.example.buoi8.entity.User;
import com.example.buoi8.respository.IUserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private IUserRespository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User u = userRepository.findByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        Set<GrantedAuthority> authorities = u.getRoles().stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
        return new
                org.springframework.security.core.userdetails.User(username, u.getPassword(), authorities);
    }
}