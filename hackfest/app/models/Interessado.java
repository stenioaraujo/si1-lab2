package models;

import javax.persistence.*;

import play.data.validation.Constraints.*;
import play.db.ebean.*;

@Entity
public class Interessado extends Model{

	@Id
	@GeneratedValue
	public Long id;
	
	@Required
	public String nome;
	
	@Required
	public String email;
	
	public Interessado() {}
	
	public Interessado(String email, String nome) {
		this.email = email;
		this.nome = nome;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj.getClass().equals(this.getClass())) {
			return email.equals(((Interessado)obj).email);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return email == null? 0 : email.hashCode();
	}
}
