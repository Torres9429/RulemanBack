package utez.edu.mx.ruleman.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.ruleman.config.MessagesGlobals;
import utez.edu.mx.ruleman.config.exception.BadRequestException;
import utez.edu.mx.ruleman.config.exception.ConflictException;
import utez.edu.mx.ruleman.config.exception.ResourceNotFoundException;
import utez.edu.mx.ruleman.model.Servicio;
import utez.edu.mx.ruleman.repository.ServicioRepository;
import utez.edu.mx.ruleman.repository.VehiculoRepository;
import utez.edu.mx.ruleman.repository.TipoServicioRepository;
import utez.edu.mx.ruleman.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ServicioService {

    private static final Logger log = LoggerFactory.getLogger(ServicioService.class);

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private TipoServicioRepository tipoServicioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<Servicio> getAllServicios() {
        log.info("Obteniendo listado completo de servicios");
        return servicioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Servicio getServicioById(Long id) {
        log.info("Buscando servicio con ID: {}", id);

        if (id == null || id <= 0) {
            log.warn("ID de servicio inválido: {}", id);
            throw new BadRequestException("El ID del servicio debe ser un número positivo");
        }

        return servicioRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Servicio no encontrado con ID: {}", id);
                    return new ResourceNotFoundException(MessagesGlobals.ERROR_SERVICIO_NOT_FOUND);
                });
    }

    public Servicio saveServicio(Servicio servicio) {
        log.info("Intentando guardar servicio");

        // Validaciones de negocio
        validateServicio(servicio);

        // Validar que el vehículo exista
        if (servicio.getVehiculo() == null || servicio.getVehiculo().getId() == null) {
            throw new BadRequestException("El vehículo es obligatorio");
        }

        if (!vehiculoRepository.existsById(servicio.getVehiculo().getId())) {
            log.warn("Vehículo no encontrado con ID: {}", servicio.getVehiculo().getId());
            throw new ResourceNotFoundException(MessagesGlobals.ERROR_VEHICULO_NOT_FOUND);
        }

        // Validar que el tipo de servicio exista
        if (servicio.getTipoServicio() == null || servicio.getTipoServicio().getId() == null) {
            throw new BadRequestException("El tipo de servicio es obligatorio");
        }

        if (!tipoServicioRepository.existsById(servicio.getTipoServicio().getId())) {
            log.warn("Tipo de servicio no encontrado con ID: {}", servicio.getTipoServicio().getId());
            throw new ResourceNotFoundException(MessagesGlobals.ERROR_TIPO_SERVICIO_NOT_FOUND);
        }

        // Validar mecánico si está presente
        if (servicio.getMecanico() != null && servicio.getMecanico().getId() != null) {
            if (!usuarioRepository.existsById(servicio.getMecanico().getId())) {
                log.warn("Mecánico no encontrado con ID: {}", servicio.getMecanico().getId());
                throw new ResourceNotFoundException(MessagesGlobals.ERROR_USUARIO_NOT_FOUND);
            }
        }

        try {
            Servicio savedServicio = servicioRepository.save(servicio);
            log.info("Servicio guardado exitosamente con ID: {}", savedServicio.getId());
            return savedServicio;
        } catch (DataIntegrityViolationException ex) {
            log.error("Error de integridad al guardar servicio: {}", ex.getMessage());
            throw new ConflictException(MessagesGlobals.ERROR_DATA_INTEGRITY);
        }
    }

    public Servicio updateServicio(Long id, Servicio servicioDetails) {
        log.info("Intentando actualizar servicio con ID: {}", id);

        Servicio existingServicio = getServicioById(id);

        // Validaciones de negocio
        validateServicio(servicioDetails);

        // Validar referencias si se están cambiando
        if (servicioDetails.getVehiculo() != null && servicioDetails.getVehiculo().getId() != null) {
            if (!vehiculoRepository.existsById(servicioDetails.getVehiculo().getId())) {
                throw new ResourceNotFoundException(MessagesGlobals.ERROR_VEHICULO_NOT_FOUND);
            }
            existingServicio.setVehiculo(servicioDetails.getVehiculo());
        }

        if (servicioDetails.getTipoServicio() != null && servicioDetails.getTipoServicio().getId() != null) {
            if (!tipoServicioRepository.existsById(servicioDetails.getTipoServicio().getId())) {
                throw new ResourceNotFoundException(MessagesGlobals.ERROR_TIPO_SERVICIO_NOT_FOUND);
            }
            existingServicio.setTipoServicio(servicioDetails.getTipoServicio());
        }

        if (servicioDetails.getMecanico() != null && servicioDetails.getMecanico().getId() != null) {
            if (!usuarioRepository.existsById(servicioDetails.getMecanico().getId())) {
                throw new ResourceNotFoundException(MessagesGlobals.ERROR_USUARIO_NOT_FOUND);
            }
            existingServicio.setMecanico(servicioDetails.getMecanico());
        }

        // Actualizar campos
        if (servicioDetails.getFechaSalida() != null) {
            existingServicio.setFechaSalida(servicioDetails.getFechaSalida());
        }
        existingServicio.setCostoTotal(servicioDetails.getCostoTotal());
        existingServicio.setComentario(servicioDetails.getComentario());
        existingServicio.setEstado(servicioDetails.isEstado());

        try {
            Servicio updatedServicio = servicioRepository.save(existingServicio);
            log.info("Servicio actualizado exitosamente con ID: {}", updatedServicio.getId());
            return updatedServicio;
        } catch (DataIntegrityViolationException ex) {
            log.error("Error de integridad al actualizar servicio: {}", ex.getMessage());
            throw new ConflictException(MessagesGlobals.ERROR_DATA_INTEGRITY);
        }
    }

    public void deleteServicio(Long id) {
        log.info("Intentando eliminar servicio con ID: {}", id);

        Servicio servicio = getServicioById(id);

        try {
            servicioRepository.deleteById(id);
            log.info("Servicio eliminado exitosamente con ID: {}", id);
        } catch (DataIntegrityViolationException ex) {
            log.error("Error de integridad al eliminar servicio: {}", ex.getMessage());
            throw new ConflictException("No se puede eliminar el servicio porque tiene piezas asociadas");
        }
    }

    private void validateServicio(Servicio servicio) {
        if (servicio == null) {
            throw new BadRequestException("El servicio no puede ser nulo");
        }

        if (servicio.getFechaEntrada() == null) {
            throw new BadRequestException("La fecha de entrada es obligatoria");
        }

        if (servicio.getFechaEntrada().isAfter(LocalDateTime.now())) {
            throw new BadRequestException("La fecha de entrada no puede ser futura");
        }

        if (servicio.getFechaSalida() != null && servicio.getFechaSalida().isBefore(servicio.getFechaEntrada())) {
            throw new BadRequestException("La fecha de salida no puede ser anterior a la fecha de entrada");
        }

        if (servicio.getCostoTotal() < 0) {
            throw new BadRequestException("El costo total no puede ser negativo");
        }
    }
}