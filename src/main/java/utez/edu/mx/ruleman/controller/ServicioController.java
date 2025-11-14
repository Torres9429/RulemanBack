package utez.edu.mx.ruleman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.ruleman.model.Servicio;
import utez.edu.mx.ruleman.service.ServicioService;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "*")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @PostMapping("/")
    public Servicio createServicio(@RequestBody Servicio servicio) {
        return servicioService.saveServicio(servicio);
    }

    @GetMapping("/")
    public List<Servicio> getAllServicios() {
        return servicioService.getAllServicios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicio> getServicioById(@PathVariable Long id) {
        return servicioService.getServicioById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servicio> updateServicio(@PathVariable Long id, @RequestBody Servicio servicioDetails) {
        return servicioService.getServicioById(id)
                .map(servicio -> {
                    servicio.setFechaSalida(servicioDetails.getFechaSalida());
                    servicio.setCostoTotal(servicioDetails.getCostoTotal());
                    servicio.setComentario(servicioDetails.getComentario());
                    servicio.setEstado(servicioDetails.isEstado());
                    servicio.setVehiculo(servicioDetails.getVehiculo());
                    servicio.setMecanico(servicioDetails.getMecanico());
                    servicio.setTipoServicio(servicioDetails.getTipoServicio());
                    Servicio updatedServicio = servicioService.saveServicio(servicio);
                    return ResponseEntity.ok(updatedServicio);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicio(@PathVariable Long id) {
        if (servicioService.deleteServicio(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}