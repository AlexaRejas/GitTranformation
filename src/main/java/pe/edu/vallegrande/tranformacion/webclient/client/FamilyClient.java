package pe.edu.vallegrande.tranformacion.webclient.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import pe.edu.vallegrande.tranformacion.dto.FamilyDTO;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FamilyClient {

    private final WebClient coreServiceWebClient;
    private static final String BASE_PATH = "/api/v1/families";

    /**
     * Buscar una familia por ID.
     */
    public Mono<FamilyDTO> findById(Long id) {
        return coreServiceWebClient.get()
                .uri(BASE_PATH + "/{id}", id)
                .retrieve()
                .bodyToMono(FamilyDTO.class)
                .onErrorResume(WebClientResponseException.NotFound.class, e -> Mono.empty());
    }
}