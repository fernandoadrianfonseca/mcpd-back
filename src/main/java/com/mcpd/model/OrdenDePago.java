package com.mcpd.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tesoreriaordenpago")
public class OrdenDePago implements Serializable {

    @Id
    @Column(name = "numero", nullable = false, length = 255)
    private String numero;

    @Column(name = "codigo", length = 255)
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "Beneficiario", nullable = false)
    private Contribuyente beneficiario;

    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(name = "autorizadohacienda", nullable = false)
    private boolean autorizadoHacienda;

    @Column(name = "autorizahacienda", columnDefinition = "varchar(max)", nullable = false)
    private String autorizaHacienda;

    @Column(name = "retencioniva", nullable = false)
    private double retencionIva;

    @Column(name = "retencioniibb", nullable = false)
    private double retencionIIbb;

    @Column(name = "retencionsellos", nullable = false)
    private double retencionSellos;

    @Column(name = "retencionsuss", nullable = false)
    private double retencionSuss;

    @Column(name = "retencionganancias", nullable = false)
    private double retencionGanancias;

    @Column(name = "gestioninformatica", nullable = false)
    private double gestionInformatica;

    @Column(name = "fondoreparo", nullable = false)
    private double fondoReparo;

    @Column(name = "total", nullable = false)
    private double total;

    @Column(name = "pagado", nullable = false)
    private boolean pagado;

    @Column(name = "fechapago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPago;

    @Column(name = "ordenescompra", length = 1800)
    private String ordenesCompra;

    @Column(name = "facturas", length = 1800)
    private String facturas;

    @Column(name = "observaciones", length = 1800, nullable = false)
    private String observaciones;

    @Column(name = "resolucion", length = 1800)
    private String resolucion;

    @Column(name = "compensacion", nullable = false)
    private long compensacion;

    @Column(name = "procesada", nullable = false)
    private boolean procesada;

    @Column(name = "plan", nullable = false)
    private boolean plan;

    @Column(name = "sueldo", nullable = false)
    private boolean sueldo;

    @Column(name = "proveedor", nullable = false)
    private boolean proveedor;

    @Column(name = "viatico", nullable = false)
    private boolean viatico;

    @Column(name = "anticipo", nullable = false)
    private boolean anticipo;

    @Column(name = "partidas", length = 255, nullable = false)
    private String partidas;

    @Column(name = "dxh", nullable = false)
    private boolean dxh;

    @Column(name = "fondo", nullable = false)
    private boolean fondo;

    @Column(name = "reintegro", nullable = false)
    private boolean reintegro;

    @Column(name = "libramiento", length = 255)
    private String libramiento;

    @Column(name = "nrolibramiento", nullable = false)
    private long nroLibramiento;

    @Column(name = "ok", nullable = false)
    private boolean ok;

    @Column(name = "cheque", length = 255)
    private String cheque;

    @Column(name = "reintegroempleado", nullable = false)
    private boolean reintegroEmpleado;

    @Column(name = "revisada", nullable = false)
    private boolean revisada;

    @Column(name = "resocambio", length = 255)
    private String resoCambio;

    @Column(name = "fechacambio")
    @Temporal(TemporalType.DATE)
    private Date fechaCambio;

    @Transient
    private double neto;

    @Column(name = "razon", length = 250)
    private String razon;

    @Column(name = "updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @PostLoad
    public void calcularNeto() {
        this.neto = Math.round(
                ((((((total - retencionIva) - retencionIIbb) - retencionSellos) - retencionSuss) - retencionGanancias)
                        - gestionInformatica - fondoReparo) * 100.0
        ) / 100.0;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Contribuyente getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(Contribuyente beneficiario) {
        this.beneficiario = beneficiario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isAutorizadoHacienda() {
        return autorizadoHacienda;
    }

    public void setAutorizadoHacienda(boolean autorizadoHacienda) {
        this.autorizadoHacienda = autorizadoHacienda;
    }

    public String getAutorizaHacienda() {
        return autorizaHacienda;
    }

    public void setAutorizaHacienda(String autorizaHacienda) {
        this.autorizaHacienda = autorizaHacienda;
    }

    public double getRetencionIva() {
        return retencionIva;
    }

    public void setRetencionIva(double retencionIva) {
        this.retencionIva = retencionIva;
    }

    public double getRetencionIIbb() {
        return retencionIIbb;
    }

    public void setRetencionIIbb(double retencionIIbb) {
        this.retencionIIbb = retencionIIbb;
    }

    public double getRetencionSellos() {
        return retencionSellos;
    }

    public void setRetencionSellos(double retencionSellos) {
        this.retencionSellos = retencionSellos;
    }

    public double getRetencionSuss() {
        return retencionSuss;
    }

    public void setRetencionSuss(double retencionSuss) {
        this.retencionSuss = retencionSuss;
    }

    public double getRetencionGanancias() {
        return retencionGanancias;
    }

    public void setRetencionGanancias(double retencionGanancias) {
        this.retencionGanancias = retencionGanancias;
    }

    public double getGestionInformatica() {
        return gestionInformatica;
    }

    public void setGestionInformatica(double gestionInformatica) {
        this.gestionInformatica = gestionInformatica;
    }

    public double getFondoReparo() {
        return fondoReparo;
    }

    public void setFondoReparo(double fondoReparo) {
        this.fondoReparo = fondoReparo;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getOrdenesCompra() {
        return ordenesCompra;
    }

    public void setOrdenesCompra(String ordenesCompra) {
        this.ordenesCompra = ordenesCompra;
    }

    public String getFacturas() {
        return facturas;
    }

    public void setFacturas(String facturas) {
        this.facturas = facturas;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public long getCompensacion() {
        return compensacion;
    }

    public void setCompensacion(long compensacion) {
        this.compensacion = compensacion;
    }

    public boolean isProcesada() {
        return procesada;
    }

    public void setProcesada(boolean procesada) {
        this.procesada = procesada;
    }

    public boolean isPlan() {
        return plan;
    }

    public void setPlan(boolean plan) {
        this.plan = plan;
    }

    public boolean isSueldo() {
        return sueldo;
    }

    public void setSueldo(boolean sueldo) {
        this.sueldo = sueldo;
    }

    public boolean isProveedor() {
        return proveedor;
    }

    public void setProveedor(boolean proveedor) {
        this.proveedor = proveedor;
    }

    public boolean isViatico() {
        return viatico;
    }

    public void setViatico(boolean viatico) {
        this.viatico = viatico;
    }

    public boolean isAnticipo() {
        return anticipo;
    }

    public void setAnticipo(boolean anticipo) {
        this.anticipo = anticipo;
    }

    public String getPartidas() {
        return partidas;
    }

    public void setPartidas(String partidas) {
        this.partidas = partidas;
    }

    public boolean isDxh() {
        return dxh;
    }

    public void setDxh(boolean dxh) {
        this.dxh = dxh;
    }

    public boolean isFondo() {
        return fondo;
    }

    public void setFondo(boolean fondo) {
        this.fondo = fondo;
    }

    public boolean isReintegro() {
        return reintegro;
    }

    public void setReintegro(boolean reintegro) {
        this.reintegro = reintegro;
    }

    public String getLibramiento() {
        return libramiento;
    }

    public void setLibramiento(String libramiento) {
        this.libramiento = libramiento;
    }

    public long getNroLibramiento() {
        return nroLibramiento;
    }

    public void setNroLibramiento(long nroLibramiento) {
        this.nroLibramiento = nroLibramiento;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getCheque() {
        return cheque;
    }

    public void setCheque(String cheque) {
        this.cheque = cheque;
    }

    public boolean isReintegroEmpleado() {
        return reintegroEmpleado;
    }

    public void setReintegroEmpleado(boolean reintegroEmpleado) {
        this.reintegroEmpleado = reintegroEmpleado;
    }

    public boolean isRevisada() {
        return revisada;
    }

    public void setRevisada(boolean revisada) {
        this.revisada = revisada;
    }

    public String getResoCambio() {
        return resoCambio;
    }

    public void setResoCambio(String resoCambio) {
        this.resoCambio = resoCambio;
    }

    public Date getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(Date fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public double getNeto() {
        return neto;
    }

    public void setNeto(double neto) {
        this.neto = neto;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
