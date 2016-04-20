package servletPackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionManage {
	//concunrretnHashMap to control the synchronization;
	private static ConcurrentHashMap<String, Session> SessionTable = new ConcurrentHashMap<>();
	private static final String cookieName = "CS5300J17";
	public static final int TimeOut = 30;

	//build new cookies, and add to the response;
	public static void updateResponse(String value, HttpServletResponse response) {
		Cookie c = new Cookie(cookieName, value);
  	    c.setMaxAge(TimeOut);
  	    response.addCookie(c);
	}
	//first time connected to the website;
	public static String BuildSession(HttpServletResponse response) {
		Session nsession = new Session();
		//new a random number as Session ID;
		String ID = UUID.randomUUID().toString();
		SessionTable.put(ID, nsession);
		String value = getValue(ID);
		updateResponse(value, response);
		return value;
	}
	
	public static String RefreshMessage(String lastval, HttpServletResponse response) {
		String[] arr = lastval.split("_");
		Session s = SessionTable.get(arr[0]);
		//if cookie is out of date, new a session
		if(s.isTimeOut()) {
			SessionTable.remove(arr[0]);
			return BuildSession(response);
		}
		//else update the version number and expire time;
		else {
			s.refresh();
	 		String value = getValue(arr[0]);
			updateResponse(value, response);
			return value;
		}
	}	
	public static String ReplaceMessage(String lastval, String nmessage, HttpServletResponse response) {
		String [] arr = lastval.split("_");
		Session s = SessionTable.get(arr[0]);
		//if cookie is out of date;
		if(s.isTimeOut()) {
			SessionTable.remove(arr[0]);
			return BuildSession(response);
		}
		//else update the message, 
		else {
			s.replace(nmessage);
			String value = getValue(arr[0]);
			updateResponse(value, response);
			return value;
		}
	}
	
	public static void remove(String lastvalue) {
		String[] arr = lastvalue.split("_");
		SessionTable.remove(arr[0]);
	}
	
	private static String getValue(String ID) {
		Session s = SessionTable.get(ID);
		return ID + "_" + s.getVnum() + "_" + s.getlocalData();
	}
	
	public static Session getSession(String key) {
		return SessionTable.get(key);
	}
	
	public static void ClearTable() {
		for(String key : SessionTable.keySet()) {
			Calendar time = SessionTable.get(key).getExtime();
			if(Calendar.getInstance().after(time)) {
				SessionTable.remove(key);
			}
		}
	}
}
