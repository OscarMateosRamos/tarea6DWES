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
		Date fechahora = CrearMensaje.getFechahora();
		String mensaje = CrearMensaje.getMensaje();
		Long idEjemplar = CrearMensaje.getEjemplar().getId();

		if (!servEjemplar.existeNombreEjemplar(mensaje)) {
			model.addAttribute("error", "El nombre de ejemplar introducido no existe.");
			return "CrearMensaje";
		} else {
			boolean existeidEjemplar = servEjemplar.existeIdEjemplar(idEjemplar);

			if (!existeidEjemplar) {
				model.addAttribute("error", "El id de ejemplar introducido no existe ");
				return "CrearMensaje";
			} else {

				boolean PersonaMensaje = servPersona.existeNombrePersona(controlador.getUsername());

				if (!PersonaMensaje) {
					model.addAttribute("error", "El nombre de la persona introducida no existe");
					return "CrearMensaje";
				} else {
					Mensaje m = new Mensaje();
					Persona p = new Persona();
					Ejemplar ej = new Ejemplar();

					m.setPersona(p);
					m.setEjemplar(ej);

					LocalDate fecha = LocalDate.now();

					fechahora = Date.valueOf(fecha);
					m.setFechahora(fechahora);
					m.setMensaje(mensaje);

					servMensaje.insertar(m);
				}

			}

		}

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
