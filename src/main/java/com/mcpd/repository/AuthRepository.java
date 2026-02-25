package com.mcpd.repository;

import com.mcpd.dto.UsuarioDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Repositorio de autenticación basado en procedimientos almacenados (SQL Server).
 *
 * <p>
 * Encapsula la invocación a stored procedures del esquema de seguridad:
 * <ul>
 *   <li>{@code seguridadVerificaUsuario}: autentica credenciales y devuelve datos del usuario</li>
 *   <li>{@code seguridadModificaUsuario}: cambia contraseña del usuario</li>
 *   <li>{@code seguridadBlanqueaUsuario}: resetea/blanquea contraseña del usuario</li>
 * </ul>
 *
 * <p>
 * Este repositorio utiliza {@link jakarta.persistence.EntityManager} y {@link jakarta.persistence.StoredProcedureQuery}
 * para ejecutar SPs y mapear el resultado a {@link com.mcpd.dto.UsuarioDto}.
 */
@Repository
public class AuthRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Ejecuta el procedimiento {@code seguridadVerificaUsuario} para autenticar un usuario.
     *
     * <p>
     * Si el procedimiento devuelve resultado, mapea el primer row a {@link UsuarioDto}.
     * El campo {@code vence} se formatea como {@code yyyy-MM-dd}.
     *
     * @param usuario nombre de usuario.
     * @param password contraseña.
     * @return {@link UsuarioDto} si hubo resultado; {@code null} si no se autenticó.
     * @throws RuntimeException ante errores de ejecución/mapeo del procedimiento.
     */
    public UsuarioDto autenticarUsuario(String usuario, String password) {
        try {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("seguridadVerificaUsuario");

            query.registerStoredProcedureParameter("Usuario", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("Password", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("NuevoSistema", Boolean.class, ParameterMode.IN);

            query.setParameter("Usuario", usuario);
            query.setParameter("Password", password);
            query.setParameter("NuevoSistema", true);

            boolean hasResult = query.execute();

            if (hasResult) {
                Object[] result = (Object[]) query.getSingleResult();
                UsuarioDto usuarioDto = new UsuarioDto();
                usuarioDto.setNombre((String) result[0]);
                usuarioDto.setPerfil((Integer) result[1]);
                usuarioDto.setDependencia((String) result[2]);
                usuarioDto.setSecretaria((String) result[6]);
                usuarioDto.setAdministracion((String) result[7]);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                usuarioDto.setVence(result[3] != null ? dateFormat.format((Date) result[3]) : null);

                usuarioDto.setModulo((Integer) result[4]);
                usuarioDto.setLegajo(((Number) result[5]).longValue());

                return usuarioDto;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error de autenticación: " + e.getMessage());
        }
        return null;
    }

    /**
     * Ejecuta el procedimiento {@code seguridadModificaUsuario} para actualizar la contraseña.
     *
     * @param usuario nombre de usuario.
     * @param password nueva contraseña.
     * @throws RuntimeException ante errores de ejecución del procedimiento.
     */
    public void modificarUsuario(String usuario, String password) {
        try {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("seguridadModificaUsuario");

            query.registerStoredProcedureParameter("Usuario", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("Password", String.class, ParameterMode.IN);

            query.setParameter("Usuario", usuario);
            query.setParameter("Password", password);

            query.execute();
        } catch (Exception e) {
            throw new RuntimeException("Error al modificar usuario: " + e.getMessage());
        }
    }

    /**
     * Ejecuta el procedimiento {@code seguridadBlanqueaUsuario} para resetear/blanquear la contraseña.
     *
     * @param usuario nombre de usuario.
     * @throws RuntimeException ante errores de ejecución del procedimiento.
     */
    public void blanquearUsuario(String usuario) {
        try {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("seguridadBlanqueaUsuario");

            query.registerStoredProcedureParameter("Usuario", String.class, ParameterMode.IN);
            query.setParameter("Usuario", usuario);

            query.execute();
        } catch (Exception e) {
            throw new RuntimeException("Error al blanquear usuario: " + e.getMessage());
        }
    }
}
