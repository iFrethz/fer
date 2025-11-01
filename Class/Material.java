package Class;
import java.io.Serializable;

public abstract class Material implements Serializable {
    protected String codigo;
    protected String titulo;
    protected int unidadesDisponibles;

    public Material(String codigo, String titulo, int unidadesDisponibles) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.unidadesDisponibles = unidadesDisponibles;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public int getUnidadesDisponibles() { return unidadesDisponibles; }
    public void setUnidadesDisponibles(int unidadesDisponibles) { this.unidadesDisponibles = unidadesDisponibles; }

    public abstract String getDetalles();
}