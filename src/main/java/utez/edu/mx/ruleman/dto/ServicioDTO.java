package utez.edu.mx.ruleman.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class ServicioDTO {

    private Long id;

    @NotNull(message = "La fecha de entrada es obligatoria")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaEntrada;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaSalida;

    @NotNull(message = "El costo total es obligatorio")
    @DecimalMin(value = "0.0", message = "El costo total debe ser mayor o igual a cero")
    private Double costoTotal;

    @Size(max = 100, message = "El comentario no puede exceder 100 caracteres")
    private String comentario;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @NotNull(message = "El vehículo es obligatorio")
    private Long vehiculoId;

    private Long mecanicoId;

    @NotNull(message = "El tipo de servicio es obligatorio")
    private Long tipoServicioId;

    public ServicioDTO() {
    }

    public ServicioDTO(Long id, LocalDateTime fechaEntrada, LocalDateTime fechaSalida,
                       Double costoTotal, String comentario, String estado,
                       Long vehiculoId, Long mecanicoId, Long tipoServicioId) {
        this.id = id;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.costoTotal = costoTotal;
        this.comentario = comentario;
        this.estado = estado;
        this.vehiculoId = vehiculoId;
        this.mecanicoId = mecanicoId;
        this.tipoServicioId = tipoServicioId;
    }

    // Getters y Setters
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

    public Double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(Double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(Long vehiculoId) {
        this.vehiculoId = vehiculoId;
    }

    public Long getMecanicoId() {
        return mecanicoId;
    }

    public void setMecanicoId(Long mecanicoId) {
        this.mecanicoId = mecanicoId;
    }

    public Long getTipoServicioId() {
        return tipoServicioId;
    }

    public void setTipoServicioId(Long tipoServicioId) {
        this.tipoServicioId = tipoServicioId;
    }
}

