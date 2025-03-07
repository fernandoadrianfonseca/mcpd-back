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
}
