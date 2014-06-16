package models;

import play.data.validation.Constraints.*;
import play.db.ebean.*;

import java.util.*;

import javax.persistence.*;

@Entity
public class Meta extends Model {
	@Id
	@GeneratedValue
	public long id;
	
	@Required
	@MinLength(1)
	public String objective;
	
	@Required
	public int priority;
		
	public boolean complete;
	
	public Meta() {}
	
	public Meta(String objective, int priority) {
		this.objective = objective;
		this.priority = priority;
	}
	
	public void setObjective(String objective) {
		this.objective = objective;
	}
	
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	
	public static void save(Meta meta) {
		meta.save();
	}
	
	public static void remove(long id) {
		find.ref(id).delete();
	}
	
	public static List<Meta> all() {
		return find.orderBy("priority").findList();
	}
	
	public static Finder<Long, Meta> find = new Finder<Long, Meta>(Long.class, Meta.class);
}
