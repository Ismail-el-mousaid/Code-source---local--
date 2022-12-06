package org.sid.msModules.sec;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Adaptateur Config
@Configuration
public class KeycloakAdapterConfig {
    //La configuration sera basé sur spring boot -> application.properties (ne plus chercher keycloak.json)
    @Bean
    public KeycloakSpringBootConfigResolver springBootConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

}
