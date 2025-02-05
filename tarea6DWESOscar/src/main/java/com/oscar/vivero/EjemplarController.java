package com.oscar.vivero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.oscar.vivero.modelo.Ejemplar;
import com.oscar.vivero.modelo.Planta;
import com.oscar.vivero.repositories.EjemplarRepository;
import com.oscar.vivero.servicios.Controlador;
import com.oscar.vivero.servicios.ServiciosEjemplar;
import com.oscar.vivero.servicios.ServiciosMensaje;
import com.oscar.vivero.servicios.ServiciosPersona;
import com.oscar.vivero.servicios.ServiciosPlanta;

@Controller
public class EjemplarController {

	@Autowired
	EjemplarRepository ejemplarrepo;

	ServiciosPersona servPersona;

	ServiciosMensaje servMensaje;

	ServiciosEjemplar servEjemplar;

	ServiciosPlanta servPlanta;

	Controlador controlador;

	@PostMapping("/CamposEjemplar")
	public String InsertarEjemplar(@ModelAttribute Ejemplar CrearEjemplar, Model model) {

		String codigo = CrearEjemplar.getPlanta().getCodigo().trim().toUpperCase();

		if (servPlanta.existeCodigoPlanta(codigo)) {

			Planta planta = (Planta) servPlanta.encontrarPlantasPorCodigo(codigo);

			CrearEjemplar.setPlanta(planta);
			CrearEjemplar.setNombre(planta.getCodigo());

			try {

				servEjemplar.insertarEjemplar(CrearEjemplar);
				model.addAttribute("success", "Ejemplar creado ");
				return "CrearEjemplar";
			} catch (Exception e) {
				model.addAttribute("error", "Error al crear el ejemplar: " + e.getMessage());
				return "CrearEjemplar";
			}
		} else {

			model.addAttribute("error", "No existe el código de la planta.");
			return "CrearEjemplar";
		}
	}

	@GetMapping("/CrearEjemplar")
	public String mostrarCrearEjemplarFormulario(Model model) {
		model.addAttribute("ejemplar", new Ejemplar());
		return "CrearEjemplar";
	}

}
