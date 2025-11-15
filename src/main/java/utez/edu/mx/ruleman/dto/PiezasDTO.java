package utez.edu.mx.ruleman.dto;

import jakarta.validation.constraints.*;

public class PiezasDTO {

    private Long id;

    @NotBlank(message = "El nombre de la pieza es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    private String nombre;

    @NotNull(message = "El costo unitario es obligatorio")
    @Min(value = 1, message = "El costo unitario debe ser mayor a cero")
    private Integer costoUnitario;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a cero")
    private Integer cantidad;

    @NotNull(message = "El servicio es obligatorio")
    private Long servicioId;

    public PiezasDTO() {
    }

    public PiezasDTO(Long id, String nombre, Integer costoUnitario, Integer cantidad, Long servicioId) {
        this.id = id;
        this.nombre = nombre;
        this.costoUnitario = costoUnitario;
        this.cantidad = cantidad;
        this.servicioId = servicioId;
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

    public Integer getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(Integer costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Long getServicioId() {
        return servicioId;
    }

    public void setServicioId(Long servicioId) {
        this.servicioId = servicioId;
    }
}

