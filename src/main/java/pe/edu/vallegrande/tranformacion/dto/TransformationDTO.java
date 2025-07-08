package pe.edu.vallegrande.tranformacion.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransformationDTO {
    private Long id;
    private Date firstPlanDate;
    private Date lastUpdateDate;
    private String duration;
    private String status;

    // IDs para la creación/actualización (compatibilidad con requests simples)
    private Long goalId;
    private Long familyId;

    // Objetos completos para la respuesta y requests complejos
    private GoalDTO goal;
    private FamilyDTO family;

    // Métodos helper para obtener los IDs cuando los objetos están presentes
    public Long getGoalId() {
        if (goalId != null) {
            return goalId;
        }
        return goal != null ? goal.getId() : null;
    }

    public Long getFamilyId() {
        if (familyId != null) {
            return familyId;
        }
        return family != null ? family.getId() : null;
    }

    // Métodos setter para asegurar compatibilidad
    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }
}



