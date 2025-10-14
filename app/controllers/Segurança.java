package controllers;

import play.mvc.Before;
import play.mvc.Controller;

public class Segurança extends Controller {

	@Before
	static void verificacao() {
		if(session.get("cargo") == null) {
			flash.error("Você deve estar logado para acessar esta página");
		}
		Logins.login();
	}
}
