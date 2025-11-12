package domain;

import java.time.LocalDateTime;


public class Sesion {

    private int id;
    private Pelicula pelicula; 
    private Sala sala;         
    private LocalDateTime fechaHora; 
    private double precio;
    private int entradasVendidas; 

   
    public Sesion(int id, Pelicula pelicula, Sala sala, LocalDateTime fechaHora, double precio) {
        this.id = id;
        this.pelicula = pelicula;
        this.sala = sala;
        this.fechaHora = fechaHora;
        this.precio = precio;
        this.entradasVendidas = 0; // Se inicializa a 0
    }

    // --- Getters y Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getEntradasVendidas() {
        return entradasVendidas;
    }

    public void setEntradasVendidas(int entradasVendidas) {
        this.entradasVendidas = entradasVendidas;
    }

   
    public int getAsientosDisponibles() {
        return sala.getCapacidad() - entradasVendidas;
    }

    
    public boolean venderEntradas(int cantidad) {
        if (getAsientosDisponibles() >= cantidad) {
            this.entradasVendidas += cantidad;
            return true;
        }
        return false; //SI NO QUEDAN ASIENTOS
    }

    @Override
    public String toString() {
        return pelicula.getTitulo() + " - " + sala.getNombre() + " (" + fechaHora.toString() + ")";
    }
}