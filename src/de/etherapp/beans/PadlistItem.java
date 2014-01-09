package de.etherapp.beans;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import de.etherapp.epclient.Pad;

public class PadlistItem {
	public Pad pad;
 
	public PadlistItem(Pad pad) {
		this.pad = pad;
    }

	public Pad getPad(){
		return pad;
	}
	
	public String getPadId() {
		return pad.getPadId();
	}
    
	public void setPadId(String padId) {
		pad.setPadId(padId);
	}
	
	public String getPadName() {
		return pad.getPadName();
	}

	public void setPadName(String padName) {
		pad.setPadName(padName);
	}

	public String getPadGroup() {
		return pad.getPadGroup();
	}

	public void setPadGroup(String padGroup) {
		pad.setPadGroup(padGroup);
	}
     
	public long getUsersCount() {
		return pad.getUsersCount();
	}
    
    public String getUsersCountString() {
		return Long.toString(pad.getUsersCount());
	}

	public void setUsersCount(long usersCount) {
		pad.setUsersCount(usersCount);
	}

	public long getRevCount() {
		return pad.getRevCount();
	}
	
	public String getRevCountString() {
		return Long.toString(pad.getRevCount());
	}

	public void setRevCount(long revCount) {
		pad.setRevCount(revCount);
	}
	
	public long getLastEdited() {
		return pad.getLastEdited();
	}
	
	public void setLastEdited(long lastEdited) {
		pad.setLastEdited(lastEdited);
	}
	
	public String getLastEditedString() {
		//converts timestamp to datetime format
		//NOTE: should be outsourced to helper class
		long lastEdited = pad.getLastEdited();
		
		if(lastEdited == 0){
			return null;
		}
		
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(lastEdited);         
		Date d = (Date) c.getTime();        
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.GERMANY); 
		format.setTimeZone(TimeZone.getTimeZone("GMT+1"));
		return format.format(d);
	}
}
