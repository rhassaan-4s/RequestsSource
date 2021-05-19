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

/**
 * @author mragab
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 * 
 * @hibernate.class table = "common_department"
 */
@Entity
@Table (name = "common_department")

public class Department implements Serializable,Auditable {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String description;
	private Boolean isDefault = new Boolean(false);
	
	
//	@ManyToMany(mappedBy ="watchers")
//	@OrderBy("creationDate desc")
//	private List<CommunicationThread>watchedThreads = new ArrayList<CommunicationThread>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return Returns the description.
	 * @hibernate.property column = "description"
	 */
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("description", this.description)
		/*.append("watchedThreads", this.watchedThreads)*/.toString();
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Department)) {
			return false;
		}
		Department rhs = (Department) o;
		return new EqualsBuilder().append(this.description, rhs.getDescription()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(2090939697, 874530185).append(this.description).toHashCode();
	}
	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return this.description;
	}
	public Boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

//	public List<CommunicationThread> getWatchedThreads() {
//		return watchedThreads;
//	}
//	public void setWatchedThreads(List<CommunicationThread> watchedThreads) {
//		this.watchedThreads = watchedThreads;
//	}
	
}
