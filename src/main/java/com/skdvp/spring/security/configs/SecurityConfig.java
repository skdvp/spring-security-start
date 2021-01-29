package com.skdvp.spring.security.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/authenticated/**").authenticated()
                .antMatchers("/only_for_admins/**").hasRole("ADMIN")
                .antMatchers("/read_profile/**").hasAuthority("READ_PROFILE")
                .and()
                .formLogin()
                .and()
                .logout().logoutSuccessUrl("/");

    }

    @Bean
    public JdbcUserDetailsManager user(DataSource dataSource) {

        UserDetails user = User.builder()
                .username("user")
                .password("{bcrypt}$2a$10$5Oe1Y6AafLNqu9b4/jTptuysvHAcmFFk/RCe09yLzl74MBBedOINy")  //100
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$10$5Oe1Y6AafLNqu9b4/jTptuysvHAcmFFk/RCe09yLzl74MBBedOINy")  //100
                .roles("USER", "ADMIN")
                .build();

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        if (jdbcUserDetailsManager.userExists(user.getUsername())) {
            jdbcUserDetailsManager.deleteUser(user.getUsername());
        }
        if (jdbcUserDetailsManager.userExists(admin.getUsername())) {
            jdbcUserDetailsManager.deleteUser(admin.getUsername());
        }

        jdbcUserDetailsManager.createUser(user);
        jdbcUserDetailsManager.createUser(admin);

        return jdbcUserDetailsManager;
    }


}
