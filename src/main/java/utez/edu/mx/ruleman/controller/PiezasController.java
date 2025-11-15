package utez.edu.mx.ruleman.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.ruleman.config.MessagesGlobals;
import utez.edu.mx.ruleman.dto.PiezasDTO;
import utez.edu.mx.ruleman.mapper.PiezasMapper;
import utez.edu.mx.ruleman.model.Piezas;
import utez.edu.mx.ruleman.service.PiezasService;
import utez.edu.mx.ruleman.utils.Message;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/piezas")
@CrossOrigin(origins = "*")
public class PiezasController {

    private static final Logger log = LoggerFactory.getLogger(PiezasController.class);

    @Autowired
    private PiezasService piezasService;

    @PostMapping("/")
    public ResponseEntity<Message<PiezasDTO>> createPiezas(@Valid @RequestBody PiezasDTO piezasDTO) {
        log.info("POST /api/piezas/ - Crear nueva pieza");
        Piezas piezas = PiezasMapper.toEntity(piezasDTO);
        Piezas savedPiezas = piezasService.savePiezas(piezas);
        PiezasDTO responseDTO = PiezasMapper.toDTO(savedPiezas);
        Message<PiezasDTO> response = Message.success(HttpStatus.CREATED, "Pieza creada exitosamente", responseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/")
    public ResponseEntity<Message<List<PiezasDTO>>> getAllPiezas() {
        log.info("GET /api/piezas/ - Obtener todas las piezas");
        List<Piezas> piezas = piezasService.getAllPiezas();
        List<PiezasDTO> piezasDTOs = piezas.stream()
                .map(PiezasMapper::toDTO)
                .collect(Collectors.toList());
        Message<List<PiezasDTO>> response = Message.success(HttpStatus.OK, MessagesGlobals.SUCCESS_LIST_RETRIEVED, piezasDTOs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message<PiezasDTO>> getPiezasById(@PathVariable Long id) {
        log.info("GET /api/piezas/{} - Obtener pieza por ID", id);
        Piezas piezas = piezasService.getPiezasById(id);
        PiezasDTO piezasDTO = PiezasMapper.toDTO(piezas);
        Message<PiezasDTO> response = Message.success(HttpStatus.OK, MessagesGlobals.SUCCESS_RETRIEVED, piezasDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message<PiezasDTO>> updatePiezas(@PathVariable Long id, @Valid @RequestBody PiezasDTO piezasDTO) {
        log.info("PUT /api/piezas/{} - Actualizar pieza", id);
        Piezas piezas = PiezasMapper.toEntity(piezasDTO);
        Piezas updatedPiezas = piezasService.updatePiezas(id, piezas);
        PiezasDTO responseDTO = PiezasMapper.toDTO(updatedPiezas);
        Message<PiezasDTO> response = Message.success(HttpStatus.OK, MessagesGlobals.SUCCESS_UPDATED, responseDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message<Void>> deletePiezas(@PathVariable Long id) {
        log.info("DELETE /api/piezas/{} - Eliminar pieza", id);
        piezasService.deletePiezas(id);
        Message<Void> response = Message.success(HttpStatus.OK, MessagesGlobals.SUCCESS_DELETED, null);
        return ResponseEntity.ok(response);
    }
}