package com.multicar.control_merca.repository;

import com.multicar.control_merca.model.Mercaderia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MercaderiaRepository extends JpaRepository<Mercaderia, Long> {
    // Esta interfaz le dice a Spring: "Ey, prepara la tabla porque voy a guardar datos"
}