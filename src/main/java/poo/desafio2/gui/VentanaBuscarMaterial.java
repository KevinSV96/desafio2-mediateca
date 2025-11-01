package poo.desafio2.gui;

import poo.desafio2.dao.*;
import poo.desafio2.model.*;
import poo.desafio2.util.LoggerManager;
import poo.desafio2.exceptions.DatabaseException;
import poo.desafio2.exceptions.ValidationException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaBuscarMaterial extends JFrame {
    private JTextField campoBusqueda;
    private JComboBox<String> comboCriterio;
    private JComboBox<String> comboTipo;
    private JButton btnBuscar;
    private JButton btnLimpiar;
    private JButton btnCerrar;
    private JTable tablaResultados;

    public VentanaBuscarMaterial() {
        super("🔍 Buscar Material - Mediateca");
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
        setPreferredSize(new Dimension(700, 500));
    }

    private void inicializarComponentes() {
        // Campos de búsqueda
        campoBusqueda = new JTextField(20);
        comboCriterio = new JComboBox<>(new String[]{"Título", "Autor/Director/Artista", "ID"});
        comboTipo = new JComboBox<>(new String[]{"Todos", "Libros", "Revistas", "CDs", "DVDs"});
        
        // Botones
        btnBuscar = new JButton("🔍 Buscar");
        btnLimpiar = new JButton("🗑️ Limpiar");
        btnCerrar = new JButton("❌ Cerrar");
        
        // Estilo botones
        btnBuscar.setBackground(new Color(34, 139, 34));
        btnBuscar.setForeground(Color.WHITE);
        btnLimpiar.setBackground(new Color(218, 165, 32));
        btnLimpiar.setForeground(Color.WHITE);
        btnCerrar.setBackground(new Color(178, 34, 34));
        btnCerrar.setForeground(Color.WHITE);
        
        // Tabla de resultados
        String[] columnas = {"ID", "Título", "Tipo", "Detalle", "Disponibles"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaResultados = new JTable(model);
    }

    private void ensamblarInterfaz() {
        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new GridLayout(2, 1, 10, 10));
        panelBusqueda.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Fila 1: Criterios
        JPanel panelCriterios = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCriterios.add(new JLabel("Buscar por:"));
        panelCriterios.add(comboCriterio);
        panelCriterios.add(new JLabel("Tipo:"));
        panelCriterios.add(comboTipo);
        panelCriterios.add(new JLabel("Texto:"));
        panelCriterios.add(campoBusqueda);
        
        // Fila 2: Botones
        JPanel panelBotonesBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBotonesBusqueda.add(btnBuscar);
        panelBotonesBusqueda.add(btnLimpiar);
        panelBotonesBusqueda.add(btnCerrar);
        
        panelBusqueda.add(panelCriterios);
        panelBusqueda.add(panelBotonesBusqueda);

        // Panel de resultados
        JScrollPane scrollPane = new JScrollPane(tablaResultados);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Resultados de la búsqueda"));

        add(panelBusqueda, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void configurarListeners() {
        btnBuscar.addActionListener(e -> realizarBusqueda());
        btnLimpiar.addActionListener(e -> limpiarBusqueda());
        btnCerrar.addActionListener(e -> dispose());
        
        // Buscar al presionar Enter
        campoBusqueda.addActionListener(e -> realizarBusqueda());
        
        // ✅ NUEVO: Doble-clic para editar en resultados de búsqueda
        tablaResultados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int fila = tablaResultados.getSelectedRow();
                    if (fila >= 0) {
                        String idSeleccionado = (String) tablaResultados.getValueAt(fila, 0);
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
            
            LoggerManager.getLogger(VentanaBuscarMaterial.class)
                .info("Editando material desde búsqueda - ID: " + id);
                
        } catch (Exception e) {
            LoggerManager.getLogger(VentanaBuscarMaterial.class)
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

    private void realizarBusqueda() {
        try {
            String texto = campoBusqueda.getText().trim();
            String criterio = (String) comboCriterio.getSelectedItem();
            String tipo = (String) comboTipo.getSelectedItem();
            
            // Validación
            if (texto.isEmpty()) {
                throw new ValidationException("texto búsqueda", texto, "Ingrese un término de búsqueda");
            }
            
            DefaultTableModel model = (DefaultTableModel) tablaResultados.getModel();
            model.setRowCount(0);
            
            int resultados = 0;
            
            // Buscar según tipo seleccionado
            if (tipo.equals("Todos") || tipo.equals("Libros")) {
                resultados += buscarLibros(texto, criterio, model);
            }
            if (tipo.equals("Todos") || tipo.equals("Revistas")) {
                resultados += buscarRevistas(texto, criterio, model);
            }
            if (tipo.equals("Todos") || tipo.equals("CDs")) {
                resultados += buscarCDs(texto, criterio, model);
            }
            if (tipo.equals("Todos") || tipo.equals("DVDs")) {
                resultados += buscarDVDs(texto, criterio, model);
            }
            
            LoggerManager.getLogger(VentanaBuscarMaterial.class)
                .info("Búsqueda completada - Término: '" + texto + "' - Resultados: " + resultados);
            
            if (resultados == 0) {
                JOptionPane.showMessageDialog(this,
                    "🔍 No se encontraron materiales con los criterios especificados",
                    "Búsqueda sin resultados",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (ValidationException e) {
            LoggerManager.getLogger(VentanaBuscarMaterial.class)
                .warn("Validación fallida en búsqueda: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                "❌ " + e.getMessage(),
                "Error de Validación",
                JOptionPane.WARNING_MESSAGE);
                
        } catch (Exception e) {
            DatabaseException dbEx = new DatabaseException("búsqueda", "materiales", e);
            LoggerManager.getLogger(VentanaBuscarMaterial.class)
                .error("Error en búsqueda", dbEx);
            JOptionPane.showMessageDialog(this,
                "⚠️ Error en la búsqueda: " + e.getMessage(),
                "Error del Sistema",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private int buscarLibros(String texto, String criterio, DefaultTableModel model) {
        try {
            LibroDAO libroDAO = new LibroDAO();
            List<Libro> libros = libroDAO.obtenerTodos();
            int encontrados = 0;
            
            for (Libro libro : libros) {
                boolean coincide = false;
                switch (criterio) {
                    case "Título":
                        coincide = libro.getTitulo().toLowerCase().contains(texto.toLowerCase());
                        break;
                    case "Autor/Director/Artista":
                        coincide = libro.getAutor().toLowerCase().contains(texto.toLowerCase());
                        break;
                    case "ID":
                        coincide = libro.getIdMaterial().equalsIgnoreCase(texto);
                        break;
                }
                
                if (coincide) {
                    model.addRow(new Object[]{
                        libro.getIdMaterial(),
                        libro.getTitulo(),
                        "Libro",
                        "Autor: " + libro.getAutor(),
                        libro.getUnidadesDisponibles()
                    });
                    encontrados++;
                }
            }
            return encontrados;
        } catch (Exception e) {
            LoggerManager.getLogger(VentanaBuscarMaterial.class)
                .warn("Error buscando libros: " + e.getMessage());
            return 0;
        }
    }

    private int buscarRevistas(String texto, String criterio, DefaultTableModel model) {
        try {
            RevistaDAO revistaDAO = new RevistaDAO();
            List<Revista> revistas = revistaDAO.obtenerTodos();
            int encontrados = 0;
            
            for (Revista revista : revistas) {
                boolean coincide = false;
                switch (criterio) {
                    case "Título":
                        coincide = revista.getTitulo().toLowerCase().contains(texto.toLowerCase());
                        break;
                    case "Autor/Director/Artista":
                        coincide = revista.getEditorial().toLowerCase().contains(texto.toLowerCase());
                        break;
                    case "ID":
                        coincide = revista.getIdMaterial().equalsIgnoreCase(texto);
                        break;
                }
                
                if (coincide) {
                    model.addRow(new Object[]{
                        revista.getIdMaterial(),
                        revista.getTitulo(),
                        "Revista",
                        "Editorial: " + revista.getEditorial(),
                        revista.getUnidadesDisponibles()
                    });
                    encontrados++;
                }
            }
            return encontrados;
        } catch (Exception e) {
            LoggerManager.getLogger(VentanaBuscarMaterial.class)
                .warn("Error buscando revistas: " + e.getMessage());
            return 0;
        }
    }

    private int buscarCDs(String texto, String criterio, DefaultTableModel model) {
        try {
            CDDAO cdDAO = new CDDAO();
            List<CD> cds = cdDAO.obtenerTodos();
            int encontrados = 0;
            
            for (CD cd : cds) {
                boolean coincide = false;
                switch (criterio) {
                    case "Título":
                        coincide = cd.getTitulo().toLowerCase().contains(texto.toLowerCase());
                        break;
                    case "Autor/Director/Artista":
                        coincide = cd.getArtista().toLowerCase().contains(texto.toLowerCase());
                        break;
                    case "ID":
                        coincide = cd.getIdMaterial().equalsIgnoreCase(texto);
                        break;
                }
                
                if (coincide) {
                    model.addRow(new Object[]{
                        cd.getIdMaterial(),
                        cd.getTitulo(),
                        "CD",
                        "Artista: " + cd.getArtista(),
                        cd.getUnidadesDisponibles()
                    });
                    encontrados++;
                }
            }
            return encontrados;
        } catch (Exception e) {
            LoggerManager.getLogger(VentanaBuscarMaterial.class)
                .warn("Error buscando CDs: " + e.getMessage());
            return 0;
        }
    }

    private int buscarDVDs(String texto, String criterio, DefaultTableModel model) {
        try {
            DVDDAO dvdDAO = new DVDDAO();
            List<DVD> dvds = dvdDAO.obtenerTodos();
            int encontrados = 0;
            
            for (DVD dvd : dvds) {
                boolean coincide = false;
                switch (criterio) {
                    case "Título":
                        coincide = dvd.getTitulo().toLowerCase().contains(texto.toLowerCase());
                        break;
                    case "Autor/Director/Artista":
                        coincide = dvd.getDirector().toLowerCase().contains(texto.toLowerCase());
                        break;
                    case "ID":
                        coincide = dvd.getIdMaterial().equalsIgnoreCase(texto);
                        break;
                }
                
                if (coincide) {
                    model.addRow(new Object[]{
                        dvd.getIdMaterial(),
                        dvd.getTitulo(),
                        "DVD",
                        "Director: " + dvd.getDirector(),
                        dvd.getUnidadesDisponibles()
                    });
                    encontrados++;
                }
            }
            return encontrados;
        } catch (Exception e) {
            LoggerManager.getLogger(VentanaBuscarMaterial.class)
                .warn("Error buscando DVDs: " + e.getMessage());
            return 0;
        }
    }

    private void limpiarBusqueda() {
        campoBusqueda.setText("");
        ((DefaultTableModel) tablaResultados.getModel()).setRowCount(0);
        LoggerManager.getLogger(VentanaBuscarMaterial.class)
            .info("Búsqueda limpiada");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaBuscarMaterial ventana = new VentanaBuscarMaterial();
            ventana.setVisible(true);
        });
    }
}