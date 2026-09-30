package master.security;

import master.entity.Role;
import master.entity.User;
import master.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Cree un compte administrateur de demonstration au demarrage, uniquement s'il
 * n'existe pas deja et si la base est vide de tout utilisateur "admin".
 * Pratique pour tester tout de suite les routes reservees au role ADMIN (ex: DELETE)
 * sans devoir modifier une ligne en base a la main.
 *
 * IMPORTANT : mot de passe de demonstration uniquement, a changer/retirer en production.
 */
@Component
public class DataInitializer {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void seedAdmin() {
        if (!userRepository.existsByUsername(ADMIN_USERNAME)) {
            User admin = new User();
            admin.setUsername(ADMIN_USERNAME);
            admin.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }
    }
}
