package com.example.easyway;

import java.util.Objects;

public class Ruta {

    private String lugarOrigenç;
    private String lugarFinal;
    private String duracion;
    private String distancia;
    private String tiempoDeInicio;
    private String tiempoDeFinal;
    private int duracionSegunto;
    public Ruta(String lugarOrigenç, String lugarFinal, String duracion, String distancia, String tiempoDeInicio, String tiempoDeFinal,int duracionSegunto) {
        this.lugarOrigenç = lugarOrigenç;
        this.lugarFinal = lugarFinal;
        this.duracion = duracion;
        this.distancia = distancia;
        this.tiempoDeInicio = tiempoDeInicio;
        this.tiempoDeFinal = tiempoDeFinal;
        this.duracionSegunto=duracionSegunto;
    }

    public String toString(){
        return "Lugar origen : " + lugarOrigenç + "\nLugarDestino :" + lugarFinal + "\nDuracion : " + duracion + "\nDistancia : " + distancia;
    }

    public String getInfoOrigenDest(){
        return "" + lugarOrigenç + " > " + lugarFinal;
    }

    public String getInfoTiempo(){
        return "" + duracion + " :\n" + tiempoDeInicio + " - " + tiempoDeFinal;
    }

    public String getLugarFinal() {
        return lugarFinal;
    }

    public void setLugarFinal(String lugarFinal) {
        this.lugarFinal = lugarFinal;
    }

    public int getDuracionSegunto() {
        return duracionSegunto;
    }

    public void setDuracionSegunto(int duracionSegunto) {
        this.duracionSegunto = duracionSegunto;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getTiempoDeInicio() {
        return tiempoDeInicio;
    }

    public void setTiempoDeInicio(String tiempoDeInicio) {
        this.tiempoDeInicio = tiempoDeInicio;
    }

    public String getTiempoDeFinal() {
        return tiempoDeFinal;
    }

    public void setTiempoDeFinal(String tiempoDeFinal) {
        this.tiempoDeFinal = tiempoDeFinal;
    }

    public String getLugarOrigenç() {
        return lugarOrigenç;
    }

    public void setLugarOrigenç(String lugarOrigenç) {
        this.lugarOrigenç = lugarOrigenç;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ruta ruta = (Ruta) o;
        return Objects.equals(lugarOrigenç, ruta.lugarOrigenç) && Objects.equals(lugarFinal, ruta.lugarFinal) && Objects.equals(duracion, ruta.duracion) && Objects.equals(distancia, ruta.distancia) && Objects.equals(tiempoDeInicio, ruta.tiempoDeInicio) && Objects.equals(tiempoDeFinal, ruta.tiempoDeFinal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lugarOrigenç, lugarFinal, duracion, tiempoDeInicio, tiempoDeFinal);
    }

}
