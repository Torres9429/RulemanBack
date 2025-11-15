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
import utez.edu.mx.ruleman.model.Rol;
import utez.edu.mx.ruleman.repository.RolRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RolService {

    private static final Logger log = LoggerFactory.getLogger(RolService.class);

    @Autowired
    private RolRepository rolRepository;

    @Transactional(readOnly = true)
    public List<Rol> getAllRoles() {
        log.info("Obteniendo listado completo de roles");
        return rolRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Rol getRolById(Long id) {
        log.info("Buscando rol con ID: {}", id);

        if (id == null || id <= 0) {
            log.warn("ID de rol inválido: {}", id);
            throw new BadRequestException("El ID del rol debe ser un número positivo");
        }

        return rolRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Rol no encontrado con ID: {}", id);
                    return new ResourceNotFoundException(MessagesGlobals.ERROR_ROL_NOT_FOUND);
                });
    }

    public Rol saveRol(Rol rol) {
        log.info("Intentando guardar rol: {}", rol.getNombre());

        // Validaciones de negocio
        validateRol(rol);

        try {
            Rol savedRol = rolRepository.save(rol);
            log.info("Rol guardado exitosamente con ID: {}", savedRol.getId());
            return savedRol;
        } catch (DataIntegrityViolationException ex) {
            log.error("Error de integridad al guardar rol: {}", ex.getMessage());
            throw new ConflictException(MessagesGlobals.ERROR_ROL_NOMBRE_DUPLICADO);
        }
    }

    public Rol updateRol(Long id, Rol rolDetails) {
        log.info("Intentando actualizar rol con ID: {}", id);

        Rol existingRol = getRolById(id);

        // Validar que el nombre no esté duplicado si se está cambiando
        if (!existingRol.getNombre().equals(rolDetails.getNombre())) {
            validateRol(rolDetails);
        }

        existingRol.setNombre(rolDetails.getNombre());
        existingRol.setDescripcion(rolDetails.getDescripcion());

        try {
            Rol updatedRol = rolRepository.save(existingRol);
            log.info("Rol actualizado exitosamente con ID: {}", updatedRol.getId());
            return updatedRol;
        } catch (DataIntegrityViolationException ex) {
            log.error("Error de integridad al actualizar rol: {}", ex.getMessage());
            throw new ConflictException(MessagesGlobals.ERROR_ROL_NOMBRE_DUPLICADO);
        }
    }

    public void deleteRol(Long id) {
        log.info("Intentando eliminar rol con ID: {}", id);

        Rol rol = getRolById(id);

        try {
            rolRepository.deleteById(id);
            log.info("Rol eliminado exitosamente con ID: {}", id);
        } catch (DataIntegrityViolationException ex) {
            log.error("Error de integridad al eliminar rol: {}", ex.getMessage());
            throw new ConflictException("No se puede eliminar el rol porque tiene usuarios asociados");
        }
    }

    private void validateRol(Rol rol) {
        if (rol == null) {
            throw new BadRequestException("El rol no puede ser nulo");
        }

        if (rol.getNombre() == null || rol.getNombre().trim().isEmpty()) {
            throw new BadRequestException("El nombre del rol es obligatorio");
        }

        if (rol.getNombre().length() > 20) {
            throw new BadRequestException("El nombre del rol no puede exceder 20 caracteres");
        }

        if (rol.getDescripcion() == null || rol.getDescripcion().trim().isEmpty()) {
            throw new BadRequestException("La descripción del rol es obligatoria");
        }

        if (rol.getDescripcion().length() > 100) {
            throw new BadRequestException("La descripción del rol no puede exceder 100 caracteres");
        }
    }
}