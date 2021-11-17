package com._4s_.common.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;

@Entity
@Table(name = "common_flag")
public class Flag implements Serializable, Auditable{
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private String comments;
	private Boolean flag = new Boolean(false);

	public Flag() {
		// TODO Auto-generated constructor stub
	}
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public String getEntityDisplayName() {
		return name;
	}
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof Flag)) {
			return false;
		}
		Flag rhs = (Flag) object;
		return new EqualsBuilder().append(this.name,
				rhs.getName()).isEquals();
	}
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147).append(this.name).toHashCode();
	}
	public String toString() {
		return new ToStringBuilder(this).append("name", this.name).toString();
	}


	
	
	public String getComment() {
		return comments;
	}

	public void setComment(String comments) {
		this.comments = comments;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}




	



