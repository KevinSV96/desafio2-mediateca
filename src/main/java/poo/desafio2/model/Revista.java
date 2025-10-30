package poo.desafio2.model;

import java.time.LocalDate;

public class Revista extends Material {
    private String editorial;
    private String periodicidad;
    private LocalDate fechaPublicacion;

    public Revista() {
        super();
        setTipo(TipoMaterial.REVISTA);
    }

    public Revista(String idMaterial, String titulo, int unidadesDisponibles,
                   String editorial, String periodicidad, LocalDate fechaPublicacion) {
        super(idMaterial, titulo, TipoMaterial.REVISTA, unidadesDisponibles);
        this.editorial = editorial;
        this.periodicidad = periodicidad;
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
}