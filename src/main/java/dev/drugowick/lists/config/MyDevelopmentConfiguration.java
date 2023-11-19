package dev.drugowick.lists.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
@ConditionalOnProperty(name = "app.dev-mode", havingValue = "true")
public class MyDevelopmentConfiguration {

    public MyDevelopmentConfiguration() {}

    @Bean
    DevData developmentData() {
        return new DevData();
    }
}

/**
 * The local dev security config, that enables a developer/developer user
 */
@Configuration
@ConditionalOnProperty(name = "app.dev-mode", havingValue = "true")
class DevSecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        var admin = User
                .withUsername(DevUtil.USERNAME)
                .password("{noop}" + DevUtil.USERNAME)
                .roles("USER", "ADMIN")
                .build();
        var user = User
                .withUsername(DevUtil.STRANGER)
                .password("{noop}" + DevUtil.STRANGER)
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        // set the name of the attribute the CsrfToken will be populated on
        requestHandler.setCsrfRequestAttributeName(null);
        http.authorizeHttpRequests()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().formLogin()
                .and().csrf((csrf) -> csrf
                        .csrfTokenRequestHandler(requestHandler)
                );
        return http.build();
    }
}

/**
 * A class data inserts data for the developer user
 */
class DevData {

    public DevData() {
        System.out.println("Adding development data.");
        addData(DevUtil.USERNAME);
        addData(DevUtil.STRANGER);
    }

    private void addData(String username) {
        System.out.println("Not doing anything...");
    }
}

record DevUtil() {
    public static String USERNAME = "developer";
    public static String STRANGER = "stranger";
}
