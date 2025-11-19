package utez.edu.mx.ruleman.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.ruleman.config.MessagesGlobals;
import utez.edu.mx.ruleman.config.exception.BadRequestException;
import utez.edu.mx.ruleman.config.exception.ConflictException;
import utez.edu.mx.ruleman.config.exception.ResourceNotFoundException;
import utez.edu.mx.ruleman.dto.CambiarContrasenaDTO;
import utez.edu.mx.ruleman.model.Usuario;
import utez.edu.mx.ruleman.repository.UsuarioRepository;
import utez.edu.mx.ruleman.repository.RolRepository;

import java.util.List;
import java.util.regex.Pattern;

@Service
@Transactional
public class UsuarioService {
    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<Usuario> getAllUsuarios() {
        log.info("Obteniendo listado completo de usuarios");
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario getUsuarioById(Long id) {
        log.info("Buscando usuario con ID: {}", id);

        if (id == null || id <= 0) {
            log.warn("ID de usuario inválido: {}", id);
            throw new BadRequestException("El ID del usuario debe ser un número positivo");
        }

        return usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Usuario no encontrado con ID: {}", id);
                    return new ResourceNotFoundException(MessagesGlobals.ERROR_USUARIO_NOT_FOUND);
                });
    }

    @Transactional(readOnly = true)
    public Usuario getUsuarioByCorreo(String correo) {
        log.info("Buscando usuario con correo: {}", correo);

        if (correo == null || correo.trim().isEmpty()) {
            throw new BadRequestException("El correo es obligatorio");
        }

        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> {
                    log.warn("Usuario no encontrado con correo: {}", correo);
                    return new ResourceNotFoundException(MessagesGlobals.ERROR_USUARIO_NOT_FOUND);
                });
    }

    public Usuario saveUsuario(Usuario usuario) {
        log.info("Intentando guardar usuario: {}", usuario.getCorreo());

        // Validaciones de negocio
        validateUsuario(usuario);

        // Validar que el rol exista
        if (usuario.getRol() == null || usuario.getRol().getId() == null) {
            log.warn("Intento de crear usuario sin rol");
            throw new BadRequestException("El rol es obligatorio");
        }

        if (!rolRepository.existsById(usuario.getRol().getId())) {
            log.warn("Rol no encontrado con ID: {}", usuario.getRol().getId());
            throw new ResourceNotFoundException("Rol no encontrado con ID: " + usuario.getRol().getId() + ". Debe crear el rol primero.");
        }

        // Validar que el correo no esté duplicado
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            log.warn("Correo duplicado: {}", usuario.getCorreo());
            throw new ConflictException(MessagesGlobals.ERROR_USUARIO_CORREO_DUPLICADO);
        }

        // Hashear la contraseña
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        try {
            Usuario savedUsuario = usuarioRepository.save(usuario);
            log.info("Usuario guardado exitosamente con ID: {}", savedUsuario.getId());
            return savedUsuario;
        } catch (DataIntegrityViolationException ex) {
            log.error("Error de integridad al guardar usuario: {}", ex.getMessage());
            throw new ConflictException(MessagesGlobals.ERROR_DATA_INTEGRITY);
        }
    }

    public Usuario updateUsuario(Long id, Usuario usuarioDetails) {
        log.info("Intentando actualizar usuario con ID: {}", id);

        Usuario existingUsuario = getUsuarioById(id);

        // Validar usuario
        validateUsuario(usuarioDetails);

        // Validar que el correo no esté duplicado si se está cambiando
        if (!existingUsuario.getCorreo().equals(usuarioDetails.getCorreo())) {
            if (usuarioRepository.findByCorreo(usuarioDetails.getCorreo()).isPresent()) {
                log.warn("Correo duplicado: {}", usuarioDetails.getCorreo());
                throw new ConflictException(MessagesGlobals.ERROR_USUARIO_CORREO_DUPLICADO);
            }
        }

        // Validar rol si se está cambiando
        if (usuarioDetails.getRol() != null && usuarioDetails.getRol().getId() != null) {
            if (!rolRepository.existsById(usuarioDetails.getRol().getId())) {
                throw new ResourceNotFoundException(MessagesGlobals.ERROR_ROL_NOT_FOUND);
            }
            existingUsuario.setRol(usuarioDetails.getRol());
        }

        // Actualizar campos
        existingUsuario.setNombre(usuarioDetails.getNombre());
        existingUsuario.setApellidos(usuarioDetails.getApellidos());
        existingUsuario.setNumeroTelefono(usuarioDetails.getNumeroTelefono());
        existingUsuario.setCorreo(usuarioDetails.getCorreo());
        existingUsuario.setDebeCambiarContrasena(usuarioDetails.isDebeCambiarContrasena());
        existingUsuario.setEstatus(usuarioDetails.isEstatus());

        if (usuarioDetails.getContrasena() != null && !usuarioDetails.getContrasena().trim().isEmpty()) {
            existingUsuario.setContrasena(passwordEncoder.encode(usuarioDetails.getContrasena()));
        }

        try {
            Usuario updatedUsuario = usuarioRepository.save(existingUsuario);
            log.info("Usuario actualizado exitosamente con ID: {}", updatedUsuario.getId());
            return updatedUsuario;
        } catch (DataIntegrityViolationException ex) {
            log.error("Error de integridad al actualizar usuario: {}", ex.getMessage());
            throw new ConflictException(MessagesGlobals.ERROR_DATA_INTEGRITY);
        }
    }

    public void deleteUsuario(Long id) {
        log.info("Intentando eliminar usuario con ID: {}", id);

        Usuario usuario = getUsuarioById(id);

        try {
            usuarioRepository.deleteById(id);
            log.info("Usuario eliminado exitosamente con ID: {}", id);
        } catch (DataIntegrityViolationException ex) {
            log.error("Error de integridad al eliminar usuario: {}", ex.getMessage());
            throw new ConflictException("No se puede eliminar el usuario porque tiene vehículos o servicios asociados");
        }
    }
    public void cambiarContrasenaInterno(Long id, CambiarContrasenaDTO cambiarContrasenaDTO) {
        log.info("Intentando cambiar contrasena del usuario con ID: {}", id);
        Usuario usuario = getUsuarioById(id);

        if (!passwordEncoder.matches(cambiarContrasenaDTO.getContrasenaActual(), usuario.getContrasena())) {
            log.warn("Contrasena actual no coincide para el usuario ID: {}", id);
            throw new BadRequestException("La contrasena actual no coincide");
        }
        try {
            usuario.setContrasena(passwordEncoder.encode(cambiarContrasenaDTO.getNuevaContrasena()));            usuarioRepository.save(usuario);
            log.info("Contrasena cambiada exitosamente con ID: {}", id);
        } catch (DataIntegrityViolationException ex) {
            log.error("Error de integridad al cambiar contrasena: {}", ex.getMessage());
            throw new ConflictException(MessagesGlobals.ERROR_DATA_INTEGRITY);
        }


    }

    private void validateUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new BadRequestException("El usuario no puede ser nulo");
        }

        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new BadRequestException("El nombre es obligatorio");
        }

        if (usuario.getNombre().length() > 50) {
            throw new BadRequestException("El nombre no puede exceder 50 caracteres");
        }

        if (usuario.getApellidos() == null || usuario.getApellidos().trim().isEmpty()) {
            throw new BadRequestException("Los apellidos son obligatorios");
        }

        if (usuario.getApellidos().length() > 50) {
            throw new BadRequestException("Los apellidos no pueden exceder 50 caracteres");
        }

        if (usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty()) {
            throw new BadRequestException("El correo es obligatorio");
        }

        if (!EMAIL_PATTERN.matcher(usuario.getCorreo()).matches()) {
            throw new BadRequestException("El formato del correo electrónico es inválido");
        }

        if (usuario.getCorreo().length() > 50) {
            throw new BadRequestException("El correo no puede exceder 50 caracteres");
        }

        if (usuario.getNumeroTelefono() != null && usuario.getNumeroTelefono().length() > 10) {
            throw new BadRequestException("El número de teléfono no puede exceder 10 caracteres");
        }
    }
}