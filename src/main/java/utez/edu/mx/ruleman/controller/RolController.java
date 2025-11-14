package utez.edu.mx.ruleman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.ruleman.model.Rol;
import utez.edu.mx.ruleman.service.RolService;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RolController {

    @Autowired
    private RolService rolService;

    @PostMapping("/")
    public Rol createRol(@RequestBody Rol rol) {
        return rolService.saveRol(rol);
    }

    @GetMapping("/")
    public List<Rol> getAllRoles() {
        return rolService.getAllRoles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> getRolById(@PathVariable Long id) {
        return rolService.getRolById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> updateRol(@PathVariable Long id, @RequestBody Rol rolDetails) {
        return rolService.getRolById(id)
                .map(rol -> {
                    rol.setNombre(rolDetails.getNombre());
                    rol.setDescripcion(rolDetails.getDescripcion());
                    Rol updatedRol = rolService.saveRol(rol);
                    return ResponseEntity.ok(updatedRol);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Long id) {
        if (rolService.deleteRol(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}