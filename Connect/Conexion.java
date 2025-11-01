import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import Class.CD;
import Class.DVD;
import Class.Libro;
import Class.Material;
import Class.Revista;

public class Conexion {
    private static final String URL = "jdbc:mysql://51.222.156.107:3306/mediateca";
    private static final String USER = "fer";
    private static final String PASSWORD = "RSwMW5kSr9skVIeG";

    private static Connection conn;

    public static void conectarBD() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.err.println("Error conectando a BD: " + e.getMessage());
        }
    }

    public static void cargarDatosDesdeBD(List<Material> materiales) {
        materiales.clear();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Libros");
            while (rs.next()) {
                materiales.add(new Libro(rs.getString("codigo"), rs.getString("titulo"), rs.getString("autor"),
                        rs.getInt("paginas"), rs.getString("editorial"), rs.getString("isbn"),
                        rs.getInt("anio_publicacion"), rs.getInt("unidades_disponibles")));
            }
            rs.close();

            rs = stmt.executeQuery("SELECT * FROM Revistas");
            while (rs.next()) {
                materiales.add(new Revista(rs.getString("codigo"), rs.getString("titulo"), rs.getString("editorial"),
                        rs.getDate("fecha_publicacion").toLocalDate(),
                        rs.getInt("unidades_disponibles")));
            }
            rs.close();

            rs = stmt.executeQuery("SELECT * FROM CD");
            while (rs.next()) {
                materiales.add(new CD(rs.getString("codigo"), rs.getString("titulo"), rs.getString("artista"),
                        rs.getString("genero"), Duration.parse(rs.getString("duracion")),
                        rs.getInt("num_canciones"), rs.getInt("unidades_disponibles")));
            }
            rs.close();

            rs = stmt.executeQuery("SELECT * FROM DVDs");
            while (rs.next()) {
                materiales.add(new DVD(rs.getString("codigo"), rs.getString("titulo"), rs.getString("director"),
                        Duration.parse(rs.getString("duracion")), rs.getString("genero"),
                        rs.getInt("unidades_disponibles")));
            }
            rs.close();

            stmt.close();
        } catch (SQLException e) {
            System.err.println("Error cargando datos: " + e.getMessage());
        }
    }

    public static void guardarEnBD(Material m) {
        try {
            if (m instanceof Libro) {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO Libros VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                Libro l = (Libro) m;
                ps.setString(1, l.getCodigo());
                ps.setString(2, l.getTitulo());
                ps.setString(3, l.getAutor());
                ps.setInt(4, l.getPaginas());
                ps.setString(5, l.getEditorial());
                ps.setString(6, l.getIsbn());
                ps.setInt(7, l.getAnioPublicacion());
                ps.setInt(8, l.getUnidadesDisponibles());
                ps.executeUpdate();
                ps.close();
            } else if (m instanceof Revista) {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO Revistas VALUES (?, ?, ?, ?, ?)");
                Revista r = (Revista) m;
                ps.setString(1, r.getCodigo());
                ps.setString(2, r.getTitulo());
                ps.setString(3, r.getEditorial());
                ps.setDate(4, Date.valueOf(r.getFechaPublicacion()));
                ps.setInt(5, r.getUnidadesDisponibles());
                ps.executeUpdate();
                ps.close();
            } else if (m instanceof CD) {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO CD VALUES (?, ?, ?, ?, ?, ?, ?)");
                CD c = (CD) m;
                ps.setString(1, c.getCodigo());
                ps.setString(2, c.getTitulo());
                ps.setString(3, c.getArtista());
                ps.setString(4, c.getGenero());
                ps.setString(5, c.getDuracion().toString());
                ps.setInt(6, c.getNumCanciones());
                ps.setInt(7, c.getUnidadesDisponibles());
                ps.executeUpdate();
                ps.close();
            } else if (m instanceof DVD) {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO DVDs VALUES (?, ?, ?, ?, ?, ?)");
                DVD d = (DVD) m;
                ps.setString(1, d.getCodigo());
                ps.setString(2, d.getTitulo());
                ps.setString(3, d.getDirector());
                ps.setString(4, d.getDuracion().toString());
                ps.setString(5, d.getGenero());
                ps.setInt(6, d.getUnidadesDisponibles());
                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException e) {
            System.err.println("Error guardando: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void actualizarEnBD(Material m) {
        try {
            if (m instanceof Libro) {
                PreparedStatement ps = conn.prepareStatement("UPDATE Libros SET titulo=?, autor=?, paginas=?, editorial=?, isbn=?, anio_publicacion=?, unidades_disponibles=? WHERE codigo=?");
                Libro l = (Libro) m;
                ps.setString(1, l.getTitulo());
                ps.setString(2, l.getAutor());
                ps.setInt(3, l.getPaginas());
                ps.setString(4, l.getEditorial());
                ps.setString(5, l.getIsbn());
                ps.setInt(6, l.getAnioPublicacion());
                ps.setInt(7, l.getUnidadesDisponibles());
                ps.setString(8, l.getCodigo());
                ps.executeUpdate();
                ps.close();
            } else if (m instanceof Revista) {
                PreparedStatement ps = conn.prepareStatement("UPDATE Revistas SET titulo=?, editorial=?, fecha_publicacion=?, unidades_disponibles=? WHERE codigo=?");
                Revista r = (Revista) m;
                ps.setString(1, r.getTitulo());
                ps.setString(2, r.getEditorial());
                ps.setDate(3, Date.valueOf(r.getFechaPublicacion()));
                ps.setInt(4, r.getUnidadesDisponibles());
                ps.setString(5, r.getCodigo());
                ps.executeUpdate();
                ps.close();
            } else if (m instanceof CD) {
                PreparedStatement ps = conn.prepareStatement("UPDATE CD SET titulo=?, artista=?, genero=?, duracion=?, num_canciones=?, unidades_disponibles=? WHERE codigo=?");
                CD c = (CD) m;
                ps.setString(1, c.getTitulo());
                ps.setString(2, c.getArtista());
                ps.setString(3, c.getGenero());
                ps.setString(4, c.getDuracion().toString());
                ps.setInt(5, c.getNumCanciones());
                ps.setInt(6, c.getUnidadesDisponibles());
                ps.setString(7, c.getCodigo());
                ps.executeUpdate();
                ps.close();
            } else if (m instanceof DVD) {
                PreparedStatement ps = conn.prepareStatement("UPDATE DVDs SET titulo=?, director=?, duracion=?, genero=?, unidades_disponibles=? WHERE codigo=?");
                DVD d = (DVD) m;
                ps.setString(1, d.getTitulo());
                ps.setString(2, d.getDirector());
                ps.setString(3, d.getDuracion().toString());
                ps.setString(4, d.getGenero());
                ps.setInt(5, d.getUnidadesDisponibles());
                ps.setString(6, d.getCodigo());
                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException e) {
            System.err.println("Error actualizando: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void borrarDeBD(String codigo, String tipo) {
        try {
            String query = "";
            if (tipo.equals("Libro")) query = "DELETE FROM Libros WHERE codigo=?";
            else if (tipo.equals("Revista")) query = "DELETE FROM Revistas WHERE codigo=?";
            else if (tipo.equals("CD")) query = "DELETE FROM CD WHERE codigo=?";
            else if (tipo.equals("DVD")) query = "DELETE FROM DVDs WHERE codigo=?";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, codigo);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.err.println("Error borrando: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void cerrarConexion() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Error cerrando conexión: " + e.getMessage());
        }
    }
}
