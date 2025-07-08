package pe.edu.vallegrande.tranformacion.service.Impl;

import org.springframework.stereotype.Service;
import pe.edu.vallegrande.tranformacion.dto.FamilyDTO;
import pe.edu.vallegrande.tranformacion.dto.GoalDTO;
import pe.edu.vallegrande.tranformacion.dto.TransformationDTO;
import pe.edu.vallegrande.tranformacion.mapper.TransformationMapper;
import pe.edu.vallegrande.tranformacion.model.Transformation;
import pe.edu.vallegrande.tranformacion.repository.TransformationRepository;
import pe.edu.vallegrande.tranformacion.service.TransformationService;
import pe.edu.vallegrande.tranformacion.webclient.client.FamilyClient;
import pe.edu.vallegrande.tranformacion.webclient.client.GoalClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class TransformationServiceImpl implements TransformationService {

    private final GoalClient goalClient;
    private final FamilyClient familyClient;
    private final TransformationRepository transformationRepository;
    private final TransformationMapper transformationMapper;

    public TransformationServiceImpl(GoalClient goalClient, FamilyClient familyClient,
                                     TransformationRepository transformationRepository,
                                     TransformationMapper transformationMapper) {
        this.goalClient = goalClient;
        this.familyClient = familyClient;
        this.transformationRepository = transformationRepository;
        this.transformationMapper = transformationMapper;
    }

    @Override
    public Mono<TransformationDTO> createTransformation(TransformationDTO transformationDTO) {
        return goalClient.getGoalById(transformationDTO.getGoalId())
                .flatMap(goalDTO -> {
                    if (goalDTO == null) {
                        return Mono.error(new RuntimeException("Goal no encontrado con ID: " + transformationDTO.getGoalId()));
                    }
                    return familyClient.findById(transformationDTO.getFamilyId())
                            .flatMap(familyDTO -> {
                                if (familyDTO == null) {
                                    return Mono.error(new RuntimeException("Familia no encontrada con ID: " + transformationDTO.getFamilyId()));
                                }
                                try {
                                    // Asignar los objetos completos al DTO
                                    transformationDTO.setGoal(goalDTO);
                                    transformationDTO.setFamily(familyDTO);

                                    Transformation transformation = transformationMapper.toEntity(transformationDTO);
                                    Transformation savedTransformation = transformationRepository.save(transformation);
                                    TransformationDTO resultDTO = transformationMapper.toDTO(savedTransformation);
                                    return Mono.just(resultDTO);
                                } catch (Exception e) {
                                    return Mono.error(new RuntimeException("Error al guardar la transformación: " + e.getMessage()));
                                }
                            });
                })
                .onErrorResume(e -> {
                    String errorMessage = e.getMessage() != null ? e.getMessage() : "Error desconocido";
                    return Mono.error(new RuntimeException("Error al crear la transformación: " + errorMessage));
                });
    }

    @Override
    public List<TransformationDTO> getAllTransformations() {
        List<Transformation> transformations = transformationRepository.findAll();
        return transformationMapper.toDTOList(transformations);
    }

    @Override
    public TransformationDTO getTransformationById(Long id) {
        Optional<Transformation> transformation = transformationRepository.findById(id);
        return transformation.map(transformationMapper::toDTO).orElse(null);
    }

    @Override
    public TransformationDTO updateTransformation(TransformationDTO transformationDTO) {
        // Verificar si la transformación existe
        Optional<Transformation> existingTransformation = transformationRepository.findById(transformationDTO.getId());
        if (!existingTransformation.isPresent()) {
            throw new RuntimeException("Transformación no encontrada con ID: " + transformationDTO.getId());
        }

        // Verificar si el Goal existe usando block() (no recomendado pero funcional)
        try {
            GoalDTO goalDTO = goalClient.getGoalById(transformationDTO.getGoalId()).block();
            if (goalDTO == null) {
                throw new RuntimeException("Goal no encontrado con ID: " + transformationDTO.getGoalId());
            }
            transformationDTO.setGoal(goalDTO);

            // Verificar si la Familia existe
            FamilyDTO familyDTO = familyClient.findById(transformationDTO.getFamilyId()).block();
            if (familyDTO == null) {
                throw new RuntimeException("Familia no encontrada con ID: " + transformationDTO.getFamilyId());
            }
            transformationDTO.setFamily(familyDTO);

            Transformation transformation = transformationMapper.toEntity(transformationDTO);
            Transformation updatedTransformation = transformationRepository.save(transformation);
            return transformationMapper.toDTO(updatedTransformation);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la transformación: " + e.getMessage());
        }
    }

    @Override
    public void deleteTransformation(Long id) {
        Optional<Transformation> transformation = transformationRepository.findById(id);
        if (!transformation.isPresent()) {
            throw new RuntimeException("Transformación no encontrada con ID: " + id);
        }
        transformationRepository.deleteById(id);
    }
}