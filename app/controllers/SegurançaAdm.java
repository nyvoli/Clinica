package controllers;

import play.mvc.Before;
import play.mvc.Controller;

public class SegurançaAdm extends Controller {
	
	@Before
	static void verificacao() {
		if(session.get("cargo") != "ADMINISTRADOR") {
			flash.error("Você deve estar logado como Administrador para acessar esta página");
		}
		Logins.login();
	}

}
