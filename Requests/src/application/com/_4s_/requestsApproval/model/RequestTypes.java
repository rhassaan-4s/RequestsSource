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
@Table(name="request_types")
public class RequestTypes implements Auditable,Serializable {
	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="request_types_seq")
	@SequenceGenerator(name="request_types_seq",sequenceName="request_types_seq")//(generate=GeneratorType.IDENTITY)
	private Long id;
	
	private String description;

	@ManyToOne
	@JoinColumn(name="parentId")
	private RequestTypes parentId;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return "RequestTypes Addition";
	}
	
	@Override
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("name", this.description)
		 .toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.getId())
		.toHashCode();
	}

	public void setParentId(RequestTypes parentId) {
		this.parentId = parentId;
	}

	public RequestTypes getParentId() {
		return parentId;
	}
	
	
}
