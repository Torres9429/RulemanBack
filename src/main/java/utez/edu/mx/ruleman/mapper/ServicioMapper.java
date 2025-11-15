package utez.edu.mx.ruleman.mapper;

import utez.edu.mx.ruleman.config.exception.BadRequestException;
import utez.edu.mx.ruleman.dto.ServicioDTO;
import utez.edu.mx.ruleman.enums.EstadoServicio;
import utez.edu.mx.ruleman.model.Servicio;
import utez.edu.mx.ruleman.model.TipoServicio;
import utez.edu.mx.ruleman.model.Usuario;
import utez.edu.mx.ruleman.model.Vehiculo;

public class ServicioMapper {

    public static ServicioDTO toDTO(Servicio servicio) {
        if (servicio == null) {
            return null;
        }
        return new ServicioDTO(
                servicio.getId(),
                servicio.getFechaEntrada(),
                servicio.getFechaSalida(),
                servicio.getCostoTotal(),
                servicio.getComentario(),
                servicio.getEstado() != null ? servicio.getEstado().name() : null,
                servicio.getVehiculo() != null ? servicio.getVehiculo().getId() : null,
                servicio.getMecanico() != null ? servicio.getMecanico().getId() : null,
                servicio.getTipoServicio() != null ? servicio.getTipoServicio().getId() : null
        );
    }

    public static Servicio toEntity(ServicioDTO dto) {
        if (dto == null) {
            return null;
        }
        Servicio servicio = new Servicio();
        servicio.setId(dto.getId());
        servicio.setFechaEntrada(dto.getFechaEntrada());
        servicio.setFechaSalida(dto.getFechaSalida());
        servicio.setCostoTotal(dto.getCostoTotal());
        servicio.setComentario(dto.getComentario());

        if (dto.getEstado() != null && !dto.getEstado().trim().isEmpty()) {
            try {
                //valueOf para convertir el String al enum correspondiente
                servicio.setEstado(EstadoServicio.valueOf(dto.getEstado().toUpperCase()));
            } catch (IllegalArgumentException e) {
                // Si es un estado inválido se lanza un error
                throw new BadRequestException("El valor de estado '" + dto.getEstado() + "' no es válido.");
            }
        }

        if (dto.getVehiculoId() != null) {
            Vehiculo vehiculo = new Vehiculo();
            vehiculo.setId(dto.getVehiculoId());
            servicio.setVehiculo(vehiculo);
        }

        if (dto.getMecanicoId() != null) {
            Usuario mecanico = new Usuario();
            mecanico.setId(dto.getMecanicoId());
            servicio.setMecanico(mecanico);
        }

        if (dto.getTipoServicioId() != null) {
            TipoServicio tipoServicio = new TipoServicio();
            tipoServicio.setId(dto.getTipoServicioId());
            servicio.setTipoServicio(tipoServicio);
        }

        return servicio;
    }

    public static void updateEntityFromDTO(ServicioDTO dto, Servicio servicio) {
        if (dto == null || servicio == null) {
            return;
        }

        if (dto.getFechaEntrada() != null) {
            servicio.setFechaEntrada(dto.getFechaEntrada());
        }

        servicio.setFechaSalida(dto.getFechaSalida());

        if (dto.getCostoTotal() != null) {
            servicio.setCostoTotal(dto.getCostoTotal());
        }

        servicio.setComentario(dto.getComentario());

        if (dto.getEstado() != null && !dto.getEstado().trim().isEmpty()) {
            try {
                servicio.setEstado(EstadoServicio.valueOf(dto.getEstado().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("El valor de estado '" + dto.getEstado() + "' no es válido.");
            }
        }

        if (dto.getVehiculoId() != null) {
            Vehiculo vehiculo = new Vehiculo();
            vehiculo.setId(dto.getVehiculoId());
            servicio.setVehiculo(vehiculo);
        }

        if (dto.getMecanicoId() != null) {
            Usuario mecanico = new Usuario();
            mecanico.setId(dto.getMecanicoId());
            servicio.setMecanico(mecanico);
        }

        if (dto.getTipoServicioId() != null) {
            TipoServicio tipoServicio = new TipoServicio();
            tipoServicio.setId(dto.getTipoServicioId());
            servicio.setTipoServicio(tipoServicio);
        }
    }
}

