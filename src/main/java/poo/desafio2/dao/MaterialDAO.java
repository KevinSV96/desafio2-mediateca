package poo.desafio2.dao;

import java.util.List;
import poo.desafio2.model.Material;

public interface MaterialDAO<T extends Material> {
    void insertar(T material) throws Exception;
    T obtenerPorId(String id) throws Exception;
    List<T> obtenerTodos() throws Exception;
    void actualizar(T material) throws Exception;
    void eliminar(String id) throws Exception;
}