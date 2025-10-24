package controllers;

import security.AdmSecurity;
import play.mvc.Before;
import play.mvc.Controller;
import security.AtdSecurity;

public class Segurança extends Controller {

	@Before
	static void verificarPermissao() {
		if (!session.contains("userName")) {
			flash.error("Você deve estar logado para acessar");
			Logins.login();
		}
	}

	@Before
	static void verificarAdm() {
		String cargo = session.get("cargo");
		AdmSecurity annotation = getActionAnnotation(AdmSecurity.class);
		if (annotation != null && (cargo == null || !cargo.equals("ADMINISTRADOR"))) {
			flash.error("Você deve estar logado como Administrador para acessar esta página");
			session.clear();
			Logins.login();
		}
	}

	@Before
	static void verificarAtendente() {
		String cargo = session.get("cargo");
		AtdSecurity annotation = getActionAnnotation(AtdSecurity.class);
		if (annotation != null && (cargo == null || !cargo.equals("ATENDENTE"))) {
			flash.error("Você deve estar logado como Atendente para acessar esta página");
			session.clear();
			Logins.login();
		}
	}
}
