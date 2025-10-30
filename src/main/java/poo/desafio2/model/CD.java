package poo.desafio2.model;

public class CD extends Material {
    private String artista;
    private String genero;
    private int duracion;
    private int numCanciones;

    public CD() {
        super();
        setTipo(TipoMaterial.CD);
    }

    public CD(String idMaterial, String titulo, int unidadesDisponibles,
              String artista, String genero, int duracion, int numCanciones) {
        super(idMaterial, titulo, TipoMaterial.CD, unidadesDisponibles);
        this.artista = artista;
        this.genero = genero;
        this.duracion = duracion;
        this.numCanciones = numCanciones;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        if (duracion < 0) {
            throw new IllegalArgumentException("La duración no puede ser negativa");
        }
        this.duracion = duracion;
    }

    public int getNumCanciones() {
        return numCanciones;
    }

    public void setNumCanciones(int numCanciones) {
        if (numCanciones < 0) {
            throw new IllegalArgumentException("El número de canciones no puede ser negativo");
        }
        this.numCanciones = numCanciones;
    }
}