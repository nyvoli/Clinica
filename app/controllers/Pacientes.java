package controllers;

import java.util.List;

import models.Status;
import models.Consulta;
import models.Paciente;
import play.data.validation.Valid;
import play.mvc.Controller;
import play.mvc.With;
import controllers.*;

@With(Segurança.class)
public class Pacientes extends Controller {

	public static void form(Paciente paciente) {
		render(paciente);
	}

	public static void editar(Long id) {
		Paciente paciente = Paciente.findById(id);
		renderTemplate("pacientes/form.html", paciente);
	}

	public static void salvar(Paciente paciente) {
		// para verificar se já há algum paciente com esse CPF no banco de dados
		Paciente cpfExiste = Paciente.find("byCpf", paciente.cpf).first();

		// não aceita campos vazios
		if (isEmpty(paciente.nome) || isEmpty(paciente.telefone) || (paciente.dataNascimento == null)
				|| isEmpty(paciente.cpf) || isEmpty(paciente.convenio)) {
			flash.error("Todos os campos devem ser preenchidos");
			renderTemplate("pacientes/form.html", paciente);
			// telefone precisa ser válido
		} else if (paciente.telefone.length() < 11) {
			flash.error("Insira um telefone válido");
			renderTemplate("pacientes/form.html", paciente);
			// cpf precisa ser válido
		} else if (paciente.cpf.length() < 11) {
			flash.error("Insira um CPF válido");
			renderTemplate("pacientes/form.html", paciente);
			// verifica se o cpf pertence a outro paciente
		} else if (cpfExiste != null && cpfExiste.id != paciente.id) {
			flash.error("Já existe um paciente cadastrado com este CPF");
			System.out.println(">>>>>>" + paciente.id);
			renderTemplate("pacientes/form.html", paciente);

		} else if (!paciente.cpf.matches("\\d+")) {
			flash.error("Insira um CPF válido");
			renderTemplate("pacientes/form.html", paciente);
		}
		// se nenhum dos casos, está tudo certo
		else {
			paciente.save();
			detalhar(paciente.id, null);
		}
	};

	public static void detalhar(Long id, String busca) {
		// para buscar as consultas disponíveis no template
		Paciente paciente = Paciente.findById(id);
		List<Consulta> consultas = null;
		if (busca != null && !busca.trim().isEmpty()) {
			String filtro = "%" + busca.toLowerCase() + "%";
			consultas = Consulta.find("lower(especialidade) like :filtro and status != :statusInativo")
					.bind("filtro", filtro).bind("statusInativo", Status.INATIVO).fetch();
		}
		render(paciente, consultas, busca);
	}

	public static void remover(Long id) {
		Paciente paciente = Paciente.findById(id);
		List<Consulta> consultas = paciente.consultas;

		if (consultas == null) {
			paciente.status = Status.INATIVO;
			paciente.save();
			flash.success("O paciente foi excluído com sucesso");
			Atendentes.menu(null);
		} else if (consultas != null) {
			for (int i = 0; i < consultas.size(); i++) {
				Consulta consulta = consultas.get(i);
				consulta.status = Status.ATIVO;
				consulta.paciente = null;
				paciente.consultas.remove(consulta);
				paciente.status = Status.INATIVO;
				consulta.save();
				paciente.save();
			}
			flash.success("O paciente foi excluído com sucesso");
			renderTemplate("Atendentes/menu.html");
		}
	}

	public static void verificacao(String cpf) {

		Paciente paciente = Paciente.find("byCpf", cpf).first();

		while (cpf.length() < 11) {
			flash.error("CPF inválido");
			Atendentes.menu(null);
		}

		while (!cpf.matches("\\d+")) {
			flash.error("Insira um CPF válido");
			Atendentes.menu(null);
		}
		if (paciente == null) {
			flash.error("Este paciente não está cadastrado");
			form(null);
		} else if (paciente.status == Status.INATIVO) {
			flash.error("Este paciente está desativado");
			Atendentes.menu(null);
		} else {
			renderTemplate("Pacientes/detalhar.html", paciente);
		}
	}

	private static boolean isEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}

}