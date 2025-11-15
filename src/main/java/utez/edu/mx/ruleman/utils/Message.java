package utez.edu.mx.ruleman.utils;

import org.springframework.http.HttpStatus;

public class Message<T> {
    private String text;
    private TypesResponse type;
    private T data;
    private HttpStatus status;
    private boolean success;
    private int statusCode;

    public Message(String text, TypesResponse type) {
        this.text = text;
        this.type = type;
    }


    public Message(T result, String text, TypesResponse type) {
        this.text = text;
        this.type = type;
        this.data = result;
    }

    // Constructor completo con todos los campos
    public Message(boolean success, HttpStatus status, String text, T data) {
        this.success = success;
        this.status = status;
        this.statusCode = status.value();
        this.text = text;
        this.data = data;
        // Asegurar que 'type' nunca sea null
        this.type = success ? TypesResponse.SUCCESS : TypesResponse.ERROR;
    }

    // Método estático para casos de éxito
    public static <T> Message<T> success(HttpStatus status, String message, T data) {
        Message<T> msg = new Message<>(true, status, message, data);
        msg.setType(TypesResponse.SUCCESS);
        return msg;
    }

    // Método estático para casos de error
    public static <T> Message<T> error(HttpStatus status, String message, T data) {
        Message<T> msg = new Message<>(false, status, message, data);
        msg.setType(TypesResponse.ERROR);
        return msg;
    }

    // Método estático opcional para advertencias
    public static <T> Message<T> warning(HttpStatus status, String message, T data) {
        Message<T> msg = new Message<>(false, status, message, data);
        msg.setType(TypesResponse.WARNING);
        return msg;
    }

    // Getters y Setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TypesResponse getType() {
        return type;
    }

    public void setType(TypesResponse type) {
        this.type = type;
    }

    public T getResult() {
        return data;
    }

    public void setResult(T result) {
        this.data = result;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
        this.statusCode = status.value();
        // actualizar 'type' acorde a success si hiciera falta
        if (this.type == null) {
            this.type = this.success ? TypesResponse.SUCCESS : TypesResponse.ERROR;
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
        if (this.type == null) {
            this.type = success ? TypesResponse.SUCCESS : TypesResponse.ERROR;
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}