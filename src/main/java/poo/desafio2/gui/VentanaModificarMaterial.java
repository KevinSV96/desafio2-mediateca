package poo.desafio2.gui;

import poo.desafio2.dao.*;
import poo.desafio2.model.*;
import poo.desafio2.util.LoggerManager;
import poo.desafio2.exceptions.ValidationException;

import javax.swing.*;
import java.awt.*;

public class VentanaModificarMaterial extends JFrame {
    private JComboBox<String> comboTipo;
    private JTextField txtTitulo;
    private JLabel lblIdValor;
    private JPanel panelContenidoDinamico;
    private CardLayout cardLayout;
    
    // Paneles específicos
    private JPanel panelLibro;
    private JPanel panelRevista;
    private JPanel panelCD;
    private JPanel panelDVD;
    
    private String idMaterialActual;
    private TipoMaterial tipoMaterialActual;

    public VentanaModificarMaterial() {
        super("✏️ Modificar Material");
        configurarVentana();
        inicializarComponentes();
        ensamblarInterfaz();
        configurarListeners();
        pack();
        setLocationRelativeTo(null);
        setPreferredSize(new Dimension(500, 450));
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
    }

    private void inicializarComponentes() {
        comboTipo = new JComboBox<>(new String[]{"Libro", "Revista", "CD", "DVD"});
        txtTitulo = new JTextField(25);
        lblIdValor = new JLabel("---");
        
        // Crear paneles específicos
        crearPanelLibro();
        crearPanelRevista();
        crearPanelCD();
        crearPanelDVD();
        
        cardLayout = new CardLayout();
        panelContenidoDinamico = new JPanel(cardLayout);
        panelContenidoDinamico.add(panelLibro, "Libro");
        panelContenidoDinamico.add(panelRevista, "Revista");
        panelContenidoDinamico.add(panelCD, "CD");
        panelContenidoDinamico.add(panelDVD, "DVD");
    }

   private void crearPanelLibro() {
    panelLibro = new JPanel(new GridLayout(4, 2, 10, 10));
    panelLibro.add(new JLabel("Autor:*"));
    panelLibro.add(new JTextField());
    panelLibro.add(new JLabel("Año Publicación:"));
    panelLibro.add(new JTextField());
    panelLibro.add(new JLabel("Editorial:"));
    panelLibro.add(new JTextField());
    panelLibro.add(new JLabel("Unidades Disponibles:*"));
    panelLibro.add(new JTextField());
}
   
    private void crearPanelRevista() {
        panelRevista = new JPanel(new GridLayout(4, 2, 10, 10));
        panelRevista.add(new JLabel("Editorial:"));
        panelRevista.add(new JTextField());
        panelRevista.add(new JLabel("Periodicidad:"));
        panelRevista.add(new JTextField());
        panelRevista.add(new JLabel("Fecha Publicación (YYYY-MM-DD):"));
        panelRevista.add(new JTextField());
        panelRevista.add(new JLabel("Unidades Disponibles:"));
        panelRevista.add(new JTextField());
    }

    private void crearPanelCD() {
        panelCD = new JPanel(new GridLayout(5, 2, 10, 10));
        panelCD.add(new JLabel("Artista:"));
        panelCD.add(new JTextField());
        panelCD.add(new JLabel("Género:"));
        panelCD.add(new JTextField());
        panelCD.add(new JLabel("Duración (min):"));
        panelCD.add(new JTextField());
        panelCD.add(new JLabel("Número de Canciones:"));
        panelCD.add(new JTextField());
        panelCD.add(new JLabel("Unidades Disponibles:"));
        panelCD.add(new JTextField());
    }

    private void crearPanelDVD() {
        panelDVD = new JPanel(new GridLayout(4, 2, 10, 10));
        panelDVD.add(new JLabel("Director:"));
        panelDVD.add(new JTextField());
        panelDVD.add(new JLabel("Duración (min):"));
        panelDVD.add(new JTextField());
        panelDVD.add(new JLabel("Género:"));
        panelDVD.add(new JTextField());
        panelDVD.add(new JLabel("Unidades Disponibles:"));
        panelDVD.add(new JTextField());
    }

