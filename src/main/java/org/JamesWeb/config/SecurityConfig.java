    package org.JamesWeb.config;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.AuthenticationProvider;
    import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
    import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
    import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

    @Configuration
    @EnableWebSecurity
    public class SecurityConfig{
        @Autowired
        private UserDetailsService userDetailsService;
        private final SessionCheckFilter sessionCheckFilter;
        public SecurityConfig(SessionCheckFilter sessionCheckFilter){
            this.sessionCheckFilter = sessionCheckFilter;
        }
        @Autowired
        private jwtFilterAuthentication jwtFilterAuthentication;
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http)
                throws Exception {

            http
                    .cors(cors->{})
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(auth -> auth

                            .requestMatchers(
                                    new AntPathRequestMatcher("/"),
                                    new AntPathRequestMatcher("/processPayment/**"),
                                    new AntPathRequestMatcher("/api/admin/login")
                            )
                            .permitAll()

                            .requestMatchers(
                                    new AntPathRequestMatcher("/register/**"),
                                    new AntPathRequestMatcher("/css/**"),
                                    new AntPathRequestMatcher("/js/**"),
                                    new AntPathRequestMatcher("/images/**")
                            )
                            .permitAll()
                            .requestMatchers(
                                    new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
                            .requestMatchers(
                                    new AntPathRequestMatcher("/user/**")).hasRole("USER")
                            .anyRequest()
                            .authenticated()
                    );
            http.logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
            );
            http.addFilterBefore(
                    jwtFilterAuthentication,
                    UsernamePasswordAuthenticationFilter.class);
            http.addFilterAfter(
                     sessionCheckFilter,
                    UsernamePasswordAuthenticationFilter.class
            );

            return http.build();
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {

            DaoAuthenticationProvider provider =
                    new DaoAuthenticationProvider();

            provider.setUserDetailsService(userDetailsService);

            provider.setPasswordEncoder(passwordEncoder());

            return provider;
        }
        @Bean
        public AuthenticationManager authenticationManager(
                AuthenticationConfiguration configuration)
                throws Exception {

            return configuration.getAuthenticationManager();
        }
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

    }


