package poo.desafio2.gui;

import poo.desafio2.dao.*;
import poo.desafio2.model.*;
import poo.desafio2.util.LoggerManager;
import poo.desafio2.exceptions.DatabaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaListarMateriales extends JFrame {
    private JTable tablaMateriales;
    private JComboBox<String> comboTipoMaterial;
    private JButton btnRefrescar;
    private JButton btnCerrar;

    public VentanaListarMateriales() {
        super("📋 Lista de Materiales - Mediateca");
        configurarVentana();
        inicializarComponentes();
        ensamblarInterfaz();
        configurarListeners();
        cargarMateriales();
        pack();
        setLocationRelativeTo(null);
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(800, 500));
    }

    private void inicializarComponentes() {
        // Combo para filtrar por tipo
        comboTipoMaterial = new JComboBox<>(new String[]{"Todos", "Libros", "Revistas", "CDs", "DVDs"});
        
        // Botones
        btnRefrescar = new JButton("🔄 Actualizar Lista");
        btnCerrar = new JButton("❌ Cerrar");
        
        // Estilo botones
        btnRefrescar.setBackground(new Color(70, 130, 180));
        btnRefrescar.setForeground(Color.WHITE);
        btnCerrar.setBackground(new Color(178, 34, 34));
        btnCerrar.setForeground(Color.WHITE);
        
        // Tabla
       String[] columnas = {"ID", "Título", "Tipo", "Detalle", "Páginas/Duración", "Disponibles"};
    DefaultTableModel model = new DefaultTableModel(columnas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    tablaMateriales = new JTable(model);
    // ... resto del código igual ...
}

    private void ensamblarInterfaz() {
        // Panel superior con controles
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelSuperior.add(new JLabel("Filtrar por tipo:"));
        panelSuperior.add(comboTipoMaterial);
        panelSuperior.add(btnRefrescar);
        panelSuperior.add(btnCerrar);

        // Panel central con tabla
        JScrollPane scrollPane = new JScrollPane(tablaMateriales);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void configurarListeners() {
        btnRefrescar.addActionListener(e -> cargarMateriales());
        btnCerrar.addActionListener(e -> dispose());
        comboTipoMaterial.addActionListener(e -> cargarMateriales());
        
        // ✅ NUEVO: Doble-clic para editar
        tablaMateriales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { // Doble clic
                    int fila = tablaMateriales.getSelectedRow();
                    if (fila >= 0) {
                        String idSeleccionado = (String) tablaMateriales.getValueAt(fila, 0);
                        editarMaterial(idSeleccionado);
                    }
                }
            }
        });
    }

    // ✅ NUEVO: Método para editar material
    private void editarMaterial(String id) {
        try {
            Material material = obtenerMaterialPorId(id);
            VentanaModificarMaterial ventanaEdicion = new VentanaModificarMaterial();
            ventanaEdicion.cargarMaterialParaEdicion(id, material);
            
            LoggerManager.getLogger(VentanaListarMateriales.class)
                .info("Editando material - ID: " + id);
                
        } catch (Exception e) {
            LoggerManager.getLogger(VentanaListarMateriales.class)
                .error("Error al cargar material para edición: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                "❌ Error al cargar el material: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // ✅ NUEVO: Método para obtener material por ID
    private Material obtenerMaterialPorId(String id) throws Exception {
        if (id.startsWith("LIB")) {
            LibroDAO libroDAO = new LibroDAO();
            return libroDAO.obtenerPorId(id);
        } else if (id.startsWith("REV")) {
            RevistaDAO revistaDAO = new RevistaDAO();
            return revistaDAO.obtenerPorId(id);
        } else if (id.startsWith("CD")) {
            CDDAO cdDAO = new CDDAO();
            return cdDAO.obtenerPorId(id);
        } else if (id.startsWith("DVD")) {
            DVDDAO dvdDAO = new DVDDAO();
            return dvdDAO.obtenerPorId(id);
        }
        throw new Exception("Tipo de material no reconocido: " + id);
    }

    private void cargarMateriales() {
        try {
            String tipoSeleccionado = (String) comboTipoMaterial.getSelectedItem();
            DefaultTableModel model = (DefaultTableModel) tablaMateriales.getModel();
            model.setRowCount(0); // Limpiar tabla

            switch (tipoSeleccionado) {
                case "Todos":
                    cargarLibros(model);
                    cargarRevistas(model);
                    cargarCDs(model);
                    cargarDVDs(model);
                    break;
                case "Libros":
                    cargarLibros(model);
                    break;
                case "Revistas":
                    cargarRevistas(model);
                    break;
                case "CDs":
                    cargarCDs(model);
                    break;
                case "DVDs":
                    cargarDVDs(model);
                    break;
            }

            LoggerManager.getLogger(VentanaListarMateriales.class)
                .info("Lista de materiales cargada - Filtro: " + tipoSeleccionado + " - Total: " + model.getRowCount());

        } catch (Exception e) {
            DatabaseException dbEx = new DatabaseException("consulta", "materiales", e);
            LoggerManager.getLogger(VentanaListarMateriales.class)
                .error("Error al cargar materiales", dbEx);
            
            JOptionPane.showMessageDialog(this,
                "❌ Error al cargar materiales: " + e.getMessage(),
                "Error de Base de Datos",
                JOptionPane.ERROR_MESSAGE);
        }
    }

  private void cargarLibros(DefaultTableModel model) {
    try {
        LibroDAO libroDAO = new LibroDAO();
        List<Libro> libros = libroDAO.obtenerTodos();
        
        for (Libro libro : libros) {
            model.addRow(new Object[]{
                libro.getIdMaterial(),
                libro.getTitulo(),
                "Libro", 
                "Autor: " + libro.getAutor(),
                libro.getNumPaginas() + " páginas",  // ✅ NUEVA COLUMNA
                libro.getUnidadesDisponibles()
            });
        }
    } catch (Exception e) {
        LoggerManager.getLogger(VentanaListarMateriales.class)
            .warn("Error al cargar libros: " + e.getMessage());
    }
}

private void cargarRevistas(DefaultTableModel model) {
    try {
        RevistaDAO revistaDAO = new RevistaDAO();
        List<Revista> revistas = revistaDAO.obtenerTodos();
        
        for (Revista revista : revistas) {
            model.addRow(new Object[]{
                revista.getIdMaterial(),
                revista.getTitulo(),
                "Revista",
                "Editorial: " + revista.getEditorial(),
                "Per: " + revista.getPeriodicidad(),  // ✅ NUEVA COLUMNA
                revista.getUnidadesDisponibles()
            });
        }
    } catch (Exception e) {
        LoggerManager.getLogger(VentanaListarMateriales.class)
            .warn("Error al cargar revistas: " + e.getMessage());
    }
}

  private void cargarCDs(DefaultTableModel model) {
    try {
        CDDAO cdDAO = new CDDAO();
        List<CD> cds = cdDAO.obtenerTodos();
        
        for (CD cd : cds) {
            model.addRow(new Object[]{
                cd.getIdMaterial(),
                cd.getTitulo(),
                "CD Audio",
                "Artista: " + cd.getArtista(),
                cd.getDuracion() + " min, " + cd.getNumCanciones() + " canciones", // ✅ NUEVA COLUMNA
                cd.getUnidadesDisponibles()
            });
        }
    } catch (Exception e) {
        LoggerManager.getLogger(VentanaListarMateriales.class)
            .warn("Error al cargar CDs: " + e.getMessage());
    }
}

private void cargarDVDs(DefaultTableModel model) {
    try {
        DVDDAO dvdDAO = new DVDDAO();
        List<DVD> dvds = dvdDAO.obtenerTodos();
        
        for (DVD dvd : dvds) {
            model.addRow(new Object[]{
                dvd.getIdMaterial(),
                dvd.getTitulo(),
                "DVD",
                "Director: " + dvd.getDirector(),
                dvd.getDuracion() + " min",  // ✅ NUEVA COLUMNA
                dvd.getUnidadesDisponibles()
            });
        }
    } catch (Exception e) {
        LoggerManager.getLogger(VentanaListarMateriales.class)
            .warn("Error al cargar DVDs: " + e.getMessage());
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaListarMateriales ventana = new VentanaListarMateriales();
            ventana.setVisible(true);
        });
    }
}