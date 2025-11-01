package Class;
import java.time.LocalDate;

public class Revista extends Material {
    private String editorial;
    private LocalDate fechaPublicacion;

    public Revista(String codigo, String titulo, String editorial, LocalDate fechaPublicacion, int unidadesDisponibles) {
        super(codigo, titulo, unidadesDisponibles);
        this.editorial = editorial;
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }
    public LocalDate getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(LocalDate fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }

    @Override
    public String getDetalles() {
        return "Editorial: " + editorial + ", Fecha: " + fechaPublicacion;
    }
}