package com._4s_.common.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com._4s_.auditing.model.Auditable;

@Entity
@Table(name="common_clients")
public class Clients implements Serializable,Auditable {

	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="CLIENT_SEQ")
	@SequenceGenerator(name="CLIENT_SEQ",sequenceName="CLIENT_SEQ")
	private Long id;
	
	private String clientName;
	
	private String portNo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getPortNo() {
		return portNo;
	}

	public void setPortNo(String portNo) {
		this.portNo = portNo;
	}

	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return "Clients";
	}
	
	
}
