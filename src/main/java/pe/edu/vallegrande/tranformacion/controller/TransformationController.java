package pe.edu.vallegrande.tranformacion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.tranformacion.dto.TransformationDTO;
import pe.edu.vallegrande.tranformacion.service.TransformationService;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Transformation")
@CrossOrigin(origins = "*", allowCredentials = "false")
public class TransformationController {

    private final TransformationService transformationService;

    @Autowired
    public TransformationController(TransformationService transformationService) {
        this.transformationService = transformationService;
    }

    @GetMapping("/")
    public ResponseEntity<List<TransformationDTO>> obtenerTodasLasTransformaciones() {
        try {
            List<TransformationDTO> transformations = transformationService.getAllTransformations();
            return ResponseEntity.ok(transformations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerTransformacionPorId(@PathVariable Long id) {
        try {
            TransformationDTO transformation = transformationService.getTransformationById(id);
            return transformation != null ? ResponseEntity.ok(transformation)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transformación no encontrada con ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    @PostMapping("/crear")
    public Mono<ResponseEntity<Object>> crearTransformacion(@RequestBody TransformationDTO transformationDTO) {
        return transformationService.createTransformation(transformationDTO)
                .map(createdTransformation -> ResponseEntity.status(HttpStatus.CREATED).body((Object) createdTransformation))
                .onErrorResume(e -> {
                    String errorMessage = e.getMessage() != null ? e.getMessage() : "Error desconocido al crear la transformación";
                    Map<String, String> errorBody = new HashMap<>();
                    errorBody.put("error", errorMessage);
                    return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody));
                });
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarTransformacion(@PathVariable Long id, @RequestBody TransformationDTO transformationDTO) {
        try {
            // Asegurar que el ID del path coincida con el del DTO
            transformationDTO.setId(id);
            TransformationDTO updatedTransformation = transformationService.updateTransformation(transformationDTO);
            return ResponseEntity.ok(updatedTransformation);
        } catch (RuntimeException e) {
            String errorMessage = e.getMessage() != null ? e.getMessage() : "Error al actualizar la transformación";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } catch (Exception e) {
            String errorMessage = e.getMessage() != null ? e.getMessage() : "Error interno del servidor";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarTransformacion(@PathVariable Long id) {
        try {
            transformationService.deleteTransformation(id);
            return ResponseEntity.ok("Transformación eliminada correctamente.");
        } catch (RuntimeException e) {
            String errorMessage = e.getMessage() != null ? e.getMessage() : "Error al eliminar la transformación";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } catch (Exception e) {
            String errorMessage = e.getMessage() != null ? e.getMessage() : "Error interno del servidor";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }



}