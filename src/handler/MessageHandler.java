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
		JsonObject jsonData = gson.fromJson( message, JsonObject.class);
		gson.toJson(message);
		
		session.getP
		
		String type = jsonData.get("type").getAsString();
		String msg = jsonData.get("msg").getAsString();
		
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

