package cl.esperanza.reporte.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import cl.esperanza.reporte.dto.CreateReporteRequest;
import cl.esperanza.reporte.model.Reporte;
import cl.esperanza.reporte.repository.ReporteRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReporteService {
    
    @Autowired
    private ReporteRepository reporteRepo;

    @Autowired
    private WebClient pagoWebClient;

    @Autowired
    private WebClient facturacionWebClient;

    @Autowired
    private WebClient reparacionWebClient;

    public Reporte generarYGuardarBalanceTrimestral(CreateReporteRequest request) {
        try {
            Double totalIngresos = 0.0;
            Double totalEgresos = 0.0;

            try {
                // Usando 'pagoWebClient' en singular como lo tienes declarado
                totalIngresos = pagoWebClient.get()
                    .uri("/total-recaudado")
                    .retrieve()
                    .bodyToMono(Double.class)
                    .defaultIfEmpty(0.0) 
                    .block();
            } catch (Exception e) {
                System.err.println("No se pudo conectar con Pagos, usando 0.0: " + e.getMessage());
            }

            try {
                totalEgresos = reparacionWebClient.get()
                    .uri("/total-costos")
                    .retrieve()
                    .bodyToMono(Double.class)
                    .defaultIfEmpty(0.0) 
                    .block();
            } catch (Exception e) {
                System.err.println("No se pudo conectar con Reparaciones, usando 0.0: " + e.getMessage());
            }

            if (totalIngresos == null) totalIngresos = 0.0;
            if (totalEgresos == null) totalEgresos = 0.0;


            double balanceFinal = totalIngresos - totalEgresos;
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