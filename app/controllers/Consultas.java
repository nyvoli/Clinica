package controllers;

import java.util.List;

import models.Consulta;
import play.mvc.Controller;

public class Consultas extends Controller {

	public static void listar(String busca) {
		
		List<Consulta> consultas;
		if(busca.isEmpty()) {
		consultas = Consulta.findAll();	
		} else {
		String filtro = "%" + busca.toLowerCase() + "%";
		consultas = Consulta.find("lower(especialidade like ?", filtro).fetch();
		}
		
		
		render(consultas);
	}
}
