package Class;
import java.time.Duration;

public class DVD extends Material {
    private String director;
    private Duration duracion;
    private String genero;

    public DVD(String codigo, String titulo, String director, Duration duracion, String genero, int unidadesDisponibles) {
        super(codigo, titulo, unidadesDisponibles);
        this.director = director;
        this.duracion = duracion;
        this.genero = genero;
    }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }
    public Duration getDuracion() { return duracion; }
    public void setDuracion(Duration duracion) { this.duracion = duracion; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    @Override
    public String getDetalles() {
        return "Director: " + director + ", Duración: " + duracion + ", Género: " + genero;
    }
}