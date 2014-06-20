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
    	inicializaDB();
    	
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
    
    private static void inicializaDB() {
    	if (Evento.all().size() == 0) {
    		String[] nomesTemas = new String[]{"hacker", "si", "pokemon", "tubarao", "mimimi"};
    		
    		List<Tema> temas = new ArrayList<Tema>();
    		for (String tema: nomesTemas) {
    			temas.add(new Tema(tema));
    		}
    		
    		String descricao = "blablabla";
    		String date = "2014-06-17";
    		String[] titulos = new String[]{"Coisa linda", "De se ver", "Eh Voce", "meu bem querer", "Evento fantastico", "sem muito conteudo", "desculpem", "nao queria que fosse assim", "nove", "10"};
    		for (int i = 0; i < titulos.length; i++) {
    			ArrayList<Tema> temaParaEvento = new ArrayList<Tema>();
    			temaParaEvento.add(temas.get(i%temas.size()));
    			temaParaEvento.add(temas.get((i+1)%temas.size()));
    			
    			Evento evento = new Evento(titulos[i], descricao, date, temaParaEvento);
    			if (i < 3) {
    				for (int k = 0; k < 6; k++) {
    					evento.addInteressado(new Interessado(k+"email@email.com", "Email"));
    				}
    			}
    			if (i == 2) {
    				for (int k = 0; k < 2; k++) {
    					evento.addInteressado(new Interessado((k+50)+"email@email.com", "Email"));;
    				}
    			}
    			
    			Evento.save(evento);
    		}
    		
    	}
    }

}
