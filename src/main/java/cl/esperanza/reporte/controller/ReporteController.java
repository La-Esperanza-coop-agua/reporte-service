package cl.esperanza.reporte.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.esperanza.reporte.dto.CreateReporteRequest;
import cl.esperanza.reporte.model.Reporte;
import cl.esperanza.reporte.service.ReporteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @PostMapping("/generar-balance")
    public ResponseEntity<Reporte> crearBalanceTrimestral(@Valid @RequestBody CreateReporteRequest request) {
        Reporte reporteProcesado = reporteService.generarYGuardarBalanceTrimestral(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteProcesado);
    }
}