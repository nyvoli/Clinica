package controllers;

import java.util.List;

import models.Atendente;
import models.Consulta;
import models.Paciente;
import models.Status;
import models.Tipo;
import play.mvc.Controller;
import play.mvc.With;
import security.AtdSecurity;
import security.AdmSecurity;

@With(Segurança.class)
public class Atendentes extends Controller {

	@AdmSecurity
	public static void detalhar(Long id) {
		Atendente atendente = Atendente.findById(id);
		render(atendente);
	}

	@AdmSecurity
	public static void form(Atendente atendente) {
		render(atendente);
	}

	public static void editar(Long id) {
		Atendente atendente = Atendente.findById(id);
		renderTemplate("Atendentes/form.html", atendente);
	}

	public static void remover(Long id) {
		Atendente atendente = Atendente.findById(id);
		atendente.delete();
		flash.success("O atendente foi excluído com sucesso");
		Administradores.menu();
	}

	public static void cadastrar(Atendente atendente) {
		Atendente atdExistente = Atendente.find("byEmail", atendente.email).first();

		if (atendente.nome.isEmpty() || atendente.email.isEmpty() || atendente.senha.isEmpty()) {
			flash.error("Todos os campos devem estar preenchidos");
			renderTemplate("Atendentes/form.html", atendente);
		} else if (!atendente.email.contains("@gmail.com")) {
			flash.error("Insira um email válido");
			renderTemplate("Atendentes/form.html", atendente);
		} else if (atdExistente != null && !atdExistente.id.equals(atendente.id)) {
			flash.error("Já existe um atendente cadastrado com esse email");
			renderTemplate("Atendentes/form.html", atendente);
		} else {
			atendente.tipo = Tipo.ATENDENTE;
			atendente.save();
			detalhar(atendente.id);
		}
	}

	@AtdSecurity
	// menu dos atendentes com lógica da busca
	public static void menu(String busca) {
		List<Consulta> consultas = null;
		if (busca != null && !busca.trim().isEmpty()) {
			String filtro = "%" + busca.toLowerCase() + "%";
			consultas = Consulta.find("lower(especialidade) like :filtro and status != :statusInativo")
					.bind("filtro", filtro).bind("statusInativo", Status.INATIVO).fetch();
		}
		render(busca, consultas);
	}

	// listar todos os pacientes cadastrados
	public static void lista_all(String busca) {
		List<Paciente> pacientes = Paciente.findAll();

		if (busca != null && !busca.trim().isEmpty()) {
			pacientes = Paciente.find("lower(nome) like" + "or lower(email) like ?1", "%" + busca.toLowerCase() + "%")
					.fetch();
		}
		render(pacientes, busca);

	}
}