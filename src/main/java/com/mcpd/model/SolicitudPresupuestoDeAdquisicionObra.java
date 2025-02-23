package com.mcpd.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "comprasAdquisicionSolicitudPresupuestoObras")
public class SolicitudPresupuestoDeAdquisicionObra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero")
    private Long numero;

    @Column(name = "pedido", nullable = false)
    private Long pedido;

    @Column(name = "presentar", nullable = false)
    private LocalDateTime presentar;

    @Column(name = "razon", length = 250)
    private String razon;

    @Column(name = "fantasia", length = 250)
    private String fantasia;

    @ManyToOne
    @JoinColumn(name = "proveedor", referencedColumnName = "cuit")
    private Proveedor proveedor;

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Long getPedido() {
        return pedido;
    }

    public void setPedido(Long pedido) {
        this.pedido = pedido;
    }

    public LocalDateTime getPresentar() {
        return presentar;
    }

    public void setPresentar(LocalDateTime presentar) {
        this.presentar = presentar;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
}
