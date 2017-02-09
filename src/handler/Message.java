package handler;

import java.util.ArrayList;
import java.util.HashMap;

import javax.websocket.Session;

public abstract class Message {
	
	static public HashMap<Integer, ArrayList<Session>> kursSessions;
	
	String type;
	
	public Message(String t){
		this.type = t;
	}
}
