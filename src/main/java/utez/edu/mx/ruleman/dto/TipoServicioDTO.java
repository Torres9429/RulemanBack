package utez.edu.mx.ruleman.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TipoServicioDTO {

    private Long id;

    @NotBlank(message = "El nombre del tipo de servicio es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción del tipo de servicio es obligatoria")
    @Size(max = 70, message = "La descripción no puede exceder 70 caracteres")
    private String descripcion;

    @NotNull(message = "El estatus es obligatorio")
    private Boolean estatus;

    public TipoServicioDTO() {
    }

    public TipoServicioDTO(Long id, String nombre, String descripcion, Boolean estatus) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estatus = estatus;
    }

    // Getters y Setters
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

    public Boolean getEstatus() {
        return estatus;
    }

    public void setEstatus(Boolean estatus) {
        this.estatus = estatus;
    }
}

