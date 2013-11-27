package de.etherapp.beans;

public class PadlistItem {
    private String title;
    private Integer usersCount;
    private Integer revCount;
    private Integer lastEdited;
 
    public PadlistItem(String title, int usersCount, int revCount, int lastEdited) {
        this.title = title;
        this.usersCount = usersCount;
        this.revCount = revCount;
        this.lastEdited = lastEdited;
    }

    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
     
    public int getUsersCount() {
		return usersCount;
	}
    
    public String getUsersCountString() {
		return usersCount.toString();
	}

	public void setUsersCount(int usersCount) {
		this.usersCount = usersCount;
	}

	public int getRevCount() {
		return revCount;
	}
	
	public String getRevCountString() {
		return revCount.toString();
	}

	public void setRevCount(int revCount) {
		this.revCount = revCount;
	}
	
	public int getLastEdited() {
		return lastEdited;
	}
	
	public String getLastEditedString() {
		return lastEdited.toString();
	}

	public void setLastEdited(int lastEdited) {
		this.lastEdited = lastEdited;
	}
}
