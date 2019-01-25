package com.julioflores.prueba2;

public class Tintes {
    private int id, nopedido, cantidad, nointentos, lote;
    private String producto, observaciones, tipoenvase, etapa1, personaasignada;
    private String fechacaptura, fechaasigacion, fechaaprobacion;

    public Tintes(){

    }

    public Tintes(int id, int nopedido, int cantidad, int lote, String producto, String observaciones, String tipoenvase, String etapa1,
                  String personaasignada, String fechacaptura, String fechaasigacion, String fechaaprobacion, int nointentos) {
        this.id = id;
        this.nopedido = nopedido;
        this.cantidad = cantidad;
        this.producto = producto;
        this.observaciones = observaciones;
        this.tipoenvase = tipoenvase;
        this.etapa1 = etapa1;
        this.personaasignada = personaasignada;
        this.fechacaptura = fechacaptura;
        this.fechaasigacion = fechaasigacion;
        this.fechaaprobacion = fechaaprobacion;
        this.nointentos = nointentos;
        this.lote = lote;
    }

    public int getLote() {
        return lote;
    }

    public void setLote(int lote) {
        this.lote = lote;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getNopedido() {
        return nopedido;
    }
    public void setNopedido(int nopedido) {
        this.nopedido = nopedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTipoenvase() {
        return tipoenvase;
    }

    public void setTipoenvase(String tipoenvase) {
        this.tipoenvase = tipoenvase;
    }

    public String getEtapa1() {
        return etapa1;
    }

    public void setEtapa1(String etapa1) {
        this.etapa1 = etapa1;
    }

    public String getPersonaasignada() {
        return personaasignada;
    }

    public String setPersonaasignada(String personaasignada) {
        this.personaasignada = personaasignada;
        return personaasignada;
    }

    public String getFechacaptura() {
        return fechacaptura;
    }

    public void setFechacaptura(String fechacaptura) {
        this.fechacaptura = fechacaptura;
    }

    public String getFechaasigacion() {
        return fechaasigacion;
    }

    public void setFechaasigacion(String fechaasigacion) {
        this.fechaasigacion = fechaasigacion;
    }

    public String getFechaaprobacion() {
        return fechaaprobacion;
    }

    public void setFechaaprobacion(String fechaaprobacion) {
        this.fechaaprobacion = fechaaprobacion;
    }

    public int getNointentos() { return nointentos; }
    public void setNointentos(int nointentos) { this.nointentos = nointentos; }

    @Override
    public String toString() {
        return id + " "+producto + nopedido + observaciones + cantidad + tipoenvase + fechacaptura + fechaasigacion + fechaaprobacion + personaasignada +
                etapa1 + nointentos + lote;
    }
}