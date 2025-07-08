package pe.edu.vallegrande.tranformacion.service;

import pe.edu.vallegrande.tranformacion.dto.TransformationDTO;
import reactor.core.publisher.Mono;
import java.util.List;

public interface TransformationService {

    Mono<TransformationDTO> createTransformation(TransformationDTO transformationDTO);

    List<TransformationDTO> getAllTransformations();

    TransformationDTO getTransformationById(Long id);

    TransformationDTO updateTransformation(TransformationDTO transformationDTO);

    void deleteTransformation(Long id);

}