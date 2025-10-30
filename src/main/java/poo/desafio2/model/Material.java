package poo.desafio2.model;

import java.time.LocalDateTime;

public class Material {
    private String idMaterial;
    private String titulo;
    private TipoMaterial tipo;
    private int unidadesDisponibles;
    private LocalDateTime creadoEn;

    public Material() {
    }

    public Material(String idMaterial, String titulo, TipoMaterial tipo, int unidadesDisponibles) {
        this.idMaterial = idMaterial;
        this.titulo = titulo;
        this.tipo = tipo;
        this.unidadesDisponibles = unidadesDisponibles;
        this.creadoEn = LocalDateTime.now();
    }

    public String getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(String idMaterial) {
        if (idMaterial == null || idMaterial.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del material no puede estar vacío");
        }
        if (!idMaterial.matches("^(LIB|REV|CD|DVD)\\d{5}$")) {
            throw new IllegalArgumentException("Formato de ID inválido. Ej: LIB00001, REV00001");
        }
        this.idMaterial = idMaterial;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public TipoMaterial getTipo() {
        return tipo;
    }

    public void setTipo(TipoMaterial tipo) {
        this.tipo = tipo;
    }

    public int getUnidadesDisponibles() {
        return unidadesDisponibles;
    }

    public void setUnidadesDisponibles(int unidadesDisponibles) {
        if (unidadesDisponibles < 0) {
            throw new IllegalArgumentException("Las unidades disponibles no pueden ser negativas");
        }
        this.unidadesDisponibles = unidadesDisponibles;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }
}