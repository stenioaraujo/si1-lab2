package models;

import javax.persistence.*;

import play.data.validation.Constraints.*;
import play.db.ebean.*;

import java.util.*;

@Entity
public class Week extends Model {

	/**
	 * Inicialmente de 1 a 6
	 */
	@Id
	@Min(1)
	@Max(6)
	public Long id;
	
	public static Finder<Long, Week> find = new Finder<Long, Week>(Long.class, Week.class);
	
	@OneToMany(cascade=CascadeType.ALL)
	public List<Meta> metas;
	
	public Week() {}
	
	public Week(List<Meta> metas, Long id) {
		this.metas = metas;
		this.id = id;
	}
	
	public static void save(Week week) {
		week.save();
	}
	
	public void addMeta(Meta meta) {
		if (meta != null) {
			metas.add(meta);
		}
	}
	
	public void removeMeta(Meta meta) {
		if (meta != null) {
			metas.remove(meta);
		}
	}
	
	public static Week getById(Long id) {
		return find.ref(id);
	}
	
	public static List<Week> all() {
		return find.all();
	}
	
	@SuppressWarnings("unchecked")
	public List<Meta> getMetas() {
		Collections.sort(metas, new Comparator<Meta>() {
			@Override
			public int compare(Meta arg0, Meta arg1) {
				return arg0.priority - arg1.priority;
			}			
		});
		
		return metas;
	}
	
}
