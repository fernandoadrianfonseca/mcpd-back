package com.mcpd.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "comprasCertificadoObraAdenda")
public class CertificadoDeAdendaObra implements Serializable {
    @Id
    @Column(name = "numero", length = 50, nullable = false)
    private String numero;

    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "obraPublica", nullable = false, referencedColumnName = "numero")
    private PedidoDeAdquisicionObraPublica obraPublica;

    @Column(name = "monto", nullable = false)
    private double monto;

    @Column(name = "pagado", nullable = false)
    private boolean pagado = false;

    @Column(name = "Observa", columnDefinition = "TEXT")
    private String observa;

    @Column(name = "uvis", nullable = false)
    private double uvis = 0;

    @Column(name = "valorUvi", nullable = false)
    private double valorUvi = 0;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public PedidoDeAdquisicionObraPublica getObraPublica() {
        return obraPublica;
    }

    public void setObraPublica(PedidoDeAdquisicionObraPublica obraPublica) {
        this.obraPublica = obraPublica;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public String getObserva() {
        return observa;
    }

    public void setObserva(String observa) {
        this.observa = observa;
    }

    public double getUvis() {
        return uvis;
    }

    public void setUvis(double uvis) {
        this.uvis = uvis;
    }

    public double getValorUvi() {
        return valorUvi;
    }

    public void setValorUvi(double valorUvi) {
        this.valorUvi = valorUvi;
    }
}