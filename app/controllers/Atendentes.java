package controllers;

import java.util.List;

import models.Consulta;
import models.Paciente;
import models.Status;
import play.mvc.Controller;

public class Atendentes extends Controller {

	
	public static void index() {
		render();
	}
	
    public static void menu(String busca) {
    	List<Consulta> consultas = null;
	    if (busca != null && !busca.trim().isEmpty()) {
			String filtro = "%" + busca.toLowerCase() + "%";
			consultas = Consulta.find("lower(especialidade) like :filtro and status != :statusInativo" ).bind("filtro", filtro).bind("statusInativo", Status.INATIVO).fetch();
			}
    	
        render(busca, consultas); }
    
    public static void lista_all(){
        List<Paciente> pacientes = Paciente.findAll();
        render(pacientes);
    }
}