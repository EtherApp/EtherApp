package de.etherapp.beans;

public class PadlistItem {
    private String padId;
    private Long usersCount;
    private Long revCount;
    private Long lastEdited;
 
    public PadlistItem(String padId, long usersCount, long revCount, long lastEdited) {
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
		return lastEdited.toString();
	}

	public void setLastEdited(long lastEdited) {
		this.lastEdited = lastEdited;
	}
}
