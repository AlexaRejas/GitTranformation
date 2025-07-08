package pe.edu.vallegrande.tranformacion.dto;

import lombok.Data;

@Data
public class GoalDTO {
    private Long id;
    private String name;
    private String indicator;
    private String objective;
    private String currentSituation;
    private String status;
    private Long sessionId; // Si es necesario
}