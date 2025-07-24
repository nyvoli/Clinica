package models;

import java.util.Date;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Consulta extends Model{

	public String especialidade;
	public String medico;
	public Date data;
}
