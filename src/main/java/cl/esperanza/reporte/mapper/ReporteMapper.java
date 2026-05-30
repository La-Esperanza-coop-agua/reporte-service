package cl.esperanza.reporte.mapper;

import cl.esperanza.reporte.dto.CreateReporteRequest;
import cl.esperanza.reporte.model.Reporte;
import java.time.LocalDate;

public class ReporteMapper {

    public static Reporte toModel(CreateReporteRequest request) {
        Reporte reporte = new Reporte();
        reporte.setFechaGeneracion(LocalDate.now());
        reporte.setAnio(request.anio());
        reporte.setTrimestre(request.trimestre());
        reporte.setEntidadDestino(request.entidadDestino());
        
        // Estos valores se inicializan en 0 y el Service los llenará con los WebClient
        reporte.setIngresosTotales(0.0);
        reporte.setEgresosTotales(0.0);
        reporte.setInformeTecnico("");
        reporte.setSirveParaPostulacion(false);
        
        return reporte;
    }
}
