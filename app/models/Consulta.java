package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.db.jpa.Model;

@Entity
public class Consulta extends Model{

	public String especialidade;
	public String medico;
	public String data;
	@Enumerated(EnumType.STRING)
	public Status status;
	
	@ManyToOne
	public Paciente paciente;

	public Consulta(String especialidade, String medico, String data) {
	    this.especialidade = especialidade;
	    this.medico = medico;
	    this.data = data;
	    this.status = Status.ATIVO; 
	}
}
