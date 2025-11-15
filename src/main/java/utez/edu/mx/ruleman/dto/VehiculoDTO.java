package utez.edu.mx.ruleman.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class VehiculoDTO {

    private Long id;

    @Size(max = 30, message = "La marca no puede exceder 30 caracteres")
    private String marca;

    @Size(max = 50, message = "El modelo no puede exceder 50 caracteres")
    private String modelo;

    @NotBlank(message = "El número de serie es obligatorio")
    @Size(max = 20, message = "El número de serie no puede exceder 20 caracteres")
    private String numeroSerie;

    @NotBlank(message = "La placa es obligatoria")
    @Size(max = 15, message = "La placa no puede exceder 15 caracteres")
    private String placa;

    @Size(max = 100, message = "El comentario no puede exceder 50 caracteres")
    private String comentario;

    private Boolean estatus;

    @NotNull(message = "El usuario propietario es obligatorio")
    private Long usuarioId;

    public VehiculoDTO() {
    }

    public VehiculoDTO(Long id, String marca, String modelo, String numeroSerie,
                       String placa, String comentario, Boolean estatus, Long usuarioId) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.numeroSerie = numeroSerie;
        this.placa = placa;
        this.comentario = comentario;
        this.estatus = estatus;
        this.usuarioId = usuarioId;
    }

    // Getters y Setters
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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Boolean getEstatus() {
        return estatus;
    }

    public void setEstatus(Boolean estatus) {
        this.estatus = estatus;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}

