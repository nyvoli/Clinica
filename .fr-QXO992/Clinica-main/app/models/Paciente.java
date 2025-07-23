package models;

import java.util.Date;
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
	public String CPF;
	public String telefone;
	
	@Enumerated(EnumType.STRING)
	public Status status;
	
	public Paciente() {
		this.status = Status.ATIVO;
	}
}