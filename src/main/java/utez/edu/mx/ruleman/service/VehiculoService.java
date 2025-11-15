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
import utez.edu.mx.ruleman.model.Vehiculo;
import utez.edu.mx.ruleman.repository.VehiculoRepository;
import utez.edu.mx.ruleman.repository.UsuarioRepository;

import java.util.List;

@Service
@Transactional
public class VehiculoService {

    private static final Logger log = LoggerFactory.getLogger(VehiculoService.class);

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<Vehiculo> getAllVehiculos() {
        log.info("Obteniendo listado completo de vehículos");
        return vehiculoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Vehiculo getVehiculoById(Long id) {
        log.info("Buscando vehículo con ID: {}", id);

        if (id == null || id <= 0) {
            log.warn("ID de vehículo inválido: {}", id);
            throw new BadRequestException("El ID del vehículo debe ser un número positivo");
        }

        return vehiculoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Vehículo no encontrado con ID: {}", id);
                    return new ResourceNotFoundException(MessagesGlobals.ERROR_VEHICULO_NOT_FOUND);
                });
    }

    @Transactional(readOnly = true)
    public List<Vehiculo> getVehiculosByClienteId(Long clienteId) {
        log.info("Obteniendo vehículos para el cliente con ID: {}", clienteId);

        if (clienteId == null || clienteId <= 0) {
            throw new BadRequestException("El ID del cliente es inválido");
        }

        if (!usuarioRepository.existsById(clienteId)) {
            throw new ResourceNotFoundException("No se encontró el cliente con ID: " + clienteId);
        }

        return vehiculoRepository.findByUsuarioId(clienteId);
    }

    public Vehiculo saveVehiculo(Vehiculo vehiculo) {
        log.info("Intentando guardar vehículo con placa: {}", vehiculo.getPlaca());

        // Validaciones de negocio
        validateVehiculo(vehiculo);

        // Validar que el usuario exista
        if (vehiculo.getUsuario() == null || vehiculo.getUsuario().getId() == null) {
            throw new BadRequestException("El usuario propietario es obligatorio");
        }

        if (!usuarioRepository.existsById(vehiculo.getUsuario().getId())) {
            log.warn("Usuario no encontrado con ID: {}", vehiculo.getUsuario().getId());
            throw new ResourceNotFoundException(MessagesGlobals.ERROR_USUARIO_NOT_FOUND);
        }

        try {
            Vehiculo savedVehiculo = vehiculoRepository.save(vehiculo);
            log.info("Vehículo guardado exitosamente con ID: {}", savedVehiculo.getId());
            return savedVehiculo;
        } catch (DataIntegrityViolationException ex) {
            log.error("Error de integridad al guardar vehículo: {}", ex.getMessage());

            if (ex.getMessage().contains("placa")) {
                throw new ConflictException(MessagesGlobals.ERROR_VEHICULO_PLACA_DUPLICADA);
            } else if (ex.getMessage().contains("numeroSerie") || ex.getMessage().contains("numero_serie")) {
                throw new ConflictException(MessagesGlobals.ERROR_VEHICULO_SERIE_DUPLICADA);
            }

            throw new ConflictException(MessagesGlobals.ERROR_DATA_INTEGRITY);
        }
    }

    public Vehiculo updateVehiculo(Long id, Vehiculo vehiculoDetails) {
        log.info("Intentando actualizar vehículo con ID: {}", id);

        Vehiculo existingVehiculo = getVehiculoById(id);

        // Validar vehículo
        validateVehiculo(vehiculoDetails);

        // Validar usuario si se está cambiando
        if (vehiculoDetails.getUsuario() != null && vehiculoDetails.getUsuario().getId() != null) {
            if (!usuarioRepository.existsById(vehiculoDetails.getUsuario().getId())) {
                throw new ResourceNotFoundException(MessagesGlobals.ERROR_USUARIO_NOT_FOUND);
            }
            existingVehiculo.setUsuario(vehiculoDetails.getUsuario());
        }

        // Actualizar campos
        existingVehiculo.setMarca(vehiculoDetails.getMarca());
        existingVehiculo.setModelo(vehiculoDetails.getModelo());
        existingVehiculo.setNumeroSerie(vehiculoDetails.getNumeroSerie());
        existingVehiculo.setPlaca(vehiculoDetails.getPlaca());
        existingVehiculo.setComentario(vehiculoDetails.getComentario());
        existingVehiculo.setEstatus(vehiculoDetails.isEstatus());

        try {
            Vehiculo updatedVehiculo = vehiculoRepository.save(existingVehiculo);
            log.info("Vehículo actualizado exitosamente con ID: {}", updatedVehiculo.getId());
            return updatedVehiculo;
        } catch (DataIntegrityViolationException ex) {
            log.error("Error de integridad al actualizar vehículo: {}", ex.getMessage());

            if (ex.getMessage().contains("placa")) {
                throw new ConflictException(MessagesGlobals.ERROR_VEHICULO_PLACA_DUPLICADA);
            } else if (ex.getMessage().contains("numeroSerie") || ex.getMessage().contains("numero_serie")) {
                throw new ConflictException(MessagesGlobals.ERROR_VEHICULO_SERIE_DUPLICADA);
            }

            throw new ConflictException(MessagesGlobals.ERROR_DATA_INTEGRITY);
        }
    }

    public void deleteVehiculo(Long id) {
        log.info("Intentando eliminar vehículo con ID: {}", id);

        Vehiculo vehiculo = getVehiculoById(id);

        try {
            vehiculoRepository.deleteById(id);
            log.info("Vehículo eliminado exitosamente con ID: {}", id);
        } catch (DataIntegrityViolationException ex) {
            log.error("Error de integridad al eliminar vehículo: {}", ex.getMessage());
            throw new ConflictException("No se puede eliminar el vehículo porque tiene servicios asociados");
        }
    }

    private void validateVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new BadRequestException("El vehículo no puede ser nulo");
        }

        if (vehiculo.getNumeroSerie() == null || vehiculo.getNumeroSerie().trim().isEmpty()) {
            throw new BadRequestException("El número de serie es obligatorio");
        }

        if (vehiculo.getNumeroSerie().length() > 20) {
            throw new BadRequestException("El número de serie no puede exceder 20 caracteres");
        }

        if (vehiculo.getPlaca() == null || vehiculo.getPlaca().trim().isEmpty()) {
            throw new BadRequestException("La placa es obligatoria");
        }

        if (vehiculo.getPlaca().length() > 15) {
            throw new BadRequestException("La placa no puede exceder 15 caracteres");
        }

        if (vehiculo.getMarca() != null && vehiculo.getMarca().length() > 30) {
            throw new BadRequestException("La marca no puede exceder 30 caracteres");
        }

        if (vehiculo.getModelo() != null && vehiculo.getModelo().length() > 50) {
            throw new BadRequestException("El modelo no puede exceder 50 caracteres");
        }

        if (vehiculo.getComentario() != null && vehiculo.getComentario().length() > 100) {
            throw new BadRequestException("El comentario no puede exceder 100 caracteres");
        }
    }
}