package master.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Enregistre automatiquement le filtre "springSecurityFilterChain" (DelegatingFilterProxy)
 * dans le conteneur Servlet, sans avoir a le declarer a la main dans web.xml.
 * Il ira chercher le bean springSecurityFilterChain (cree par @EnableWebSecurity dans
 * SecurityConfig) dans le contexte RACINE, charge par ContextLoaderListener (voir web.xml
 * et root-context.xml) : c'est pour cela qu'un contexte racine est desormais indispensable.
 */
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
    // Rien a surcharger : le comportement par defaut convient (mapping sur /*)
}
