package com.multicar.control_merca.model;

import jakarta.persistence.*;

@Entity
public class Mercaderia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoInterno;
    private String proveedor;
    private String descripcion;
    private String estado = "Averiado";
    private int cantidad;
    private String observacion;
    private String fechaManual; // Cambio a entrada manual

    // --- GETTERS Y SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigoInterno() { return codigoInterno; }
    public void setCodigoInterno(String codigoInterno) { this.codigoInterno = codigoInterno; }
    public String getProveedor() { return proveedor; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
    public String getFechaManual() { return fechaManual; }
    public void setFechaManual(String fechaManual) { this.fechaManual = fechaManual; }
}