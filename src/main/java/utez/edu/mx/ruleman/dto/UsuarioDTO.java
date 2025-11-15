package utez.edu.mx.ruleman.dto;

import jakarta.validation.constraints.*;

public class UsuarioDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 50, message = "Los apellidos no pueden exceder 50 caracteres")
    private String apellidos;

    @Size(max = 10, message = "El número de teléfono no puede exceder 10 caracteres")
    @Pattern(regexp = "^[0-9]{10}$", message = "El número de teléfono debe tener exactamente 10 dígitos")
    private String numeroTelefono;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo electrónico es inválido")
    @Size(max = 50, message = "El correo no puede exceder 50 caracteres")
    private String correo;

    @Size(max = 255, message = "La contraseña no puede exceder 255 caracteres")
    private String contrasena;

    private Boolean debeCambiarContrasena;

    private Boolean estatus;

    @NotNull(message = "El rol es obligatorio")
    private Long rolId;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String nombre, String apellidos, String numeroTelefono,
                      String correo, Boolean debeCambiarContrasena, Boolean estatus, Long rolId) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numeroTelefono = numeroTelefono;
        this.correo = correo;
        this.debeCambiarContrasena = debeCambiarContrasena;
        this.estatus = estatus;
        this.rolId = rolId;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Boolean getDebeCambiarContrasena() {
        return debeCambiarContrasena;
    }

    public void setDebeCambiarContrasena(Boolean debeCambiarContrasena) {
        this.debeCambiarContrasena = debeCambiarContrasena;
    }

    public Boolean getEstatus() {
        return estatus;
    }

    public void setEstatus(Boolean estatus) {
        this.estatus = estatus;
    }

    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }
}

