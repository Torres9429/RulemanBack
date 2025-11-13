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

    @Column(name = "comentario", columnDefinition = "VARCHAR(50)")
    private String comentario;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mecanico_id") // Nullable
    private Usuario mecanico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipoServicio_id", nullable = false)
    private TipoServicio tipoServicio;

    @OneToMany(mappedBy = "servicio")
    @JsonIgnore
    private Set<Piezas> piezas;

    public Servicio() {
    }

    public Servicio(LocalDateTime fechaEntrada, double costoTotal, boolean estado, Vehiculo vehiculo, TipoServicio tipoServicio) {
        this.fechaEntrada = fechaEntrada;
        this.costoTotal = costoTotal;
        this.estado = estado;
        this.vehiculo = vehiculo;
        this.tipoServicio = tipoServicio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDateTime fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Usuario getMecanico() {
        return mecanico;
    }

    public void setMecanico(Usuario mecanico) {
        this.mecanico = mecanico;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public Set<Piezas> getPiezas() {
        return piezas;
    }

    public void setPiezas(Set<Piezas> piezas) {
        this.piezas = piezas;
    }
}