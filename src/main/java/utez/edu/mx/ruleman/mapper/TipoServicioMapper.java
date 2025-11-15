package utez.edu.mx.ruleman.mapper;

import utez.edu.mx.ruleman.dto.TipoServicioDTO;
import utez.edu.mx.ruleman.model.TipoServicio;

public class TipoServicioMapper {

    public static TipoServicioDTO toDTO(TipoServicio tipoServicio) {
        if (tipoServicio == null) {
            return null;
        }
        return new TipoServicioDTO(
                tipoServicio.getId(),
                tipoServicio.getNombre(),
                tipoServicio.getDescripcion(),
                tipoServicio.isEstatus()
        );
    }

    public static TipoServicio toEntity(TipoServicioDTO dto) {
        if (dto == null) {
            return null;
        }
        TipoServicio tipoServicio = new TipoServicio();
        tipoServicio.setId(dto.getId());
        tipoServicio.setNombre(dto.getNombre());
        tipoServicio.setDescripcion(dto.getDescripcion());
        tipoServicio.setEstatus(dto.getEstatus() != null ? dto.getEstatus() : true);
        return tipoServicio;
    }

    public static void updateEntityFromDTO(TipoServicioDTO dto, TipoServicio tipoServicio) {
        if (dto == null || tipoServicio == null) {
            return;
        }
        tipoServicio.setNombre(dto.getNombre());
        tipoServicio.setDescripcion(dto.getDescripcion());

        if (dto.getEstatus() != null) {
            tipoServicio.setEstatus(dto.getEstatus());
        }
    }
}

