package utez.edu.mx.ruleman.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "vehiculo")
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "marca", columnDefinition = "VARCHAR(30)")
    private String marca;
    @Column(name = "modelo", columnDefinition = "VARCHAR(50)")
    private String modelo;
    @Column(name = "numeroSerie", columnDefinition = "VARCHAR(20)")
    private String numeroSerie;
    @Column(name = "placa", columnDefinition = "VARCHAR(15)")
    private String placa;
    @Column(name = "estatus", columnDefinition = "BOOL DEFAULT TRUE")
    private boolean estatus;
    @CreationTimestamp
    @Column(name = "fechaCreacion",columnDefinition = "TIMESTAMP DEFAULT NOW()")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @UpdateTimestamp
    @Column(name = "ultimaActualizacion",columnDefinition = "TIMESTAMP DEFAULT NOW()")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaActualizacion;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    @ManyToMany(mappedBy = "vehiculos")
    private Set<Servicio> servicios;

    public Vehiculo() {
    }

    public Vehiculo(Long id, String marca, String modelo, String numeroSerie, String placa, boolean estatus, Usuario usuario, Set<Servicio> servicios) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.numeroSerie = numeroSerie;
        this.placa = placa;
        this.estatus = estatus;
        this.usuario = usuario;
        this.servicios = servicios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public boolean isEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(Set<Servicio> servicios) {
        this.servicios = servicios;
    }
}
