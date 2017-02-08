package handler;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import models.Kurs;
import models.Lehrer;
import database.DBManager;

@ServerEndpoint("/XKursEintragenServlet")
public class MessageHandler {

	@OnOpen
	public void onOpen(){
		
	}
   
	// Nachricht schicken
	// session.getBasicRemote().sendText("Du bist: " +c.getName()+ "\n");
	
	@OnMessage
	public void onMessage(Session session, String message) {
	   
		DBManager dbm = new DBManager();
		
		Gson gson = new Gson();
		JsonObject jsonData = gson.fromJson(message, JsonObject.class);
		gson.toJson(message);
		
		String type = jsonData.get("type").getAsString();
		
		switch(type){
		
		case "kursInfoRequest":
			
			String studentId = jsonData.get("userId").getAsString();
			String kursId = jsonData.get("kursId").getAsString();
			
			String respType = "kursInfo";
			String kursName = "";
			String lehrerName = "";
			
			ArrayList<Kurs> kListe = dbm.getKurseStudent(Integer.parseInt(studentId));
			
			for(Kurs k: kListe){
				if(k.getID() == Integer.parseInt(kursId)){
					kursName = k.getName();
				}
			}
			
			ArrayList<Lehrer> lehrerListe = dbm.getKurslehrer(Integer.parseInt(kursId));
			lehrerName = lehrerListe.get(0).getVorname()+" "+lehrerListe.get(0).getNachname();
			
			KursInfoRequestMessage responseObj = new KursInfoRequestMessage(respType, kursName, lehrerName);
			
			try {
				session.getBasicRemote().sendText(gson.toJson(responseObj));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			break;
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

class KursInfoRequestMessage {
	
	private String respType;
	private String kursName;
	private String lehrerName;
	
	public KursInfoRequestMessage(String rT, String kN, String lN){
		this.respType = rT;
		this.kursName = kN;
		this.lehrerName = lN;
	}
	
}
