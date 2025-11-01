package poo.desafio2.gui;

import java.awt.Dimension;
import javax.swing.*;
import poo.desafio2.util.LoggerManager;



public class Main {
    
    
    
    public static void main(String[] args) {

        // Configurar el look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        // Inicializar logger
        try {
            LoggerManager.initialize();
            LoggerManager.getLogger(Main.class).info("✅ Sistema de Mediateca iniciado");
        } catch (Exception e) {
            System.err.println("Error inicializando logger: " + e.getMessage());
        }
        
        // Ejecutar en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            crearYMostrarInterfazPrincipal();
        });
    }
    
    private static void crearYMostrarInterfazPrincipal() {
        JFrame ventanaPrincipal = new JFrame("Sistema de Mediateca - Desafío 2");
        ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaPrincipal.setSize(400, 300);
        ventanaPrincipal.setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton btnAgregar = new JButton("➕ Agregar Material");
        JButton btnModificar = new JButton("✏️ Modificar Material");
        JButton btnListar = new JButton("📋 Listar Materiales");
        JButton btnBuscar = new JButton("🔍 Buscar Material");
        
        // Estilo a los botones
        for (JButton boton : new JButton[]{btnAgregar, btnModificar, btnListar, btnBuscar}) {
            boton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            boton.setMaximumSize(new Dimension(200, 40));
            boton.setFocusPainted(false);
        }
        
        // ✅ ACTUALIZADO: Conectar con las ventanas reales de Persona 3
        btnAgregar.addActionListener(e -> {
            LoggerManager.getLogger(Main.class).info("Abriendo VentanaAgregarMaterial");
            new VentanaAgregarMaterial().setVisible(true);
        });
        
        btnModificar.addActionListener(e -> {
            LoggerManager.getLogger(Main.class).info("Abriendo VentanaModificarMaterial");
            new VentanaModificarMaterial().setVisible(true);
        });
        
        btnListar.addActionListener(e -> {
            LoggerManager.getLogger(Main.class).info("Abriendo VentanaListarMateriales");
            new VentanaListarMateriales().setVisible(true);
        });
        
        btnBuscar.addActionListener(e -> {
            LoggerManager.getLogger(Main.class).info("Abriendo VentanaBuscarMaterial");
            new VentanaBuscarMaterial().setVisible(true);
        });
        
        panel.add(btnAgregar);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnModificar);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnListar);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnBuscar);
        
        ventanaPrincipal.add(panel);
        ventanaPrincipal.setVisible(true);
        
        LoggerManager.getLogger(Main.class).info("Interfaz principal mostrada - Sistema listo");
    }
}