package com.conal.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//When the client application needs to acquire an Access Token, it will do so after a simple form-login driven auth process:
@Configuration
@Slf4j
public class ServerSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        log.info("Configuring AuthenticationManagerBuilder with user john, password 123, roles USER.");
        auth.inMemoryAuthentication()
                .withUser("john")
                .password("123")
                .roles("USER");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        log.info("Configuring AuthenticationManagerBean.");
        return super.authenticationManagerBean();
    }

    // note - the form login configuration isn't actually necessary for the password flow, only for the implicit flow
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        log.info("Configuring HttpSecurity.");
        http.csrf().disable()
                .authorizeRequests().antMatchers("/login", "/oauth/token").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll();
    }
}
