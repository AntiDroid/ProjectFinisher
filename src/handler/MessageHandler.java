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
			//TODO
			/*
			 * <-  lehrerKursInfoRequest = {
						userId 
						kursId
					};
				->	lehrerKursInfo
						folienSatzList:ArrayList<Foliensatz>
			 */
		}
		case "folienSatzRequest":
		{
			//TODO
			/*
			 * <- folienSatzRequest = {
							userId
							folienSatzId
					};
				-> folienSatz
						folienList:ArrayList<Folie>
			 */
		}
		case "folienInfoRequest":
		{
			//TODO
			/*
			 * <- folienInfoRequest = {
							userId
							folienId
						};
				->	folienInfo
						folie:Folie
						bereichList:ArrayList<Auswahlbereich>
						bAuswerteList:ArrayList<Integer> //bei bereichn. da muschma a liste mit anzahl von den ausgewählten breichen schicken. indizes gleich wie bei den auswahlbereichn
						hAuswerteList:ArrayList<Uservoting> //da kanschma gleis ganze Uservoting gebn, weil i die koordinaten brauch
															// aaaber bei aner riesen lister von Uservotings kanns performanceprobleme geben glabi, vlt schickschma lei die koords in a andas objekt
			 */
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
				bereichList = dbm.getAuswahlbereiche(f);
			}
				
			ArrayList<Kurs> kList = dbm.getKurseStudent(studentID);
			
			for(Kurs k: kList){
				if(k.getID() == kursID){
					kursName = k.getName();
				}
			}
			
			ArrayList<Lehrer> lehrerListe = dbm.getKurslehrer(kursID);
			lehrerName = lehrerListe.get(0).getVorname()+" "+lehrerListe.get(0).getNachname();
			
			KursInfoRequestMessage responseObj = new KursInfoRequestMessage(respType, kursName, lehrerName, f, bereichList);
			
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
			ArrayList<Auswahlbereich> bereichList = dbm.getAuswahlbereiche(f);
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
			int kursId = jsonData.get("kursId").getAsInt();
			int posX = jsonData.get("posX").getAsInt();
			int posY = jsonData.get("posY").getAsInt();		
			
			
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

class KursInfoRequestMessage extends Message {
	
	String kursName;
	String lehrerName;
	Folie folie;
	ArrayList<Auswahlbereich> bereichList;
	
	public KursInfoRequestMessage(String rT, String kN, String lN, Folie f, ArrayList<Auswahlbereich> bl){
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
