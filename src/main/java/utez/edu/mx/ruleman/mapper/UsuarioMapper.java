package utez.edu.mx.ruleman.mapper;

import utez.edu.mx.ruleman.dto.UsuarioDTO;
import utez.edu.mx.ruleman.model.Rol;
import utez.edu.mx.ruleman.model.Usuario;

public class UsuarioMapper {

    public static UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellidos(),
                usuario.getNumeroTelefono(),
                usuario.getCorreo(),
                usuario.isDebeCambiarContrasena(),
                usuario.isEstatus(),
                usuario.getRol() != null ? usuario.getRol().getId() : null
        );
    }

    public static Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuario.setNumeroTelefono(dto.getNumeroTelefono());
        usuario.setCorreo(dto.getCorreo());
        usuario.setContrasena(dto.getContrasena());
        usuario.setDebeCambiarContrasena(dto.getDebeCambiarContrasena() != null ? dto.getDebeCambiarContrasena() : true);
        usuario.setEstatus(dto.getEstatus() != null ? dto.getEstatus() : true);

        // Configurar Rol - SIEMPRE debe tener rolId por validación @NotNull
        if (dto.getRolId() != null) {
            Rol rol = new Rol();
            rol.setId(dto.getRolId());
            usuario.setRol(rol);
        }

        return usuario;
    }

    public static void updateEntityFromDTO(UsuarioDTO dto, Usuario usuario) {
        if (dto == null || usuario == null) {
            return;
        }
        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuario.setNumeroTelefono(dto.getNumeroTelefono());
        usuario.setCorreo(dto.getCorreo());

        if (dto.getContrasena() != null && !dto.getContrasena().trim().isEmpty()) {
            usuario.setContrasena(dto.getContrasena());
        }

        if (dto.getDebeCambiarContrasena() != null) {
            usuario.setDebeCambiarContrasena(dto.getDebeCambiarContrasena());
        }

        if (dto.getEstatus() != null) {
            usuario.setEstatus(dto.getEstatus());
        }

        if (dto.getRolId() != null) {
            Rol rol = new Rol();
            rol.setId(dto.getRolId());
            usuario.setRol(rol);
        }
    }
}

