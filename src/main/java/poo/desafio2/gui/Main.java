package poo.desafio2.gui;

import java.awt.Dimension;
import javax.swing.*;
import poo.desafio2.util.LoggerManager;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        try {
            LoggerManager.initialize();
            LoggerManager.getLogger(Main.class).info("✅ Sistema de Mediateca iniciado");
        } catch (Exception e) {
            System.err.println("Error inicializando logger: " + e.getMessage());
        }
        
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
        
        for (JButton boton : new JButton[]{btnAgregar, btnModificar, btnListar, btnBuscar}) {
            boton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            boton.setMaximumSize(new Dimension(200, 40));
            boton.setFocusPainted(false);
        }
        
        btnAgregar.addActionListener(e -> {
            new VentanaAgregarMaterial().setVisible(true);
        });
        
        btnModificar.addActionListener(e -> {
            new VentanaModificarMaterial().setVisible(true);
        });
        
        btnListar.addActionListener(e -> {
            JOptionPane.showMessageDialog(ventanaPrincipal, "Función en desarrollo por Persona 3");
        });
        
        btnBuscar.addActionListener(e -> {
            JOptionPane.showMessageDialog(ventanaPrincipal, "Función en desarrollo por Persona 3");
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
    }
}