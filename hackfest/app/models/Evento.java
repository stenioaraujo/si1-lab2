package models;

import java.util.*;

import javax.persistence.*;

import play.data.validation.Constraints.*;
import play.db.ebean.*;

@Entity
public class Evento extends Model {
	static Finder<Long, Evento> find = new Finder<Long, Evento>(Long.class, Evento.class);
	
	@Id
	@GeneratedValue
	public long id;
	
	public Map<String, String> interessados = new HashMap<String, String>();
	
	@Required
	@ManyToMany(cascade = CascadeType.ALL)
	public List<Tema> temas;
	
	@Required
	public String titulo;

	@Required
	public String descricao;
	
	@Required
	public String date;
	
	public Evento() {}
	
	public Evento(String titulo, String descricao, String date, List<Tema> temas) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.date = date;
		addTemas(temas);
	}
	
	public void addTema(Tema tema) {
		if(tema != null) {
			tema.addEvento(this);
			temas.add(tema);
		}
	}
	
	public void addTemas(List<Tema> temas) {
		for (Tema tema: temas) {
			addTema(tema);
		}
	}
	
	public static List<Evento> all() {
		return find.all();
	}
	
	public static void save(Evento evento) {
		evento.save();
	}
	
}