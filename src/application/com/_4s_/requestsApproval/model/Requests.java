package com._4s_.requestsApproval.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;

@Entity//(access=AccessType.FIELD)
@Table(name="requests")
public class Requests implements Auditable,Serializable {
	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="requests_seq")
	@SequenceGenerator(name="requests_seq",sequenceName="requests_seq", allocationSize = 1)//(generate=GeneratorType.IDENTITY)
	private Long id;
	
	private String code;
	
	@ManyToOne
	@JoinColumn(name="request_type")
	private RequestTypes request_type;
	
	private String request_name;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public RequestTypes getRequest_type() {
		return request_type;
	}
	public void setRequest_type(RequestTypes request_type) {
		this.request_type = request_type;
	}
	public String getRequest_name() {
		return request_name;
	}
	public void setRequest_name(String request_name) {
		this.request_name = request_name;
	}
	
	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return "Requests Addition";
	}
	
	@Override
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("name", this.request_name)
		 .toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.getId())
		.toHashCode();
	}
}
