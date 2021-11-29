package com._4s_.security.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com._4s_.requestsApproval.model.GroupAcc;

@Entity
@Table (name = "security_ip_address")
public class IPAddress {
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "ipAddressID", sequenceName = "IMEI_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "imeiID")
	private Long id;
	
	private String ip;
	
	@ManyToOne
	@JoinColumn(name="users")
	private User users;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIP() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public User getUsers() {
		return users;
	}

	public void setUsers(User users) {
		this.users = users;
	}
	
	
	
}
