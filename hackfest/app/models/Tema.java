package models;

import javax.persistence.*;

import play.data.validation.Constraints.*;
import play.db.ebean.*;

import java.util.*;

@Entity
public class Tema extends Model {
	static Finder<String, Tema> find = new Finder<String, Tema>(String.class, Tema.class);

	@Id
	@Required
	public String tema;
	
	@ManyToMany(cascade = CascadeType.ALL)
	public List<Evento> eventos;
	
	public Tema() {}
	
	public Tema(String tema) {
		this.tema = tema;
	}
	
	public void addEvento(Evento evento) {
		if (evento != null) {
			eventos.add(evento);
		}
	}
	
	public static List<Tema> all() {
		return find.all();
	}
	
	public static void save(Tema tema) {
		tema.save();
	}
}
