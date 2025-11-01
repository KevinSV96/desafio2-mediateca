package poo.desafio2.gui;

import poo.desafio2.dao.*;
import poo.desafio2.model.*;
import poo.desafio2.util.LoggerManager;
import poo.desafio2.exceptions.ValidationException;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class VentanaAgregarMaterial extends JFrame {
    private static final Color COLOR_GUARDAR = new Color(30, 144, 255);
    private static final Color COLOR_CANCELAR = new Color(105, 105, 105);
    private static final Color COLOR_TEXTO_BOTON = Color.WHITE;

    // Componentes base
    private JComboBox<String> comboTipo;
    private JTextField campoTitulo;
    private JButton botonGuardar;
    private JButton botonCancelar;
    
    // Paneles específicos por tipo
    private JPanel panelLibro;
    private JPanel panelRevista;
    private JPanel panelCD;
    private JPanel panelDVD;
    private CardLayout cardLayout;
    private JPanel panelContenidoDinamico;

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
        setPreferredSize(new Dimension(500, 500));
    }

    private void inicializarComponentes() {
        // Combo para seleccionar tipo
        comboTipo = new JComboBox<>(new String[]{"Libro", "Revista", "CD", "DVD"});
        campoTitulo = new JTextField(25);

        // Botones
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

        // Crear paneles específicos
        crearPanelLibro();
        crearPanelRevista();
        crearPanelCD();
        crearPanelDVD();
        
        // Panel dinámico
        cardLayout = new CardLayout();
        panelContenidoDinamico = new JPanel(cardLayout);
        panelContenidoDinamico.add(panelLibro, "Libro");
        panelContenidoDinamico.add(panelRevista, "Revista");
        panelContenidoDinamico.add(panelCD, "CD");
        panelContenidoDinamico.add(panelDVD, "DVD");
    }

    private void crearPanelLibro() {
        panelLibro = new JPanel(new GridLayout(7, 2, 10, 10)); // ✅ 7 CAMPOS
        panelLibro.add(new JLabel("Autor:*"));
        panelLibro.add(new JTextField());
        panelLibro.add(new JLabel("Número de Páginas:*"));     // ✅ RESTAURADO
        panelLibro.add(new JTextField());
        panelLibro.add(new JLabel("Editorial:*"));
        panelLibro.add(new JTextField());
        panelLibro.add(new JLabel("ISBN:"));                   // ✅ RESTAURADO
        panelLibro.add(new JTextField());
        panelLibro.add(new JLabel("Año Publicación:*"));
        panelLibro.add(new JTextField());
        panelLibro.add(new JLabel("Unidades Disponibles:*"));
        panelLibro.add(new JTextField());
    }

    private void crearPanelRevista() {
        panelRevista = new JPanel(new GridLayout(5, 2, 10, 10));
        panelRevista.add(new JLabel("Editorial:*"));
        panelRevista.add(new JTextField());
        panelRevista.add(new JLabel("Periodicidad:*"));
        panelRevista.add(new JTextField());
        panelRevista.add(new JLabel("Fecha Publicación (YYYY-MM-DD):*"));
        panelRevista.add(new JTextField());
        panelRevista.add(new JLabel("Unidades Disponibles:*"));
        panelRevista.add(new JTextField());
    }

    private void crearPanelCD() {
        panelCD = new JPanel(new GridLayout(6, 2, 10, 10));
        panelCD.add(new JLabel("Artista:*"));
        panelCD.add(new JTextField());
        panelCD.add(new JLabel("Género:*"));
        panelCD.add(new JTextField());
        panelCD.add(new JLabel("Duración (minutos):*"));
        panelCD.add(new JTextField());
        panelCD.add(new JLabel("Número de Canciones:*"));
        panelCD.add(new JTextField());
        panelCD.add(new JLabel("Unidades Disponibles:*"));
        panelCD.add(new JTextField());
    }

    private void crearPanelDVD() {
        panelDVD = new JPanel(new GridLayout(5, 2, 10, 10));
        panelDVD.add(new JLabel("Director:*"));
        panelDVD.add(new JTextField());
        panelDVD.add(new JLabel("Duración (minutos):*"));
        panelDVD.add(new JTextField());
        panelDVD.add(new JLabel("Género:*"));
        panelDVD.add(new JTextField());
        panelDVD.add(new JLabel("Unidades Disponibles:*"));
        panelDVD.add(new JTextField());
    }

    private void ensamblarInterfaz() {
        JPanel panelSuperior = new JPanel(new GridLayout(2, 2, 15, 15));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));
        
        panelSuperior.add(new JLabel("Tipo de Material:"));
        panelSuperior.add(comboTipo);
        panelSuperior.add(new JLabel("Título:*"));
        panelSuperior.add(campoTitulo);

        JPanel panelBotones = crearPanelBotones();

        add(panelSuperior, BorderLayout.NORTH);
        add(panelContenidoDinamico, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
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
        comboTipo.addActionListener(e -> {
            String tipoSeleccionado = (String) comboTipo.getSelectedItem();
            cardLayout.show(panelContenidoDinamico, tipoSeleccionado);
            pack();
        });
    }

    private void manejarAccionGuardar() {
        try {
            String tipo = (String) comboTipo.getSelectedItem();
            String titulo = campoTitulo.getText().trim();
            
            // Validación título
            if (titulo.isEmpty()) {
                throw new ValidationException("título", titulo, "El título es obligatorio");
            }
            
            // Crear material según el tipo seleccionado
            Material material = crearMaterialSegunTipo(tipo, titulo);
            
            // Guardar en base de datos
            guardarMaterialEnBD(material);
            
            LoggerManager.getLogger(VentanaAgregarMaterial.class)
               .info("Material agregado exitosamente - Tipo: " + tipo + ", Título: " + titulo);
            
            JOptionPane.showMessageDialog(this,
                "✅ " + tipo + " agregado exitosamente!\nTítulo: " + titulo +
                "\nCódigo: " + material.getIdMaterial(),
                "Guardado Exitoso", 
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
                "⚠️ Error al guardar: " + e.getMessage(),
                "Error del Sistema", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private Material crearMaterialSegunTipo(String tipo, String titulo) throws ValidationException {
        switch (tipo) {
            case "Libro":
                Libro libro = crearLibro(titulo);
                libro.setTipo(poo.desafio2.model.TipoMaterial.LIBRO);
                return libro;
            case "Revista":
                Revista revista = crearRevista(titulo);
                revista.setTipo(poo.desafio2.model.TipoMaterial.REVISTA);
                return revista;
            case "CD":
                CD cd = crearCD(titulo);
                cd.setTipo(poo.desafio2.model.TipoMaterial.CD);
                return cd;
            case "DVD":
                DVD dvd = crearDVD(titulo);
                dvd.setTipo(poo.desafio2.model.TipoMaterial.DVD);
                return dvd;
            default:
                throw new ValidationException("tipo", tipo, "Tipo de material no válido");
        }
    }

    private Libro crearLibro(String titulo) throws ValidationException {
        JTextField txtAutor = (JTextField) panelLibro.getComponent(1);
        JTextField txtPaginas = (JTextField) panelLibro.getComponent(3);
        JTextField txtEditorial = (JTextField) panelLibro.getComponent(5);
        JTextField txtISBN = (JTextField) panelLibro.getComponent(7);
        JTextField txtAnio = (JTextField) panelLibro.getComponent(9);
        JTextField txtUnidades = (JTextField) panelLibro.getComponent(11);
        
        // Validaciones obligatorias
        String autor = txtAutor.getText().trim();
        String paginasStr = txtPaginas.getText().trim();
        String editorial = txtEditorial.getText().trim();
        String anioStr = txtAnio.getText().trim();
        String unidadesStr = txtUnidades.getText().trim();
        
        if (autor.isEmpty()) throw new ValidationException("autor", autor, "El autor es obligatorio");
        if (paginasStr.isEmpty()) throw new ValidationException("páginas", paginasStr, "El número de páginas es obligatorio");
        if (editorial.isEmpty()) throw new ValidationException("editorial", editorial, "La editorial es obligatoria");
        if (anioStr.isEmpty()) throw new ValidationException("año", anioStr, "El año de publicación es obligatorio");
        if (unidadesStr.isEmpty()) throw new ValidationException("unidades", unidadesStr, "Las unidades disponibles son obligatorias");
        
        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libro.setIsbn(txtISBN.getText().trim()); // ISBN opcional
        
        try {
            libro.setNumPaginas(Integer.parseInt(paginasStr));
            libro.setAnioPublicacion(Integer.parseInt(anioStr));
            libro.setUnidadesDisponibles(Integer.parseInt(unidadesStr));
        } catch (NumberFormatException e) {
            throw new ValidationException("número", "valor", "Formato numérico inválido");
        }
        
        return libro;
    }

    private Revista crearRevista(String titulo) throws ValidationException {
        JTextField txtEditorial = (JTextField) panelRevista.getComponent(1);
        JTextField txtPeriodicidad = (JTextField) panelRevista.getComponent(3);
        JTextField txtFecha = (JTextField) panelRevista.getComponent(5);
        JTextField txtUnidades = (JTextField) panelRevista.getComponent(7);
        
        String editorial = txtEditorial.getText().trim();
        String periodicidad = txtPeriodicidad.getText().trim();
        String fechaStr = txtFecha.getText().trim();
        String unidadesStr = txtUnidades.getText().trim();
        
        if (editorial.isEmpty()) throw new ValidationException("editorial", editorial, "La editorial es obligatoria");
        if (periodicidad.isEmpty()) throw new ValidationException("periodicidad", periodicidad, "La periodicidad es obligatoria");
        if (fechaStr.isEmpty()) throw new ValidationException("fecha", fechaStr, "La fecha de publicación es obligatoria");
        if (unidadesStr.isEmpty()) throw new ValidationException("unidades", unidadesStr, "Las unidades disponibles son obligatorias");
        
        Revista revista = new Revista();
        revista.setTitulo(titulo);
        revista.setEditorial(editorial);
        revista.setPeriodicidad(periodicidad);
        
        try {
            // Convertir fecha
            revista.setFechaPublicacion(java.time.LocalDate.parse(fechaStr));
            revista.setUnidadesDisponibles(Integer.parseInt(unidadesStr));
        } catch (java.time.format.DateTimeParseException e) {
            throw new ValidationException("fecha", fechaStr, "Formato de fecha inválido. Use YYYY-MM-DD");
        } catch (NumberFormatException e) {
            throw new ValidationException("unidades", unidadesStr, "Formato numérico inválido para unidades");
        }
        
        return revista;
    }

    private CD crearCD(String titulo) throws ValidationException {
        JTextField txtArtista = (JTextField) panelCD.getComponent(1);
        JTextField txtGenero = (JTextField) panelCD.getComponent(3);
        JTextField txtDuracion = (JTextField) panelCD.getComponent(5);
        JTextField txtCanciones = (JTextField) panelCD.getComponent(7);
        JTextField txtUnidades = (JTextField) panelCD.getComponent(9);
        
        String artista = txtArtista.getText().trim();
        String genero = txtGenero.getText().trim();
        String duracionStr = txtDuracion.getText().trim();
        String cancionesStr = txtCanciones.getText().trim();
        String unidadesStr = txtUnidades.getText().trim();
        
        if (artista.isEmpty()) throw new ValidationException("artista", artista, "El artista es obligatorio");
        if (genero.isEmpty()) throw new ValidationException("género", genero, "El género es obligatorio");
        if (duracionStr.isEmpty()) throw new ValidationException("duración", duracionStr, "La duración es obligatoria");
        if (cancionesStr.isEmpty()) throw new ValidationException("canciones", cancionesStr, "El número de canciones es obligatorio");
        if (unidadesStr.isEmpty()) throw new ValidationException("unidades", unidadesStr, "Las unidades disponibles son obligatorias");
        
        CD cd = new CD();
        cd.setTitulo(titulo);
        cd.setArtista(artista);
        cd.setGenero(genero);
        
        try {
            cd.setDuracion(Integer.parseInt(duracionStr));
            cd.setNumCanciones(Integer.parseInt(cancionesStr));
            cd.setUnidadesDisponibles(Integer.parseInt(unidadesStr));
        } catch (NumberFormatException e) {
            throw new ValidationException("número", "valor", "Formato numérico inválido");
        }
        
        return cd;
    }

    private DVD crearDVD(String titulo) throws ValidationException {
        JTextField txtDirector = (JTextField) panelDVD.getComponent(1);
        JTextField txtDuracion = (JTextField) panelDVD.getComponent(3);
        JTextField txtGenero = (JTextField) panelDVD.getComponent(5);
        JTextField txtUnidades = (JTextField) panelDVD.getComponent(7);
        
        String director = txtDirector.getText().trim();
        String duracionStr = txtDuracion.getText().trim();
        String genero = txtGenero.getText().trim();
        String unidadesStr = txtUnidades.getText().trim();
        
        if (director.isEmpty()) throw new ValidationException("director", director, "El director es obligatorio");
        if (duracionStr.isEmpty()) throw new ValidationException("duración", duracionStr, "La duración es obligatoria");
        if (genero.isEmpty()) throw new ValidationException("género", genero, "El género es obligatorio");
        if (unidadesStr.isEmpty()) throw new ValidationException("unidades", unidadesStr, "Las unidades disponibles son obligatorias");
        
        DVD dvd = new DVD();
        dvd.setTitulo(titulo);
        dvd.setDirector(director);
        dvd.setGenero(genero);
        
        try {
            dvd.setDuracion(Integer.parseInt(duracionStr));
            dvd.setUnidadesDisponibles(Integer.parseInt(unidadesStr));
        } catch (NumberFormatException e) {
            throw new ValidationException("número", "valor", "Formato numérico inválido");
        }
        
        return dvd;
    }

    private void guardarMaterialEnBD(Material material) throws Exception {
        // Generar ID automático
        String id = generarIdUnico(material.getTipo());
        material.setIdMaterial(id);
        
        System.out.println("💾 Guardando material - ID: " + id + ", Tipo: " + material.getTipo());
        
        // Guardar según el tipo
        if (material instanceof Libro) {
            System.out.println("📖 Guardando como Libro...");
            LibroDAO libroDAO = new LibroDAO();
            libroDAO.insertar((Libro) material);
        } else if (material instanceof Revista) {
            System.out.println("📰 Guardando como Revista...");
            RevistaDAO revistaDAO = new RevistaDAO();
            revistaDAO.insertar((Revista) material);
        } else if (material instanceof CD) {
            System.out.println("💿 Guardando como CD...");
            CDDAO cdDAO = new CDDAO();
            cdDAO.insertar((CD) material);
        } else if (material instanceof DVD) {
            System.out.println("📀 Guardando como DVD...");
            DVDDAO dvdDAO = new DVDDAO();
            dvdDAO.insertar((DVD) material);
        }
        
        System.out.println("✅ Material guardado exitosamente!");
    }

    private String generarIdUnico(TipoMaterial tipo) {
        String prefix = "";
        switch (tipo) {
            case LIBRO: prefix = "LIB"; break;
            case REVISTA: prefix = "REV"; break;
            case CD: prefix = "CDA"; break;      // ✅ CAMBIADO de CD a CDA
            case DVD: prefix = "DVD"; break;
        }
        
        // ✅ GENERAR CORRELATIVO DE 5 DÍGITOS (00001-99999)
        int correlativo = (int)(Math.random() * 99999) + 1;
        return prefix + String.format("%05d", correlativo);
    }

    private void limpiarCampos() {
        campoTitulo.setText("");
        // Limpiar todos los campos específicos según el tipo actual
        String tipoActual = (String) comboTipo.getSelectedItem();
        limpiarPanelSegunTipo(tipoActual);
    }

    private void limpiarPanelSegunTipo(String tipo) {
        JPanel panel = null;
        switch (tipo) {
            case "Libro": panel = panelLibro; break;
            case "Revista": panel = panelRevista; break;
            case "CD": panel = panelCD; break;
            case "DVD": panel = panelDVD; break;
        }
        
        if (panel != null) {
            for (int i = 0; i < panel.getComponentCount(); i++) {
                if (panel.getComponent(i) instanceof JTextField) {
                    ((JTextField) panel.getComponent(i)).setText("");
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaAgregarMaterial ventana = new VentanaAgregarMaterial();
            ventana.setVisible(true);
        });
    }
}