package com.oscar.vivero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.oscar.vivero.modelo.Credenciales;
import com.oscar.vivero.modelo.Persona;
import com.oscar.vivero.modelo.Planta;
import com.oscar.vivero.servicios.ServiciosCredenciales;
import com.oscar.vivero.servicios.ServiciosPersona;

@Controller
public class PersonaController {
	@Autowired
	ServiciosPersona servPersona;

	@Autowired
	ServiciosCredenciales servCredenciales;

	@GetMapping("/personas")
	public String listarPersonas(Model model) {
		model.addAttribute("personas", servPersona.vertodasPersonas());
		return "listadodePersonas";
	}

	@PostMapping("/CamposPlanta")
	public String InsertarPersona(@ModelAttribute Persona CrearPersonas, Model model) {

		Persona p = new Persona();

		String nombre = CrearPersonas.getNombre();
		String email = CrearPersonas.getEmail();
		Credenciales Credenciales = CrearPersonas.getCredencial();

		// Comprobar que exitse la Credencial que se inserta
		boolean existeCred = servCredenciales.existeCredencial(nombre);

		p.setNombre(nombre);
		p.setEmail(email);
		p.setCredencial(Credenciales);
		boolean camposValidos = servPersona.validarPersona(nombre, email);

		if (!camposValidos) {
			model.addAttribute("error", " campos de la Planta Invalidos.");
			return "CrearPersonas";
		}

		servPersona.insertarPersona(p);

		return "/CrearPersonas";
	}

	@GetMapping("/CrearPersonas")
	public String mostrarFormulario(Model model) {
		model.addAttribute("persona", new Persona());
		return "CrearPersonas";
	}

}
