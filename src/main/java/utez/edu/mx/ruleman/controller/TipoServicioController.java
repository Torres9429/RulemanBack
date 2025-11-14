package utez.edu.mx.ruleman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.ruleman.model.TipoServicio;
import utez.edu.mx.ruleman.service.TipoServicioService;

import java.util.List;

@RestController
@RequestMapping("/api/tiposervicio")
@CrossOrigin(origins = "*")
public class TipoServicioController {

    @Autowired
    private TipoServicioService tipoServicioService;

    @PostMapping("/")
    public TipoServicio createTipoServicio(@RequestBody TipoServicio tipoServicio) {
        return tipoServicioService.saveTipoServicio(tipoServicio);
    }

    @GetMapping("/")
    public List<TipoServicio> getAllTipoServicios() {
        return tipoServicioService.getAllTipoServicios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoServicio> getTipoServicioById(@PathVariable Long id) {
        return tipoServicioService.getTipoServicioById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoServicio> updateTipoServicio(@PathVariable Long id, @RequestBody TipoServicio tipoServicioDetails) {
        return tipoServicioService.getTipoServicioById(id)
                .map(tipoServicio -> {
                    tipoServicio.setNombre(tipoServicioDetails.getNombre());
                    tipoServicio.setDescripcion(tipoServicioDetails.getDescripcion());
                    tipoServicio.setEstatus(tipoServicioDetails.isEstatus());
                    TipoServicio updatedTipoServicio = tipoServicioService.saveTipoServicio(tipoServicio);
                    return ResponseEntity.ok(updatedTipoServicio);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoServicio(@PathVariable Long id) {
        if (tipoServicioService.deleteTipoServicio(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}