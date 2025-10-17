package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Consulta;
import models.Paciente;
import controllers.Pacientes;
import models.Status;
import play.mvc.Controller;

public class Consultas extends Controller {

	public static void salvar(Long pacienteId, Long consultaId){
		Paciente paciente = Paciente.findById(pacienteId);
		Consulta consulta = Consulta.findById(consultaId);
		
		if (paciente.consultas == null ) {
			paciente.consultas = new ArrayList<Consulta>();
		}
		
		consulta.paciente = paciente; // vincula a consulta ao paciente
		paciente.consultas.add(consulta); //adiciona essa consulta a lista de consultas do paciente
		consulta.status = Status.INATIVO; //indisponibiliza a consulta para outros pacientes
		consulta.save();
		paciente.save();
		flash.success("Consulta agendada com sucesso!");
		Pacientes.detalhar(paciente.id, null);
	}
	
	public static void remover(Long pacienteId, Long consultaId) {
		Paciente paciente = Paciente.findById(pacienteId);
		Consulta consulta = Consulta.findById(consultaId);
		consulta.paciente = null;
		paciente.consultas.remove(consulta);
		consulta.status = Status.ATIVO;
		consulta.save();
		paciente.save();
		Pacientes.detalhar(paciente.id, null);

	}
		
}
