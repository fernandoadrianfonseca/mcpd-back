package com.mcpd.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "rrhhempleado")
public class Empleado implements Serializable {

    @Id
    @Column(name = "legajo", nullable = false)
    private Long legajo;

    @Column(name = "cuil", nullable = false)
    private Long cuil;

    @Column(name = "categoria")
    private Integer categoria;

    @Column(name = "cuenta", length = 50)
    private String cuenta;

    @Column(name = "activo", nullable = false)
    private Boolean activo = false;

    @Column(name = "fechaingreso")
    private LocalDateTime fechaIngreso;

    @Column(name = "agrupamiento", nullable = false)
    private Integer agrupamiento;

    @Column(name = "esposa", nullable = false)
    private Boolean esposa = false;

    @Column(name = "hijos", nullable = false)
    private Integer hijos = 0;

    @Column(name = "escprim", nullable = false)
    private Integer escprim = 0;

    @Column(name = "escsec", nullable = false)
    private Integer escsec = 0;

    @Column(name = "ayudaprim", nullable = false)
    private Integer ayudaprim = 0;

    @Column(name = "ayudasec", nullable = false)
    private Integer ayudasec = 0;

    @Column(name = "hijoinc", nullable = false)
    private Integer hijoinc = 0;

    @Column(name = "titulo", nullable = false)
    private Integer titulo = 0;

    @Column(name = "carpeta", nullable = false)
    private Boolean carpeta = false;

    @Column(name = "fechacarpeta", nullable = false)
    private LocalDateTime fechaCarpeta = LocalDateTime.of(1900, 1, 1, 0, 0);

    @Column(name = "usopc")
    private Boolean usoPc;

    @Column(name = "manejofondos", nullable = false)
    private Boolean manejoFondos = false;

    @Column(name = "inspector", nullable = false)
    private Boolean inspector = false;

    @Column(name = "puericultura", nullable = false)
    private Boolean puericultura = false;

    @Column(name = "jefatura", nullable = false)
    private Boolean jefatura = false;

    @Column(name = "laboreventual", nullable = false)
    private Boolean laborEventual = false;

    @Column(name = "riesgo", nullable = false)
    private Boolean riesgo = false;

    @Column(name = "encargadocuadrilla", nullable = false)
    private Boolean encargadoCuadrilla = false;

    @Column(name = "ultimoascenso", nullable = false)
    private LocalDateTime ultimoAscenso = LocalDateTime.of(1900, 1, 1, 0, 0);;

    @Column(name = "isprovol", nullable = false)
    private Boolean isprovol = false;

    @Column(name = "familiaracargo", nullable = false)
    private Boolean familiarAcargo = false;

    @Column(name = "soem", nullable = false)
    private Boolean soem = false;

    @Column(name = "apap", nullable = false)
    private Boolean apap = false;

    @Column(name = "tpc", nullable = false)
    private Boolean tpc = false;

    @Column(name = "tpcamparo", nullable = false)
    private Boolean tpcAmparo = false;

    @Column(name = "cajaahorro", nullable = false)
    private Boolean cajaAhorro = false;

    @Column(name = "sucursal", nullable = false)
    private Integer sucursal = 0;

    @Column(name = "antreconocida", nullable = false)
    private Integer antReconocida = 0;

    @Column(name = "tesoreria", nullable = false)
    private Boolean tesoreria = false;

    @Column(name = "ate", nullable = false)
    private Boolean ate = false;

    @Column(name = "secretaria", length = 50)
    private String secretaria;

    @Column(name = "comision", nullable = false)
    private Boolean comision = false;

    @Column(name = "nocturnidad", nullable = false)
    private Boolean nocturnidad = false;

    @Column(name = "jefehcd", nullable = false)
    private Boolean jefeHCD = false;

    @Column(name = "directorhcd", nullable = false)
    private Boolean directorHCD = false;

    @Column(name = "vacacionesplanta", nullable = false)
    private Float vacacionesPlanta = 0.0f;

    @Column(name = "articulos", nullable = false)
    private Integer articulos = 0;

    @Column(name = "antprivada", nullable = false)
    private Integer antPrivada = 0;

    @Column(name = "vacacionespolitica", nullable = false)
    private Float vacacionesPolitica = 0.0f;

    @Column(name = "actualizado", nullable = false)
    private LocalDateTime actualizado = LocalDateTime.of(1900, 1, 1, 0, 0);

    @Column(name = "horas100", nullable = false)
    private Float horas100 = 0.0f;

    @Column(name = "horas50", nullable = false)
    private Float horas50 = 0.0f;

    @Column(name = "sistema", nullable = false)
    private Boolean sistema;

    /*@Lob
    @Column(name = "dato", nullable = false)
    private byte[] dato;*/

