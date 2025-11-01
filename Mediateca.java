import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Class.CD;
import Class.DVD;
import Class.Libro;
import Class.Material;
import Class.Revista;

import java.awt.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Mediateca extends JFrame {
    private List<Material> materiales = new ArrayList<>();
    private JTable table;
    private DefaultTableModel tableModel;

    public Mediateca() {
        Conexion.conectarBD();
        Conexion.cargarDatosDesdeBD(materiales);
        initUI();
    }

    private void initUI() {
        setTitle("Sistema de Gestión de Mediateca");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar Material");
        JButton btnModificar = new JButton("Modificar Material");
        JButton btnListar = new JButton("Listar Materiales");
        JButton btnBorrar = new JButton("Borrar Material");
        JButton btnBuscar = new JButton("Buscar Material");
        JButton btnSalir = new JButton("Salir");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnListar);
        panelBotones.add(btnBorrar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Código", "Título", "Tipo", "Detalles", "Unidades"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnAgregar.addActionListener(e -> agregarMaterial());
        btnModificar.addActionListener(e -> modificarMaterial());
        btnListar.addActionListener(e -> listarMateriales());
        btnBorrar.addActionListener(e -> borrarMaterial());
        btnBuscar.addActionListener(e -> buscarMaterial());
        btnSalir.addActionListener(e -> {
            Conexion.cerrarConexion();
            System.exit(0);
        });
    }

    private void agregarMaterial() {
        String[] tipos = {"Libro", "Revista", "CD", "DVD"};
        String tipo = (String) JOptionPane.showInputDialog(this, "Selecciona tipo:", "Agregar", JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);
        if (tipo == null) return;

        String prefijo = tipo.equals("Libro") ? "LIB" : tipo.equals("Revista") ? "REV" : tipo.equals("CD") ? "CDs" : "DVD";
        String codigo = prefijo + String.format("%05d", materiales.size() + 1);

        String titulo = JOptionPane.showInputDialog("Título:");
        int unidades = Integer.parseInt(JOptionPane.showInputDialog("Unidades disponibles:"));

        Material m = null;
        if (tipo.equals("Libro")) {
            String autor = JOptionPane.showInputDialog("Autor:");
            int paginas = Integer.parseInt(JOptionPane.showInputDialog("Páginas:"));
            String editorial = JOptionPane.showInputDialog("Editorial:");
            String isbn = JOptionPane.showInputDialog("ISBN:");
            int anio = Integer.parseInt(JOptionPane.showInputDialog("Año de publicación:"));
            m = new Libro(codigo, titulo, autor, paginas, editorial, isbn, anio, unidades);
        } else if (tipo.equals("Revista")) {
            String editorial = JOptionPane.showInputDialog("Editorial:");
            LocalDate fecha = LocalDate.parse(JOptionPane.showInputDialog("Fecha de publicación (YYYY-MM-DD):"));
            m = new Revista(codigo, titulo, editorial, fecha, unidades);
        } else if (tipo.equals("CD")) {
            String artista = JOptionPane.showInputDialog("Artista:");
            String genero = JOptionPane.showInputDialog("Género:");
            Duration duracion = Duration.parse(JOptionPane.showInputDialog("Duración (HH:MM:SS):"));
            int numCanciones = Integer.parseInt(JOptionPane.showInputDialog("Número de canciones:"));
            m = new CD(codigo, titulo, artista, genero, duracion, numCanciones, unidades);
        } else if (tipo.equals("DVD")) {
            String director = JOptionPane.showInputDialog("Director:");
            Duration duracion = Duration.parse(JOptionPane.showInputDialog("Duración (HH:MM:SS):"));
            String genero = JOptionPane.showInputDialog("Género:");
            m = new DVD(codigo, titulo, director, duracion, genero, unidades);
        }

        if (m != null) {
            materiales.add(m);
            Conexion.guardarEnBD(m);
            Conexion.cargarDatosDesdeBD(materiales);
            JOptionPane.showMessageDialog(this, "Material agregado.");
        }
    }

    private void modificarMaterial() {
        String codigo = JOptionPane.showInputDialog("Código del material a modificar:");
        Material m = materiales.stream().filter(mat -> mat.getCodigo().equals(codigo)).findFirst().orElse(null);
        if (m == null) {
            JOptionPane.showMessageDialog(this, "Material no encontrado.");
            return;
        }

        String titulo = JOptionPane.showInputDialog("Nuevo título:", m.getTitulo());
        int unidades = Integer.parseInt(JOptionPane.showInputDialog("Nuevas unidades disponibles:", String.valueOf(m.getUnidadesDisponibles())));

        if (m instanceof Libro) {
            Libro l = (Libro) m;
            String autor = JOptionPane.showInputDialog("Nuevo autor:", l.getAutor());
            int paginas = Integer.parseInt(JOptionPane.showInputDialog("Nuevas páginas:", String.valueOf(l.getPaginas())));
            String editorial = JOptionPane.showInputDialog("Nueva editorial:", l.getEditorial());
            String isbn = JOptionPane.showInputDialog("Nuevo ISBN:", l.getIsbn());
            int anio = Integer.parseInt(JOptionPane.showInputDialog("Nuevo año de publicación:", String.valueOf(l.getAnioPublicacion())));
            l.setTitulo(titulo);
            l.setAutor(autor);
            l.setPaginas(paginas);
            l.setEditorial(editorial);
            l.setIsbn(isbn);
            l.setAnioPublicacion(anio);
            l.setUnidadesDisponibles(unidades);
        } else if (m instanceof Revista) {
            Revista r = (Revista) m;
            String editorial = JOptionPane.showInputDialog("Nueva editorial:", r.getEditorial());
            LocalDate fecha = LocalDate.parse(JOptionPane.showInputDialog("Nueva fecha de publicación (YYYY-MM-DD):", r.getFechaPublicacion().toString()));
            r.setTitulo(titulo);
            r.setEditorial(editorial);
            r.setFechaPublicacion(fecha);
            r.setUnidadesDisponibles(unidades);
        } else if (m instanceof CD) {
            CD c = (CD) m;
            String artista = JOptionPane.showInputDialog("Nuevo artista:", c.getArtista());
            String genero = JOptionPane.showInputDialog("Nuevo género:", c.getGenero());
            Duration duracion = Duration.parse(JOptionPane.showInputDialog("Nueva duración (HH:MM:SS):", c.getDuracion().toString()));
            int numCanciones = Integer.parseInt(JOptionPane.showInputDialog("Nuevo número de canciones:", String.valueOf(c.getNumCanciones())));
            c.setTitulo(titulo);
            c.setArtista(artista);
            c.setGenero(genero);
            c.setDuracion(duracion);
            c.setNumCanciones(numCanciones);
            c.setUnidadesDisponibles(unidades);
        } else if (m instanceof DVD) {
            DVD d = (DVD) m;
            String director = JOptionPane.showInputDialog("Nuevo director:", d.getDirector());
            Duration duracion = Duration.parse(JOptionPane.showInputDialog("Nueva duración (HH:MM:SS):", d.getDuracion().toString()));
            String genero = JOptionPane.showInputDialog("Nuevo género:", d.getGenero());
            d.setTitulo(titulo);
            d.setDirector(director);
            d.setDuracion(duracion);
            d.setGenero(genero);
            d.setUnidadesDisponibles(unidades);
        }

        Conexion.actualizarEnBD(m);
        Conexion.cargarDatosDesdeBD(materiales);
        JOptionPane.showMessageDialog(this, "Material modificado.");
    }

    private void listarMateriales() {
        Conexion.cargarDatosDesdeBD(materiales);
        tableModel.setRowCount(0);
        for (Material m : materiales) {
            String tipo = m.getClass().getSimpleName();
            tableModel.addRow(new Object[]{m.getCodigo(), m.getTitulo(), tipo, m.getDetalles(), m.getUnidadesDisponibles()});
        }
    }

    private void borrarMaterial() {
        String codigo = JOptionPane.showInputDialog("Código del material a borrar:");
        Material m = materiales.stream().filter(mat -> mat.getCodigo().equals(codigo)).findFirst().orElse(null);
        if (m != null) {
            String tipo = m.getClass().getSimpleName();
            materiales.remove(m);
            Conexion.borrarDeBD(codigo, tipo);
            Conexion.cargarDatosDesdeBD(materiales);
            JOptionPane.showMessageDialog(this, "Material borrado.");
        } else {
            JOptionPane.showMessageDialog(this, "Material no encontrado.");
        }
    }

    private void buscarMaterial() {
        String criterio = JOptionPane.showInputDialog("Buscar por título:");
        List<Material> resultados = materiales.stream().filter(m -> m.getTitulo().contains(criterio)).toList();
        tableModel.setRowCount(0);
        for (Material m : resultados) {
            String tipo = m.getClass().getSimpleName();
            tableModel.addRow(new Object[]{m.getCodigo(), m.getTitulo(), tipo, m.getDetalles(), m.getUnidadesDisponibles()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Mediateca().setVisible(true));
    }
}
