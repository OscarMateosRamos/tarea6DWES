package com.oscar.vivero;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping({ "/", "MenuInvitado" })
	public String MenuInvitado() {

		return "MenuInvitado";

	}

}