    private void ensamblarInterfaz() {
        // Panel superior con ID y tipo
        JPanel panelSuperior = new JPanel(new GridLayout(3, 2, 15, 10));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        
        panelSuperior.add(new JLabel("ID a Modificar:"));
        panelSuperior.add(lblIdValor);
        panelSuperior.add(new JLabel("Tipo de Material:"));
        panelSuperior.add(comboTipo);
        panelSuperior.add(new JLabel("Título:"));
        panelSuperior.add(txtTitulo);

        // Panel de botones
        JPanel panelBotones = crearPanelBotones();

        add(panelSuperior, BorderLayout.NORTH);
        add(panelContenidoDinamico, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton btnActualizar = new JButton("🔄 Actualizar Material");
        JButton btnCancelar = new JButton("❌ Cancelar Edición");

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

    private void configurarListeners() {
        comboTipo.addActionListener(e -> {
            String tipoSeleccionado = (String) comboTipo.getSelectedItem();
            cardLayout.show(panelContenidoDinamico, tipoSeleccionado);
            pack();
        });
    }

    public void cargarMaterialParaEdicion(String id, Material material) {
        this.idMaterialActual = id;
        this.tipoMaterialActual = material.getTipo();
        
        lblIdValor.setText(id);
        txtTitulo.setText(material.getTitulo());
        
        // Configurar combo según el tipo
        comboTipo.setSelectedItem(material.getTipo().getValor());
        
        // Cargar datos específicos según el tipo
        cargarDatosEspecificos(material);
        
        this.setVisible(true);
    }

    private void cargarDatosEspecificos(Material material) {
        if (material instanceof Libro) {
            cargarDatosLibro((Libro) material);
        } else if (material instanceof Revista) {
            cargarDatosRevista((Revista) material);
        } else if (material instanceof CD) {
            cargarDatosCD((CD) material);
        } else if (material instanceof DVD) {
            cargarDatosDVD((DVD) material);
        }
    }

    private void cargarDatosLibro(Libro libro) {
    ((JTextField) panelLibro.getComponent(1)).setText(libro.getAutor());
    ((JTextField) panelLibro.getComponent(3)).setText(libro.getAnioPublicacion() > 0 ? 
        String.valueOf(libro.getAnioPublicacion()) : "");
    ((JTextField) panelLibro.getComponent(5)).setText(libro.getEditorial());
    ((JTextField) panelLibro.getComponent(7)).setText(String.valueOf(libro.getUnidadesDisponibles()));
}
    

    private void cargarDatosRevista(Revista revista) {
        ((JTextField) panelRevista.getComponent(1)).setText(revista.getEditorial());
        ((JTextField) panelRevista.getComponent(3)).setText(revista.getPeriodicidad());
        ((JTextField) panelRevista.getComponent(5)).setText(revista.getFechaPublicacion() != null ? 
            revista.getFechaPublicacion().toString() : "");
        ((JTextField) panelRevista.getComponent(7)).setText(String.valueOf(revista.getUnidadesDisponibles()));
    }

    private void cargarDatosCD(CD cd) {
        ((JTextField) panelCD.getComponent(1)).setText(cd.getArtista());
        ((JTextField) panelCD.getComponent(3)).setText(cd.getGenero());
        ((JTextField) panelCD.getComponent(5)).setText(String.valueOf(cd.getDuracion()));
        ((JTextField) panelCD.getComponent(7)).setText(String.valueOf(cd.getNumCanciones()));
        ((JTextField) panelCD.getComponent(9)).setText(String.valueOf(cd.getUnidadesDisponibles()));
    }

    private void cargarDatosDVD(DVD dvd) {
        ((JTextField) panelDVD.getComponent(1)).setText(dvd.getDirector());
        ((JTextField) panelDVD.getComponent(3)).setText(String.valueOf(dvd.getDuracion()));
        ((JTextField) panelDVD.getComponent(5)).setText(dvd.getGenero());
        ((JTextField) panelDVD.getComponent(7)).setText(String.valueOf(dvd.getUnidadesDisponibles()));
    }

    private void accionActualizar() {
        try {
            String nuevoTitulo = txtTitulo.getText().trim();
            String tipo = (String) comboTipo.getSelectedItem();

            if (nuevoTitulo.isEmpty()) {
                throw new ValidationException("título", nuevoTitulo, "El título no puede estar vacío");
            }

            Material material = crearMaterialActualizado(nuevoTitulo, tipo);
            actualizarMaterialEnBD(material);

            LoggerManager.getLogger(VentanaModificarMaterial.class)
               .info("Material actualizado - ID: " + this.idMaterialActual + 
                     ", Tipo: " + tipo + ", Título: " + nuevoTitulo);

            JOptionPane.showMessageDialog(this,
                "✅ Material (ID " + this.idMaterialActual + ") actualizado con éxito!\n" +
                "Tipo: " + tipo + "\nTítulo: " + nuevoTitulo,
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

    private Material crearMaterialActualizado(String titulo, String tipo) throws ValidationException {
        Material material = null;
        
        switch (tipo) {
            case "Libro":
                material = crearLibroActualizado(titulo);
                break;
            case "Revista":
                material = crearRevistaActualizada(titulo);
                break;
            case "CD":
                material = crearCDActualizado(titulo);
                break;
            case "DVD":
                material = crearDVDActualizado(titulo);
                break;
        }
        
        material.setIdMaterial(this.idMaterialActual);
        return material;
    }

    private Libro crearLibroActualizado(String titulo) throws ValidationException {
    JTextField txtAutor = (JTextField) panelLibro.getComponent(1);
    JTextField txtAnio = (JTextField) panelLibro.getComponent(3);
    JTextField txtEditorial = (JTextField) panelLibro.getComponent(5);
    JTextField txtUnidades = (JTextField) panelLibro.getComponent(7);
    
    String autor = txtAutor.getText().trim();
    String unidadesStr = txtUnidades.getText().trim();
    
    if (autor.isEmpty()) {
        throw new ValidationException("autor", autor, "El autor es obligatorio");
    }
    if (unidadesStr.isEmpty()) {
        throw new ValidationException("unidades", unidadesStr, "Las unidades disponibles son obligatorias");
    }
    
    Libro libro = new Libro();
    libro.setTitulo(titulo);
    libro.setAutor(autor);
    libro.setEditorial(txtEditorial.getText().trim());
    
    try {
        if (!txtAnio.getText().trim().isEmpty()) {
            libro.setAnioPublicacion(Integer.parseInt(txtAnio.getText().trim()));
        }
        libro.setUnidadesDisponibles(Integer.parseInt(unidadesStr));
    } catch (NumberFormatException e) {
        throw new ValidationException("número", "valor", "Formato numérico inválido");
    }
    
    return libro;
}

  private Revista crearRevistaActualizada(String titulo) throws ValidationException {
    JTextField txtEditorial = (JTextField) panelRevista.getComponent(1);
    String editorial = txtEditorial.getText().trim();
    if (editorial.isEmpty()) throw new ValidationException("editorial", editorial, "La editorial es obligatoria");
    
    Revista revista = new Revista();
    revista.setTitulo(titulo);
    revista.setEditorial(editorial);
    revista.setPeriodicidad(((JTextField) panelRevista.getComponent(3)).getText().trim());
    
    try {
        // ✅ CORRECCIÓN: Declarar la variable unidades aquí
        String unidades = ((JTextField) panelRevista.getComponent(7)).getText().trim();
        if (!unidades.isEmpty()) revista.setUnidadesDisponibles(Integer.parseInt(unidades));
    } catch (NumberFormatException e) {
        throw new ValidationException("unidades", "valor", "Formato numérico inválido");
    }
    
    return revista;
}

    private CD crearCDActualizado(String titulo) throws ValidationException {
        JTextField txtArtista = (JTextField) panelCD.getComponent(1);
        String artista = txtArtista.getText().trim();
        if (artista.isEmpty()) throw new ValidationException("artista", artista, "El artista es obligatorio");
        
        CD cd = new CD();
        cd.setTitulo(titulo);
        cd.setArtista(artista);
        cd.setGenero(((JTextField) panelCD.getComponent(3)).getText().trim());
        
        try {
            String duracion = ((JTextField) panelCD.getComponent(5)).getText().trim();
            if (!duracion.isEmpty()) cd.setDuracion(Integer.parseInt(duracion));
            
            String canciones = ((JTextField) panelCD.getComponent(7)).getText().trim();
            if (!canciones.isEmpty()) cd.setNumCanciones(Integer.parseInt(canciones));
            
            String unidades = ((JTextField) panelCD.getComponent(9)).getText().trim();
            if (!unidades.isEmpty()) cd.setUnidadesDisponibles(Integer.parseInt(unidades));
        } catch (NumberFormatException e) {
            throw new ValidationException("número", "valor", "Formato numérico inválido");
        }
        
        return cd;
    }

    private DVD crearDVDActualizado(String titulo) throws ValidationException {
        JTextField txtDirector = (JTextField) panelDVD.getComponent(1);
        String director = txtDirector.getText().trim();
        if (director.isEmpty()) throw new ValidationException("director", director, "El director es obligatorio");
        
        DVD dvd = new DVD();
        dvd.setTitulo(titulo);
        dvd.setDirector(director);
        dvd.setGenero(((JTextField) panelDVD.getComponent(5)).getText().trim());
        
        try {
            String duracion = ((JTextField) panelDVD.getComponent(3)).getText().trim();
            if (!duracion.isEmpty()) dvd.setDuracion(Integer.parseInt(duracion));
            
            String unidades = ((JTextField) panelDVD.getComponent(7)).getText().trim();
            if (!unidades.isEmpty()) dvd.setUnidadesDisponibles(Integer.parseInt(unidades));
        } catch (NumberFormatException e) {
            throw new ValidationException("número", "valor", "Formato numérico inválido");
        }
        
        return dvd;
    }

    private void actualizarMaterialEnBD(Material material) throws Exception {
        if (material instanceof Libro) {
            LibroDAO libroDAO = new LibroDAO();
            libroDAO.actualizar((Libro) material);
        } else if (material instanceof Revista) {
            RevistaDAO revistaDAO = new RevistaDAO();
            revistaDAO.actualizar((Revista) material);
        } else if (material instanceof CD) {
            CDDAO cdDAO = new CDDAO();
            cdDAO.actualizar((CD) material);
        } else if (material instanceof DVD) {
            DVDDAO dvdDAO = new DVDDAO();
            dvdDAO.actualizar((DVD) material);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaModificarMaterial ventana = new VentanaModificarMaterial();
            // Ejemplo de prueba
            Libro libro = new Libro("LIB00001", "Cien años de soledad", 5, 
                                   "Gabriel Garcia Marquez", 417, "Sudamericana", "978-8437604947", 1967);
            ventana.cargarMaterialParaEdicion("LIB00001", libro);
        });
    }
}