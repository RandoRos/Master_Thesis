package org.SOC;

import java.util.ArrayList;

public class Link {
	public boolean link;
	ArrayList<String> routing_table;
	
	public Link(boolean nlink) {
		this.link = nlink;
		this.routing_table = new ArrayList<String>();
	}

	public boolean isLink() {
		return link;
	}

	public void setLink(boolean link) {
		this.link = link;
	}
	
	public ArrayList<String> getRT() {
		return routing_table;
	}

	public void setRouting_table(ArrayList<String> routing_table) {
		this.routing_table = routing_table;
	}

	public void addRT (String element) {
		if (element != null) {
			if (!(this.checkRT(element))) {
				this.routing_table.add(element);
			}
		}
	}

	public boolean checkRT(String string) {
		for (String s1 : this.getRT()) {
			if (s1.equals(string)) {
				return true;
			}
		}	
		return false;
	}
}
