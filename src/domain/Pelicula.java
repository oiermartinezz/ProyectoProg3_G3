package domain;

public class Pelicula {

    private int id;
    private String titulo;
    private String genero;
    private int duracionMinutos;
    private String calificacionEdad; 
    private String sinopsis;
    
    public Pelicula() {
    }

    public Pelicula(int id, String titulo, String genero, int duracionMinutos, String calificacionEdad, String sinopsis) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.duracionMinutos = duracionMinutos;
        this.calificacionEdad = calificacionEdad;
        this.sinopsis = sinopsis;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public String getCalificacionEdad() {
        return calificacionEdad;
    }

    public void setCalificacionEdad(String calificacionEdad) {
        this.calificacionEdad = calificacionEdad;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    @Override
    public String toString() {
        return "Pelicula [id=" + id + ", titulo=" + titulo + ", genero=" + genero + ", duracion=" + duracionMinutos + " min]";
    }
}