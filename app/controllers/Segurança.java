package controllers;

import models.Administrador;
import play.mvc.Before;
import play.mvc.Controller;
import security.Atendente;

public class Segurança extends Controller {

	@Before
	static void verificarAtendente() {
		String cargo = session.get("cargo");
		Atendente annotation = getActionAnnotation(Atendente.class);
		if(annotation != null && (cargo == null || !cargo.equals("ATENDENTE"))) {
			flash.error("Você deve estar logado como Atendente para acessar esta página");
			Logins.login();
		}	
	}
}
