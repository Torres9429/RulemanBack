package utez.edu.mx.ruleman.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.ruleman.config.MessagesGlobals;
import utez.edu.mx.ruleman.dto.AsignacionMecanicoDTO;
import utez.edu.mx.ruleman.dto.ServicioDTO;
import utez.edu.mx.ruleman.mapper.ServicioMapper;
import utez.edu.mx.ruleman.model.Servicio;
import utez.edu.mx.ruleman.service.ServicioService;
import utez.edu.mx.ruleman.utils.Message;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "*")
public class ServicioController {

    private static final Logger log = LoggerFactory.getLogger(ServicioController.class);

    @Autowired
    private ServicioService servicioService;

    @PostMapping("/")
    public ResponseEntity<Message<ServicioDTO>> createServicio(@Valid @RequestBody ServicioDTO servicioDTO) {
        log.info("POST /api/servicios/ - Crear nuevo servicio");
        Servicio servicio = ServicioMapper.toEntity(servicioDTO);
        Servicio savedServicio = servicioService.saveServicio(servicio);
        ServicioDTO responseDTO = ServicioMapper.toDTO(savedServicio);
        Message<ServicioDTO> response = Message.success(HttpStatus.CREATED, "Servicio creado exitosamente", responseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/")
    public ResponseEntity<Message<List<ServicioDTO>>> getAllServicios() {
        log.info("GET /api/servicios/ - Obtener todos los servicios");
        List<Servicio> servicios = servicioService.getAllServicios();
        List<ServicioDTO> servicioDTOs = servicios.stream()
                .map(ServicioMapper::toDTO)
                .collect(Collectors.toList());
        Message<List<ServicioDTO>> response = Message.success(HttpStatus.OK, MessagesGlobals.SUCCESS_LIST_RETRIEVED, servicioDTOs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message<ServicioDTO>> getServicioById(@PathVariable Long id) {
        log.info("GET /api/servicios/{} - Obtener servicio por ID", id);
        Servicio servicio = servicioService.getServicioById(id);
        ServicioDTO servicioDTO = ServicioMapper.toDTO(servicio);
        Message<ServicioDTO> response = Message.success(HttpStatus.OK, MessagesGlobals.SUCCESS_RETRIEVED, servicioDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message<ServicioDTO>> updateServicio(@PathVariable Long id, @Valid @RequestBody ServicioDTO servicioDTO) {
        log.info("PUT /api/servicios/{} - Actualizar servicio", id);
        Servicio servicio = ServicioMapper.toEntity(servicioDTO);
        Servicio updatedServicio = servicioService.updateServicio(id, servicio);
        ServicioDTO responseDTO = ServicioMapper.toDTO(updatedServicio);
        Message<ServicioDTO> response = Message.success(HttpStatus.OK, MessagesGlobals.SUCCESS_UPDATED, responseDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message<Void>> deleteServicio(@PathVariable Long id) {
        log.info("DELETE /api/servicios/{} - Eliminar servicio", id);
        servicioService.deleteServicio(id);
        Message<Void> response = Message.success(HttpStatus.OK, MessagesGlobals.SUCCESS_DELETED, null);
        return ResponseEntity.ok(response);
    }
    @PatchMapping("/{servicioId}/asignar-mecanico")
    public ResponseEntity<Message<ServicioDTO>> asignarMecanico(
            @PathVariable Long servicioId,
            @Valid @RequestBody AsignacionMecanicoDTO asignacionDTO) {
        log.info("PATCH /api/servicios/{}/asignar-mecanico - Asignando mecánico", servicioId);
        Servicio servicioActualizado = servicioService.asignarMecanico(servicioId, asignacionDTO.getMecanicoId());

        ServicioDTO responseDTO = ServicioMapper.toDTO(servicioActualizado);
        Message<ServicioDTO> response = Message.success(HttpStatus.OK, "Mecánico asignado exitosamente", responseDTO);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/mecanico/{mecanicoId}")
    public ResponseEntity<Message<List<ServicioDTO>>> getServiciosPorMecanico(
            @PathVariable Long mecanicoId,
            @RequestParam(required = false) String estado,
            @RequestParam(defaultValue = "fechaEntrada,asc") String sort) {

        log.info("GET /api/servicios/mecanico/{} - Obteniendo servicios. Filtro estado: {}, Sort: {}", mecanicoId, estado, sort);

        //Parsear el string de ordenamiento
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction sortDirection = sortParams[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sortOrder = Sort.by(sortDirection, sortField);

        List<Servicio> servicios = servicioService.getServiciosPorMecanico(mecanicoId, estado, sortOrder);

        //Mapear a DTOs
        List<ServicioDTO> dtos = servicios.stream().map(ServicioMapper::toDTO).collect(Collectors.toList());

        Message<List<ServicioDTO>> response = Message.success(HttpStatus.OK, "Lista de servicios obtenida", dtos);

        return ResponseEntity.ok(response);
    }
}