package poo.desafio2.gui;

import javax.swing.*;
import java.awt.*;
import poo.desafio2.util.LoggerManager;
import poo.desafio2.exceptions.ValidationException;

public class VentanaModificarMaterial extends JFrame {
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JLabel lblIdValor;
    private int idMaterialActual;

    public VentanaModificarMaterial() {
        super("Ventana de Edición: Modificar Material");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel panelId = crearPanelId();
        JPanel panelCampos = crearPanelEntrada();
        JPanel panelBotones = crearPanelBotones();

        add(panelId, BorderLayout.NORTH);
        add(panelCampos, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private JPanel crearPanelId() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 10));
        panel.add(new JLabel("ID a Modificar:"));
        lblIdValor = new JLabel("---");
        panel.add(lblIdValor);
        return panel;
    }

    private JPanel crearPanelEntrada() {
        JPanel panelContenido = new JPanel(new GridLayout(2, 2, 15, 15));
        JPanel panelMargen = new JPanel(new BorderLayout());
        panelMargen.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        txtTitulo = new JTextField(25);
        txtAutor = new JTextField(25);

        panelContenido.add(new JLabel("Título:"));
        panelContenido.add(txtTitulo);
        panelContenido.add(new JLabel("Autor:"));
        panelContenido.add(txtAutor);

        panelMargen.add(panelContenido, BorderLayout.CENTER);
        return panelMargen;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton btnActualizar = new JButton("Actualizar Material");
        JButton btnCancelar = new JButton("Cancelar Edicion");

        btnActualizar.setBackground(new Color(60, 179, 113));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFocusPainted(false);
        btnActualizar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnActualizar.setPreferredSize(new Dimension(200, 40));

        btnCancelar.setBackground(new Color(220, 20, 60));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnCancelar.setPreferredSize(new Dimension(180, 40));

        panel.add(btnActualizar);
        panel.add(btnCancelar);

        btnActualizar.addActionListener(e -> accionActualizar());
        btnCancelar.addActionListener(e -> dispose());

        return panel;
    }

    public void cargarMaterialParaEdicion(int id, String titulo, String autor) {
        this.idMaterialActual = id;
        lblIdValor.setText("ID: " + id);
        txtTitulo.setText(titulo);
        txtAutor.setText(autor);
        this.setVisible(true);
    }

    private void accionActualizar() {
        try {
            String nuevoTitulo = txtTitulo.getText().trim();
            String nuevoAutor = txtAutor.getText().trim();

            if (nuevoTitulo.isEmpty()) {
                throw new ValidationException("título", nuevoTitulo, "El título no puede estar vacío");
            }
            if (nuevoAutor.isEmpty()) {
                throw new ValidationException("autor", nuevoAutor, "El autor no puede estar vacío");
            }

            LoggerManager.getLogger(VentanaModificarMaterial.class)
               .info("Material actualizado - ID: " + this.idMaterialActual + 
                     ", Título: " + nuevoTitulo + ", Autor: " + nuevoAutor);

            JOptionPane.showMessageDialog(this,
                "✅ Material (ID " + this.idMaterialActual + ") actualizado con éxito!\n" +
                "Título: " + nuevoTitulo + "\nAutor: " + nuevoAutor +
                "\n\nPróximamente se actualizará en la base de datos",
                "Actualización Exitosa",
                JOptionPane.INFORMATION_MESSAGE);

            this.dispose();

        } catch (ValidationException e) {
            LoggerManager.getLogger(VentanaModificarMaterial.class)
               .warn("Validación fallida en modificación: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                "❌ " + e.getMessage(),
                "Error de Validación",
                JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            LoggerManager.getLogger(VentanaModificarMaterial.class)
               .error("Error inesperado en modificación: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(this,
                "⚠️ Error inesperado: " + e.getMessage(),
                "Error del Sistema",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaModificarMaterial ventana = new VentanaModificarMaterial();
            ventana.cargarMaterialParaEdicion(42, "Cien años de soledad", "Gabriel Garcia Marquez");
        });
    }
}