    @Column(name = "vence", nullable = false)
    private LocalDate vence = LocalDate.of(1900, 1, 1);

    @Column(name = "dependencia", nullable = false, length = 50)
    private String dependencia;

    @Column(name = "perfil", nullable = false)
    private Integer perfil = 3;

    @Column(name = "laborhsc", nullable = false)
    private Boolean laborHSC = false;

    @Column(name = "cuidadorhsc", nullable = false)
    private Boolean cuidadorHSC = false;

    @Column(name = "recolector", nullable = false)
    private Boolean recolector = false;

    @Column(name = "transito", nullable = false)
    private Boolean transito = false;

    @Column(name = "bromatologia", nullable = false)
    private Boolean bromatologia = false;

    @Column(name = "obra", nullable = false)
    private Boolean obra = false;

    @Column(name = "ambiente", nullable = false)
    private Boolean ambiente = false;

    @Column(name = "canino", nullable = false)
    private Boolean canino = false;

    @Column(name = "higiene", nullable = false)
    private Boolean higiene = false;

    @Column(name = "ordenanza", nullable = false)
    private Boolean ordenanza = false;

    @Column(name = "flotapesada", nullable = false)
    private Boolean flotaPesada = false;

    @Column(name = "tareasadminhcd", nullable = false)
    private Boolean tareasAdminHCD = false;

    @Column(name = "choferespem", nullable = false)
    private Boolean choferesPem = false;

    @Column(name = "laborsalamaquinacalderacj", nullable = false)
    private Boolean laborSalaMaquinaCalderaCJ = false;

    @Column(name = "responfinancont", nullable = false)
    private Boolean responFinanCont = false;

    @Column(name = "tareascementerio", nullable = false)
    private Boolean tareasCementerio = false;

    @Column(name = "labormuseosarch", nullable = false)
    private Boolean laborMuseosArch = false;

    @Column(name = "labormuseos", nullable = false)
    private Boolean laborMuseos = false;

    @Column(name = "laborconservarch", nullable = false)
    private Boolean laborConservArch = false;

    @Column(name = "laborconservlab", nullable = false)
    private Boolean laborConservLab = false;

    @Column(name = "trabajaenprovincia", nullable = false)
    private Boolean trabajaEnProvincia = false;

    @Transient
    private String nombre;

    public Long getLegajo() {
        return legajo;
    }

    public void setLegajo(Long legajo) {
        this.legajo = legajo;
    }

    public Long getCuil() {
        return cuil;
    }

