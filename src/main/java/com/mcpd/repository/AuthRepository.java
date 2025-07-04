package com.mcpd.repository;

import com.mcpd.dto.UsuarioDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.text.SimpleDateFormat;

@Repository
public class AuthRepository {

    @PersistenceContext
    private EntityManager entityManager;

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
