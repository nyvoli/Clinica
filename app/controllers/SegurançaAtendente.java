package controllers;

import play.mvc.Before;
import play.mvc.Controller;

public class SegurançaAtendente extends Controller {
	
	@Before
	static void verificacao() {
		if(session.get("cargo") != "ATENDENTE") {
			flash.error("Você deve estar logado como Atendente para acessar esta página");
		}
		Logins.login();
	}

}
