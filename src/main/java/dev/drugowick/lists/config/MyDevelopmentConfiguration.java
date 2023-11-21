package dev.drugowick.lists.config;

import dev.drugowick.lists.domain.entity.MyList;
import dev.drugowick.lists.domain.entity.MyListItem;
import dev.drugowick.lists.service.MyListService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import java.util.List;

@Configuration
@EnableWebSecurity
@ConditionalOnProperty(name = "app.dev-mode", havingValue = "true")
public class MyDevelopmentConfiguration {

    private final MyListService myListService;
    public MyDevelopmentConfiguration(MyListService myListService) {
        this.myListService = myListService;
    }


    @Bean
    DevData developmentData() {
        return new DevData(myListService);
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

    private final MyListService myListService;

    public DevData(MyListService myListService) {
        this.myListService = myListService;
        System.out.println("Adding development data.");
        addData(DevUtil.USERNAME);
        addData(DevUtil.STRANGER);
    }

    private void addData(String username) {
        List.of("This is a list", "This is another list").forEach(title -> {
            var list = new MyList();
            list.setTitle(title);
            list.setDescription("'" + title + "' is a list that contains items of a list, the way a list should be.");
            list.setUsername(username);

            List.of("This is an item", "This is yet another item").forEach(itemTitle -> {
                var item = new MyListItem();
                item.setList(list);
                item.setDescription(itemTitle);
                list.addItem(item);
            });
            myListService.add(list);
        });

    }
}

record DevUtil() {
    public static String USERNAME = "developer";
    public static String STRANGER = "stranger";
}
