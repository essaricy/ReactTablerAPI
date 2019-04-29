package com.softvision.digital.web.config.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        http
                .authorizeRequests()
                .antMatchers("/api/login/v1/**").permitAll()
                .antMatchers("/api/invoice/v1/**").authenticated();
        //http.authorizeRequests().antMatchers("/api/**").permitAll();
        //http.authorizeRequests().antMatchers("/api/invoice/v1/**").authenticated();
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/api/login/v1/**");
        //webSecurity.ignoring().antMatchers("/api/**");
    }

}
