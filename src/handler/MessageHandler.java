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
	
	@OnMessage
	public void onMessage(Session session, String message) {
		
	   System.out.println(message);
		DBManager dbm = new DBManager();
		
		Gson gson = new Gson();
		JsonObject jsonData = gson.fromJson(message, JsonObject.class);
		gson.toJson(message);
		
		String type = jsonData.get("type").getAsString();
		
		switch(type){
		
		case "deleteFoliensatz":{
			/*
			 *  deleteFoliensatz = {
			type : "deleteFoliensatz",
			userId : userId,
			folienSatzId : nowfolienSatzId
		};
			 */
			 break;
		}
		case "folieInaktivieren":{
			/*
			 * folieInaktivieren = {
			type : "folieInaktivieren",
			userId : userId,
			kursId : kursId,
			folienId : nowFolienId,
			sessionId : sessionId
		};
			 */
			break;
		}
		case "newBereich":{
			
			//int userID = jsonData.get("userId").getAsInt();
			//int kursID = jsonData.get("kursId").getAsInt();
			int folienID = jsonData.get("folienId").getAsInt();
			String sessionID = jsonData.get("sessionId").getAsString();
			int oLX = jsonData.get("oLX").getAsInt();
			int oLY = jsonData.get("oLY").getAsInt();
			int uRX = jsonData.get("uRX").getAsInt();
			int uRY = jsonData.get("uRY").getAsInt();
			
			Folie f = dbm.getFolie(folienID);
			
			Auswahlbereich ab = new Auswahlbereich(folienID, f, oLX, oLY, uRX, uRY);
			dbm.save(ab);
			
			sendFolienInfo(session, gson, dbm, folienID, sessionID);
			
			break;
		}
		case "delBereich":{
			
			//int userID = jsonData.get("userId").getAsInt();
			//int kursID = jsonData.get("kursId").getAsInt();
			int folienID = jsonData.get("folienId").getAsInt();
			String sessionID = jsonData.get("sessionId").getAsString();
			int bereichID = jsonData.get("bereichId").getAsInt();
			
			dbm.delete(dbm.getAuswahlbereich(bereichID));
			
			sendFolienInfo(session, gson, dbm, folienID, sessionID);
			
			break;
		}
		
		case "folienSatzDeleteRequest":
		{
			//int userID = jsonData.get("userId").getAsInt();
			int kursID = jsonData.get("kursId").getAsInt();
			int foliensatzID = jsonData.get("folienSatzId").getAsInt();
			
			dbm.delete(dbm.getFoliensatz(foliensatzID));
			
			ArrayList<Foliensatz> folienSatzList = dbm.getFoliensätze(kursID);
			
			KursInfoMessageLehrer responseObj = new KursInfoMessageLehrer(folienSatzList, Message.kursSessions.size());
			
			try {
				session.getBasicRemote().sendText(gson.toJson(responseObj));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			break;
		}
		
		case "folienTypChange":
		{	
			// int userID = jsonData.get("userId").getAsInt();
			int folienID = jsonData.get("folienId").getAsInt();
			char folienTyp = jsonData.get("folienTyp").getAsCharacter();
			String sessionID = jsonData.get("sessionId").getAsString();
			
			Folie f = dbm.getFolie(folienID);
			f.setFolienTyp(folienTyp);
			dbm.save(f);
			
			sendFolienInfo(session, gson, dbm, folienID, sessionID);
			
			break;
		}
		case "lehrerKursInfoRequest":
		{
			//int userID = jsonData.get("userId").getAsInt();
			int kursID = jsonData.get("kursId").getAsInt();
		
			ArrayList<Foliensatz> folienSatzList = dbm.getFoliensätze(kursID);

			
			KursInfoMessageLehrer responseObj = new KursInfoMessageLehrer(folienSatzList, Message.kursSessions.size());
			
			try {
				session.getBasicRemote().sendText(gson.toJson(responseObj));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Message.kursSessions.putIfAbsent(kursID, new ArrayList<Session>());
			
			break;
		}
		
		case "folienSatzRequest":
		{
			//int userID = jsonData.get("userId").getAsInt();
			int folienSatzID = jsonData.get("folienSatzId").getAsInt();
			
			ArrayList<Folie> folienList = dbm.getFolien(folienSatzID);
			
			FoliensatzFolienMessage responseObj = new FoliensatzFolienMessage(folienList);
			
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
			
			sendFolienInfo(session, gson, dbm, folienID, sessionID);
			
			break;
		}
		
		case "folienDeleteRequest":
		{
			
			// int userID = jsonData.get("userId").getAsInt();
			// int kursID = jsonData.get("kursId").getAsInt();
			int folienID = jsonData.get("folienId").getAsInt();
			
			int folienSatzID = dbm.getFolie(folienID).getFoliensatzID();
			
			dbm.delete(dbm.getFolie(folienID));
			
			Foliensatz folienSatz = dbm.getFoliensatz(folienSatzID);
			
			UpdatedFoliensatzMessage responseObj = new UpdatedFoliensatzMessage(folienSatz);
			
			try {
				session.getBasicRemote().sendText(gson.toJson(responseObj));
			} catch (IOException e) {
				e.printStackTrace();
			}

			break;
		}

		case "kursInfoRequest":
		{
			int studentID = jsonData.get("userId").getAsInt();
			int kursID = jsonData.get("kursId").getAsInt();
			
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
			
			KursInfoMessageStudent responseObj = new KursInfoMessageStudent(kursName, lehrerName, f, bereichList);
			
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
			
			Folie f = dbm.getFolie(folienID);
			ArrayList<Auswahlbereich> bereichList = dbm.getAuswahlbereiche(folienID);
			FolienUpdateRequestMessage responseObj = new FolienUpdateRequestMessage(f, bereichList);
			
			Message.aktiveFolie.put(kursID, f);
			
			try {
			
				//TODO nach allen Broadcast die SessionListe leeren und OK antwort verlangen mit der man Liste wieder füllt
				
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
			String ao = jsonData.get("bereichNr").getAsString();
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
			String ao = jsonData.get("bereichNr").getAsString();
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
		{
			DefaultMessage responseObj = new DefaultMessage();
			
			try {
				session.getBasicRemote().sendText(gson.toJson(responseObj));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			break;
		}
		}
		
		dbm.dispose();
	}
	
	@OnError
	public void onError(Throwable t){
		System.out.println("MessageHandler-Error");
		System.out.println();
		t.printStackTrace();
		System.out.println("\n\n\n");
	}
   
	@OnClose
	public void onClose(){
		System.out.println("MessageHandler-Close");
	}
	
	public boolean isInBereich(Uservoting uv, Auswahlbereich aw) {

		if (uv.getKoordX() >= aw.getObenLinksX() && uv.getKoordX() <= aw.getUntenRechtsX()) {
			if (uv.getKoordY() >= aw.getObenLinksY() && uv.getKoordY() <= aw.getUntenRechtsY()) {
				return true;
			}
		}

		return false;
	}
	
	public void sendFolienInfo(Session session, Gson gson, DBManager dbm, int folienID, String sessionID){
		
		Folie folie = dbm.getFolie(folienID);
		ArrayList<Auswahlbereich> bereichList = dbm.getAuswahlbereiche(folienID);
		ArrayList<Uservoting> votings = dbm.getUservotings(0, folienID, sessionID);
		ArrayList<Integer> bAuswertung = new ArrayList<Integer>();
		
		for(Auswahlbereich aw: bereichList){
		
			int counter = 0;
			
			for(Uservoting uv: votings){
				if(isInBereich(uv, aw)){
					counter++;
				}
			}
			
			bAuswertung.add(counter);
		}
		
		FolienInfoMessage responseObj = new FolienInfoMessage(folie, bereichList, bAuswertung, votings);
		
		try {
			session.getBasicRemote().sendText(gson.toJson(responseObj));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

abstract class Message {
	
	static public HashMap<Integer, ArrayList<Session>> kursSessions = new HashMap<Integer, ArrayList<Session>>();
	static public HashMap<Integer, Folie> aktiveFolie = new HashMap<Integer, Folie>();
	
	String type;
	
	public Message(String t){
		this.type = t;
	}
}

class KursInfoMessageLehrer extends Message {
	
	ArrayList<Foliensatz> folienSatzList;
	int anzOnline;
	
	public KursInfoMessageLehrer(ArrayList<Foliensatz> fsl, int aO){
		super("lehrerKursInfo");
		this.folienSatzList = fsl;
		this.anzOnline = aO;
	}
}

class FolienInfoMessage extends Message {
	
	Folie folie;
	ArrayList<Auswahlbereich> bereichList;
	ArrayList<Integer> bAuswerteList;
	ArrayList<Uservoting> votings;
	
	public FolienInfoMessage(Folie f, ArrayList<Auswahlbereich> bereiche, ArrayList<Integer> bAL, ArrayList<Uservoting> hAL){
		super("folienInfo");
		this.folie = f;
		this.bereichList = bereiche;
		this.bAuswerteList = bAL;
		this.votings = hAL;
	}
}

class UpdatedFoliensatzMessage extends Message {
	
	Foliensatz folienSatz;
	
	public UpdatedFoliensatzMessage(Foliensatz fS){
		super("folienSatz");
		this.folienSatz = fS;
	}
}

class FoliensatzFolienMessage extends Message {
	
	ArrayList<Folie> folienList;
	
	public FoliensatzFolienMessage(ArrayList<Folie> folien){
		super("folienSatz");
		this.folienList = folien;
	}
	
}

class KursInfoMessageStudent extends Message {
	
	String kursName;
	String lehrerName;
	Folie folie;
	ArrayList<Auswahlbereich> bereichList;
	
	public KursInfoMessageStudent(String kN, String lN, Folie f, ArrayList<Auswahlbereich> bl){
		super("kursInfo");
		this.kursName = kN;
		this.lehrerName = lN;
		this.folie = f;
		this.bereichList = bl;
	}
}

class FolienUpdateRequestMessage extends Message {
	
	Folie folie;
	ArrayList<Auswahlbereich> bereichList;
	
	public FolienUpdateRequestMessage(Folie f, ArrayList<Auswahlbereich> bl){
		super("folienUpdate");
		this.folie = f;
		this.bereichList = bl;
	}
}

class DefaultMessage extends Message {
	
	public DefaultMessage(){
		super("defaultMessage");
	}
}
