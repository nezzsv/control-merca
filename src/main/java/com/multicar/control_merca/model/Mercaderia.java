package com.multicar.control_merca.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity // Esto le dice a Spring que cree una tabla en MySQL
public class Mercaderia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoInterno;
    private String descripcion;
    private String estado; // PENDIENTE, ENVIADO, NOTA_CREDITO
    private LocalDateTime fechaReporte = LocalDateTime.now();

    // Getters y Setters (Necesarios para que Java lea los datos)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigoInterno() { return codigoInterno; }
    public void setCodigoInterno(String codigoInterno) { this.codigoInterno = codigoInterno; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDateTime getFechaReporte() { return fechaReporte; }
    public void setFechaReporte(LocalDateTime fechaReporte) { this.fechaReporte = fechaReporte; }
}