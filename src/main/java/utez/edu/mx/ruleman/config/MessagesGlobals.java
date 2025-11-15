package utez.edu.mx.ruleman.config;

public class MessagesGlobals {
    // Mensajes de error generales
    public static final String ERROR_VALIDATION = "Error de validación en la solicitud.";
    public static final String ERROR_INTERNAL = "Error interno del servidor.";
    public static final String ERROR_DATA_INTEGRITY = "Error de integridad en los datos. Verifique la información proporcionada.";
    public static final String ERROR_EMAIL_SENDING = "Hubo un error en el envío del correo. Por favor, intentalo mas tarde.";
    public static final String ERROR_UNKNOWN_PROPERTIES = "Se enviaron campos no reconocidos en la solicitud.";


    public static final String ERROR_REQUIRED_FIELD = "Campo obligatorio faltante: ";
    public static final String ERROR_INVALID_FORMAT = "Formato inválido en el campo: ";
    public static final String ERROR_DUPLICATE_ENTRY = "Ya existe un registro con estos datos.";
    public static final String ERROR_FK_VIOLATION = "El recurso relacionado no existe o no puede ser referenciado.";


    public static final String ERROR_RESOURCE_NOT_FOUND = "Recurso no encontrado.";
    public static final String ERROR_ROL_NOT_FOUND = "Rol no encontrado.";
    public static final String ERROR_USUARIO_NOT_FOUND = "Usuario no encontrado.";
    public static final String ERROR_VEHICULO_NOT_FOUND = "Vehículo no encontrado.";
    public static final String ERROR_SERVICIO_NOT_FOUND = "Servicio no encontrado.";
    public static final String ERROR_TIPO_SERVICIO_NOT_FOUND = "Tipo de servicio no encontrado.";
    public static final String ERROR_PIEZA_NOT_FOUND = "Pieza no encontrada.";


    public static final String ERROR_USUARIO_CORREO_DUPLICADO = "Ya existe un usuario con ese correo electrónico.";
    public static final String ERROR_VEHICULO_PLACA_DUPLICADA = "Ya existe un vehículo con esa placa.";
    public static final String ERROR_VEHICULO_SERIE_DUPLICADA = "Ya existe un vehículo con ese número de serie.";
    public static final String ERROR_ROL_NOMBRE_DUPLICADO = "Ya existe un rol con ese nombre.";


    public static final String SUCCESS_CREATED = "Recurso creado exitosamente.";
    public static final String SUCCESS_UPDATED = "Recurso actualizado exitosamente.";
    public static final String SUCCESS_DELETED = "Recurso eliminado exitosamente.";
    public static final String SUCCESS_RETRIEVED = "Recurso obtenido exitosamente.";
    public static final String SUCCESS_LIST_RETRIEVED = "Listado obtenido exitosamente.";

    private MessagesGlobals() {} // Evita instanciación

}
