package models;

import javax.persistence.Transient;
import javax.validation.constraints.Max;

import net.sf.oval.constraint.MaxSize;
import net.sf.oval.constraint.MinSize;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.Min;
import play.data.validation.Required;
import play.db.jpa.Model;
@Entity
public class Paciente extends Model{

	public String nome;
	public Date dataNascimento;
	public String convenio;
	public String telefone;
	public String cpf;
	@Transient
	public Integer idade;
	
	@Enumerated(EnumType.STRING)
	public Status status;
	
	//calculo de idade
	public int getIdade() {
		if (idade == null) {			
			LocalDate localDataNascimento = dataNascimento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate dataCorrente = LocalDate.now();
			Period periodo = Period.between(localDataNascimento, dataCorrente);
			idade = periodo.getYears();			
		}
		return idade;
	}
	public Paciente() {
		this.status = Status.ATIVO;
	}
	@OneToMany
	public List<Consulta> consultas;
}