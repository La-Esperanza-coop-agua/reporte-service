package cl.esperanza.reporte.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.esperanza.reporte.model.Reporte;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Integer> {
    Optional<Reporte> findByAnioAndTrimestre(int anio, int trimestre);

    List<Reporte> findBySirveParaPostulacionTrue();

    List<Reporte> findByFechaGeneracionBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
