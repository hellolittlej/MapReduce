package servletPackage;

import java.util.Calendar;
import java.util.Date;

public class Session {
	public int VersionNum;
	private String Message;
	private Calendar Extime;
	private String locationData;
	public static final int TimeOut = 30;
	
	public Session() {
		VersionNum = 0;
		Message = "Hello User";	
		locationData = "0";	
		setExtime();
		//curtime = new Date();
	}
	
	public int getVnum() {
		return VersionNum;
	}	
	
	public String getlocalData() {
		return locationData;
	}
	
	public void refresh() {
		VersionNum ++;
		setExtime();
	}
	//replace also needs to update the message;
	public void replace(String nmessage) {
		refresh();
		Message = nmessage;
	}
	
	public Calendar getExtime() {
		return Extime;
	}
	//set the expiration time as the current time + timeout;
	public void setExtime() {
		Extime = Calendar.getInstance();
		Extime.add(Calendar.SECOND, TimeOut);
	}
	public String getMessage() {
		return Message;
	}
	//When the current time is longer than the life circle of the cookie;
	public boolean isTimeOut() {
		return Calendar.getInstance().after(Extime);
	}
}
