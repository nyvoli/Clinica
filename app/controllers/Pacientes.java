package controllers;

import java.util.List;
import models.Status;
import models.Paciente;
import play.mvc.Controller;

public class Pacientes extends Controller {

	
	public static void form(Paciente paciente) {
		render(paciente);
	}
	
    public static void salvar(Paciente paciente) {
    	if(isEmpty(paciente.nome) || isEmpty(paciente.telefone) || (paciente.dataNascimento == null) || isEmpty(paciente.cpf) || isEmpty(paciente.convenio)) {
    		flash.error("Todos os campos devem ser preenchidos");
    		form(paciente);
    	} else if (paciente.telefone.length() < 11) { 
    		flash.error("Insira um telefone válido");
    		form(paciente);
    	} else if (paciente.cpf.length() < 11) {
    		flash.error("Insira um CPF válido");
    		form(paciente);
    	}
    	else {
    		paciente.save();
            detalhar(paciente.id); 
    	} 
    }
    
    public static void editar(Long id) {
    	Paciente paciente = Paciente.findById(id);
    	renderTemplate("Pacientes/form.html", paciente);
    }
    
    public static void detalhar(Long id) {
        Paciente paciente = Paciente.findById(id);
    	render(paciente);
    }

    public static void login() {
        render();
    }
    public static void lista_all(){
        List<Paciente> pacientes = Paciente.findAll();
        render(pacientes);
    }

    private static boolean isEmpty(String value){
        return value == null || value.trim().isEmpty();
    }
    
    public static void verificacao(String cpf) {
    	Paciente paciente = Paciente.find("byCpf", cpf).first();
        
        if(paciente != null) {
        	renderTemplate("Pacientes/detalhar.html", paciente);
        } else {
        	renderTemplate("Pacientes/form.html");
        }
    }
    
}