package com.julioflores.prueba2;

public class Envases {
    private int ids, nopedidos, cantidades;
    private String fechacapturas, fechaaprobaciones, fechaenvases,etapa1s, productos, tipoenvases, 	personaasignadas;
    public Envases(){

    }

    public Envases(int ids, int nopedidos, int cantidades, String fechacapturas, String fechaaprobaciones,
                   String fechaenvases, String etapa1s, String productos, String tipoenvases, String 	personaasignadas) {
        this.ids = ids;
        this.nopedidos = nopedidos;
        this.cantidades = cantidades;
        this.fechaenvases=fechaenvases;
        this.productos = productos;
        this.tipoenvases = tipoenvases;
        this.etapa1s = etapa1s;
        this.personaasignadas = personaasignadas;
        this.fechacapturas = fechacapturas;
        this.fechaaprobaciones = fechaaprobaciones;

    }


    public int getIds() {
        return ids;
    }

    public void setIds(int ids) {
        this.ids = ids;
    }

    public int getNopedidos() {
        return nopedidos;
    }

    public void setNopedidos(int nopedidos) {
        this.nopedidos = nopedidos;
    }

    public int getCantidades() {
        return cantidades;
    }

    public void setCantidades(int cantidades) {
        cantidades = cantidades;
    }

    public String getFechaCapturas() {
        return fechacapturas;
    }

    public void setFechaCapturas(String fechacapturas) {
        this.fechacapturas = fechacapturas;
    }

    public String getFechaaprobaciones() {
        return fechaaprobaciones;
    }

    public void setFechaaprobaciones(String fechaaprobaciones) {
        this.fechaaprobaciones = fechaaprobaciones;
    }

    public String getFechaenvases() {
        return fechaenvases;
    }

    public void setFechaenvases(String fechaenvases) {
        this.fechaenvases = fechaenvases;
    }

    public String getEtapa1s() {
        return etapa1s;
    }

    public void setEtapa1s(String etapa1s) {
        this.etapa1s = etapa1s;
    }

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }

    public String getTipoenvases() {
        return tipoenvases;
    }

    public void setTipoenvases(String tipoenvases) {
        this.tipoenvases = tipoenvases;
    }

    public String getPersonaasignadas() {
        return personaasignadas;
    }

    public void setPersonaasignadas(String personaasignadas) {
        this.personaasignadas = personaasignadas;
    }

    @Override
    public String toString() {
        return ids + " "+productos + nopedidos  + cantidades + tipoenvases +
                fechacapturas + fechaaprobaciones + personaasignadas + etapa1s + fechaenvases;
    }

}
