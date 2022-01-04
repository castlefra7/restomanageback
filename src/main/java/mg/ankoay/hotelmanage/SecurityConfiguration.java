/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.ankoay.hotelmanage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import mg.ankoay.hotelmanage.security.AdminAuthority;
import mg.ankoay.hotelmanage.security.jwt.JWTAuthenticationFilter;
import mg.ankoay.hotelmanage.security.jwt.JWTAuthorizationFilter;
import mg.ankoay.hotelmanage.security.jwt.SecurityConstants;

/**
 *
 * @author lacha
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Configuration
	@Order(1)
	public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		protected void configure(HttpSecurity http) throws Exception {
			http
            .antMatcher("/api/**")                               
			.authorizeRequests()
			.antMatchers(SecurityConstants.SIGN_UP_URL).permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(new JWTAuthenticationFilter(authenticationManager()))
            .addFilter(new JWTAuthorizationFilter(authenticationManager()))
            // this disables session creation on Spring Security
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .csrf().disable();
		}
	}

	@Configuration
	public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(final HttpSecurity http) throws Exception {

			http.authorizeRequests().antMatchers("/login").permitAll().antMatchers("/perform_logout").permitAll()
					.antMatchers("/css/**").permitAll().antMatchers("/js/**").permitAll().antMatchers("/images/**")
					.permitAll()
					
					.antMatchers("/back/statistics/**").hasAuthority(AdminAuthority.ADMIN)
					
					.antMatchers("/stat/**").hasAuthority(AdminAuthority.ADMIN)

					// .anyRequest().permitAll()
					.anyRequest().authenticated()

					.and()

					.formLogin().loginPage("/login").loginProcessingUrl("/perform_login").defaultSuccessUrl("/", true)
					.failureUrl("/login?error=Nom ou mot de passe incorrect")

					.and()

					.logout().logoutUrl("/perform_logout").logoutSuccessUrl("/login").deleteCookies("JSESSIONID")

					.and()

					.csrf();
			// .ignoringAntMatchers(publicUrls);
			http.cors();
		}
	}

}
