package de.etherapp.beans;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import de.etherapp.app.padclient.Pad;

public class PadlistItem {
	private String padId;
	private Long usersCount;
	private Long revCount;
	private Long lastEdited;
 
	public PadlistItem(Pad pad) {
		this.padId = pad.getPadId();
		this.usersCount = pad.getUsersCount();
		this.revCount = pad.getRevCount();
		this.lastEdited = pad.getLastEdited();
    }

	public String getPadId() {
		return padId;
	}
    
	public void setPadId(String padId) {
		this.padId = padId;
	}
     
	public long getUsersCount() {
		return usersCount;
	}
    
    public String getUsersCountString() {
		return usersCount.toString();
	}

	public void setUsersCount(long usersCount) {
		this.usersCount = usersCount;
	}

	public long getRevCount() {
		return revCount;
	}
	
	public String getRevCountString() {
		return revCount.toString();
	}

	public void setRevCount(long revCount) {
		this.revCount = revCount;
	}
	
	public long getLastEdited() {
		return lastEdited;
	}
	
	public String getLastEditedString() {
		//converts timestamp to datetime format
		//NOTE: should be outsourced to helper class
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(lastEdited);         
		Date d = (Date) c.getTime();        
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.GERMANY); 
		format.setTimeZone(TimeZone.getTimeZone("GMT+1"));
		return format.format(d);
	}

	public void setLastEdited(long lastEdited) {
		this.lastEdited = lastEdited;
	}
}
