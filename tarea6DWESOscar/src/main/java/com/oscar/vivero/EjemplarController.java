package com.oscar.vivero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.oscar.vivero.modelo.Ejemplar;
import com.oscar.vivero.repositories.EjemplarRepository;
import com.oscar.vivero.servicios.Controlador;
import com.oscar.vivero.servicios.ServiciosMensaje;
import com.oscar.vivero.servicios.ServiciosPersona;

@Controller
public class EjemplarController {

	@Autowired
	EjemplarRepository ejemplarrepo;

	ServiciosPersona servPersona;

	ServiciosMensaje servMensaje;

	Controlador controlador;

	@PostMapping("/CamposEjemplar")
	public String InsertarEjemplar(@ModelAttribute Ejemplar CrearEjemplar, Model model) {
		
		
		return "/CrearEjemplar";
	}

	@GetMapping("/CrearEjemplar")
	public String mostrarCrearEjemplarFormulario(Model model) {
		model.addAttribute("ejemplar", new Ejemplar());
		return "CrearEjemplar";
	}

}
