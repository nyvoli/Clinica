package controllers;

import java.util.List;
import models.Status;
import models.Consulta;
import models.Paciente;
import play.mvc.Controller;

public class Pacientes extends Controller {

	
	public static void form(Paciente paciente) {
		render(paciente);
	}
	
	public static void salvar(Paciente paciente) {
		//para verificar se já há algum paciente com esse CPF no banco de dados
	    Paciente cpfExiste = Paciente.find("byCpf", paciente.cpf).first();

	    //não aceita campos vazios
	    if (isEmpty(paciente.nome) || isEmpty(paciente.telefone) || (paciente.dataNascimento == null)
	        || isEmpty(paciente.cpf) || isEmpty(paciente.convenio)) {
	        flash.error("Todos os campos devem ser preenchidos");
	        form(paciente);
	    //telefone precisa ser válido
	    } else if (paciente.telefone.length() < 11) {
	        flash.error("Insira um telefone válido");
	        form(paciente);
	    //cpf precisa ser válido
	    } else if (paciente.cpf.length() < 11) {
	        flash.error("Insira um CPF válido");
	        form(paciente);
	    // verifica se o cpf pertence a outro paciente
	    } else if (cpfExiste != null && (paciente.id == null || !cpfExiste.id.equals(paciente.id))) {
	        flash.error("Já existe um paciente cadastrado com este CPF");
	        form(paciente);
	    // se nenhum dos casos, está tudo certo
	    } else{
	            paciente.save();
	            detalhar(paciente.id, null);
	        }};

    
    public static void editar(Long id) {
    	Paciente paciente = Paciente.findById(id);
    	renderTemplate("Pacientes/form.html", paciente);
    }
    
    public static void detalhar(Long id, String busca) {
    	//para buscar as consultas disponíveis no template
        Paciente paciente = Paciente.findById(id);
        List<Consulta> consultas = null;
        if (busca != null && !busca.trim().isEmpty()) {
    		String filtro = "%" + busca.toLowerCase() + "%";
    		consultas = Consulta.find("lower(especialidade) like :filtro and status != :statusInativo" ).bind("filtro", filtro).bind("statusInativo", Status.INATIVO).fetch();
    		}
        render(paciente, consultas, busca);
    }
    
    public static void remover(Long id) {
    	Paciente paciente = Paciente.findById(id);
    	paciente.status = Status.INATIVO;
    	paciente.save();
    	flash.success("O paciente foi excluído com sucesso");
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