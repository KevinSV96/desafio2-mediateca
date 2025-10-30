package poo.desafio2.gui;

import javax.swing.*;
import java.awt.*;
import poo.desafio2.util.LoggerManager;
import poo.desafio2.exceptions.ValidationException;

public class VentanaAgregarMaterial extends JFrame {
    private static final Color COLOR_GUARDAR = new Color(30, 144, 255);
    private static final Color COLOR_CANCELAR = new Color(105, 105, 105);
    private static final Color COLOR_TEXTO_BOTON = Color.WHITE;

    private JTextField campoTitulo;
    private JTextField campoAutor;
    private JButton botonGuardar;
    private JButton botonCancelar;

    public VentanaAgregarMaterial() {
        super("➕ Agregar Nuevo Material");
        configurarVentana();
        inicializarComponentes();
        ensamblarInterfaz();
        configurarListeners();
        pack();
        setLocationRelativeTo(null);
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
    }

    private void inicializarComponentes() {
        campoTitulo = new JTextField(25);
        campoAutor = new JTextField(25);

        botonGuardar = new JButton(" GUARDAR MATERIAL ");
        botonGuardar.setBackground(COLOR_GUARDAR);
        botonGuardar.setForeground(COLOR_TEXTO_BOTON);
        botonGuardar.setFocusPainted(false);
        botonGuardar.setFont(new Font("Arial", Font.BOLD, 14));
        botonGuardar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        botonCancelar = new JButton(" CANCELAR ");
        botonCancelar.setBackground(COLOR_CANCELAR);
        botonCancelar.setForeground(COLOR_TEXTO_BOTON);
        botonCancelar.setFocusPainted(false);
        botonCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        botonCancelar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
    }

    private void ensamblarInterfaz() {
        JPanel panelContenido = crearPanelContenido();
        JPanel panelBotones = crearPanelBotones();
        add(panelContenido, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private JPanel crearPanelContenido() {
        JPanel panelCampos = new JPanel(new GridLayout(2, 2, 15, 15));
        panelCampos.add(new JLabel("Título:"));
        panelCampos.add(campoTitulo);
        panelCampos.add(new JLabel("Autor:"));
        panelCampos.add(campoAutor);

        JPanel panelMargen = new JPanel(new BorderLayout());
        panelMargen.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));
        panelMargen.add(panelCampos, BorderLayout.CENTER);
        return panelMargen;
    }

    private JPanel crearPanelBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.add(botonGuardar);
        panelBotones.add(botonCancelar);
        return panelBotones;
    }

    private void configurarListeners() {
        botonGuardar.addActionListener(e -> manejarAccionGuardar());
        botonCancelar.addActionListener(e -> dispose());
    }

    private void manejarAccionGuardar() {
        try {
            String titulo = campoTitulo.getText().trim();
            String autor = campoAutor.getText().trim();
            
            if (titulo.isEmpty()) {
                throw new ValidationException("título", titulo, "El título es obligatorio");
            }
            if (autor.isEmpty()) {
                throw new ValidationException("autor", autor, "El autor es obligatorio");
            }
            
            LoggerManager.getLogger(VentanaAgregarMaterial.class)
               .info("Validación exitosa - Próximamente se guardará en BD: " + titulo);
            
            JOptionPane.showMessageDialog(this,
                "✅ Validación exitosa!\nTítulo: " + titulo + "\nAutor: " + autor +
                "\n\nPróximamente se conectará con la base de datos",
                "Validación Exitosa", 
                JOptionPane.INFORMATION_MESSAGE);
            
            limpiarCampos();
            
        } catch (ValidationException e) {
            LoggerManager.getLogger(VentanaAgregarMaterial.class)
               .warn("Validación fallida: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "❌ " + e.getMessage(),
                "Error de Validación", 
                JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            LoggerManager.getLogger(VentanaAgregarMaterial.class)
               .error("Error inesperado: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(this, 
                "⚠️ Error inesperado: " + e.getMessage(),
                "Error del Sistema", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        campoTitulo.setText("");
        campoAutor.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaAgregarMaterial ventana = new VentanaAgregarMaterial();
            ventana.setVisible(true);
        });
    }
}