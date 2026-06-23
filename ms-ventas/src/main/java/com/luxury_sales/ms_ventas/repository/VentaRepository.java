package com.luxury_sales.ms_ventas.repository;

import com.luxury_sales.ms_ventas.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;



public interface VentaRepository extends JpaRepository<Venta, Long> {
}