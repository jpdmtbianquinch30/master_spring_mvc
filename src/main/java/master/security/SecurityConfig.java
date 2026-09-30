package master.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Regles d'acces aux routes. Adapte du support de cours (section 9, SecurityConfig.java)
 * a ce projet Spring MVC "classique" (pas Spring Boot) :
 *  - on ne securise QUE l'API REST (/api/**) : c'est l'objet du cours fourni.
 *  - les pages MVC Thymeleaf (/products, /types, ...) et Swagger restent en libre acces,
 *    puisqu'il n'existe pas de formulaire de login pour ces pages ; les proteger de la
 *    meme facon casserait l'IHM existante sans apporter de valeur pedagogique ici.
 *  - lecture (GET) des produits/types : publique.
 *  - creation/modification (POST/PUT/PATCH) : necessite d'etre authentifie (n'importe quel role).
 *  - suppression (DELETE) : reservee au role ADMIN.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .exceptionHandling(handling -> handling
                        // Sans token / token invalide -> 401 (au lieu du 403 par defaut faute d'entry point)
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendError(javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED,
                                        "Authentification requise"))
                        // Authentifie mais role insuffisant -> 403
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                response.sendError(javax.servlet.http.HttpServletResponse.SC_FORBIDDEN,
                                        "Acces refuse : role insuffisant"))
                )
                .authorizeHttpRequests(auth -> auth
                        // Routes publiques : inscription et connexion
                        .requestMatchers(new AntPathRequestMatcher("/api/auth/**")).permitAll()

                        // Documentation Swagger
                        .requestMatchers(
                                new AntPathRequestMatcher("/swagger-ui/**"),
                                new AntPathRequestMatcher("/swagger-resources/**"),
                                new AntPathRequestMatcher("/v2/api-docs/**"),
                                new AntPathRequestMatcher("/webjars/**")
                        ).permitAll()

                        // Lecture publique des produits/types
                        .requestMatchers(
                                new AntPathRequestMatcher("/api/products/**", HttpMethod.GET.name()),
                                new AntPathRequestMatcher("/api/types/**", HttpMethod.GET.name())
                        ).permitAll()

                        // Creation / modification : etre authentifie suffit
                        .requestMatchers(
                                new AntPathRequestMatcher("/api/products/**", HttpMethod.POST.name()),
                                new AntPathRequestMatcher("/api/types/**", HttpMethod.POST.name())
                        ).authenticated()
                        .requestMatchers(
                                new AntPathRequestMatcher("/api/products/**", HttpMethod.PUT.name()),
                                new AntPathRequestMatcher("/api/types/**", HttpMethod.PUT.name())
                        ).authenticated()
                        .requestMatchers(
                                new AntPathRequestMatcher("/api/products/**", HttpMethod.PATCH.name()),
                                new AntPathRequestMatcher("/api/types/**", HttpMethod.PATCH.name())
                        ).authenticated()

                        // Suppression : reservee aux administrateurs
                        .requestMatchers(
                                new AntPathRequestMatcher("/api/products/**", HttpMethod.DELETE.name()),
                                new AntPathRequestMatcher("/api/types/**", HttpMethod.DELETE.name())
                        ).hasRole("ADMIN")

                        // Le reste (pages MVC Thymeleaf, ressources statiques...) : hors perimetre de ce cours,
                        // on les laisse accessibles pour ne pas casser l'IHM existante.
                        .anyRequest().permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // a restreindre en prod
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}