package poo.desafio2.dao;

import poo.desafio2.model.Revista;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RevistaDAO extends MaterialDAOImpl<Revista> {
    
    @Override
    protected void insertarEspecifico(Revista revista, Connection conn) throws SQLException {
        String sql = "INSERT INTO revista (id_material, editorial, periodicidad, fecha_publicacion, unidades_disponibles) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, revista.getIdMaterial());
        stmt.setString(2, revista.getEditorial());
        stmt.setString(3, revista.getPeriodicidad());
        LocalDate fecha = revista.getFechaPublicacion();
        stmt.setDate(4, fecha != null ? java.sql.Date.valueOf(fecha) : null);
        stmt.setInt(5, revista.getUnidadesDisponibles());
        stmt.executeUpdate();
    }

    @Override
    protected void actualizarEspecifico(Revista revista, Connection conn) throws SQLException {
        String sql = "UPDATE revista SET editorial = ?, periodicidad = ?, " +
                    "fecha_publicacion = ?, unidades_disponibles = ? " +
                    "WHERE id_material = ?";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, revista.getEditorial());
        stmt.setString(2, revista.getPeriodicidad());
        LocalDate fecha = revista.getFechaPublicacion();
        stmt.setDate(3, fecha != null ? java.sql.Date.valueOf(fecha) : null);
        stmt.setInt(4, revista.getUnidadesDisponibles());
        stmt.setString(5, revista.getIdMaterial());
        stmt.executeUpdate();
    }

    @Override
    public Revista obtenerPorId(String id) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbConnection.getConnection();
            String sql = "SELECT m.*, r.* FROM material m " +
                        "INNER JOIN revista r ON m.id_material = r.id_material " +
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
    public List<Revista> obtenerTodos() throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Revista> revistas = new ArrayList<>();
        
        try {
            conn = dbConnection.getConnection();
            String sql = "SELECT m.*, r.* FROM material m " +
                        "INNER JOIN revista r ON m.id_material = r.id_material";
            
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                revistas.add(construirObjeto(rs));
            }
            return revistas;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    protected Revista construirObjeto(ResultSet rs) throws SQLException {
        Revista revista = new Revista();
        revista.setIdMaterial(rs.getString("id_material"));
        revista.setTitulo(rs.getString("titulo"));
        revista.setUnidadesDisponibles(rs.getInt("unidades_disponibles"));
        revista.setEditorial(rs.getString("editorial"));
        revista.setPeriodicidad(rs.getString("periodicidad"));
        java.sql.Date sqlDate = rs.getDate("fecha_publicacion");
        revista.setFechaPublicacion(sqlDate != null ? sqlDate.toLocalDate() : null);
        return revista;
    }
}