package com.oscar.vivero;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.oscar.vivero.modelo.Ejemplar;
import com.oscar.vivero.modelo.Mensaje;
import com.oscar.vivero.modelo.Persona;
import com.oscar.vivero.servicios.Controlador;
import com.oscar.vivero.servicios.ServiciosEjemplar;
import com.oscar.vivero.servicios.ServiciosMensaje;
import com.oscar.vivero.servicios.ServiciosPersona;

@Controller
public class MensajesController {

	@Autowired
	ServiciosMensaje servMensaje;

	@Autowired
	ServiciosPersona servPersona;

	@Autowired
	ServiciosEjemplar servEjemplar;

	@Autowired
	Controlador controlador;

	@PostMapping("/CamposMensaje")
	public String InsertarMensaje(@ModelAttribute Mensaje CrearMensaje, Model model) {
		Mensaje m = new Mensaje();

		java.sql.Date fechahora = java.sql.Date.valueOf(LocalDate.now());
		String mensaje = CrearMensaje.getMensaje();

		Long ejemplarId = CrearMensaje.getEjemplar().getId();

		if (ejemplarId == null || ejemplarId == 0) {
			model.addAttribute("error", "No has seleccionado un Ejemplar o el ID es inválido.");
			return "CrearMensaje";
		}

		Ejemplar ej = servEjemplar.buscarPorId(ejemplarId);
		if (ej == null) {
			model.addAttribute("error", "No existe el ejemplar");
			return "CrearMensaje";
		}

		Persona p = servPersona.buscarPorNombre(controlador.getUsername());

		m.setPersona(p);
		m.setEjemplar(ej);
		m.setFechahora(fechahora);
		m.setMensaje(mensaje);

		servMensaje.insertar(m);

		return "CrearMensaje";
	}

	@GetMapping("/CrearMensajes")
	public String mostrarCrearMensajeFormulario(Model model) {
		model.addAttribute("mensaje", new Mensaje());
		List<Ejemplar> ejemplares = servEjemplar.vertodosEjemplares();
		model.addAttribute("ejemplares", ejemplares);
		return "crearMensaje";
	}

}
