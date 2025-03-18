package com.oscar.vivero.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oscar.vivero.modelo.Planta;

@Repository
public interface PlantaRepository extends JpaRepository<Planta, Long> {

	List<Planta> findAllByOrderByNombrecomunAsc();

	List<Planta> findByCodigo(String codigo);

	boolean existsByCodigo(String codigo);

	@Query("SELECT DISTINCT p.codigo FROM Planta p")
	List<String> findDistinctTiposDePlanta();

}
