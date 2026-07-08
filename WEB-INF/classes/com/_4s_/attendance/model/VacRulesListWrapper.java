package com._4s_.attendance.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VacRulesListWrapper implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4450196460062228004L;

	private List<VacRule> rules = new ArrayList<VacRule>();

	public List<VacRule> getRules() {
		return rules;
	}

	public void setRules(List<VacRule> rules) {
		this.rules = rules;
	}
}
