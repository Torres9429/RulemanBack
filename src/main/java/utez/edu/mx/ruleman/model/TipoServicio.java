package utez.edu.mx.ruleman.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tipo_servicio")
public class TipoServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;
    @NotBlank(message = "La descripción es obligatoria")
    @Column(name = "descripcion", columnDefinition = "VARCHAR(70)")
    private String descripcion;
    @Column(name = "estatus", columnDefinition = "BOOL DEFAULT TRUE")
    private boolean estatus;

    public TipoServicio() {
    }

    public TipoServicio(Long id, String nombre, String descripcion, boolean estatus) {
        this.id = id;
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
}
