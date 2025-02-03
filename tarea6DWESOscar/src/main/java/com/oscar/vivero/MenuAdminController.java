package com.oscar.vivero;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuAdminController {

	@GetMapping("/MenuAdmin")
	public String mostarMenuAdmin() {
		return "MenuAdmin";
	}
}

