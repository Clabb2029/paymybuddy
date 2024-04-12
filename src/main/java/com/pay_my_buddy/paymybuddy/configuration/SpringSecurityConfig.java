package com.pay_my_buddy.paymybuddy.configuration;

import com.pay_my_buddy.paymybuddy.repository.UserRepository;
import com.pay_my_buddy.paymybuddy.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(
                    request -> request
                            .requestMatchers("/register/**", "/login", "styles.css").permitAll()
                            .anyRequest().authenticated()
                )
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/home")
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/login?logout")
                                .deleteCookies("JSESSIONID")
                                .invalidateHttpSession(true)
                );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(userRepository);
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    ApplicationListener<AuthenticationFailureBadCredentialsEvent> failureEvent() {
        return event -> System.err.println("Bad Credentials login " + event.getAuthentication().getClass().getSimpleName() + " - " + event.getAuthentication().getName());
    }

    @Bean
    ApplicationListener<AuthenticationSuccessEvent> successEvent() {
        return event -> System.out.println("Success login " + event.getAuthentication().getClass().getSimpleName() + " - " + event.getAuthentication().getName());
    }

}
