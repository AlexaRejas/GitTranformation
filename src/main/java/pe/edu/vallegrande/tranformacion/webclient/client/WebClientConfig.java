package pe.edu.vallegrande.tranformacion.webclient.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {


    @Bean(name = "goalWebClient")
    public WebClient goalWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://vg-ms-goal.onrender.com") // Sin token por ahora
                .defaultHeader("Content-Type", "application/json")
                .build();
    }


    @Value("${service.api.family}")
    private String familyUrl;

    /**
     * Configura el WebClient con base URL y filtro JWT.
     */
    @Bean
    public WebClient coreServiceWebClient() {
        return WebClient.builder()
                .baseUrl(familyUrl)
                .filter(authHeaderFilter()) // Añade token JWT si existe en el contexto
                .build();
    }

    /**
     * Filtro que agrega Authorization si está presente.
     */
    private ExchangeFilterFunction authHeaderFilter() {
        return (request, next) -> Mono.deferContextual(ctx -> {
            if (ctx.hasKey("Authorization")) {
                String token = ctx.get("Authorization");
                return next.exchange(
                        ClientRequest.from(request)
                                .headers(headers -> headers.setBearerAuth(token))
                                .build()
                );
            }
            return next.exchange(request);
        });
    }
}