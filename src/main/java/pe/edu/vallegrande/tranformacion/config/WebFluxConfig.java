package pe.edu.vallegrande.tranformacion.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WebFluxConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:4200") // Mantener allowedOriginPatterns
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Agregado OPTIONS
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600); // Agregado cache time
    }
}