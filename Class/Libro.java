package Class;
public class Libro extends Material {
    private String autor;
    private int paginas;
    private String editorial;
    private String isbn;
    private int anioPublicacion;

    public Libro(String codigo, String titulo, String autor, int paginas, String editorial, String isbn, int anioPublicacion, int unidadesDisponibles) {
        super(codigo, titulo, unidadesDisponibles);
        this.autor = autor;
        this.paginas = paginas;
        this.editorial = editorial;
        this.isbn = isbn;
        this.anioPublicacion = anioPublicacion;
    }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public int getPaginas() { return paginas; }
    public void setPaginas(int paginas) { this.paginas = paginas; }
    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public int getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(int anioPublicacion) { this.anioPublicacion = anioPublicacion; }

    @Override
    public String getDetalles() {
        return "Autor: " + autor + ", Páginas: " + paginas + ", Editorial: " + editorial + ", ISBN: " + isbn + ", Año: " + anioPublicacion;
    }
}