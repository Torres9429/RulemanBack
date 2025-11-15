package utez.edu.mx.ruleman.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.ruleman.config.MessagesGlobals;
import utez.edu.mx.ruleman.config.exception.BadRequestException;
import utez.edu.mx.ruleman.config.exception.ResourceNotFoundException;
import utez.edu.mx.ruleman.dto.VehiculoDTO;
import utez.edu.mx.ruleman.mapper.VehiculoMapper;
import utez.edu.mx.ruleman.model.Vehiculo;
import utez.edu.mx.ruleman.service.VehiculoService;
import utez.edu.mx.ruleman.utils.Message;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin(origins = "*")
public class VehiculoController {

    private static final Logger log = LoggerFactory.getLogger(VehiculoController.class);

    @Autowired
    private VehiculoService vehiculoService;

    @PostMapping("/")
    public ResponseEntity<Message<VehiculoDTO>> createVehiculo(@Valid @RequestBody VehiculoDTO vehiculoDTO) {
        log.info("POST /api/vehiculos/ - Crear nuevo vehículo");
        Vehiculo vehiculo = VehiculoMapper.toEntity(vehiculoDTO);
        Vehiculo savedVehiculo = vehiculoService.saveVehiculo(vehiculo);
        VehiculoDTO responseDTO = VehiculoMapper.toDTO(savedVehiculo);
        Message<VehiculoDTO> response = Message.success(HttpStatus.CREATED, "Vehículo creado exitosamente", responseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/")
    public ResponseEntity<Message<List<VehiculoDTO>>> getAllVehiculos() {
        log.info("GET /api/vehiculos/ - Obtener todos los vehículos");
        List<Vehiculo> vehiculos = vehiculoService.getAllVehiculos();
        List<VehiculoDTO> vehiculoDTOs = vehiculos.stream()
                .map(VehiculoMapper::toDTO)
                .collect(Collectors.toList());
        Message<List<VehiculoDTO>> response = Message.success(HttpStatus.OK, MessagesGlobals.SUCCESS_LIST_RETRIEVED, vehiculoDTOs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message<VehiculoDTO>> getVehiculoById(@PathVariable Long id) {
        log.info("GET /api/vehiculos/{} - Obtener vehículo por ID", id);
        Vehiculo vehiculo = vehiculoService.getVehiculoById(id);
        VehiculoDTO vehiculoDTO = VehiculoMapper.toDTO(vehiculo);
        Message<VehiculoDTO> response = Message.success(HttpStatus.OK, MessagesGlobals.SUCCESS_RETRIEVED, vehiculoDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<Message<List<VehiculoDTO>>> getVehiculosByClienteId(@PathVariable Long clienteId) {
        log.info("GET /api/vehiculos/cliente/{} - Obtener vehículos por ID de cliente", clienteId);
        List<Vehiculo> vehiculos = vehiculoService.getVehiculosByClienteId(clienteId);

        // 2. Mapea la lista de entidades a una lista de DTOs
        List<VehiculoDTO> vehiculoDTOs = vehiculos.stream()
                .map(VehiculoMapper::toDTO)
                .collect(Collectors.toList());

        Message<List<VehiculoDTO>> response = Message.success(
                HttpStatus.OK,
                MessagesGlobals.SUCCESS_LIST_RETRIEVED,
                vehiculoDTOs
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message<VehiculoDTO>> updateVehiculo(@PathVariable Long id, @Valid @RequestBody VehiculoDTO vehiculoDTO) {
        log.info("PUT /api/vehiculos/{} - Actualizar vehículo", id);
        Vehiculo vehiculo = VehiculoMapper.toEntity(vehiculoDTO);
        Vehiculo updatedVehiculo = vehiculoService.updateVehiculo(id, vehiculo);
        VehiculoDTO responseDTO = VehiculoMapper.toDTO(updatedVehiculo);
        Message<VehiculoDTO> response = Message.success(HttpStatus.OK, MessagesGlobals.SUCCESS_UPDATED, responseDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message<Void>> deleteVehiculo(@PathVariable Long id) {
        log.info("DELETE /api/vehiculos/{} - Eliminar vehículo", id);
        vehiculoService.deleteVehiculo(id);
        Message<Void> response = Message.success(HttpStatus.OK, MessagesGlobals.SUCCESS_DELETED, null);
        return ResponseEntity.ok(response);
    }
}