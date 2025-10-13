package controllers;

import models.Administrador;
import models.Atendente;
import play.mvc.Controller;
import controllers.*;

public class Logins extends Controller {

	public static void login() {
		render();
	}
	
	public static void logar(String email, String senha) {
		Atendente atendenteExist = Atendente.find("email = ?1 and senha = ?2", email, senha).first();
		Administrador admExist = Administrador.find("email = ?1 and senha = ?2", email, senha).first();

		//se não achar nenhum dos dois
		if(atendenteExist == null && admExist == null) {
			flash.error("Login ou senha inválidos");
			login();
		}
		
		//se achar atendente mas não administrador
		 else if (atendenteExist != null && admExist == null) {
			session.put("userName", atendenteExist.nome);
			session.put("userEmail", atendenteExist.email);
			session.put("cargo", atendenteExist.tipo);
			//redireciona para pag inicial dos atendentes
			Atendentes.index();
		}
		
		//se achar adm mas não atendente	
		 else if (atendenteExist == null && admExist != null) {
			session.put("userName", admExist.nome);
			session.put("userEmail", admExist.email);
			session.put("cargo", admExist.tipo);
			//redireciona para pag inicial dos administradores
			Administradores.index();
		}
	}
}
