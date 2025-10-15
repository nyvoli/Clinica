package controllers;

import models.Administrador;
import play.mvc.Before;
import play.mvc.Controller;
import security.Atendente;

public class Segurança extends Controller {

	@Before
	static void verificacao() {
		if(session.get("cargo") == null) {
			flash.error("Você deve estar logado para acessar esta página");
			Logins.login();
		}
		
	}
	
	@Before
	static void verificarAtendente() {
		String cargo = session.get("userCargo");
		Atendente annotation = getActionAnnotation(Atendente.class);
		if(annotation != null && session.get("cargo") != "ATENDENTE") {
			flash.error("Você deve estar logado como Atendente para acessar esta página");
			Logins.login();
		}
		
	}
}
