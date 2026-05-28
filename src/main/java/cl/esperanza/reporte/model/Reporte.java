package cl.esperanza.reporte.model;

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

    //@Column(nullable = false)
    //private 

    //@Column(nullable = false)
    //private 
    
    //@Column(nullable = false)
    //private 

    @Column(nullable = false, length = 150)
    private String informeTecnico;
}
