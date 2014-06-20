package controllers;

import java.util.*;

import models.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;
import static play.mvc.Http.RequestBody;

public class Application extends Controller {
	
	static Form<Interessado> formInteressado = Form.form(Interessado.class);
	static Form<Evento> formEvento = Form.form(Evento.class);
	
    public static Result index(String tema) {
    	Tema temaSearch = Tema.getById(tema);
    	if (temaSearch != null && !tema.equals("all")) {
    		return ok(hackfest.render(Tema.all(), temaSearch.getEventos(), tema));
    	}
        return ok(hackfest.render(Tema.all(), Evento.all(), tema));
    }
    
    public static Result reservar(long id) {
    	Form<Interessado> formInteressadoRequest = formInteressado.bindFromRequest();
    	String tema[] = {"all"};
    	
    	if (!formInteressadoRequest.hasErrors()) {
    		Interessado interessado = formInteressadoRequest.get();
    		Evento evento = Evento.getById(id);
    		if (evento != null) {
    			evento.addInteressado(new Interessado(interessado.email, interessado.nome));
    			Evento.save(evento);
    		}
    		
    		tema = request().body().asFormUrlEncoded().get("tema");
    		if (tema == null) {
    			tema = new String[]{"all"};
    		}
    	}
    	
    	return redirect(routes.Application.index(tema[0]));
    }
    
    public static Result cadastrar() {
    	Form<Evento> formEventoRequest = formEvento.bindFromRequest();
    	String msg = "Não cadastrado";
    	
    	if(!formEventoRequest.hasErrors()) {
    		Evento evento = formEventoRequest.get();
    		if (evento.temas.size() >= 2) {
	    		Evento.save(new Evento(evento.titulo, evento.descricao, evento.date, evento.temas));
	    		msg = "Cadastrado: "+evento.titulo;
    		} else { 
    			msg = "Evento deve ter no mínimo 2 temas";
    		}
    	}
    	
    	return redirect(routes.Application.admin(msg));
    }
    
    public static Result admin(String msg) {
    	return ok(admin.render(Tema.all(), msg));
    

}
