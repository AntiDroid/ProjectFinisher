package handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import models.Auswahlbereich;
import models.Folie;
import models.Foliensatz;
import models.Kurs;
import models.Lehrer;
import models.Uservoting;
import database.DBManager;

@ServerEndpoint("/MessageHandler")
public class MessageHandler {

	// Folientyp
	// Heatplot 		- H
	// Choice  			- C
	// Multiple Choice 	- M
	// reine Anzeige 	- A
	
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
		
		case "lehrerKursInfoRequest":
		{
			//int userID = jsonData.get("userId").getAsInt();
			int kursID = jsonData.get("kursId").getAsInt();
			
			String respType = "lehrerKursInfo";
			ArrayList<Foliensatz> folienSatzList = dbm.getFoliensätze(kursID);

			
			KursInfoMessageLehrer responseObj = new KursInfoMessageLehrer(respType, folienSatzList);
			
			try {
				session.getBasicRemote().sendText(gson.toJson(responseObj));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			break;
		}
		
		case "folienSatzRequest":
		{
			//int userID = jsonData.get("userId").getAsInt();
			int folienSatzID = jsonData.get("folienSatzId").getAsInt();
			
			String respType = "folienSatz";
			ArrayList<Folie> folienList = dbm.getFolien(folienSatzID);
			
			FoliensatzFolienMessage responseObj = new FoliensatzFolienMessage(respType, folienList);
			
			try {
				session.getBasicRemote().sendText(gson.toJson(responseObj));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			break;
		}

		case "folienInfoRequest":
		{
			
			// int userID = jsonData.get("userId").getAsInt();
			int folienID = jsonData.get("folienId").getAsInt();
			String sessionID = jsonData.get("sessionId").getAsString();
			
			String respType = "folienInfo";
			Folie folie = dbm.getFolie(folienID);
			ArrayList<Auswahlbereich> bereichList = dbm.getAuswahlbereiche(folienID);
			ArrayList<Integer> bAuswerteList = new ArrayList<Integer>();
			ArrayList<Uservoting> hAuswerteList = dbm.getUservotings(0, folienID, sessionID);
			ArrayList<Uservoting> list = dbm.getUservotings(0, folienID, sessionID);
			
			for(int i = 0; i < bereichList.size(); i++){
			
				int counter = 0;
				
				for(Uservoting uv: list){
					if(uv.getAuswahloption().equals(i+1))
						counter++;
				}
				
				bAuswerteList.add(counter);
			}
			
			FolienInfoMessage responseObj = new FolienInfoMessage(respType, folie, bereichList, bAuswerteList, hAuswerteList);
			
			try {
				session.getBasicRemote().sendText(gson.toJson(responseObj));
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		
		case "folienDeleteRequest":
		{
			
			// int userID = jsonData.get("userId").getAsInt();
			// int kursID = jsonData.get("kursId").getAsInt();
			int folienID = jsonData.get("folienId").getAsInt();
			
			String respType = "folienSatz";
			int folienSatzID = dbm.getFolie(folienID).getFoliensatzID();
			
			dbm.delete(dbm.getFolie(folienID));
			
			Foliensatz folienSatz = dbm.getFoliensatz(folienSatzID);
			
			UpdatedFoliensatzMessage responseObj = new UpdatedFoliensatzMessage(respType, folienSatz);
			
			try {
				session.getBasicRemote().sendText(gson.toJson(responseObj));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		case "kursInfoRequest":
		{
			int studentID = jsonData.get("userId").getAsInt();
			int kursID = jsonData.get("kursId").getAsInt();
			
			String respType = "kursInfo";
			String kursName = "";
			String lehrerName = "";
			Folie f = Message.aktiveFolie.get(kursID);
			ArrayList<Auswahlbereich> bereichList = null;
			
			if(f != null){
				bereichList = dbm.getAuswahlbereiche(f.getID());
			}
				
			ArrayList<Kurs> kList = dbm.getKurseStudent(studentID);
			
			for(Kurs k: kList){
				if(k.getID() == kursID){
					kursName = k.getName();
				}
			}
			
			ArrayList<Lehrer> lehrerListe = dbm.getKurslehrer(kursID);
			lehrerName = lehrerListe.get(0).getVorname()+" "+lehrerListe.get(0).getNachname();
			
			KursInfoMessageStudent responseObj = new KursInfoMessageStudent(respType, kursName, lehrerName, f, bereichList);
			
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
			int folienID = jsonData.get("folienId").getAsInt();
			int kursID = jsonData.get("kursId").getAsInt();
			
			// zu viele Daten durch Verschachtelung?
			// was wenn Websocket auf Client-Seite Verbindung verliert? Liste wird nicht korrigiert
			// Socket - Equals?
			
			String respType = "folienUpdate";
			Folie f = dbm.getFolie(folienID);
			ArrayList<Auswahlbereich> bereichList = dbm.getAuswahlbereiche(folienID);
			FolienUpdateRequestMessage responseObj = new FolienUpdateRequestMessage(respType, f, bereichList);
			
			Message.aktiveFolie.put(kursID, f);
			
			try {
				
				for(Session s: Message.kursSessions.get(kursID)){
					s.getBasicRemote().sendText(gson.toJson(responseObj));
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			break;
		}
		
		case "bereichAntwort":
		{
			int userId = jsonData.get("userId").getAsInt();
			//int kursId = jsonData.get("kursId").getAsInt();
			int folienId = jsonData.get("folienId").getAsInt();
			int posX = jsonData.get("posX").getAsInt();
			int posY = jsonData.get("posY").getAsInt();
			String ao = jsonData.get("auswahloption").getAsString();
			String sesID = jsonData.get("sessionId").getAsString();
		
			Uservoting uv = new Uservoting(sesID, userId, dbm.getStudent(userId), folienId, dbm.getFolie(folienId), posX, posY, ao);
			dbm.save(uv);
			
			break;
		}
		
		case "heatplotAntwort":
		{
			int userId = jsonData.get("userId").getAsInt();
			//int kursId = jsonData.get("kursId").getAsInt();
			int folienId = jsonData.get("folienId").getAsInt();
			int posX = jsonData.get("posX").getAsInt();
			int posY = jsonData.get("posY").getAsInt();		
			String ao = jsonData.get("auswahloption").getAsString();
			String sesID = jsonData.get("sessionId").getAsString();
			
			Uservoting uv = new Uservoting(sesID, userId, dbm.getStudent(userId), folienId, dbm.getFolie(folienId), posX, posY, ao);
			dbm.save(uv);
			
			break;
		}
		
		case "socketEnde":
		{
			int kursID = jsonData.get("kursId").getAsInt();
			
			Message.kursSessions.get(kursID).remove(session);
			break;
		}
		default:
			
			break;
		}
		
		dbm.dispose();
	}
	
	@OnError
	public void onError(Throwable t){
		System.out.println("MessageHandler-Error");
		System.out.println(t.toString());
		/*
		System.out.println("MessageHandler-ERROR");
		System.out.println(t.getMessage());
		*/
	}
   
	@OnClose
	public void onClose(){
		System.out.println("MessageHandler-Close");
	}
}

abstract class Message {
	
	static public HashMap<Integer, ArrayList<Session>> kursSessions;
	static public HashMap<Integer, Folie> aktiveFolie;
	
	String type;
	
	public Message(String t){
		this.type = t;
	}
}

class KursInfoMessageLehrer extends Message {
	
	ArrayList<Foliensatz> folienSatzList;
	
	public KursInfoMessageLehrer(String rT, ArrayList<Foliensatz> fsl){
		super(rT);
		this.folienSatzList = fsl;
	}
}

class FolienInfoMessage extends Message {
	
	Folie folie;
	ArrayList<Auswahlbereich> bereichList;
	ArrayList<Integer> bAuswerteList;
	ArrayList<Uservoting> hAuswerteList;
	
	public FolienInfoMessage(String rT, Folie f, ArrayList<Auswahlbereich> bereiche, ArrayList<Integer> bAL, ArrayList<Uservoting> hAL){
		super(rT);
		this.folie = f;
		this.bereichList = bereiche;
		this.bAuswerteList = bAL;
		this.hAuswerteList = hAL;
	}
}

class UpdatedFoliensatzMessage extends Message {
	
	Foliensatz folienSatz;
	
	public UpdatedFoliensatzMessage(String rT, Foliensatz fS){
		super(rT);
		this.folienSatz = fS;
	}
}

class FoliensatzFolienMessage extends Message {
	
	ArrayList<Folie> folienList;
	
	public FoliensatzFolienMessage(String rT, ArrayList<Folie> folien){
		super(rT);
		this.folienList = folien;
	}
	
}

class KursInfoMessageStudent extends Message {
	
	String kursName;
	String lehrerName;
	Folie folie;
	ArrayList<Auswahlbereich> bereichList;
	
	public KursInfoMessageStudent(String rT, String kN, String lN, Folie f, ArrayList<Auswahlbereich> bl){
		super(rT);
		this.kursName = kN;
		this.lehrerName = lN;
		this.folie = f;
		this.bereichList = bl;
	}
}

class FolienUpdateRequestMessage extends Message {
	
	Folie folie;
	ArrayList<Auswahlbereich> bereichList;
	
	public FolienUpdateRequestMessage(String rT, Folie f, ArrayList<Auswahlbereich> bl){
		super(rT);
		this.folie = f;
		this.bereichList = bl;
	}
}
