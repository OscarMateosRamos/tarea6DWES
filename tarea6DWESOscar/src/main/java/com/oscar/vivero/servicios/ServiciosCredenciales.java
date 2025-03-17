package com.oscar.vivero.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscar.vivero.modelo.Credenciales;
import com.oscar.vivero.repositories.CredencialRepository;

@Service
public class ServiciosCredenciales {

	@Autowired
	CredencialRepository credencialrepo;

	public boolean existeCredencial(String usuario) {
		List<Credenciales> credenciales = credencialrepo.findAll();

		for (Credenciales c : credenciales) {

			if (c.getUsuario() != null && c.getUsuario().equals(usuario)) {
				System.out.println("Ya existe: " + c.getUsuario() + " " + usuario);
				return true;
			}
		}
		return false;
	}

	public boolean validarUsuarioPassword(Credenciales c) {
		if (c.getUsuario().isEmpty() || c.getPassword().isEmpty() || c.getUsuario().contains(" ")
				|| c.getPassword().contains(" ")) {

			return false;
		}
		String regpassword = "^([A-Za-z0-9_!?¿+-]){3,}$";
		return c.getPassword().matches(regpassword);
	}

	public void insertarCredencial(Credenciales c) {
		credencialrepo.saveAndFlush(c);
	}

	public void findByUsuario(String usuario) {
		credencialrepo.findByUsuario(usuario);

	}

	public boolean verificaUsuario(String usuario, String password) {
		List<Credenciales> credenciales = credencialrepo.findAll();
		for (Credenciales c : credenciales) {
			if (c.getUsuario().equals(usuario) && c.getPassword().equals(password)) {
				return true;
			}
		}

		return false;
	}

	public Credenciales buscarCredencialPorUsuario(String usuario) {
		Credenciales cr = new Credenciales();

		List<Credenciales> credenciales = credencialrepo.findAll();

		for (Credenciales c : credenciales) {
			if (c.getUsuario().equals(usuario)) {

				cr = c;
			}
		}

		return cr;
	}

	public List<Credenciales> verCredenciales() {
		List<Credenciales> credenciales = credencialrepo.findAll();

		return credenciales;
	}

}
