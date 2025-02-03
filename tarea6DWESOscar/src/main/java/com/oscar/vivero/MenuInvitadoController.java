package com.oscar.vivero;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuInvitadoController {

	@GetMapping("/InvitadoMenu")
	public String mostarMenuInvitado() {
		return "MenuInvitado";
	}
}
