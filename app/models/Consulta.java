package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.db.jpa.Model;

@Entity
public class Consulta extends Model{

	public String especialidade;
	public String medico;
	public String data;
	
	public Consulta(String especialidade, String medico, String data) {
		this.especialidade = especialidade;
		this.medico = medico;
		this.data = data;
	}
}
