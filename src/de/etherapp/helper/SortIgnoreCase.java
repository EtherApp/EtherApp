package de.etherapp.helper;

import java.util.Comparator;
import de.etherapp.beans.PadlistItem;

public class SortIgnoreCase implements Comparator<Object> {
	public int compare(Object o1, Object o2) {
		PadlistItem paditem1 = (PadlistItem) o1;
		PadlistItem paditem2 = (PadlistItem) o2;
		return paditem1.getPadName().toLowerCase().compareTo(paditem2.getPadName().toLowerCase());
	}
}