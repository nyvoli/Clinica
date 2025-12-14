package controllers;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	public static void menu() {
		render();
	}

	// ajax/json
	@AdmSecurity
	public static void buscarAtendentesJson(String buscaAtd) {

		List<Map<String, Object>> resultado = new ArrayList<>();

		if (buscaAtd != null && !buscaAtd.trim().isEmpty()) {

			List<Atendente> atendentes = Atendente
					.find("lower(nome) like ?1 or lower(email) like ?1",
							"%" + buscaAtd.toLowerCase() + "%")
					.fetch();

			for (Atendente a : atendentes) {
				Map<String, Object> item = new HashMap<>();
				item.put("id", a.id);
				item.put("nome", a.nome);
				item.put("email", a.email);
				resultado.add(item);
			}
		}

		renderJSON(resultado);
	}

	@AdmSecurity
	public static void buscarConsultasJson(String buscaConsulta) {

		List<Map<String, Object>> resultado = new ArrayList<>();

		if (buscaConsulta != null && !buscaConsulta.trim().isEmpty()) {

			String filtro = "%" + buscaConsulta.toLowerCase() + "%";

			List<Consulta> consultas = Consulta
					.find("lower(especialidade) like :filtro and status != :statusInativo")
					.bind("filtro", filtro)
					.bind("statusInativo", Status.INATIVO)
					.fetch();

			for (Consulta c : consultas) {
				Map<String, Object> item = new HashMap<>();
				item.put("especialidade", c.especialidade);
				item.put("medico", c.medico);
				item.put("data", c.data);
				resultado.add(item);
			}
		}

		renderJSON(resultado);
	}

	@AdmSecurity
	public static void buscarPacientesJson(String buscaPaciente) {

    List<Map<String, Object>> resultado = new ArrayList<>();

    if (buscaPaciente != null && !buscaPaciente.trim().isEmpty()) {

        String filtro = "%" + buscaPaciente.toLowerCase() + "%";

        List<Paciente> pacientes = Paciente
            .find("lower(nome) like :filtro or cpf like :cpf")
            .bind("filtro", filtro)
            .bind("cpf", "%" + buscaPaciente + "%")
            .fetch();

        for (Paciente p : pacientes) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", p.id);
            item.put("nome", p.nome);
            item.put("cpf", p.cpf);
            resultado.add(item);
        }
    }

    renderJSON(resultado);
}
}