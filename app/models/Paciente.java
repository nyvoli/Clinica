package models;

import java.util.Date;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;
@Entity
public class Paciente extends Model{

	public String nome;
	public Date dataNascimento;
	public String convenio;
	public String telefone;
	public String cpf;
	
	@Enumerated(EnumType.STRING)
	public Status status;
	
	public Paciente() {
		this.status = Status.ATIVO;
	}
	@OneToMany
	public List<Consulta> consultas;
}