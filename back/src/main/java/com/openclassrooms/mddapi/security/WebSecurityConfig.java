package com.openclassrooms.mddapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.openclassrooms.mddapi.security.jwt.AuthTokenFilter;

/**
 * Classe de configuration de la sécurité Web.
 * 
 * @see WebSecurityConfigurerAdapter
 */
@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  
  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private AuthenticationEntryPoint unauthorizedHandler;

  /**
   * Crée un nouveau filtre d'authentification JWT.
   * 
   * @return Le nouveau filtre d'authentification JWT.
   * @see AuthTokenFilter
   */
  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  /**
   * Configure le gestionnaire d'authentification avec un service de détails d'utilisateur et un encodeur de mot de passe.
   * 
   * @param authenticationManagerBuilder Le constructeur du gestionnaire d'authentification.
   * @throws Exception Si une erreur se produit lors de la configuration.
   * @see AuthenticationManagerBuilder
   * @see UserDetailsService
   * @see PasswordEncoder
   */
  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  /**
   * Crée un nouveau gestionnaire d'authentification.
   * 
   * @return Le nouveau gestionnaire d'authentification.
   * @throws Exception Si une erreur se produit lors de la création.
   * @see AuthenticationManager
   */
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  /**
   * Crée un nouvel encodeur de mot de passe.
   * 
   * @return Le nouvel encodeur de mot de passe.
   * @see BCryptPasswordEncoder
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Configure la sécurité HTTP.
   * 
   * @param http Le constructeur de la sécurité HTTP.
   * @throws Exception Si une erreur se produit lors de la configuration.
   * @see HttpSecurity
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
      .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
      .authorizeRequests().antMatchers("/api/auth/**").permitAll()
      .antMatchers("/api/**").authenticated()
      .anyRequest().authenticated();

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
  }
}