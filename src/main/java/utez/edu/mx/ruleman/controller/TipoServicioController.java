package utez.edu.mx.ruleman.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.ruleman.config.MessagesGlobals;
import utez.edu.mx.ruleman.dto.TipoServicioDTO;
import utez.edu.mx.ruleman.mapper.TipoServicioMapper;
import utez.edu.mx.ruleman.model.TipoServicio;
import utez.edu.mx.ruleman.service.TipoServicioService;
import utez.edu.mx.ruleman.utils.Message;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tiposervicio")
@CrossOrigin(origins = "*")
public class TipoServicioController {

    private static final Logger log = LoggerFactory.getLogger(TipoServicioController.class);

    @Autowired
    private TipoServicioService tipoServicioService;

    @PostMapping("/")
    public ResponseEntity<Message<TipoServicioDTO>> createTipoServicio(@Valid @RequestBody TipoServicioDTO tipoServicioDTO) {
        log.info("POST /api/tiposervicio/ - Crear nuevo tipo de servicio");
        TipoServicio tipoServicio = TipoServicioMapper.toEntity(tipoServicioDTO);
        TipoServicio savedTipoServicio = tipoServicioService.saveTipoServicio(tipoServicio);
        TipoServicioDTO responseDTO = TipoServicioMapper.toDTO(savedTipoServicio);
        Message<TipoServicioDTO> response = Message.success(HttpStatus.CREATED, "Tipo de servicio creado exitosamente", responseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/")
    public ResponseEntity<Message<List<TipoServicioDTO>>> getAllTipoServicios() {
        log.info("GET /api/tiposervicio/ - Obtener todos los tipos de servicio");
        List<TipoServicio> tipoServicios = tipoServicioService.getAllTipoServicios();
        List<TipoServicioDTO> tipoServicioDTOs = tipoServicios.stream()
                .map(TipoServicioMapper::toDTO)
                .collect(Collectors.toList());
        Message<List<TipoServicioDTO>> response = Message.success(HttpStatus.OK, MessagesGlobals.SUCCESS_LIST_RETRIEVED, tipoServicioDTOs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message<TipoServicioDTO>> getTipoServicioById(@PathVariable Long id) {
        log.info("GET /api/tiposervicio/{} - Obtener tipo de servicio por ID", id);
        TipoServicio tipoServicio = tipoServicioService.getTipoServicioById(id);
        TipoServicioDTO tipoServicioDTO = TipoServicioMapper.toDTO(tipoServicio);
        Message<TipoServicioDTO> response = Message.success(HttpStatus.OK, MessagesGlobals.SUCCESS_RETRIEVED, tipoServicioDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message<TipoServicioDTO>> updateTipoServicio(@PathVariable Long id, @Valid @RequestBody TipoServicioDTO tipoServicioDTO) {
        log.info("PUT /api/tiposervicio/{} - Actualizar tipo de servicio", id);
        TipoServicio tipoServicio = TipoServicioMapper.toEntity(tipoServicioDTO);
        TipoServicio updatedTipoServicio = tipoServicioService.updateTipoServicio(id, tipoServicio);
        TipoServicioDTO responseDTO = TipoServicioMapper.toDTO(updatedTipoServicio);
        Message<TipoServicioDTO> response = Message.success(HttpStatus.OK, MessagesGlobals.SUCCESS_UPDATED, responseDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message<Void>> deleteTipoServicio(@PathVariable Long id) {
        log.info("DELETE /api/tiposervicio/{} - Eliminar tipo de servicio", id);
        tipoServicioService.deleteTipoServicio(id);
        Message<Void> response = Message.success(HttpStatus.OK, MessagesGlobals.SUCCESS_DELETED, null);
        return ResponseEntity.ok(response);
    }
}