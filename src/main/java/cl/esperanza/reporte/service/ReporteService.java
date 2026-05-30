package cl.esperanza.reporte.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import cl.esperanza.reporte.dto.CreateReporteRequest;
import cl.esperanza.reporte.model.Reporte;
import cl.esperanza.reporte.repository.ReporteRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;

@Service
@Transactional
public class ReporteService {
    
    private final ReporteRepository reporteRepo;
    private final WebClient pagoWebClient;
    private final WebClient facturacionWebClient;
    private final WebClient reparacionWebClient;

    // Inyección por constructor de dependencias y WebClients
    public ReporteService(ReporteRepository reporteRepo, WebClient pagoWebClient, 
                          WebClient facturacionWebClient, WebClient reparacionWebClient) {
        this.reporteRepo = reporteRepo;
        this.pagoWebClient = pagoWebClient;
        this.facturacionWebClient = facturacionWebClient;
        this.reparacionWebClient = reparacionWebClient;
    }

    public Reporte generarYGuardarBalanceTrimestral(CreateReporteRequest request) {
        try {
            // 1. Obtener Ingresos desde pago-service (8084)
            Double ingresos = pagoWebClient.get()
                    .uri("/total-recaudado")
                    .retrieve()
                    .bodyToMono(Double.class)
                    .block();

            // 2. Obtener Egresos Operacionales desde facturacion-service (8083)
            Integer egresosOps = facturacionWebClient.get()
                    .uri("/gasto/total-monto")
                    .retrieve()
                    .bodyToMono(Integer.class)
                    .block();

            // 3. Obtener Egresos Técnicos desde reparacion-service (8081)
            Double egresosTec = reparacionWebClient.get()
                    .uri("/total-costos")
                    .retrieve()
                    .bodyToMono(Double.class)
                    .onErrorReturn(0.0) // Resguardo por si reparaciones no está arriba todavía
                    .block();

            // Sanitizar nulos procedentes de la red
            double totalIngresos = (ingresos != null) ? ingresos : 0.0;
            double totalEgresos = ((egresosOps != null) ? egresosOps : 0.0) + ((egresosTec != null) ? egresosTec : 0.0);

            // 4. Instanciar el objeto Reporte mapeando los datos desde el DTO
            Reporte nuevoReporte = new Reporte();
            nuevoReporte.setFechaGeneracion(LocalDate.now());
            nuevoReporte.setAnio(request.anio());
            nuevoReporte.setTrimestre(request.trimestre());
            nuevoReporte.setIngresosTotales(totalIngresos);
            nuevoReporte.setEgresosTotales(totalEgresos);
            nuevoReporte.setEntidadDestino(request.entidadDestino());
            
            // Evaluación automática para postulación a fondos de desarrollo de APR
            nuevoReporte.setSirveParaPostulacion(totalIngresos >= totalEgresos); 
            nuevoReporte.setInformeTecnico("Balance financiero trimestral procesado automáticamente. Estado: " + 
                    (totalIngresos >= totalEgresos ? "SUPERÁVIT COMITÉ" : "DÉFICIT EN CAJA"));

            // Guardar en la tabla 'reporte' de Neon
            return reporteRepo.save(nuevoReporte);

        } catch (Exception e) {
            throw new RuntimeException("Error en la agregación de balances financieros: " + e.getMessage());
        }
    }
}