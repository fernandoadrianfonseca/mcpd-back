package com.mcpd.repository;

import com.mcpd.model.PedidoDeAdquisicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoDeAdquisicionRepository extends JpaRepository<PedidoDeAdquisicion, Long> {

    @Query(value = """
        SELECT
            'Suministros' AS origen,
            p.numero AS numero,
            p.fechasolicitud AS fechaSolicitud,
            p.nombresolicitante AS nombreSolicitante,
            p.presupuesto AS presupuesto,
            p.secretaria AS secretaria,
            p.direccion AS direccion,
            oc.partida AS partida,
            p.destino AS destino,
            p.observacion AS observacion,
            p.hacienda AS hacienda,
            p.archivado AS archivado,
            p.despacho AS despacho,
            p.numeroinstrumentoadquisicion AS numeroInstrumentoAdquisicion,
            oc.numero AS oc,
            oc.remito AS remito,
            CONCAT(f.letra, ' ', CAST(f.puntoventa AS VARCHAR), '-', CAST(f.numero AS VARCHAR), ' Fecha: ', CAST(f.fecha AS VARCHAR)) AS factura,
            f.resolucionpago AS resolucion,
            op.numero AS op,
            prov.nombrefantasia AS nombreFantasia,
            c.nombre AS nombre,
            c.cuit AS cuit,
            f.total AS totalFactura
        FROM comprasadquisicionpedido p
        LEFT JOIN comprasadquisicionordencompra oc ON p.numeroinstrumentoadquisicion = oc.instrumento
        LEFT JOIN mcpdproveedorfactura f ON oc.numero = f.ordencompracontratocertificacion
        LEFT JOIN tesoreriaordenpago op ON f.ordendepago = op.numero
        LEFT JOIN mcpdproveedor prov ON f.proveedor = prov.cuit
        LEFT JOIN mcpdcontribuyente c ON prov.cuit = c.cuit
        WHERE p.numero IS NOT NULL
        ORDER BY p.fechaSolicitud DESC 
        """, nativeQuery = true)
    List<Object[]> findPedidosConDetalles();
}
