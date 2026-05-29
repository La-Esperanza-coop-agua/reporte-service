package cl.esperanza.reporte.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reporte")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReporte;

    @Column(name = "fecha_generacion", nullable = false)
    private LocalDate fechaGeneracion;

    @Column(nullable = false)
    private int trimestre;

    @Column(nullable = false)
    private int anio;

    @Column(name = "ingresos_totales", nullable = false)
    private double ingresosTotales;

    @Column(name= "egresos_totales", nullable = false)
    private double egresosTotales;

    @Column(name = "informe_tecnico", nullable = false, length = 500)
    private String informeTecnico;

    @Column(name = "sirve_para_postulacion", nullable = false)
    private Boolean sirveParaPostulacion;

    @Column(name = "entidad_destino", nullable = false, length = 50)
    private String entidadDestino;
}
