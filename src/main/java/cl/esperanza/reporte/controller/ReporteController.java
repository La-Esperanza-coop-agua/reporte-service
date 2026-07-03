package cl.esperanza.reporte.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.esperanza.reporte.dto.CreateReporteRequest;
import cl.esperanza.reporte.model.Reporte;
import cl.esperanza.reporte.service.ReporteService;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/reportes")
@Tag(name = "Reportes", description = "Gestión de los balances y reportes de la cooperativa de agua")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @Operation(summary = "Generar un nuevo balance trimestral", 
                description = "Calcula ingresos, egresos y evalúa si califica para postulaciones.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Balance generado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error de validación en los datos enviados"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/generar-balance")
    public ResponseEntity<Reporte> crearBalanceTrimestral(@Valid @RequestBody CreateReporteRequest request) {
        try {
            Reporte reporteProcesado = reporteService.generarYGuardarBalanceTrimestral(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(reporteProcesado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}