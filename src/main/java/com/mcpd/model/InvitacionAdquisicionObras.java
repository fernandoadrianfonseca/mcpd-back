package com.mcpd.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "comprasAdquisicionInvitacionObras")
public class InvitacionAdquisicionObras implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String instrumento;

    @ManyToOne
    @JoinColumn(name = "proveedor", referencedColumnName = "cuit", nullable = false)
    private Proveedor proveedor;

    @Column(name = "apertura", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date apertura;

    @Column(name = "razon", length = 250)
    private String razon;

    @Column(name = "fantasia", length = 250)
    private String fantasia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(String instrumento) {
        this.instrumento = instrumento;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Date getApertura() {
        return apertura;
    }

    public void setApertura(Date apertura) {
        this.apertura = apertura;
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

}