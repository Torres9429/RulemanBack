package utez.edu.mx.ruleman.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tipo_servicio")
public class TipoServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", columnDefinition = "VARCHAR(50)", nullable = false)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "VARCHAR(70)", nullable = false)
    private String descripcion;

    @Column(name = "estatus", columnDefinition = "BOOL DEFAULT TRUE")
    private boolean estatus;

    @OneToMany(mappedBy = "tipoServicio")
    @JsonIgnore
    private Set<Servicio> servicios;

    public TipoServicio() {
    }

    public TipoServicio(String nombre, String descripcion, boolean estatus) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estatus = estatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    public Set<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(Set<Servicio> servicios) {
        this.servicios = servicios;
    }
}