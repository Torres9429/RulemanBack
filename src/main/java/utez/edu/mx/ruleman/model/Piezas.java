package utez.edu.mx.ruleman.model;

import jakarta.persistence.*;

@Entity
@Table(name = "piezas")
public class Piezas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", columnDefinition = "VARCHAR(50)", nullable = false)
    private String nombre;

    @Column(name = "costoUnitario", nullable = false)
    private int costoUnitario;

    @Column(name = "precio", nullable = false)
    private int precio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;

    public Piezas() {
    }

    public Piezas(String nombre, int costoUnitario, int precio, Servicio servicio) {
        this.nombre = nombre;
        this.costoUnitario = costoUnitario;
        this.precio = precio;
        this.servicio = servicio;
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

    public int getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(int costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }
}