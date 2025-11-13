package utez.edu.mx.ruleman.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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

    @Column(name = "nombre", columnDefinition = "VARCHAR(50)", nullable = false)
    private String nombre;

    @Column(name = "apellidos", columnDefinition = "VARCHAR(50)", nullable = false)
    private String apellidos;

    @Column(name = "numeroTelefono", columnDefinition = "VARCHAR(10)")
    private String numeroTelefono;

    @Column(name = "correo", columnDefinition = "VARCHAR(50)", nullable = false, unique = true)
    private String correo;

    @Column(name = "contrasena", columnDefinition = "VARCHAR(255)")
    private String contrasena;

    @Column(name = "debeCambiarContrasena", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean debeCambiarContrasena;

    @Column(name = "estatus", columnDefinition = "BOOL DEFAULT TRUE")
    private boolean estatus;

    @CreationTimestamp
    @Column(name = "fechaCreacion", columnDefinition = "TIMESTAMP DEFAULT NOW()", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @UpdateTimestamp
    @Column(name = "ultimaActualizacion", columnDefinition = "TIMESTAMP DEFAULT NOW()")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaActualizacion;

    @ManyToOne(fetch = FetchType.EAGER) // EAGER es bueno para roles
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private Set<Vehiculo> vehiculos;

    @OneToMany(mappedBy = "mecanico")
    @JsonIgnore
    private Set<Servicio> serviciosAsignados;

    public Usuario() {
    }

    public Usuario(String nombre, String apellidos, String correo, String contrasena, Rol rol) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
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

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public void setUltimaActualizacion(Date ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Set<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(Set<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public Set<Servicio> getServiciosAsignados() {
        return serviciosAsignados;
    }

    public void setServiciosAsignados(Set<Servicio> serviciosAsignados) {
        this.serviciosAsignados = serviciosAsignados;
    }
}