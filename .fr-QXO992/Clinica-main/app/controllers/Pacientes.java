package controllers;

import java.util.List;
import models.Status;
import models.Paciente;
import play.mvc.Controller;

public class Pacientes extends Controller {

	
	public static void form(Paciente paciente) {
		render();
	}
	
    public static void salvar(Paciente paciente) {
    	if(paciente.nome == null | paciente.telefone == null | paciente.dataNascimento == null | paciente.CPF == null | paciente.convenio == null) {
    		flash.error("Todos os campos devem ser preenchidos");
    		form(paciente);
    	} else {
    		paciente.save();
            detalhar(paciente); 
    	}
            
    }

    public static void detalhar(Paciente paciente) {
        render(paciente);
    }

    public static void login() {
        render();
    }
    public static void lista_all(){
        List<Paciente> pacientes = Paciente.findAll();
        render(pacientes);
    }   
    public static void verificacao(Paciente paciente) {
        List<Paciente> pacientes = Paciente.findAll();

        boolean telefoneExiste = pacientes.stream()
            .anyMatch(p -> p.telefone != null && p.telefone.equals(paciente.telefone));

        if (telefoneExiste) {
            renderTemplate("Pacientes/detalhar.html", paciente);
        } else {
            renderTemplate("Pacientes/form.html", paciente);
        }
    }
    
}