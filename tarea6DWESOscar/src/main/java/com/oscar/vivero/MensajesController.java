package com.oscar.vivero;

import java.sql.Date;
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

		if (CrearMensaje.getEjemplar() == null || CrearMensaje.getEjemplar().getId() == 0) {
			model.addAttribute("error", "Debe seleccionar un ejemplar.");
			return "CrearMensaje";
		}

		Long idEjemplar = CrearMensaje.getEjemplar().getId();

		if (!servEjemplar.existeIdEjemplar(idEjemplar)) {
			model.addAttribute("error", "No existe el idEjemplar: " + idEjemplar);
			return "CrearMensaje";
		}

		Mensaje m = new Mensaje();
		Persona p = servPersona.buscarPorNombre(controlador.getUsername());

		Ejemplar ej = servEjemplar.buscarPorId(idEjemplar);

		m.setEjemplar(ej);
		m.setFechahora(CrearMensaje.getFechahora());
		m.setMensaje(CrearMensaje.getMensaje());
		m.setPersona(p);

		servMensaje.insertar(m);

		return "CrearMensaje";
	}

	@GetMapping("/mostrarCrearMensajes")
	public String mostrarCrearMensajeFormulario(Model model) {

		model.addAttribute("mensaje", new Mensaje());

		List<Ejemplar> ejemplares = servEjemplar.vertodosEjemplares();

		model.addAttribute("ejemplares", ejemplares);
		model.addAttribute("ejemplar", new Ejemplar());

		return "CrearMensaje";
	}

}
