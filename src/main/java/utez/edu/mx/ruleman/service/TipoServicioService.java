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
import utez.edu.mx.ruleman.model.TipoServicio;
import utez.edu.mx.ruleman.repository.TipoServicioRepository;

import java.util.List;

@Service
@Transactional
public class TipoServicioService {

    private static final Logger log = LoggerFactory.getLogger(TipoServicioService.class);

    @Autowired
    private TipoServicioRepository tipoServicioRepository;

    @Transactional(readOnly = true)
    public List<TipoServicio> getAllTipoServicios() {
        log.info("Obteniendo listado completo de tipos de servicio");
        return tipoServicioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public TipoServicio getTipoServicioById(Long id) {
        log.info("Buscando tipo de servicio con ID: {}", id);

        if (id == null || id <= 0) {
            log.warn("ID de tipo de servicio inválido: {}", id);
            throw new BadRequestException("El ID del tipo de servicio debe ser un número positivo");
        }

        return tipoServicioRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Tipo de servicio no encontrado con ID: {}", id);
                    return new ResourceNotFoundException(MessagesGlobals.ERROR_TIPO_SERVICIO_NOT_FOUND);
                });
    }

    public TipoServicio saveTipoServicio(TipoServicio tipoServicio) {
        log.info("Intentando guardar tipo de servicio: {}", tipoServicio.getNombre());

        // Validaciones de negocio
        validateTipoServicio(tipoServicio);

        try {
            TipoServicio savedTipoServicio = tipoServicioRepository.save(tipoServicio);
            log.info("Tipo de servicio guardado exitosamente con ID: {}", savedTipoServicio.getId());
            return savedTipoServicio;
        } catch (DataIntegrityViolationException ex) {
            log.error("Error de integridad al guardar tipo de servicio: {}", ex.getMessage());
            throw new ConflictException(MessagesGlobals.ERROR_DATA_INTEGRITY);
        }
    }

    public TipoServicio updateTipoServicio(Long id, TipoServicio tipoServicioDetails) {
        log.info("Intentando actualizar tipo de servicio con ID: {}", id);

        TipoServicio existingTipoServicio = getTipoServicioById(id);

        // Validaciones de negocio
        validateTipoServicio(tipoServicioDetails);

        // Actualizar campos
        existingTipoServicio.setNombre(tipoServicioDetails.getNombre());
        existingTipoServicio.setDescripcion(tipoServicioDetails.getDescripcion());
        existingTipoServicio.setEstatus(tipoServicioDetails.isEstatus());

        try {
            TipoServicio updatedTipoServicio = tipoServicioRepository.save(existingTipoServicio);
            log.info("Tipo de servicio actualizado exitosamente con ID: {}", updatedTipoServicio.getId());
            return updatedTipoServicio;
        } catch (DataIntegrityViolationException ex) {
            log.error("Error de integridad al actualizar tipo de servicio: {}", ex.getMessage());
            throw new ConflictException(MessagesGlobals.ERROR_DATA_INTEGRITY);
        }
    }

    public void deleteTipoServicio(Long id) {
        log.info("Intentando eliminar tipo de servicio con ID: {}", id);

        TipoServicio tipoServicio = getTipoServicioById(id);

        try {
            tipoServicioRepository.deleteById(id);
            log.info("Tipo de servicio eliminado exitosamente con ID: {}", id);
        } catch (DataIntegrityViolationException ex) {
            log.error("Error de integridad al eliminar tipo de servicio: {}", ex.getMessage());
            throw new ConflictException("No se puede eliminar el tipo de servicio porque tiene servicios asociados");
        }
    }

    private void validateTipoServicio(TipoServicio tipoServicio) {
        if (tipoServicio == null) {
            throw new BadRequestException("El tipo de servicio no puede ser nulo");
        }

        if (tipoServicio.getNombre() == null || tipoServicio.getNombre().trim().isEmpty()) {
            throw new BadRequestException("El nombre del tipo de servicio es obligatorio");
        }

        if (tipoServicio.getNombre().length() > 50) {
            throw new BadRequestException("El nombre no puede exceder 50 caracteres");
        }

        if (tipoServicio.getDescripcion() == null || tipoServicio.getDescripcion().trim().isEmpty()) {
            throw new BadRequestException("La descripción del tipo de servicio es obligatoria");
        }

        if (tipoServicio.getDescripcion().length() > 70) {
            throw new BadRequestException("La descripción no puede exceder 70 caracteres");
        }
    }
}