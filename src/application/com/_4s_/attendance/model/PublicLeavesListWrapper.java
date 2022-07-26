package com._4s_.attendance.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PublicLeavesListWrapper implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4450196460062228004L;

	private List<PublicLeaves> pubLeaves = new ArrayList<PublicLeaves>();

	public List<PublicLeaves> getPubLeaves() {
		return pubLeaves;
	}

	public void setPubLeaves(List<PublicLeaves> pubLeaves) {
		this.pubLeaves = pubLeaves;
	}

	
}
