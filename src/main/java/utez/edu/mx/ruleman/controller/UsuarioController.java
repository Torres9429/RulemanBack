package utez.edu.mx.ruleman.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.ruleman.config.MessagesGlobals;
import utez.edu.mx.ruleman.dto.UsuarioDTO;
import utez.edu.mx.ruleman.mapper.UsuarioMapper;
import utez.edu.mx.ruleman.model.Usuario;
import utez.edu.mx.ruleman.service.UsuarioService;
import utez.edu.mx.ruleman.utils.Message;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/")
    public ResponseEntity<Message<UsuarioDTO>> createUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        log.info("POST /api/usuarios/ - Crear nuevo usuario");
        log.info("DTO recibido - rolId: {}, correo: {}", usuarioDTO.getRolId(), usuarioDTO.getCorreo());
        Usuario usuario = UsuarioMapper.toEntity(usuarioDTO);
        log.info("Entity creada - Rol: {}, RolId: {}", usuario.getRol(), usuario.getRol() != null ? usuario.getRol().getId() : "null");
        Usuario savedUsuario = usuarioService.saveUsuario(usuario);
        UsuarioDTO responseDTO = UsuarioMapper.toDTO(savedUsuario);
        Message<UsuarioDTO> response = Message.success(HttpStatus.CREATED, "Usuario creado exitosamente", responseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/")
    public ResponseEntity<Message<List<UsuarioDTO>>> getAllUsuarios() {
        log.info("GET /api/usuarios/ - Obtener todos los usuarios");
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        List<UsuarioDTO> usuarioDTOs = usuarios.stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
        Message<List<UsuarioDTO>> response = Message.success(HttpStatus.OK, MessagesGlobals.SUCCESS_LIST_RETRIEVED, usuarioDTOs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message<UsuarioDTO>> getUsuarioById(@PathVariable Long id) {
        log.info("GET /api/usuarios/{} - Obtener usuario por ID", id);
        Usuario usuario = usuarioService.getUsuarioById(id);
        UsuarioDTO usuarioDTO = UsuarioMapper.toDTO(usuario);
        Message<UsuarioDTO> response = Message.success(HttpStatus.OK, MessagesGlobals.SUCCESS_RETRIEVED, usuarioDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<Message<UsuarioDTO>> getUsuarioByCorreo(@PathVariable String correo) {
        log.info("GET /api/usuarios/correo/{} - Obtener usuario por correo", correo);
        Usuario usuario = usuarioService.getUsuarioByCorreo(correo);
        UsuarioDTO usuarioDTO = UsuarioMapper.toDTO(usuario);
        Message<UsuarioDTO> response = Message.success(HttpStatus.OK, MessagesGlobals.SUCCESS_RETRIEVED, usuarioDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message<UsuarioDTO>> updateUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        log.info("PUT /api/usuarios/{} - Actualizar usuario", id);
        Usuario usuario = UsuarioMapper.toEntity(usuarioDTO);
        Usuario updatedUsuario = usuarioService.updateUsuario(id, usuario);
        UsuarioDTO responseDTO = UsuarioMapper.toDTO(updatedUsuario);
        Message<UsuarioDTO> response = Message.success(HttpStatus.OK, MessagesGlobals.SUCCESS_UPDATED, responseDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message<Void>> deleteUsuario(@PathVariable Long id) {
        log.info("DELETE /api/usuarios/{} - Eliminar usuario", id);
        usuarioService.deleteUsuario(id);
        Message<Void> response = Message.success(HttpStatus.OK, MessagesGlobals.SUCCESS_DELETED, null);
        return ResponseEntity.ok(response);
    }
}