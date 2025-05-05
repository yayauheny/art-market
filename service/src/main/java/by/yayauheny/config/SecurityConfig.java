package by.yayauheny.config;

import static by.yayauheny.enums.Role.ADMIN;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(requestMatcher ->
            requestMatcher
                .requestMatchers("/login", "/users/register").permitAll()
                .requestMatchers("**/delete").hasAuthority(ADMIN.getAuthority())
                .requestMatchers(DELETE).hasAuthority(ADMIN.getAuthority())
                .anyRequest().authenticated())
        .httpBasic(withDefaults())
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login"))
        .formLogin(login -> login
            .loginPage("/login")
            .defaultSuccessUrl("/users")
        )
        .build();
  }
}
