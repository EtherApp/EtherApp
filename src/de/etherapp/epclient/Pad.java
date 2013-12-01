package de.etherapp.epclient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pad {

	private String padId = null;
	private String padName = null;
	private String padGroup = null;
	private long usersCount = 0;
	private long revCount   = 0;
	private long lastEdited = 0;
	
	public Pad(String padId){
		this.padId = padId;
		
		Pattern p = Pattern.compile("^(g\\..*\\$)(.*$)");
		Matcher m = p.matcher(padId);
		
		if(m.find()){
			padGroup = m.group(1);
			padName = m.group(2);
		}
		else{
			padGroup = "";
			padName = padId;
		}
	}

	public String getPadId() {
		return padId;
	}
	
	public void setPadId(String padId) {
		this.padId = padId;
	}
	
	public String getPadName() {
		return padName;
	}

	public void setPadName(String padName) {
		this.padName = padName;
	}

	public String getPadGroup() {
		return padGroup;
	}

	public void setPadGroup(String padGroup) {
		this.padGroup = padGroup;
	}

	public long getUsersCount() {
		return usersCount;
	}

	public void setUsersCount(long usersCount) {
		this.usersCount = usersCount;
	}

	public long getRevCount() {
		return revCount;
	}

	public void setRevCount(long revCount) {
		this.revCount = revCount;
	}

	public long getLastEdited() {
		return lastEdited;
	}

	public void setLastEdited(long lastEdited) {
		this.lastEdited = lastEdited;
	}
	
	
	
}
