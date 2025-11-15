package utez.edu.mx.ruleman.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.ruleman.config.MessagesGlobals;
import utez.edu.mx.ruleman.dto.RolDTO;
import utez.edu.mx.ruleman.mapper.RolMapper;
import utez.edu.mx.ruleman.model.Rol;
import utez.edu.mx.ruleman.service.RolService;
import utez.edu.mx.ruleman.utils.Message;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RolController {

    private static final Logger log = LoggerFactory.getLogger(RolController.class);

    @Autowired
    private RolService rolService;

    @PostMapping("/")
    public ResponseEntity<Message<RolDTO>> createRol(@Valid @RequestBody RolDTO rolDTO) {
        log.info("POST /api/roles/ - Crear nuevo rol");
        Rol rol = RolMapper.toEntity(rolDTO);
        Rol savedRol = rolService.saveRol(rol);
        RolDTO responseDTO = RolMapper.toDTO(savedRol);
        Message<RolDTO> response = Message.success(HttpStatus.CREATED, "Rol creado exitosamente", responseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/")
    public ResponseEntity<Message<List<RolDTO>>> getAllRoles() {
        log.info("GET /api/roles/ - Obtener todos los roles");
        List<Rol> roles = rolService.getAllRoles();
        List<RolDTO> rolDTOs = roles.stream()
                .map(RolMapper::toDTO)
                .collect(Collectors.toList());
        Message<List<RolDTO>> response = Message.success(HttpStatus.OK, MessagesGlobals.SUCCESS_LIST_RETRIEVED, rolDTOs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message<RolDTO>> getRolById(@PathVariable Long id) {
        log.info("GET /api/roles/{} - Obtener rol por ID", id);
        Rol rol = rolService.getRolById(id);
        RolDTO rolDTO = RolMapper.toDTO(rol);
        Message<RolDTO> response = Message.success(HttpStatus.OK, MessagesGlobals.SUCCESS_RETRIEVED, rolDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message<RolDTO>> updateRol(@PathVariable Long id, @Valid @RequestBody RolDTO rolDTO) {
        log.info("PUT /api/roles/{} - Actualizar rol", id);
        Rol rol = RolMapper.toEntity(rolDTO);
        Rol updatedRol = rolService.updateRol(id, rol);
        RolDTO responseDTO = RolMapper.toDTO(updatedRol);
        Message<RolDTO> response = Message.success(HttpStatus.OK, MessagesGlobals.SUCCESS_UPDATED, responseDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message<Void>> deleteRol(@PathVariable Long id) {
        log.info("DELETE /api/roles/{} - Eliminar rol", id);
        rolService.deleteRol(id);
        Message<Void> response = Message.success(HttpStatus.OK, MessagesGlobals.SUCCESS_DELETED, null);
        return ResponseEntity.ok(response);
    }
}