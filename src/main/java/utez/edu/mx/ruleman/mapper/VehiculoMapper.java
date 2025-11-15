package utez.edu.mx.ruleman.mapper;

import utez.edu.mx.ruleman.dto.VehiculoDTO;
import utez.edu.mx.ruleman.model.Usuario;
import utez.edu.mx.ruleman.model.Vehiculo;

public class VehiculoMapper {

    public static VehiculoDTO toDTO(Vehiculo vehiculo) {
        if (vehiculo == null) {
            return null;
        }
        return new VehiculoDTO(
                vehiculo.getId(),
                vehiculo.getMarca(),
                vehiculo.getModelo(),
                vehiculo.getNumeroSerie(),
                vehiculo.getPlaca(),
                vehiculo.getComentario(),
                vehiculo.isEstatus(),
                vehiculo.getUsuario() != null ? vehiculo.getUsuario().getId() : null
        );
    }

    public static Vehiculo toEntity(VehiculoDTO dto) {
        if (dto == null) {
            return null;
        }
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(dto.getId());
        vehiculo.setMarca(dto.getMarca());
        vehiculo.setModelo(dto.getModelo());
        vehiculo.setNumeroSerie(dto.getNumeroSerie());
        vehiculo.setPlaca(dto.getPlaca());
        vehiculo.setComentario(dto.getComentario());
        vehiculo.setEstatus(dto.getEstatus() != null ? dto.getEstatus() : true);

        if (dto.getUsuarioId() != null) {
            Usuario usuario = new Usuario();
            usuario.setId(dto.getUsuarioId());
            vehiculo.setUsuario(usuario);
        }

        return vehiculo;
    }

    public static void updateEntityFromDTO(VehiculoDTO dto, Vehiculo vehiculo) {
        if (dto == null || vehiculo == null) {
            return;
        }
        vehiculo.setMarca(dto.getMarca());
        vehiculo.setModelo(dto.getModelo());
        vehiculo.setNumeroSerie(dto.getNumeroSerie());
        vehiculo.setPlaca(dto.getPlaca());
        vehiculo.setComentario(dto.getComentario());

        if (dto.getEstatus() != null) {
            vehiculo.setEstatus(dto.getEstatus());
        }

        if (dto.getUsuarioId() != null) {
            Usuario usuario = new Usuario();
            usuario.setId(dto.getUsuarioId());
            vehiculo.setUsuario(usuario);
        }
    }
}

