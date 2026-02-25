package com.mcpd.dto;

/**
 * DTO que representa la identidad y el contexto del usuario autenticado.
 *
 * <p>
 * Se construye a partir del resultado del procedimiento almacenado de autenticación
 * y se utiliza para:
 * <ul>
 *   <li>Devolver información al frontend al iniciar sesión</li>
 *   <li>Generar claims dentro del JWT</li>
 *   <li>Aplicar autorización (perfil/módulo) en el filtro de seguridad</li>
 * </ul>
 *
 * <h3>Campos relevantes</h3>
 * <ul>
 *   <li><b>perfil</b>: nivel/rol de usuario (mapeado a authorities)</li>
 *   <li><b>modulo</b>: módulo habilitado para el usuario</li>
 *   <li><b>legajo</b>: identificador del usuario dentro del sistema municipal</li>
 *   <li><b>dependencia/secretaria/administracion</b>: contexto organizacional</li>
 *   <li><b>vence</b>: fecha de vencimiento (formato yyyy-MM-dd o null)</li>
 * </ul>
 */
public class UsuarioDto {

    /** Nombre visible del usuario (usado también como subject del JWT si está disponible). */
    private String nombre;

    /** Perfil numérico del usuario (se mapea a roles/authorities en el filtro). */
    private int perfil;

    /** Dependencia/área a la que pertenece el usuario. */
    private String dependencia;

    /** Fecha de vencimiento de credenciales (formato yyyy-MM-dd). */
    private String vence;

    /** Identificador del módulo asignado al usuario. */
    private int modulo;

    /** Legajo del usuario (identificador principal en el sistema). */
    private long legajo;

    /** Secretaría asociada al usuario (si aplica). */
    private String secretaria;

    /** Administración asociada al usuario (si aplica). */
    private String administracion;


    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPerfil() {
        return perfil;
    }

    public void setPerfil(int perfil) {
        this.perfil = perfil;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    public String getVence() {
        return vence;
    }

    public void setVence(String vence) {
        this.vence = vence;
    }

    public int getModulo() {
        return modulo;
    }

    public void setModulo(int modulo) {
        this.modulo = modulo;
    }

    public long getLegajo() {
        return legajo;
    }

    public void setLegajo(long legajo) {
        this.legajo = legajo;
    }

    public String getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(String secretaria) {
        this.secretaria = secretaria;
    }

    public String getAdministracion() {
        return administracion;
    }

    public void setAdministracion(String administracion) {
        this.administracion = administracion;
    }
}
