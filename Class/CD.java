package Class;
import java.time.Duration;

public class CD extends Material {
    private String artista;
    private String genero;
    private Duration duracion;
    private int numCanciones;

    public CD(String codigo, String titulo, String artista, String genero, Duration duracion, int numCanciones, int unidadesDisponibles) {
        super(codigo, titulo, unidadesDisponibles);
        this.artista = artista;
        this.genero = genero;
        this.duracion = duracion;
        this.numCanciones = numCanciones;
    }

    public String getArtista() { return artista; }
    public void setArtista(String artista) { this.artista = artista; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public Duration getDuracion() { return duracion; }
    public void setDuracion(Duration duracion) { this.duracion = duracion; }
    public int getNumCanciones() { return numCanciones; }
    public void setNumCanciones(int numCanciones) { this.numCanciones = numCanciones; }

    @Override
    public String getDetalles() {
        return "Artista: " + artista + ", Género: " + genero + ", Duración: " + duracion + ", Canciones: " + numCanciones;
    }
}