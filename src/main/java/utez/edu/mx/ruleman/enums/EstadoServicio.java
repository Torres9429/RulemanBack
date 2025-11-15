package utez.edu.mx.ruleman.enums;

import java.util.Arrays;

public enum EstadoServicio {
    INGRESADO(1, "Ingresado", "El vehículo ha ingresado a las instalaciones."),
    EN_SERVICIO(2, "En servicio", "El vehículo está en servicio."),
    EN_ESPERA_CONFIRMACION(3, "En espera de confirmación", "El servicio está en espera de confirmación del cliente para continuar."),
    LISTO_ENTREGA(4, "Listo para entrega", "El vehículo está listo para ser entregado."),
    FINALIZADO(5, "Finalizado", "EL vehículo ha sido entregado al cliente y se ha finalizado el servicio."),
    CANCELADO(6, "Cancelado", "El servicio ha sido cancelado por el cliente.");

    private final int id;
    private final String nombre;
    private final String descripcion;

    EstadoServicio(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public static EstadoServicio getById(int id) {
        return Arrays.stream(values())
                .filter(estado -> estado.id == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el estado con ID: " + id));
    }
}
