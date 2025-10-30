package poo.desafio2.exceptions;

public class DatabaseException extends Exception {
    private String operacion;
    private String tabla;
    
    public DatabaseException(String message) {
        super(message);
    }
    
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public DatabaseException(String operacion, String tabla, Throwable cause) {
        super("Error en operación '" + operacion + "' sobre la tabla '" + tabla + "'", cause);
        this.operacion = operacion;
        this.tabla = tabla;
    }
    
    public String getOperacion() { return operacion; }
    public String getTabla() { return tabla; }
}