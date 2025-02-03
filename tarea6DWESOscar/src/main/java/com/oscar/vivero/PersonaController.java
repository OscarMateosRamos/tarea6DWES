package com.oscar.vivero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.oscar.vivero.servicios.ServiciosPersona;

@Controller
public class PersonaController {
	@Autowired
	ServiciosPersona servPersona;

	@GetMapping("/personas")
	public String listarPersonas(Model model) {
		model.addAttribute("personas", servPersona.vertodasPersonas());
		return "listadodePersonas";
	}
}
