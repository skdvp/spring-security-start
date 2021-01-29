package com.skdvp.spring.security.services;

import com.skdvp.spring.security.entities.Role;
import com.skdvp.spring.security.entities.User;
import com.skdvp.spring.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {


    @Autowired
    private  UserRepository userRepository;



    public User findByUsername(String username) { // наш User
        return userRepository.findByUsername(username);
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = findByUsername(username); // наш User

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),
                mapRolesToAuthority(user.getRoles())) ;
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthority(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    // перегон наших юзеров в юзеров, которые понимает Spring Security

    // список ролей из нашего User, вместо них нужно вернуть GrantedAuthority

    // роли
    // .преобразуем к стриму
    // .приводим Role к GrantedAuthority [role -> new SimpleGrantedAuthority(role.getName()))]
    // .после маппинга производится Collectors.toList()

    // этот метод из любой пачки ролей делает любую пачку authorities c точно такими же строками
}
