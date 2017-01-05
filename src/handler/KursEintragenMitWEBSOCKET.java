package handler;
/*
import java.util.ArrayList;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import models.Kurs;
import database.DBManager;

@ServerEndpoint("/KursEintragen")
public class KursEintragenMitWEBSOCKET {

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

		for (Kurs k : kursListe) {
			if (k.getName().equals(message)) {
				addKurs = k;
				break;
			}
		}
		
		//gleiche Session wie zuvor?
		String userName = (String) session.getAttribute("benutzer");

		if (addKurs != null && !dbm.isKursBeteiligt(message, userName)) {
			dbm.addKursteilnahme(addKurs, dbm.getStudent(userName));

			//anpassen der Kursliste
			add(<li><a href="KursServlet?kursName=<%= k %>"><%= k %></a></li>);
		
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
*/
