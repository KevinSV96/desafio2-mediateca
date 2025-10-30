package poo.desafio2.dao;

import poo.desafio2.model.CD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CDDAO extends MaterialDAOImpl<CD> {
    
    @Override
    protected void insertarEspecifico(CD cd, Connection conn) throws SQLException {
        String sql = "INSERT INTO cd (id_material, artista, genero, duracion, num_canciones, unidades_disponibles) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, cd.getIdMaterial());
        stmt.setString(2, cd.getArtista());
        stmt.setString(3, cd.getGenero());
        stmt.setInt(4, cd.getDuracion());
        stmt.setInt(5, cd.getNumCanciones());
        stmt.setInt(6, cd.getUnidadesDisponibles());
        stmt.executeUpdate();
    }

    @Override
    protected void actualizarEspecifico(CD cd, Connection conn) throws SQLException {
        String sql = "UPDATE cd SET artista = ?, genero = ?, duracion = ?, " +
                    "num_canciones = ?, unidades_disponibles = ? " +
                    "WHERE id_material = ?";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, cd.getArtista());
        stmt.setString(2, cd.getGenero());
        stmt.setInt(3, cd.getDuracion());
        stmt.setInt(4, cd.getNumCanciones());
        stmt.setInt(5, cd.getUnidadesDisponibles());
        stmt.setString(6, cd.getIdMaterial());
        stmt.executeUpdate();
    }

    @Override
    public CD obtenerPorId(String id) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbConnection.getConnection();
            String sql = "SELECT m.*, c.* FROM material m " +
                        "INNER JOIN cd c ON m.id_material = c.id_material " +
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
    public List<CD> obtenerTodos() throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<CD> cds = new ArrayList<>();
        
        try {
            conn = dbConnection.getConnection();
            String sql = "SELECT m.*, c.* FROM material m " +
                        "INNER JOIN cd c ON m.id_material = c.id_material";
            
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                cds.add(construirObjeto(rs));
            }
            return cds;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    protected CD construirObjeto(ResultSet rs) throws SQLException {
        CD cd = new CD();
        cd.setIdMaterial(rs.getString("id_material"));
        cd.setTitulo(rs.getString("titulo"));
        cd.setUnidadesDisponibles(rs.getInt("unidades_disponibles"));
        cd.setArtista(rs.getString("artista"));
        cd.setGenero(rs.getString("genero"));
        cd.setDuracion(rs.getInt("duracion"));
        cd.setNumCanciones(rs.getInt("num_canciones"));
        return cd;
    }
}