package com._4s_.common.web.util;

import java.math.BigDecimal;

public class SearchWrapper {
	private BigDecimal ident;
	private BigDecimal rnum;
	private String id;
	private String description;
	
	public SearchWrapper(Object ident, Object rnum, Object id, Object description) {
		super();
		this.ident = (BigDecimal)ident;
		this.rnum = (BigDecimal)rnum;
		this.id = (String)id;
		this.description = (String)description;
	}

	public BigDecimal getIdent() {
		return ident;
	}

	public void setIdent(BigDecimal ident) {
		this.ident = ident;
	}

	public BigDecimal getRnum() {
		return rnum;
	}

	public void setRnum(BigDecimal rnum) {
		this.rnum = rnum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
