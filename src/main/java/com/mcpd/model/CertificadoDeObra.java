package com.mcpd.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "comprasCertificadoObra")
public class CertificadoDeObra implements Serializable {

    @Id
    @Column(name = "numero", length = 50)
    private String numero;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "monto", nullable = false)
    private Double monto;

    @Column(name = "pagado", nullable = false)
    private Boolean pagado = false;

    @Column(name = "Observa", columnDefinition = "TEXT")
    private String observa;

    @Column(name = "uvis", nullable = false)
    private Double uvis = 0.0;

    @Column(name = "valorUvi", nullable = false)
    private Double valorUvi = 0.0;

    @ManyToOne
    @JoinColumn(name = "obraPublica", referencedColumnName = "numero")
    private PedidoDeAdquisicionObraPublica obraPublica;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Boolean getPagado() {
        return pagado;
    }

    public void setPagado(Boolean pagado) {
        this.pagado = pagado;
    }

    public String getObserva() {
        return observa;
    }

    public void setObserva(String observa) {
        this.observa = observa;
    }

    public Double getUvis() {
        return uvis;
    }

    public void setUvis(Double uvis) {
        this.uvis = uvis;
    }

    public Double getValorUvi() {
        return valorUvi;
    }

    public void setValorUvi(Double valorUvi) {
        this.valorUvi = valorUvi;
    }

    public PedidoDeAdquisicionObraPublica getObraPublica() {
        return obraPublica;
    }

    public void setObraPublica(PedidoDeAdquisicionObraPublica obraPublica) {
        this.obraPublica = obraPublica;
    }
}
