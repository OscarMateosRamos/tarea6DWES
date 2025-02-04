package com.oscar.vivero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	@PostMapping("/CrearPersona")
	public String InsertarPersona(@RequestParam String nombre, @RequestParam String email, @RequestParam String usuario,
			@RequestParam String password, Model model) {
//	public String InsertarPersona(@RequestParam String nombre, @RequestParam String email, Model model) {

		Persona p = new Persona();
		p.setNombre(nombre);
		p.setEmail(email);
		//Persona per = (Persona) model.getAttribute("persona");

		Credenciales c = new Credenciales();
		c.setUsuario(usuario); //per.getCredencial().getUsuario());
		c.setPassword(password); //per.getCredencial().getPassword());

		boolean existeCred = servCredenciales.existeCredencial(c.getUsuario());
		if (existeCred) {
			model.addAttribute("error", "Ya existe un Usuario con esa Credencial");
			return "CrearPersonas";
		}


		boolean credValidas = servCredenciales.validarUsuarioPassword(c);
		if (!credValidas) {
			model.addAttribute("error", "Credenciales para la persona invalidas.");
			return "CrearPersonas";
		}
		boolean camposValidos = servPersona.validarPersona(nombre, email);
		if (!camposValidos) {
			model.addAttribute("error", "Campos de la persona inválidos.");

			model.addAttribute("persona", new Persona());
			return "CrearPersonas";
		}
		servCredenciales.insertarCredencial(c);

		p.setCredencial(c);
		servPersona.insertarPersona(p);

		return "/CrearPersonas";
	}

	@GetMapping("/mostrarCrearPersonas")
	public String mostrarFormulario(Model model) {
		model.addAttribute("persona", new Persona());
		return "CrearPersonas";
	}

}
