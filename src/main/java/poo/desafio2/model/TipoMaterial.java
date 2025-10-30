package poo.desafio2.model;

public enum TipoMaterial {
    LIBRO("Libro"),
    REVISTA("Revista"),
    CD("CD"),
    DVD("DVD");

    private String valor;

    TipoMaterial(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}