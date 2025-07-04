package com.mcpd.utils;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class CustomNamingStrategy implements PhysicalNamingStrategy {

    private final PhysicalNamingStrategy delegate = new CamelCaseToUnderscoresNamingStrategy();

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        Identifier result;
        if ("comprasAdquisicionPedido".equalsIgnoreCase(name.getText())) {
            result = Identifier.toIdentifier("comprasAdquisicionPedido");
        } else if ("comprasAdquisicionPedidoDetalle".equalsIgnoreCase(name.getText())) {
            result = Identifier.toIdentifier("comprasAdquisicionPedidoDetalle");
        } else {
            result = delegate.toPhysicalTableName(name, context);
        }
        System.out.println("➡️ Mapeo tabla lógica '" + name.getText() + "' ➔ física '" + result.getText() + "'");
        return result;
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return delegate.toPhysicalColumnName(name, context);
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment context) {
        return delegate.toPhysicalSchemaName(name, context);
    }

    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment context) {
        return delegate.toPhysicalCatalogName(name, context);
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
        return delegate.toPhysicalSequenceName(name, context);
    }
}

