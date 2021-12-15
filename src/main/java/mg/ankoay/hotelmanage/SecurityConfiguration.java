/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.ankoay.hotelmanage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import mg.ankoay.hotelmanage.security.AdminAuthority;

/**
 *
 * @author lacha
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
    	String [] publicUrls = new String [] {
                "/api/**"
        };
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/perform_logout").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/images/**").permitAll()
                
                .antMatchers("/api/**").permitAll()
                
                .antMatchers("/back/statistics/**").hasAuthority(AdminAuthority.ADMIN)
                
                //.anyRequest().permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=Nom ou mot de passe incorrect")
                .and()
                .logout()
                .logoutUrl("/perform_logout")
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
                .and()
                .csrf()
                .ignoringAntMatchers(publicUrls);
        http.cors();
    }

}
