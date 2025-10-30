package poo.desafio2.exceptions;

public class ValidationException extends Exception {
    private String campo;
    private String valor;
    
    public ValidationException(String message) {
        super(message);
    }
    
    public ValidationException(String campo, String valor, String mensaje) {
        super(String.format("Error en campo '%s' (valor: '%s'): %s", campo, valor, mensaje));
        this.campo = campo;
        this.valor = valor;
    }
    
    public String getCampo() { return campo; }
    public String getValor() { return valor; }
}