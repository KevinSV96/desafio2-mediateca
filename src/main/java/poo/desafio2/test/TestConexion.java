package poo.desafio2.test;

import poo.desafio2.dao.DBConnection;
import poo.desafio2.util.LoggerManager;
import java.sql.Connection;
import java.sql.Statement;

public class TestConexion {
    public static void main(String[] args) {
        try {
            System.out.println("🧪 Probando conexión a la base de datos...");
            
            Connection conn = DBConnection.getConnection();
            LoggerManager.getLogger(TestConexion.class)
                .info("✅ Conexión a BD exitosa!");
            
            // Probar consulta simple
            Statement stmt = conn.createStatement();
            stmt.execute("SELECT 1");
            stmt.close();
            
            System.out.println("✅ Consulta SQL ejecutada correctamente!");
            
            conn.close();
            System.out.println("🎉 ¡Conexión a BD funcionando perfectamente!");
            
        } catch (Exception e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }
}