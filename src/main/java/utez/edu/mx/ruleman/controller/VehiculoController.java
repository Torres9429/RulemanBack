package utez.edu.mx.ruleman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.ruleman.model.Vehiculo;
import utez.edu.mx.ruleman.service.VehiculoService;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin(origins = "*")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @PostMapping("/")
    public Vehiculo createVehiculo(@RequestBody Vehiculo vehiculo) {
        return vehiculoService.saveVehiculo(vehiculo);
    }

    @GetMapping("/")
    public List<Vehiculo> getAllVehiculos() {
        return vehiculoService.getAllVehiculos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> getVehiculoById(@PathVariable Long id) {
        return vehiculoService.getVehiculoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> updateVehiculo(@PathVariable Long id, @RequestBody Vehiculo vehiculoDetails) {
        return vehiculoService.getVehiculoById(id)
                .map(vehiculo -> {
                    vehiculo.setMarca(vehiculoDetails.getMarca());
                    vehiculo.setModelo(vehiculoDetails.getModelo());
                    vehiculo.setNumeroSerie(vehiculoDetails.getNumeroSerie());
                    vehiculo.setPlaca(vehiculoDetails.getPlaca());
                    vehiculo.setComentario(vehiculoDetails.getComentario());
                    vehiculo.setEstatus(vehiculoDetails.isEstatus());
                    vehiculo.setUsuario(vehiculoDetails.getUsuario());
                    Vehiculo updatedVehiculo = vehiculoService.saveVehiculo(vehiculo);
                    return ResponseEntity.ok(updatedVehiculo);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehiculo(@PathVariable Long id) {
        if (vehiculoService.deleteVehiculo(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}