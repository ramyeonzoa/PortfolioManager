package inhatc.cse.portfoliomanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin(form -> form
                .loginPage("/member/login")
                .defaultSuccessUrl("/")
                .failureUrl("/member/login/failure")
                .usernameParameter("email")
                .permitAll()
        );

        http.logout(Customizer.withDefaults());

        http.authorizeHttpRequests(request -> request
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/member/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
        );

        // TODO : Implement exceptionHandling

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
