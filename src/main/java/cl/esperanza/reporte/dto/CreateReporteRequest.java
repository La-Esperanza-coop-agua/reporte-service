package cl.esperanza.reporte.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateReporteRequest(
    @Min(value = 2000, message = "El año no es válido") 
    int anio,
    
    @Min(value = 1, message = "El trimestre mínimo es 1") 
    @Max(value = 4, message = "El trimestre máximo es 4") 
    int trimestre,
    
    @NotBlank(message = "La entidad de destino no puede estar vacía") 
    String entidadDestino
) {}