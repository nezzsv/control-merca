package com.multicar.control_merca.repository;

import com.multicar.control_merca.model.Mercaderia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface MercaderiaRepository extends JpaRepository<Mercaderia, Long> {
    List<Mercaderia> findByProveedor(String proveedor);
    List<Mercaderia> findByCodigoInternoContainingIgnoreCase(String codigo);
    Optional<Mercaderia> findByCodigoInterno(String codigoInterno);

    @Query("SELECT DISTINCT m.proveedor FROM Mercaderia m WHERE m.proveedor IS NOT NULL AND m.proveedor != ''")
    List<String> findDistinctProveedores();

    long countByEstado(String estado);
    long countByEstadoNot(String estado);
}