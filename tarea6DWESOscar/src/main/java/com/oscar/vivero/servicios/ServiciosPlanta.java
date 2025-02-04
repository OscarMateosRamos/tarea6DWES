package com.oscar.vivero.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscar.vivero.modelo.Planta;
import com.oscar.vivero.repositories.PlantaRepository;

@Service
public class ServiciosPlanta {

	@Autowired
	PlantaRepository plantarepo;

	public boolean validarPlanta(Planta pl) {

		if (pl.getCodigo() == null || pl.getCodigo().isEmpty()) {
			return false;
		}

		if (pl.getCodigo().length() < 3 || pl.getCodigo().length() > 20) {
			return false;
		}

		if (!plantarepo.findByCodigo(pl.getCodigo()).isEmpty()) {
			System.out.println("El código ya existe....");
			return false;
		}

		if (pl.getNombrecientifico() != null && pl.getNombrecientifico().length() > 45) {
			return false;
		}

		if (pl.getNombrecomun() != null && pl.getNombrecomun().length() > 40) {
			return false;
		}

		return true;
	}

	public void insertarPlanta(Planta pl) {
		plantarepo.saveAndFlush(pl);
	}

	public void modificarPlanta(Planta pl) {
		plantarepo.saveAndFlush(pl);
	}

	public List<Planta> vertodasPlantas() {
		List<Planta> plantas = plantarepo.findAllByOrderByNombrecomunAsc();
		return plantas;
	}

	public Planta buscarPlantaPorId(Long id) {
		return plantarepo.findById(id).orElse(null);
	}

	public boolean existeCodigoPlanta(String codigo) {
		List<Planta> plantas = plantarepo.findAll();

		for (Planta p : plantas) {
			if (p.getCodigo().equals(codigo)) {
				return true;
			}
		}
		return false;
	}

	public List<Planta> encontrartodasPlantas() {
		List<Planta> plantas = plantarepo.findAll();
		return plantas;
	}

	public List<Planta> encontrarPlantasPorCodigo(String codigo) {
		List<Planta> plantas = plantarepo.findByCodigo(codigo);
		return plantas;
	}

	public Planta buscarPlantaPorCodigo(String codigo) {
		Planta pl = new Planta();

		List<Planta> plantas = plantarepo.findAll();

		for (Planta p : plantas) {
			if (p.getCodigo().equals(codigo)) {

				pl = p;
			}
		}

		return pl;
	}

}
