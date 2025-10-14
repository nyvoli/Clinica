package controllers;

import java.util.List;

import models.Consulta;
import models.Paciente;
import models.Status;
import play.mvc.Controller;
import play.mvc.With;

@With(SegurançaAtendente.class)
public class Atendentes extends Controller {


	//menu dos atendentes com lógica da busca
    public static void menu(String busca) {
    	List<Consulta> consultas = null;
	    if (busca != null && !busca.trim().isEmpty()) {
			String filtro = "%" + busca.toLowerCase() + "%";
			consultas = Consulta.find("lower(especialidade) like :filtro and status != :statusInativo" )
					.bind("filtro", filtro).bind("statusInativo", Status.INATIVO).fetch();
			}
        render(busca, consultas); }
    
    //listar todos os pacientes cadastrados
    public static void lista_all(){
        List<Paciente> pacientes = Paciente.findAll();
        render(pacientes);
    }
}