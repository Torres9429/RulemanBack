package utez.edu.mx.ruleman.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;
    @NotBlank(message = "Los apellidos son obligatorios")
    @Column(name = "apellidos", columnDefinition = "VARCHAR(50)")
    private String apellidos;
    @Email(message = "Debe ser un correo válido")
    @NotBlank(message = "El correo es obligatorio")
    @Column(name = "correo", columnDefinition = "VARCHAR(50)", unique = true)
    private String correo;
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Column(name = "contrasena", columnDefinition = "VARCHAR(255)")
    private String contrasena;
    @Column(name = "debeCambiarContrasena", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean debeCambiarContrasena;
    @Column(name = "estatus", columnDefinition = "BOOL DEFAULT TRUE")
    private boolean estatus = true;

    //Estos campos no tienen ni getter ni setter
    @CreationTimestamp
    @Column(name = "fechaCreacion",columnDefinition = "TIMESTAMP DEFAULT NOW()")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @UpdateTimestamp
    @Column(name = "ultimaActualizacion",columnDefinition = "TIMESTAMP DEFAULT NOW()")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaActualizacion;
    @ManyToOne(fetch = FetchType.EAGER) // EAGER para que siempre cargue el rol con el usuario
    @JoinColumn(name = "rol_id", nullable = false) // Así se llamará la clave foránea en la tabla usuario
    private Rol rolId;
    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private Set<Vehiculo> vehiculos;


    public Usuario() {
    }

    public Usuario(Long id, String nombre, String apellidos, String correo, String contrasena, Rol rolId) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rolId = rolId;
    }

    public Usuario(Long id, String nombre, String apellidos, String correo, String contrasena, boolean debeCambiarContrasena, boolean estatus, Date fechaCreacion, Date ultimaActualizacion, Rol rolId) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.contrasena = contrasena;
        this.debeCambiarContrasena = debeCambiarContrasena;
        this.estatus = estatus;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacion = ultimaActualizacion;
        this.rolId = rolId;
    }

    public Usuario(Long id, String nombre, String apellidos, String correo, String contrasena, boolean debeCambiarContrasena, boolean estatus, Rol rolId, Set<Vehiculo> vehiculos) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.contrasena = contrasena;
        this.debeCambiarContrasena = debeCambiarContrasena;
        this.estatus = estatus;
        this.rolId = rolId;
        this.vehiculos = vehiculos;
    }

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

    public Rol getRolId() {
        return rolId;
    }

    public void setRolId(Rol rolId) {
        this.rolId = rolId;
    }

    public boolean isDebeCambiarContrasena() {
        return debeCambiarContrasena;
    }

    public void setDebeCambiarContrasena(boolean debeCambiarContrasena) {
        this.debeCambiarContrasena = debeCambiarContrasena;
    }

    public boolean isEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    public Set<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(Set<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }
}
