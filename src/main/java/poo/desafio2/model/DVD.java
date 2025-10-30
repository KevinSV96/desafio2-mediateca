package poo.desafio2.model;

public class DVD extends Material {
    private String director;
    private int duracion;
    private String genero;

    public DVD() {
        super();
        setTipo(TipoMaterial.DVD);
    }

    public DVD(String idMaterial, String titulo, int unidadesDisponibles,
               String director, int duracion, String genero) {
        super(idMaterial, titulo, TipoMaterial.DVD, unidadesDisponibles);
        this.director = director;
        this.duracion = duracion;
        this.genero = genero;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}