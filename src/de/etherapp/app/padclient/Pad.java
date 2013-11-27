package de.etherapp.app.padclient;

public class Pad {

	private String padId = null;
	private long usersCount = 0;
	private long revCount = 0;
	private long lastEdited = 0;
	
	public Pad(String padId, long usersCount,long revCount,long lastEdited){
		this.padId = padId;
		this.usersCount = usersCount;
		this.revCount = revCount;
		this.lastEdited = lastEdited;
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

	public void setUsersCount(int usersCount) {
		this.usersCount = usersCount;
	}

	public long getRevCount() {
		return revCount;
	}

	public void setRevCount(int revCount) {
		this.revCount = revCount;
	}

	public long getLastEdited() {
		return lastEdited;
	}

	public void setLastEdited(int lastEdited) {
		this.lastEdited = lastEdited;
	}
	
	
	
}
