package org.SOC;

public class Node {
	public Link west_link_is_OK;
	public Link east_link_is_OK;
	public Link north_link_is_OK;
	public Link south_link_is_OK;
	public boolean isVisited;
	
	public Node(boolean wl, boolean el, boolean nl, boolean sl) {
		this.west_link_is_OK = new Link(wl);
		this.east_link_is_OK = new Link(el);
		this.north_link_is_OK = new Link(nl);
		this.south_link_is_OK = new Link(sl);
		this.isVisited = false;
	}	
		
	public boolean getWest_link() {
		return this.west_link_is_OK.isLink();
	}

	public void setWest_link(boolean west_link) {
		this.west_link_is_OK.setLink(west_link);
	}

	public boolean getEast_link() {
		return this.east_link_is_OK.isLink();
	}

	public void setEast_link(boolean east_link) {
		this.east_link_is_OK.setLink(east_link);
	}

	public boolean getNorth_link() {
		return this.north_link_is_OK.isLink();
	}

	public void setNorth_link(boolean north_link) {
		this.north_link_is_OK.setLink(north_link);
	}

	public boolean getSouth_link() {
		return this.south_link_is_OK.isLink();
	}

	public void setSouth_link(boolean south_link) {
		this.south_link_is_OK.setLink(south_link);
	}

	public boolean isVisited() {
		return isVisited;
	}

	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}
	
}
