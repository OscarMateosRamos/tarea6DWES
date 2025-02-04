package com.oscar.vivero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.oscar.vivero.modelo.Planta;
import com.oscar.vivero.servicios.ServiciosPlanta;

@Controller
public class PlantaController {
	@Autowired
	ServiciosPlanta servPlanta;

	@GetMapping("/plantas")
	public String listarPlantas(Model model) {
		model.addAttribute("plantas", servPlanta.vertodasPlantas());
		return "listadodePlantas";
	}

	@PostMapping("/CamposPlanta")
	public String InsertarPlanta(@ModelAttribute Planta CrearPlanta, Model model) {

		Planta p = new Planta();

		String codigo = CrearPlanta.getCodigo();
		String nombreComun = CrearPlanta.getNombrecomun();
		String nombreCientifico = CrearPlanta.getNombrecientifico();

		if (CrearPlanta.getCodigo() == null || CrearPlanta.getCodigo().isEmpty()) {
			model.addAttribute("error", "El código de la planta es obligatorio.");
			return "CrearPlantas";
		}

		p.setCodigo(codigo);
		p.setNombrecomun(nombreComun);
		p.setNombrecientifico(nombreCientifico);
		boolean credValidas = servPlanta.validarPlanta(p);

		if (!credValidas) {
			model.addAttribute("error", " campos de la Planta Invalidos.");
			return "CrearPlantas";
		}

		servPlanta.insertarPlanta(p);
	
		return "/CrearPlantas";
	}

	@GetMapping("/CrearPlantas")
	public String mostrarFormulario(Model model) {
		model.addAttribute("planta", new Planta());
		return "CrearPlantas";
	}

}
