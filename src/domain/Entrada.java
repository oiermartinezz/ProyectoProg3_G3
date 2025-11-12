package domain;

import java.time.LocalDateTime;

public class Entrada {

    private int id;
    private Sesion sesion;
    private LocalDateTime fechaCompra;
    private double precioPagado; 

    public Entrada(int id, Sesion sesion, LocalDateTime fechaCompra, double precioPagado) {
        this.id = id;
        this.sesion = sesion;
        this.fechaCompra = fechaCompra;
        this.precioPagado = precioPagado;
    }

    // --- Getters y Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sesion getSesion() {
        return sesion;
    }

    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getPrecioPagado() {
        return precioPagado;
    }

    public void setPrecioPagado(double precioPagado) {
        this.precioPagado = precioPagado;
    }

    @Override
    public String toString() {
        return "Entrada [id=" + id + ", pelicula=" + sesion.getPelicula().getTitulo() + ", sala=" + sesion.getSala().getNombre() + ", hora=" + sesion.getFechaHora() + "]";
    }
}