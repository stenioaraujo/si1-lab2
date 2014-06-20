package controllers;

import play.*;
import play.data.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

	static Form<Week> formBindWeek = Form.form(Week.class);
	static Form<Meta> formBindMeta = Form.form(Meta.class);
	
    public static Result index() {
        return ok(views.html.meta.render(Week.all()));
    }
    
    public static Result save() {    	
    	Form<Week> formWeek = formBindWeek.bindFromRequest();
    	Form<Meta> formMeta = formBindMeta.bindFromRequest();
    	
    	if (!formWeek.hasErrors() && !formMeta.hasErrors()) {
    		Week week = formWeek.get();
    		Meta meta = formMeta.get();
    		
    		week = Week.getById(week.id);
    		if (week != null) {
    			week.addMeta(new Meta(meta.objective, meta.priority));
    			Week.save(week);
    		}
    	}
    	
    	return redirect(routes.Application.index());
    }
    
    public static Result remove(long id) {
    	Meta.remove(id);
    	
    	return redirect(routes.Application.index());
    }
    
    public static Result complete(long id) {
    	Meta.complete(id);
    	
    	return redirect(routes.Application.index());
    }
}
