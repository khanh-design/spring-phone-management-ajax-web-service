package com.example.springphonemanagementajaxwebservice.security;


import com.example.springphonemanagementajaxwebservice.controller.CustomAccessDeniedHandler;
import com.example.springphonemanagementajaxwebservice.controller.CustomerSuccessHandler;
import com.example.springphonemanagementajaxwebservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public CustomerSuccessHandler customerSuccessHandle() {
        return new CustomerSuccessHandler();
    }

    @Bean
    public CustomAccessDeniedHandler CustomAccessDeniedHandle() {
        return new CustomAccessDeniedHandler();
    }

/*        @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}12345").roles("USER")
                .and()
                .withUser("admin").password("{noop}12345").roles("ADMIN");
    }*/
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder(10));
    }

/*    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/").permitAll()
                .requestMatchers("/api/smartphones/index").hasRole("USER")
                .requestMatchers("/api/smartphones/admin").hasRole("ADMIN")
                .and()
                .formLogin()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }*/

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // Disable crsf cho đường dẫn /api/**
//        http.csrf().ignoringRequestMatchers("/**");
        http.httpBasic();
        http
                .formLogin(formLogin -> formLogin.successHandler(customerSuccessHandle()))
                .formLogin(Customizer.withDefaults());
        http.authorizeHttpRequests(author -> author
//                .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
//                .requestMatchers(HttpMethod.GET,"/register").permitAll()
//                .requestMatchers(HttpMethod.POST,"/register").permitAll()
                        .requestMatchers("/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().denyAll()
        )
                .exceptionHandling(customizer -> customizer.accessDeniedHandler(CustomAccessDeniedHandle()));
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors();
        http
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.ALL))) // clear site data
                );
    }


    /*    @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .anyRequest().authenticated()  // (1)
                    .and()
                    .formLogin()
                    .and()   // (2)
                    .httpBasic();  // (3)
        }*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
