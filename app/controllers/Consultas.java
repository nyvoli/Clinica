package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Consulta;
import models.Paciente;
import models.Status;
import play.mvc.Controller;

public class Consultas extends Controller {

	public static void salvar(Long pacienteId, Long consultaId){
		Paciente paciente = Paciente.findById(pacienteId);
		Consulta consulta = Consulta.findById(consultaId);
		
		if (paciente.consultas == null ) {
			paciente.consultas = new ArrayList<Consulta>();
		}
		
		paciente.consultas.add(consulta);
		consulta.status = Status.INATIVO;
	}
		
}
