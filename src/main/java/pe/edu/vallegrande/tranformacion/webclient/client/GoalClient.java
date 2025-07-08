package pe.edu.vallegrande.tranformacion.webclient.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.tranformacion.dto.GoalDTO;
import reactor.core.publisher.Mono;

@Service
public class GoalClient {

    private final WebClient webClient;

    @Autowired
    public GoalClient(@Qualifier("goalWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<GoalDTO> getGoalById(Long id) {
        return webClient.get()
                .uri("goal/listar/{id}", id)
                .retrieve()
                .bodyToMono(GoalDTO.class);
    }
}