    public void setCuil(Long cuil) {
        this.cuil = cuil;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getAgrupamiento() {
        return agrupamiento;
    }

    public void setAgrupamiento(Integer agrupamiento) {
        this.agrupamiento = agrupamiento;
    }

    public Boolean getEsposa() {
        return esposa;
    }

    public void setEsposa(Boolean esposa) {
        this.esposa = esposa;
    }

    public Integer getHijos() {
        return hijos;
    }

    public void setHijos(Integer hijos) {
        this.hijos = hijos;
    }

    public Integer getEscprim() {
        return escprim;
    }

    public void setEscprim(Integer escprim) {
        this.escprim = escprim;
    }

    public Integer getEscsec() {
        return escsec;
    }

    public void setEscsec(Integer escsec) {
        this.escsec = escsec;
    }

    public Integer getAyudaprim() {
        return ayudaprim;
    }

    public void setAyudaprim(Integer ayudaprim) {
        this.ayudaprim = ayudaprim;
    }

    public Integer getAyudasec() {
        return ayudasec;
    }

    public void setAyudasec(Integer ayudasec) {
        this.ayudasec = ayudasec;
    }

    public Integer getHijoinc() {
        return hijoinc;
    }

    public void setHijoinc(Integer hijoinc) {
        this.hijoinc = hijoinc;
    }

    public Integer getTitulo() {
        return titulo;
    }

    public void setTitulo(Integer titulo) {
        this.titulo = titulo;
    }

    public Boolean getCarpeta() {
        return carpeta;
    }

    public void setCarpeta(Boolean carpeta) {
        this.carpeta = carpeta;
    }

    public LocalDateTime getFechaCarpeta() {
        return fechaCarpeta;
    }

    public void setFechaCarpeta(LocalDateTime fechaCarpeta) {
        this.fechaCarpeta = fechaCarpeta;
    }

    public Boolean getUsoPc() {
        return usoPc;
    }

    public void setUsoPc(Boolean usoPc) {
        this.usoPc = usoPc;
    }

    public Boolean getManejoFondos() {
        return manejoFondos;
    }

    public void setManejoFondos(Boolean manejoFondos) {
        this.manejoFondos = manejoFondos;
    }

    public Boolean getInspector() {
        return inspector;
    }

    public void setInspector(Boolean inspector) {
        this.inspector = inspector;
    }

    public Boolean getPuericultura() {
        return puericultura;
    }

    public void setPuericultura(Boolean puericultura) {
        this.puericultura = puericultura;
    }

    public Boolean getJefatura() {
        return jefatura;
    }

    public void setJefatura(Boolean jefatura) {
        this.jefatura = jefatura;
    }

    public Boolean getLaborEventual() {
        return laborEventual;
    }

    public void setLaborEventual(Boolean laborEventual) {
        this.laborEventual = laborEventual;
    }

    public Boolean getRiesgo() {
        return riesgo;
    }

    public void setRiesgo(Boolean riesgo) {
        this.riesgo = riesgo;
    }

    public Boolean getEncargadoCuadrilla() {
        return encargadoCuadrilla;
    }

    public void setEncargadoCuadrilla(Boolean encargadoCuadrilla) {
        this.encargadoCuadrilla = encargadoCuadrilla;
    }

    public LocalDateTime getUltimoAscenso() {
        return ultimoAscenso;
    }

    public void setUltimoAscenso(LocalDateTime ultimoAscenso) {
        this.ultimoAscenso = ultimoAscenso;
    }

    public Boolean getIsprovol() {
        return isprovol;
    }

    public void setIsprovol(Boolean isprovol) {
        this.isprovol = isprovol;
    }

    public Boolean getFamiliarAcargo() {
        return familiarAcargo;
    }

    public void setFamiliarAcargo(Boolean familiarAcargo) {
        this.familiarAcargo = familiarAcargo;
    }

    public Boolean getSoem() {
        return soem;
    }

    public void setSoem(Boolean soem) {
        this.soem = soem;
    }

    public Boolean getApap() {
        return apap;
    }

    public void setApap(Boolean apap) {
        this.apap = apap;
    }

    public Boolean getTpc() {
        return tpc;
    }

    public void setTpc(Boolean tpc) {
        this.tpc = tpc;
    }

    public Boolean getTpcAmparo() {
        return tpcAmparo;
    }

    public void setTpcAmparo(Boolean tpcAmparo) {
        this.tpcAmparo = tpcAmparo;
    }

    public Boolean getCajaAhorro() {
        return cajaAhorro;
    }

    public void setCajaAhorro(Boolean cajaAhorro) {
        this.cajaAhorro = cajaAhorro;
    }

    public Integer getSucursal() {
        return sucursal;
    }

    public void setSucursal(Integer sucursal) {
        this.sucursal = sucursal;
    }

    public Integer getAntReconocida() {
        return antReconocida;
    }

    public void setAntReconocida(Integer antReconocida) {
        this.antReconocida = antReconocida;
    }

    public Boolean getTesoreria() {
        return tesoreria;
    }

    public void setTesoreria(Boolean tesoreria) {
        this.tesoreria = tesoreria;
    }

    public Boolean getAte() {
        return ate;
    }

    public void setAte(Boolean ate) {
        this.ate = ate;
    }

    public String getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(String secretaria) {
        this.secretaria = secretaria;
    }

    public Boolean getComision() {
        return comision;
    }

    public void setComision(Boolean comision) {
        this.comision = comision;
    }

    public Boolean getNocturnidad() {
        return nocturnidad;
    }

    public void setNocturnidad(Boolean nocturnidad) {
        this.nocturnidad = nocturnidad;
    }

    public Boolean getJefeHCD() {
        return jefeHCD;
    }

    public void setJefeHCD(Boolean jefeHCD) {
        this.jefeHCD = jefeHCD;
    }

    public Boolean getDirectorHCD() {
        return directorHCD;
    }

    public void setDirectorHCD(Boolean directorHCD) {
        this.directorHCD = directorHCD;
    }

    public Float getVacacionesPlanta() {
        return vacacionesPlanta;
    }

    public void setVacacionesPlanta(Float vacacionesPlanta) {
        this.vacacionesPlanta = vacacionesPlanta;
    }

    public Integer getArticulos() {
        return articulos;
    }

    public void setArticulos(Integer articulos) {
        this.articulos = articulos;
    }

    public Integer getAntPrivada() {
        return antPrivada;
    }

    public void setAntPrivada(Integer antPrivada) {
        this.antPrivada = antPrivada;
    }

    public Float getVacacionesPolitica() {
        return vacacionesPolitica;
    }

    public void setVacacionesPolitica(Float vacacionesPolitica) {
        this.vacacionesPolitica = vacacionesPolitica;
    }

    public LocalDateTime getActualizado() {
        return actualizado;
    }

    public void setActualizado(LocalDateTime actualizado) {
        this.actualizado = actualizado;
    }

    public Float getHoras100() {
        return horas100;
    }

    public void setHoras100(Float horas100) {
        this.horas100 = horas100;
    }

    public Float getHoras50() {
        return horas50;
    }

    public void setHoras50(Float horas50) {
        this.horas50 = horas50;
    }

    public Boolean getSistema() {
        return sistema;
    }

    public void setSistema(Boolean sistema) {
        this.sistema = sistema;
    }

    /*public byte[] getDato() {
        return dato;
    }

    public void setDato(byte[] dato) {
        this.dato = dato;
    }*/

    public LocalDate getVence() {
        return vence;
    }

    public void setVence(LocalDate vence) {
        this.vence = vence;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    public Integer getPerfil() {
        return perfil;
    }

    public void setPerfil(Integer perfil) {
        this.perfil = perfil;
    }

    public Boolean getLaborHSC() {
        return laborHSC;
    }

    public void setLaborHSC(Boolean laborHSC) {
        this.laborHSC = laborHSC;
    }

    public Boolean getCuidadorHSC() {
        return cuidadorHSC;
    }

    public void setCuidadorHSC(Boolean cuidadorHSC) {
        this.cuidadorHSC = cuidadorHSC;
    }

    public Boolean getRecolector() {
        return recolector;
    }

    public void setRecolector(Boolean recolector) {
        this.recolector = recolector;
    }

    public Boolean getTransito() {
        return transito;
    }

    public void setTransito(Boolean transito) {
        this.transito = transito;
    }

    public Boolean getBromatologia() {
        return bromatologia;
    }

    public void setBromatologia(Boolean bromatologia) {
        this.bromatologia = bromatologia;
    }

    public Boolean getObra() {
        return obra;
    }

    public void setObra(Boolean obra) {
        this.obra = obra;
    }

    public Boolean getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Boolean ambiente) {
        this.ambiente = ambiente;
    }

    public Boolean getCanino() {
        return canino;
    }

    public void setCanino(Boolean canino) {
        this.canino = canino;
    }

    public Boolean getHigiene() {
        return higiene;
    }

    public void setHigiene(Boolean higiene) {
        this.higiene = higiene;
    }

    public Boolean getOrdenanza() {
        return ordenanza;
    }

    public void setOrdenanza(Boolean ordenanza) {
        this.ordenanza = ordenanza;
    }

    public Boolean getFlotaPesada() {
        return flotaPesada;
    }

    public void setFlotaPesada(Boolean flotaPesada) {
        this.flotaPesada = flotaPesada;
    }

    public Boolean getTareasAdminHCD() {
        return tareasAdminHCD;
    }

    public void setTareasAdminHCD(Boolean tareasAdminHCD) {
        this.tareasAdminHCD = tareasAdminHCD;
    }

    public Boolean getChoferesPem() {
        return choferesPem;
    }

    public void setChoferesPem(Boolean choferesPem) {
        this.choferesPem = choferesPem;
    }

    public Boolean getLaborSalaMaquinaCalderaCJ() {
        return laborSalaMaquinaCalderaCJ;
    }

    public void setLaborSalaMaquinaCalderaCJ(Boolean laborSalaMaquinaCalderaCJ) {
        this.laborSalaMaquinaCalderaCJ = laborSalaMaquinaCalderaCJ;
    }

    public Boolean getResponFinanCont() {
        return responFinanCont;
    }

    public void setResponFinanCont(Boolean responFinanCont) {
        this.responFinanCont = responFinanCont;
    }

    public Boolean getTareasCementerio() {
        return tareasCementerio;
    }

    public void setTareasCementerio(Boolean tareasCementerio) {
        this.tareasCementerio = tareasCementerio;
    }

    public Boolean getLaborMuseosArch() {
        return laborMuseosArch;
    }

    public void setLaborMuseosArch(Boolean laborMuseosArch) {
        this.laborMuseosArch = laborMuseosArch;
    }

    public Boolean getLaborMuseos() {
        return laborMuseos;
    }

    public void setLaborMuseos(Boolean laborMuseos) {
        this.laborMuseos = laborMuseos;
    }

    public Boolean getLaborConservArch() {
        return laborConservArch;
    }

    public void setLaborConservArch(Boolean laborConservArch) {
        this.laborConservArch = laborConservArch;
    }

    public Boolean getLaborConservLab() {
        return laborConservLab;
    }

    public void setLaborConservLab(Boolean laborConservLab) {
        this.laborConservLab = laborConservLab;
    }

    public Boolean getTrabajaEnProvincia() {
        return trabajaEnProvincia;
    }

    public void setTrabajaEnProvincia(Boolean trabajaEnProvincia) {
        this.trabajaEnProvincia = trabajaEnProvincia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
