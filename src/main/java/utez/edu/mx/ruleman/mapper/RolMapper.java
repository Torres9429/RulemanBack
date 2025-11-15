package utez.edu.mx.ruleman.mapper;

import utez.edu.mx.ruleman.dto.RolDTO;
import utez.edu.mx.ruleman.model.Rol;

public class RolMapper {

    public static RolDTO toDTO(Rol rol) {
        if (rol == null) {
            return null;
        }
        return new RolDTO(
                rol.getId(),
                rol.getNombre(),
                rol.getDescripcion()
        );
    }

    public static Rol toEntity(RolDTO dto) {
        if (dto == null) {
            return null;
        }
        Rol rol = new Rol();
        rol.setId(dto.getId());
        rol.setNombre(dto.getNombre());
        rol.setDescripcion(dto.getDescripcion());
        return rol;
    }

    public static void updateEntityFromDTO(RolDTO dto, Rol rol) {
        if (dto == null || rol == null) {
            return;
        }
        rol.setNombre(dto.getNombre());
        rol.setDescripcion(dto.getDescripcion());
    }
}

