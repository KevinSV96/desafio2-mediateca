package poo.desafio2.dao;

import poo.desafio2.model.Libro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO extends MaterialDAOImpl<Libro> {
    
@Override
protected void insertarEspecifico(Libro libro, Connection conn) throws SQLException {
    String sql = "INSERT INTO libro (id_material, autor, num_paginas, editorial, isbn, anio_publicacion, unidades_disponibles) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    PreparedStatement stmt = conn.prepareStatement(sql);
    try {
        stmt.setString(1, libro.getIdMaterial());
        stmt.setString(2, libro.getAutor());
        stmt.setInt(3, libro.getNumPaginas());           // ✅ RESTAURADO
        stmt.setString(4, libro.getEditorial());
        
        // ISBN (puede ser null)
        if (libro.getIsbn() != null && !libro.getIsbn().isEmpty()) {
            stmt.setString(5, libro.getIsbn());
        } else {
            stmt.setNull(5, java.sql.Types.VARCHAR);
        }
        
        stmt.setInt(6, libro.getAnioPublicacion());
        stmt.setInt(7, libro.getUnidadesDisponibles());
        
        stmt.executeUpdate();
        
    } catch (SQLException e) {
        System.err.println("❌ Error al insertar libro:");
        System.err.println("   ID: " + libro.getIdMaterial());
        System.err.println("   Autor: " + libro.getAutor());
        System.err.println("   Páginas: " + libro.getNumPaginas());  // ✅ DEBUG
        System.err.println("   ISBN: " + libro.getIsbn());           // ✅ DEBUG
        throw e;
    } finally {
        if (stmt != null) stmt.close();
    }
}

   @Override
protected void actualizarEspecifico(Libro libro, Connection conn) throws SQLException {
    // ✅ ACTUALIZADO: Solo los campos actuales
    String sql = "UPDATE libro SET autor = ?, anio_publicacion = ?, editorial = ?, " +
                "unidades_disponibles = ? " +
                "WHERE id_material = ?";
    
    PreparedStatement stmt = conn.prepareStatement(sql);
    
    stmt.setString(1, libro.getAutor());
    
    // Año de publicación
    if (libro.getAnioPublicacion() > 0) {
        stmt.setInt(2, libro.getAnioPublicacion());
    } else {
        stmt.setNull(2, java.sql.Types.INTEGER);
    }
    
    // Editorial
    if (libro.getEditorial() != null && !libro.getEditorial().isEmpty()) {
        stmt.setString(3, libro.getEditorial());
    } else {
        stmt.setNull(3, java.sql.Types.VARCHAR);
    }
    
    stmt.setInt(4, libro.getUnidadesDisponibles());
    stmt.setString(5, libro.getIdMaterial());
    
    stmt.executeUpdate();
    stmt.close();
}

    @Override
    public Libro obtenerPorId(String id) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbConnection.getConnection();
            String sql = "SELECT m.*, l.* FROM material m " +
                        "INNER JOIN libro l ON m.id_material = l.id_material " +
                        "WHERE m.id_material = ?";
            
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return construirObjeto(rs);
            }
            return null;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Libro> obtenerTodos() throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Libro> libros = new ArrayList<>();
        
        try {
            conn = dbConnection.getConnection();
            String sql = "SELECT m.*, l.* FROM material m " +
                        "INNER JOIN libro l ON m.id_material = l.id_material";
            
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                libros.add(construirObjeto(rs));
            }
            return libros;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }

 @Override
protected Libro construirObjeto(ResultSet rs) throws SQLException {
    Libro libro = new Libro();
    libro.setIdMaterial(rs.getString("id_material"));
    libro.setTitulo(rs.getString("titulo"));
    libro.setUnidadesDisponibles(rs.getInt("unidades_disponibles"));
    libro.setAutor(rs.getString("autor"));
    libro.setEditorial(rs.getString("editorial"));
    libro.setAnioPublicacion(rs.getInt("anio_publicacion"));
    
    
    return libro;
}
}