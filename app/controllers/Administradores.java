package controllers;

import java.util.List;

import models.Atendente;
import models.Consulta;
import models.Paciente;
import models.Status;
import play.mvc.Controller;
import play.mvc.With;
import security.AdmSecurity;

@With(Segurança.class)
public class Administradores extends Controller {

	@AdmSecurity
	public static void menu(String buscaAtd, String buscaConsulta, String buscaPacientes) {
		List<Atendente> atendentes = null;
		// buscar atendente
		if (buscaAtd != null) {
			atendentes = Atendente
					.find("lower (nome) like ?1 " + "or lower(email) like ?1", "%" + buscaAtd.toLowerCase() + "%")
					.fetch();
		}

		List<Consulta> consultas = null;
		// buscar consultas
		if (buscaConsulta != null && !buscaConsulta.trim().isEmpty()) {
			String filtro = "%" + buscaConsulta.toLowerCase() + "%";
			consultas = Consulta.find("lower(especialidade) like :filtro and status != :statusInativo")
					.bind("filtro", filtro).bind("statusInativo", Status.INATIVO).fetch();
		}

		List<Paciente> pacientes = null;
		// buscar paciente
		if (buscaPacientes != null) {
			pacientes = Paciente
					.find("lower (nome) like ?1 " + "or cpf like ?1", "%" + buscaPacientes.toLowerCase() + "%").fetch();
		}

		render(atendentes, consultas, pacientes, buscaAtd, buscaConsulta, buscaPacientes);

	}

}
