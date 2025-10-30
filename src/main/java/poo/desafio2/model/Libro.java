package poo.desafio2.model;

import java.time.Year;

public class Libro extends Material {
    private String autor;
    private int numPaginas;
    private String editorial;
    private String isbn;
    private int anioPublicacion;

    public Libro() {
        super();
        setTipo(TipoMaterial.LIBRO);
    }

    public Libro(String idMaterial, String titulo, int unidadesDisponibles,
                 String autor, int numPaginas, String editorial, String isbn, int anioPublicacion) {
        super(idMaterial, titulo, TipoMaterial.LIBRO, unidadesDisponibles);
        this.autor = autor;
        this.numPaginas = numPaginas;
        this.editorial = editorial;
        this.isbn = isbn;
        this.anioPublicacion = anioPublicacion;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getNumPaginas() {
        return numPaginas;
    }

    public void setNumPaginas(int numPaginas) {
        this.numPaginas = numPaginas;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        if (anioPublicacion < 1000 || anioPublicacion > Year.now().getValue() + 1) {
            throw new IllegalArgumentException(
                "El año de publicación debe estar entre 1000 y " + (Year.now().getValue() + 1)
            );
        }
        this.anioPublicacion = anioPublicacion;
    }
}