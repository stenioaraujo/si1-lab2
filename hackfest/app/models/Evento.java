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
	
	@OneToMany(cascade = CascadeType.ALL)
	public Set<Interessado> interessados = new HashSet<Interessado>();
	
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
			tema = Tema.getById(tema.tema) != null ? Tema.getById(tema.tema) : tema;
			tema.addEvento(this);
			temas.add(tema);
		}
	}
	
	public void addTemas(List<Tema> temas) {
		for (Tema tema: temas) {
			addTema(tema);
		}
	}
	
	
	public void addInteressado(Interessado interessado) {
		if (interessado != null) {
			this.interessados.add(interessado);
		}
	}
	
	public static Evento getById(long id) {
		return find.ref(id);
	}
	
	public static List<Evento> all() {
		List<Evento> eventos = find.all();
		
		Collections.sort(eventos, new Comparator<Evento>() {
			@Override
			public int compare(Evento o1, Evento o2) {
				int ord = o2.interessados.size() - o1.interessados.size();
				return  ord == 0 ? o1.date.compareTo(o2.date) : ord;
			}
		});
		
		return eventos;
	}
	
	public static void save(Evento evento) {
		evento.save();
	}
	
}