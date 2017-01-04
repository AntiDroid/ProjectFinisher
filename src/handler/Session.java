package handler;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

public class Session {

	private HttpSession session;
	private String user;
	
	static ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<String, Session>();
	
	public Session (HttpSession s, String u){
		this.session = s;
		this.user = u;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
}
