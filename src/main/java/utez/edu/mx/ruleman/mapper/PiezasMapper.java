package utez.edu.mx.ruleman.mapper;

import utez.edu.mx.ruleman.dto.PiezasDTO;
import utez.edu.mx.ruleman.model.Piezas;
import utez.edu.mx.ruleman.model.Servicio;

public class PiezasMapper {

    public static PiezasDTO toDTO(Piezas piezas) {
        if (piezas == null) {
            return null;
        }
        return new PiezasDTO(
                piezas.getId(),
                piezas.getNombre(),
                piezas.getCostoUnitario(),
                piezas.getCantidad(),
                piezas.getServicio() != null ? piezas.getServicio().getId() : null
        );
    }

    public static Piezas toEntity(PiezasDTO dto) {
        if (dto == null) {
            return null;
        }
        Piezas piezas = new Piezas();
        piezas.setId(dto.getId());
        piezas.setNombre(dto.getNombre());
        piezas.setCostoUnitario(dto.getCostoUnitario());
        piezas.setCantidad(dto.getCantidad());

        if (dto.getServicioId() != null) {
            Servicio servicio = new Servicio();
            servicio.setId(dto.getServicioId());
            piezas.setServicio(servicio);
        }

        return piezas;
    }

    public static void updateEntityFromDTO(PiezasDTO dto, Piezas piezas) {
        if (dto == null || piezas == null) {
            return;
        }
        piezas.setNombre(dto.getNombre());
        piezas.setCostoUnitario(dto.getCostoUnitario());
        piezas.setCantidad(dto.getCantidad());

        if (dto.getServicioId() != null) {
            Servicio servicio = new Servicio();
            servicio.setId(dto.getServicioId());
            piezas.setServicio(servicio);
        }
    }
}

