package utez.edu.mx.ruleman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.ruleman.model.Piezas;
import utez.edu.mx.ruleman.service.PiezasService;

import java.util.List;

@RestController
@RequestMapping("/api/piezas")
@CrossOrigin(origins = "*")
public class PiezasController {

    @Autowired
    private PiezasService piezasService;

    @PostMapping("/")
    public Piezas createPiezas(@RequestBody Piezas piezas) {
        return piezasService.savePiezas(piezas);
    }

    @GetMapping("/")
    public List<Piezas> getAllPiezas() {
        return piezasService.getAllPiezas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Piezas> getPiezasById(@PathVariable Long id) {
        return piezasService.getPiezasById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Piezas> updatePiezas(@PathVariable Long id, @RequestBody Piezas piezasDetails) {
        return piezasService.getPiezasById(id)
                .map(piezas -> {
                    piezas.setNombre(piezasDetails.getNombre());
                    piezas.setCostoUnitario(piezasDetails.getCostoUnitario());
                    piezas.setCantidad(piezasDetails.getCantidad());
                    piezas.setServicio(piezasDetails.getServicio());
                    Piezas updatedPiezas = piezasService.savePiezas(piezas);
                    return ResponseEntity.ok(updatedPiezas);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePiezas(@PathVariable Long id) {
        if (piezasService.deletePiezas(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}