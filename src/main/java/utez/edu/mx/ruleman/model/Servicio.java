package utez.edu.mx.ruleman.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "servicio")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fechaEntrada", nullable = false)
    private LocalDateTime fechaEntrada;
    @Column(name = "fechaSalida")
    private LocalDateTime fechaSalida;
    @Column(name = "costoTotal", nullable = false)
    private double costoTotal;
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "vehiculo_servicio", joinColumns = @JoinColumn(name = "servicio_id"), inverseJoinColumns = @JoinColumn(name = "vehiculo_id"))
    private Set<Vehiculo> vehiculos;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "tipoServicio_id", nullable = false)
    private TipoServicio tipoServicio;
}
