package com.skdvp.spring.security.controllres;

import com.skdvp.spring.security.entities.User;
import com.skdvp.spring.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String homePage(){
        return "home";
    }

    @GetMapping("/authenticated")
    public String pageForAuthenticatedUsers(Principal principal){
        User user = userService.findByUsername(principal.getName());
        return "secured start of web service: " + user.getUsername() + " " + user.getEmail();
    }

    @GetMapping("/read_profile")
    public String pageForReadProfile(){
        return "read profile page";
    }

    @GetMapping("/only_for_admins")
    public String pageOnlyForAdmins(){
        return "admins page";
    }


}
