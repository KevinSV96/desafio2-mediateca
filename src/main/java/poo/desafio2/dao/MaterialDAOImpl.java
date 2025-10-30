package poo.desafio2.dao;

import poo.desafio2.model.Material;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class MaterialDAOImpl<T extends Material> implements MaterialDAO<T> {
    protected DBConnection dbConnection;

    public MaterialDAOImpl() {
        this.dbConnection = new DBConnection();
    }

    @Override
    public void insertar(T material) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = dbConnection.getConnection();
            conn.setAutoCommit(false);

            String sqlMaterial = "INSERT INTO material (id_material, titulo, tipo) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sqlMaterial);
            stmt.setString(1, material.getIdMaterial());
            stmt.setString(2, material.getTitulo());
            stmt.setString(3, material.getTipo().toString());
            stmt.executeUpdate();

            insertarEspecifico(material, conn);
            conn.commit();

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new Exception("Error al revertir la transacción", ex);
                }
            }
            throw new Exception("Error al insertar el material", e);
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public void actualizar(T material) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = dbConnection.getConnection();
            conn.setAutoCommit(false);

            String sqlMaterial = "UPDATE material SET titulo = ? WHERE id_material = ?";
            stmt = conn.prepareStatement(sqlMaterial);
            stmt.setString(1, material.getTitulo());
            stmt.setString(2, material.getIdMaterial());
            stmt.executeUpdate();

            actualizarEspecifico(material, conn);
            conn.commit();

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new Exception("Error al revertir la transacción", ex);
                }
            }
            throw new Exception("Error al actualizar el material", e);
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public void eliminar(String id) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = dbConnection.getConnection();
            String sql = "DELETE FROM material WHERE id_material = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception("Error al eliminar el material", e);
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }

    protected abstract void insertarEspecifico(T material, Connection conn) throws SQLException;
    protected abstract void actualizarEspecifico(T material, Connection conn) throws SQLException;
    protected abstract T construirObjeto(ResultSet rs) throws SQLException;
}