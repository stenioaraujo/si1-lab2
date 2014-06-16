package controllers;

import java.util.*;

import models.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
    	inicializaDB();
        return ok(hackfest.render(Tema.all()));
    }
    
    private static void inicializaDB() {
    	if (Evento.all().size() == 0) {
    		String[] nomesTemas = new String[]{"hacker", "si", "pokemon", "tubarao"};
    		
    		List<Tema> temas = new ArrayList<Tema>();
    		for (String tema: nomesTemas) {
    			temas.add(new Tema(tema));
    		}
    		
    		String descricao = "blablabla";
    		String date = "17/06/2014";
    		String[] titulos = new String[]{"Coisa linda", "De se ver", "Eh Voce", "meu bem querer"};
    		for (int i = 0; i < titulos.length; i++) {
    			ArrayList<Tema> temaParaEvento = new ArrayList<Tema>();
    			temaParaEvento.add(temas.get(i%temas.size()));
    			temaParaEvento.add(temas.get((i+1)%temas.size()));
    			
    			Evento.save(new Evento(titulos[i], descricao, date, temaParaEvento));
    		}
    		
    	}
    }

}
