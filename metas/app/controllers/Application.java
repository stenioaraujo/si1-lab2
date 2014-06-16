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
    	initializeDBwithDefaultValues();
        return ok(views.html.meta.render(Week.all()));
    }
    
    public static Result save() {    	
    	Form<Week> formWeek = formBindWeek.bindFromRequest();
    	Form<Meta> formMeta = formBindMeta.bindFromRequest();
    	
    	System.out.println(formWeek.errorsAsJson() + "" + formMeta.errorsAsJson());
    	
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

    /**
     * Preencher as três primeiras semanas com 10 metas
     */
	private static void initializeDBwithDefaultValues() {
		if (Week.all().size() == 0) {
    		String[] metas = new String[]{"Terminar lab 2", "Aprender html", "Aprender Scala", "Aprender play", "Aprender REST", "Aprender reflexão", "Aprender a aprender", "Aprender a dançar kizomba", "Ir pro pp mais vezes", "Tomar juízo"};
    		
    		List<List<Meta>> semanas = new ArrayList<List<Meta>>();
    		for (int i = 0; i < 6; i++) semanas.add(new ArrayList<Meta>());
    		
    		for (int j = 0, i = 0; j < metas.length; j++, i++) {
    			semanas.get(i % 3).add(new Meta(metas[j], j%5 + 1));
    		}
    		
    		for (long k = 1; k <= 6; k++) {
    			Week week = new Week(semanas.get((int)k-1), k);
    			Week.save(week);
    		}
    	}
	}
}
