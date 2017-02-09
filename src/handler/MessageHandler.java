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

import models.Auswahlbereich;
import models.Kurs;
import models.Lehrer;
import database.DBManager;

@ServerEndpoint("/MessageHandler")
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
		{
			String studentId = jsonData.get("userId").getAsString();
			String kursId = jsonData.get("kursId").getAsString();
			
			int studentID = Integer.parseInt(studentId);
			int kursID = Integer.parseInt(kursId);
			
			String respType = "kursInfo";
			String kursName = "";
			String lehrerName = "";
			
			ArrayList<Kurs> kListe = dbm.getKurseStudent(studentID);
			
			for(Kurs k: kListe){
				if(k.getID() == kursID){
					kursName = k.getName();
				}
			}
			
			ArrayList<Lehrer> lehrerListe = dbm.getKurslehrer(kursID);
			lehrerName = lehrerListe.get(0).getVorname()+" "+lehrerListe.get(0).getNachname();
			
			KursInfoRequestMessage responseObj = new KursInfoRequestMessage(respType, kursName, lehrerName);
			
			try {
				session.getBasicRemote().sendText(gson.toJson(responseObj));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			ArrayList<Session> sessionList = Message.kursSessions.get(kursID);
			
			if(sessionList == null){
				ArrayList<Session> sL = new ArrayList<Session>();
				sL.add(session);
				Message.kursSessions.put(kursID, sL);
			}
			else{
				if(!sessionList.contains(session))
					Message.kursSessions.get(kursID).add(session);
			}
			
			break;
		}
		case "folienUpdateRequest":
		{	
			String folienId = jsonData.get("folienId").getAsString();
			String kursId = jsonData.get("kursId").getAsString();
			
			int folienID = Integer.parseInt(folienId);
			int kursID = Integer.parseInt(kursId);
			
			String respType = "folienUpdate";
			String fSatzName = "";
			boolean interaktiv;
			boolean isHeatplot = false;
			ArrayList<Auswahlbereich> bereichList = new ArrayList<Auswahlbereich>();
			
			FolienUpdateRequestMessage responseObj = new FolienUpdateRequestMessage(respType, folienId, fSatzName, interaktiv, isHeatplot, bereichList);
			
			try {
				session.getBasicRemote().sendText(gson.toJson(responseObj));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			break;
		}
		default:
			
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

class KursInfoRequestMessage extends Message {
	
	String kursName;
	String lehrerName;
	
	public KursInfoRequestMessage(String rT, String kN, String lN){
		super(rT);
		this.kursName = kN;
		this.lehrerName = lN;
	}
}

class FolienUpdateRequestMessage extends Message {
	
	String type;
	int folienId;
	String fSatzName = "";
	boolean interaktiv;
	boolean isHeatplot = false;
	ArrayList<Auswahlbereich> bereichList = new ArrayList<Auswahlbereich>();
	
	public FolienUpdateRequestMessage(String rT, int fID, String fSN, boolean interaktiv, boolean iHp, ArrayList<Auswahlbereich> bl){
		super(rT);
		this.folienId = fID;
		this.fSatzName = fSN;
		this.interaktiv = interaktiv;
		this.isHeatplot = iHp;
		this.bereichList = bl;
	}
}
