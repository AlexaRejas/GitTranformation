package pe.edu.vallegrande.tranformacion.mapper;

import org.springframework.stereotype.Component;
import pe.edu.vallegrande.tranformacion.dto.*;
import pe.edu.vallegrande.tranformacion.model.*;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransformationMapper {

    public TransformationDTO toDTO(Transformation transformation) {
        TransformationDTO dto = new TransformationDTO();
        dto.setId(transformation.getId());
        dto.setFirstPlanDate(transformation.getFirstPlanDate());
        dto.setLastUpdateDate(transformation.getLastUpdateDate());
        dto.setDuration(transformation.getDuration());
        dto.setStatus(transformation.getStatus());

        if (transformation.getGoal() != null) {
            dto.setGoalId(transformation.getGoal().getId());
        }

        if (transformation.getFamily() != null) {
            dto.setFamilyId(transformation.getFamily().getId());
        }

        return dto;
    }

    public Transformation toEntity(TransformationDTO dto) {
        Transformation transformation = new Transformation();
        transformation.setId(dto.getId());
        transformation.setFirstPlanDate(dto.getFirstPlanDate());
        transformation.setLastUpdateDate(dto.getLastUpdateDate());
        transformation.setDuration(dto.getDuration());
        transformation.setStatus(dto.getStatus());

        // Asignar Goal con ID
        if (dto.getGoalId() != null) {
            Goal goal = new Goal();
            goal.setId(dto.getGoalId());
            transformation.setGoal(goal);
        } else if (dto.getGoal() != null && dto.getGoal().getId() != null) {
            Goal goal = new Goal();
            goal.setId(dto.getGoal().getId());
            transformation.setGoal(goal);
        }

        // Asignar Family con ID
        if (dto.getFamilyId() != null) {
            Family family = new Family();
            family.setId(dto.getFamilyId());
            transformation.setFamily(family);
        } else if (dto.getFamily() != null && dto.getFamily().getId() != null) {
            Family family = new Family();
            family.setId(dto.getFamily().getId());
            transformation.setFamily(family);
        }

        return transformation;
    }

    public List<TransformationDTO> toDTOList(List<Transformation> transformations) {
        return transformations.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
