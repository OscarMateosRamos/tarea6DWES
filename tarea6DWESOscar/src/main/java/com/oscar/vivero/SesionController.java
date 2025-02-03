package com.oscar.vivero;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.oscar.vivero.modelo.Credenciales;
import com.oscar.vivero.servicios.ServiciosCredenciales;

@Controller
public class SesionController {
	@Autowired
	ServiciosCredenciales servCredenciales;

	@GetMapping("/Sesion")
	public String logIn(Model model) {
		model.addAttribute("credenciales", new Credenciales());
		return "formularioLogIn";

	}

	@PostMapping("/Sesion")
	public String logInSubmit(@ModelAttribute Credenciales formularioLogIn, Model model) {

		String usuario = formularioLogIn.getUsuario();
		String password = formularioLogIn.getPassword();

		if (usuario == null || password == null || usuario.trim().isEmpty() || password.trim().isEmpty()) {
			model.addAttribute("error", "Por favor, ingrese ambos campos.");
			return "formularioLogIn";
		}

		boolean existeCred = servCredenciales.verificaUsuario(usuario, password);
		if (!existeCred) {
			model.addAttribute("error", "Las credenciales no existen.");
			return "formularioLogIn";
		}

		Credenciales c = servCredenciales.buscarCredencialPorUsuario(usuario);
		boolean credValidas = servCredenciales.validarUsuarioPassword(c);

		if (!credValidas) {
			model.addAttribute("error", "Credenciales inválidas.");
			return "formularioLogIn";
		}

		if (usuario.equals("admin") && password.equals("admin")) {
			System.out.println("--Bienvenido Admin--");
			return "/MenuAdmin";
		}

		if (usuario.equals(usuario) && password.equals(password)) {
			System.out.println("--Bienvenido--" + usuario);
			return "/MenuPersonal";
		}
		return "MenuInvitado";

	}

}
