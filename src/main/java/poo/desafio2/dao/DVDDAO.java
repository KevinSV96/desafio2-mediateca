package poo.desafio2.dao;

import poo.desafio2.model.DVD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DVDDAO extends MaterialDAOImpl<DVD> {
    
    @Override
    protected void insertarEspecifico(DVD dvd, Connection conn) throws SQLException {
        String sql = "INSERT INTO dvd (id_material, director, duracion, genero) " +
                    "VALUES (?, ?, ?, ?)";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, dvd.getIdMaterial());
        stmt.setString(2, dvd.getDirector());
        stmt.setInt(3, dvd.getDuracion());
        stmt.setString(4, dvd.getGenero());
        stmt.executeUpdate();
    }

    @Override
    protected void actualizarEspecifico(DVD dvd, Connection conn) throws SQLException {
        String sql = "UPDATE dvd SET director = ?, duracion = ?, genero = ? " +
                    "WHERE id_material = ?";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, dvd.getDirector());
        stmt.setInt(2, dvd.getDuracion());
        stmt.setString(3, dvd.getGenero());
        stmt.setString(4, dvd.getIdMaterial());
        stmt.executeUpdate();
    }

    @Override
    public DVD obtenerPorId(String id) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbConnection.getConnection();
            String sql = "SELECT m.*, d.* FROM material m " +
                        "INNER JOIN dvd d ON m.id_material = d.id_material " +
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
    public List<DVD> obtenerTodos() throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<DVD> dvds = new ArrayList<>();
        
        try {
            conn = dbConnection.getConnection();
            String sql = "SELECT m.*, d.* FROM material m " +
                        "INNER JOIN dvd d ON m.id_material = d.id_material";
            
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                dvds.add(construirObjeto(rs));
            }
            return dvds;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    protected DVD construirObjeto(ResultSet rs) throws SQLException {
        DVD dvd = new DVD();
        dvd.setIdMaterial(rs.getString("id_material"));
        dvd.setTitulo(rs.getString("titulo"));
        dvd.setDirector(rs.getString("director"));
        dvd.setDuracion(rs.getInt("duracion"));
        dvd.setGenero(rs.getString("genero"));
        return dvd;
    }
}