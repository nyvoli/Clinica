package models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import play.db.jpa.Model;
@Entity
public class Administrador extends Model {
	
	public String nome;
	public String email;
	public String senha;
	@Enumerated(EnumType.STRING)
	public Tipo tipo;
	
	
	public Administrador(String nome, String email, String senha) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.tipo = Tipo.ADMINISTRADOR;
	}

	
}
