package utez.edu.mx.ruleman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.ruleman.model.Usuario;
import utez.edu.mx.ruleman.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/")
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return usuarioService.saveUsuario(usuario);
    }

    @GetMapping("/")
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        return usuarioService.getUsuarioById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<Usuario> getUsuarioByCorreo(@PathVariable String correo) {
        return usuarioService.getUsuarioByCorreo(correo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetails) {
        return usuarioService.getUsuarioById(id)
                .map(usuario -> {
                    usuario.setNombre(usuarioDetails.getNombre());
                    usuario.setApellidos(usuarioDetails.getApellidos());
                    usuario.setNumeroTelefono(usuarioDetails.getNumeroTelefono());
                    usuario.setCorreo(usuarioDetails.getCorreo());
                    usuario.setDebeCambiarContrasena(usuarioDetails.isDebeCambiarContrasena());
                    usuario.setEstatus(usuarioDetails.isEstatus());
                    usuario.setRol(usuarioDetails.getRol());
                    Usuario updatedUsuario = usuarioService.saveUsuario(usuario);
                    return ResponseEntity.ok(updatedUsuario);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        if (usuarioService.deleteUsuario(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}