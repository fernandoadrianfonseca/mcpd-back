package com.mcpd.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "mcpdproveedorfactura")
public class ProveedorFactura implements Serializable {

    @Id
    @Column(name = "id", length = 255, nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "proveedor", nullable = false)
    private Contribuyente proveedor;

    @Column(name = "letra", length = 1, nullable = false)
    private String letra;

    @Column(name = "puntoventa", nullable = false)
    private int puntoVenta;

    @Column(name = "numero", nullable = false)
    private long numero;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @Column(name = "total", nullable = false)
    private double total;

    @Column(name = "responsable", length = 50)
    private String responsable;

    @Temporal(TemporalType.DATE)
    @Column(name = "fechaingreso", nullable = false)
    private Date fechaIngreso;

    @Column(name = "pagada", nullable = false)
    private boolean pagada;

    @Temporal(TemporalType.DATE)
    @Column(name = "fechapago")
    private Date fechaPago;

    @Column(name = "contabilizada", nullable = false)
    private boolean contabilizada;

    @Temporal(TemporalType.DATE)
    @Column(name = "fechacontabilizada")
    private Date fechaContabilizada;

    @Column(name = "nombrecontabiliza", length = 50)
    private String nombreContabiliza;

    @Column(name = "ordencompracontratocertificacion", length = 255, nullable = false)
    private String ordenCompraContratoCertificacion;

    @Column(name = "ordendepago", length = 255)
    private String ordenDePago;

    @Temporal(TemporalType.DATE)
    @Column(name = "cai")
    private Date cai;

    @Column(name = "resolucionpago", length = 50, nullable = false)
    private String resolucionPago;

    @Column(name = "administracion", length = 255, nullable = false)
    private String administracion;

    @Column(name = "partida", length = 255, nullable = false)
    private String partida;

    @Column(name = "hacienda", nullable = false)
    private boolean hacienda;

    @Column(name = "codigo", nullable = false)
    private long codigo;

    @Column(name = "comisionservicios", nullable = false)
    private long comisionServicios;

    @Column(name = "compensacion", nullable = false)
    private long compensacion;

    @Column(name = "fondo", nullable = false)
    private long fondo;

    @Column(name = "oc", nullable = false)
    private boolean oc;

    @Column(name = "certificado", nullable = false)
    private boolean certificado;

    @Column(name = "adenda", nullable = false)
    private boolean adenda;

    @Column(name = "porresolucion", nullable = false)
    private boolean porResolucion;

    @Column(name = "reintegro", nullable = false)
    private long reintegro;

    @Column(name = "observaciones", columnDefinition = "varchar(max)")
    private String observaciones;

    @Temporal(TemporalType.DATE)
    @Column(name = "periodo")
    private Date periodo;

    @Column(name = "desafecta", length = 50, nullable = false)
    private String desafecta;

    @Column(name = "autorizahacienda", length = 50)
    private String autorizaHacienda;

    @Column(name = "resoinstrumento", length = 200)
    private String resoInstrumento;

    @Column(name = "razon", length = 250)
    private String razon;

    @Column(name = "fantasia", length = 250)
    private String fantasia;

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Contribuyente getProveedor() { return proveedor; }
    public void setProveedor(Contribuyente proveedor) { this.proveedor = proveedor; }

    public String getLetra() { return letra; }
    public void setLetra(String letra) { this.letra = letra; }

    public int getPuntoVenta() { return puntoVenta; }
    public void setPuntoVenta(int puntoVenta) { this.puntoVenta = puntoVenta; }

    public long getNumero() { return numero; }
    public void setNumero(long numero) { this.numero = numero; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }

    public Date getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(Date fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public boolean isPagada() { return pagada; }
    public void setPagada(boolean pagada) { this.pagada = pagada; }

    public Date getFechaPago() { return fechaPago; }
    public void setFechaPago(Date fechaPago) { this.fechaPago = fechaPago; }

    public boolean isContabilizada() { return contabilizada; }
    public void setContabilizada(boolean contabilizada) { this.contabilizada = contabilizada; }

    public Date getFechaContabilizada() { return fechaContabilizada; }
    public void setFechaContabilizada(Date fechaContabilizada) { this.fechaContabilizada = fechaContabilizada; }

    public String getNombreContabiliza() { return nombreContabiliza; }
    public void setNombreContabiliza(String nombreContabiliza) { this.nombreContabiliza = nombreContabiliza; }

    public String getOrdenCompraContratoCertificacion() { return ordenCompraContratoCertificacion; }
    public void setOrdenCompraContratoCertificacion(String ordenCompraContratoCertificacion) { this.ordenCompraContratoCertificacion = ordenCompraContratoCertificacion; }

    public String getOrdenDePago() { return ordenDePago; }
    public void setOrdenDePago(String ordenDePago) { this.ordenDePago = ordenDePago; }

    public Date getCai() { return cai; }
    public void setCai(Date cai) { this.cai = cai; }

    public String getResolucionPago() { return resolucionPago; }
    public void setResolucionPago(String resolucionPago) { this.resolucionPago = resolucionPago; }

    public String getAdministracion() { return administracion; }
    public void setAdministracion(String administracion) { this.administracion = administracion; }

    public String getPartida() { return partida; }
    public void setPartida(String partida) { this.partida = partida; }

    public boolean isHacienda() { return hacienda; }
    public void setHacienda(boolean hacienda) { this.hacienda = hacienda; }

    public long getCodigo() { return codigo; }
    public void setCodigo(long codigo) { this.codigo = codigo; }

    public long getComisionServicios() { return comisionServicios; }
    public void setComisionServicios(long comisionServicios) { this.comisionServicios = comisionServicios; }

    public long getCompensacion() { return compensacion; }
    public void setCompensacion(long compensacion) { this.compensacion = compensacion; }

    public long getFondo() { return fondo; }
    public void setFondo(long fondo) { this.fondo = fondo; }

    public boolean isOc() { return oc; }
    public void setOc(boolean oc) { this.oc = oc; }

    public boolean isCertificado() { return certificado; }
    public void setCertificado(boolean certificado) { this.certificado = certificado; }

    public boolean isAdenda() { return adenda; }
    public void setAdenda(boolean adenda) { this.adenda = adenda; }

    public boolean isPorResolucion() { return porResolucion; }
    public void setPorResolucion(boolean porResolucion) { this.porResolucion = porResolucion; }

    public long getReintegro() { return reintegro; }
    public void setReintegro(long reintegro) { this.reintegro = reintegro; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public Date getPeriodo() { return periodo; }
    public void setPeriodo(Date periodo) { this.periodo = periodo; }

    public String getDesafecta() { return desafecta; }
    public void setDesafecta(String desafecta) { this.desafecta = desafecta; }

    public String getAutorizaHacienda() { return autorizaHacienda; }
    public void setAutorizaHacienda(String autorizaHacienda) { this.autorizaHacienda = autorizaHacienda; }

    public String getResoInstrumento() { return resoInstrumento; }
    public void setResoInstrumento(String resoInstrumento) { this.resoInstrumento = resoInstrumento; }

    public String getRazon() { return razon; }
    public void setRazon(String razon) { this.razon = razon; }

    public String getFantasia() { return fantasia; }
    public void setFantasia(String fantasia) { this.fantasia = fantasia; }
}
