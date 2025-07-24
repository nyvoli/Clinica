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
	    Paciente cpfExiste = Paciente.find("byCpf", paciente.cpf).first();

	    if (isEmpty(paciente.nome) || isEmpty(paciente.telefone) || (paciente.dataNascimento == null)
	        || isEmpty(paciente.cpf) || isEmpty(paciente.convenio)) {
	        flash.error("Todos os campos devem ser preenchidos");
	        form(paciente);

	    } else if (paciente.telefone.length() < 11) {
	        flash.error("Insira um telefone válido");
	        form(paciente);

	    } else if (paciente.cpf.length() < 11) {
	        flash.error("Insira um CPF válido");
	        form(paciente);

	    } else if (cpfExiste != null && (paciente.id == null || !cpfExiste.id.equals(paciente.id))) {
	        // verifica se o cpf pertence a outro paciente
	        flash.error("Já existe um paciente cadastrado com este CPF");
	        form(paciente);

	    } else {
	        if (paciente.id != null) {
	            // se for edição de paciente
	            Paciente existente = Paciente.findById(paciente.id);
	            existente.nome = paciente.nome;
	            existente.telefone = paciente.telefone;
	            existente.dataNascimento = paciente.dataNascimento;
	            existente.cpf = paciente.cpf;
	            existente.convenio = paciente.convenio;
	            existente.status = paciente.status;
	            existente.save();
	            detalhar(existente.id);
	        } else {
	            // se for paciente novo
	            paciente.save();
	            detalhar(paciente.id);
	        }}}

    
    public static void editar(Long id) {
    	Paciente paciente = Paciente.findById(id);
    	renderTemplate("Pacientes/form.html", paciente);
    }
    
    public static void detalhar(Long id) {
        Paciente paciente = Paciente.findById(id);
    	render(paciente);
    }
    
    public static void remover(Long id) {
    	Paciente paciente = Paciente.findById(id);
    	paciente.status = Status.INATIVO;
    	paciente.save();
    	login();
    }
    
    public static void verificacao(String cpf) {
        Paciente paciente = Paciente.find("byCpf", cpf).first();

        if (paciente == null) {
            renderTemplate("Pacientes/form.html");
        } else if (paciente.status == Status.INATIVO) {
            flash.error("Este paciente está desativado");
            login();
        } else {
            renderTemplate("Pacientes/detalhar.html", paciente);
        }
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
    
   
    
}