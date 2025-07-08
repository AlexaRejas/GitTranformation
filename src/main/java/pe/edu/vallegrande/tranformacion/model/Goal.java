package pe.edu.vallegrande.tranformacion.model;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "Goal")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String indicator;
    private String objective;
    private String currentSituation;
    private String status;
    private Long sessionId; // Si es necesario
}