package de.etherapp.epclient;

public class Pad {

	private String padId = null;
	private long usersCount = 0;
	private long revCount   = 0;
	private long lastEdited = 0;
	
	public Pad(String padId){
		this.padId = padId;
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
