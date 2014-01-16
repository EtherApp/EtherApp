package de.etherapp.beans;

public class GrouplistItem implements Comparable<GrouplistItem> {
	private String group;
 
	public GrouplistItem(String group) {
		this.group = group;
    }

	public String getGroup(){
		return group;
	}
	

	//method for alphabetical order of items in list
	@Override
	public int compareTo(GrouplistItem grpi) {
		return this.group.toLowerCase().compareTo(grpi.getGroup().toLowerCase());
	}
}
