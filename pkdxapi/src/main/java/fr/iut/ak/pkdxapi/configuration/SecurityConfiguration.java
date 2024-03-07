package fr.iut.ak.pkdxapi.configuration;


import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import fr.iut.ak.pkdxapi.repositories.UserRepository;
import fr.iut.ak.pkdxapi.services.CustomUserDetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests((authorizeRequests) -> authorizeRequests                                
        .requestMatchers("/users/register").permitAll()               
        .requestMatchers("/whois").hasAuthority("ROLE_ADMIN")
        .requestMatchers("/pkmn/types**").authenticated() 
    )                      
            .httpBasic(Customizer.withDefaults()).csrf(csrf->csrf.disable()) ;  // disable csrf security to authorize post, patch & delete

        return http.build();
    }
    
     @Bean
     public UserDetailsService userDetailsService(){
         return new CustomUserDetailsService(userRepository);
     }
     
    private UserRepository userRepository;
    public SecurityConfiguration(UserRepository userRepository){
       this.userRepository = userRepository;
    }
    
    @Bean
    public static PasswordEncoder passwordEncoder(){
	return new BCryptPasswordEncoder();
    }
}
