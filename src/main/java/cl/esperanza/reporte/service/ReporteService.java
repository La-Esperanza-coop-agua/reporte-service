package cl.esperanza.reporte.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.esperanza.reporte.model.Reporte;
import cl.esperanza.reporte.repository.ReporteRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReporteService {
    @Autowired
    private ReporteRepository reporteRepo;

    public Reporte guardarReporte(Reporte report){
        return reporteRepo.save(report);
    }
}
