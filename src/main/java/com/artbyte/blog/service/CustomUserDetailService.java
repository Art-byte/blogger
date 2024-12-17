package com.artbyte.blog.service;

import com.artbyte.blog.exception.RoleException;
import com.artbyte.blog.exception.UserException;
import com.artbyte.blog.model.Role;
import com.artbyte.blog.model.User;
import com.artbyte.blog.repository.RoleRepository;
import com.artbyte.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private UserService userService;
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        Role role = roleRepository.findById(user.getRoleId()).orElse(null);
        if(role == null){
            throw new RoleException("Role not found");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(
                        new SimpleGrantedAuthority(role.getName())
                )
        );
    }

}
