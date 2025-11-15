package utez.edu.mx.ruleman.dto;

import jakarta.validation.constraints.NotNull;

public class AsignacionMecanicoDTO {
    @NotNull(message = "El ID del mecánico es obligatorio")
    private Long mecanicoId;

    public Long getMecanicoId() {
        return mecanicoId;
    }

    public void setMecanicoId(Long mecanicoId) {
        this.mecanicoId = mecanicoId;
    }
}
