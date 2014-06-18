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
	
	public static Tema getById(String tema) {
		return find.byId(tema);
	}
	
	public static void save(Tema tema) {
		tema.save();
	}

	public List<Evento> getEventos() {
		Collections.sort(eventos, new Comparator<Evento>() {
			@Override
			public int compare(Evento arg0, Evento arg1) {
				int ord = arg1.interessados.size() - arg0.interessados.size();
				return  ord == 0 ? arg0.date.compareTo(arg1.date) : ord;
			}			
		});
		
		return eventos;
	}
}
