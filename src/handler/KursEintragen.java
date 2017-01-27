package handler;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import jdk.nashorn.internal.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import models.Kurs;
import database.DBManager;

@ServerEndpoint("/KursEintragen")
public class KursEintragen {

	@OnOpen
	public void onOpen(){
		
	}
   
	// Nachricht schicken
	// session.getBasicRemote().sendText("Du bist: " +c.getName()+ "\n");
	
	@OnMessage
	public void onMessage(Session session, String message) {
	   
		DBManager dbm = new DBManager();
		ArrayList<Kurs> kursListe = dbm.getKurse();
		
		Kurs addKurs = null;
		
		Gson gson = new Gson();
		gson.toJson(message);
		String userName = gson.fromJson("\"userName\"", String.class);
		String kursName = gson.fromJson("\"kursName\"", String.class);

		for (Kurs k : kursListe) {
			if (k.getName().equals(kursName)) {
				addKurs = k;
				break;
			}
		}

		if (addKurs != null && !dbm.isKursBeteiligt(kursName, userName)) {
			dbm.addKursteilnahme(addKurs, dbm.getStudent(userName));

			//anpassen der Kursliste
			try {
				session.getBasicRemote().sendText("<li><a href=\"KursServlet?kursName=<%= k %>\"><%= k %></a></li>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		dbm.dispose();
	}
	
	@OnError
	public void onError(Throwable t){
		System.out.println("ERROR");
	}
   
	@OnClose
	public void onClose(){
		System.out.println("STOP");
	}
}

