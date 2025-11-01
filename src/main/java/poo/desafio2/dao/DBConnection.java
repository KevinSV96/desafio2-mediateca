package poo.desafio2.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    static {
        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ Driver MySQL cargado correctamente");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error cargando driver MySQL", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        // ✅ CONFIGURACIÓN DIRECTA EN EL CÓDIGO
        String url = "jdbc:mysql://localhost:3306/mediateca?serverTimezone=UTC";
        String username = "root";
        String password = "";  // Normalmente vacío en XAMPP
        
        System.out.println("🔗 Conectando a: " + url);
        
        return DriverManager.getConnection(url, username, password);
    }
}