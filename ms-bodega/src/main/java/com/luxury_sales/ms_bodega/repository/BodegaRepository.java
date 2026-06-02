package com.luxury_sales.ms_bodega.repository;

import com.luxury_sales.ms_bodega.model.Bodega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodegaRepository extends JpaRepository<Bodega, Long> {
    
}