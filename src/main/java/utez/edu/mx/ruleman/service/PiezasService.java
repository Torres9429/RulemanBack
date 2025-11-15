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
import utez.edu.mx.ruleman.model.Piezas;
import utez.edu.mx.ruleman.repository.PiezasRepository;
import utez.edu.mx.ruleman.repository.ServicioRepository;

import java.util.List;

@Service
@Transactional
public class PiezasService {

    private static final Logger log = LoggerFactory.getLogger(PiezasService.class);

    @Autowired
    private PiezasRepository piezasRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Transactional(readOnly = true)
    public List<Piezas> getAllPiezas() {
        log.info("Obteniendo listado completo de piezas");
        return piezasRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Piezas getPiezasById(Long id) {
        log.info("Buscando pieza con ID: {}", id);

        if (id == null || id <= 0) {
            log.warn("ID de pieza inválido: {}", id);
            throw new BadRequestException("El ID de la pieza debe ser un número positivo");
        }

        return piezasRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Pieza no encontrada con ID: {}", id);
                    return new ResourceNotFoundException(MessagesGlobals.ERROR_PIEZA_NOT_FOUND);
                });
    }

    public Piezas savePiezas(Piezas piezas) {
        log.info("Intentando guardar pieza: {}", piezas.getNombre());

        // Validaciones de negocio
        validatePiezas(piezas);

        // Validar que el servicio exista
        if (piezas.getServicio() == null || piezas.getServicio().getId() == null) {
            throw new BadRequestException("El servicio es obligatorio");
        }

        if (!servicioRepository.existsById(piezas.getServicio().getId())) {
            log.warn("Servicio no encontrado con ID: {}", piezas.getServicio().getId());
            throw new ResourceNotFoundException(MessagesGlobals.ERROR_SERVICIO_NOT_FOUND);
        }

        try {
            Piezas savedPiezas = piezasRepository.save(piezas);
            log.info("Pieza guardada exitosamente con ID: {}", savedPiezas.getId());
            return savedPiezas;
        } catch (DataIntegrityViolationException ex) {
            log.error("Error de integridad al guardar pieza: {}", ex.getMessage());
            throw new ConflictException(MessagesGlobals.ERROR_DATA_INTEGRITY);
        }
    }

    public Piezas updatePiezas(Long id, Piezas piezasDetails) {
        log.info("Intentando actualizar pieza con ID: {}", id);

        Piezas existingPiezas = getPiezasById(id);

        // Validaciones de negocio
        validatePiezas(piezasDetails);

        // Validar servicio si se está cambiando
        if (piezasDetails.getServicio() != null && piezasDetails.getServicio().getId() != null) {
            if (!servicioRepository.existsById(piezasDetails.getServicio().getId())) {
                throw new ResourceNotFoundException(MessagesGlobals.ERROR_SERVICIO_NOT_FOUND);
            }
            existingPiezas.setServicio(piezasDetails.getServicio());
        }

        // Actualizar campos
        existingPiezas.setNombre(piezasDetails.getNombre());
        existingPiezas.setCostoUnitario(piezasDetails.getCostoUnitario());
        existingPiezas.setCantidad(piezasDetails.getCantidad());

        try {
            Piezas updatedPiezas = piezasRepository.save(existingPiezas);
            log.info("Pieza actualizada exitosamente con ID: {}", updatedPiezas.getId());
            return updatedPiezas;
        } catch (DataIntegrityViolationException ex) {
            log.error("Error de integridad al actualizar pieza: {}", ex.getMessage());
            throw new ConflictException(MessagesGlobals.ERROR_DATA_INTEGRITY);
        }
    }

    public void deletePiezas(Long id) {
        log.info("Intentando eliminar pieza con ID: {}", id);

        Piezas piezas = getPiezasById(id);

        try {
            piezasRepository.deleteById(id);
            log.info("Pieza eliminada exitosamente con ID: {}", id);
        } catch (DataIntegrityViolationException ex) {
            log.error("Error de integridad al eliminar pieza: {}", ex.getMessage());
            throw new ConflictException(MessagesGlobals.ERROR_DATA_INTEGRITY);
        }
    }

    private void validatePiezas(Piezas piezas) {
        if (piezas == null) {
            throw new BadRequestException("La pieza no puede ser nula");
        }

        if (piezas.getNombre() == null || piezas.getNombre().trim().isEmpty()) {
            throw new BadRequestException("El nombre de la pieza es obligatorio");
        }

        if (piezas.getNombre().length() > 50) {
            throw new BadRequestException("El nombre no puede exceder 50 caracteres");
        }

        if (piezas.getCostoUnitario() <= 0) {
            throw new BadRequestException("El costo unitario debe ser mayor a cero");
        }

        if (piezas.getCantidad() <= 0) {
            throw new BadRequestException("La cantidad debe ser mayor a cero");
        }
    }
}