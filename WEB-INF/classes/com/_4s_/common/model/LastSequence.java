package com._4s_.common.model; 

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;

/**
 * @hibernate.class table = "common_last_sequence"
 */
@Entity
@Table (name = "common_last_sequence")
public class LastSequence implements Serializable,Auditable{
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="common_last_seq")
	@SequenceGenerator(name="common_last_seq",sequenceName="common_last_seq")
	private Long id;
	private String className;
	private Long classSequence;
	
	/**
	 * @return
	 * @hibernate.id generator-class = "native"
	 */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return Returns the description.
	 * @hibernate.property column = "class_name"
	 */
	public String getClassName() {
		return className;
	}
	public void setClassName(String class_name) {
		this.className = class_name;
	}

	/**
	 * @return Returns the description.
	 * @hibernate.property column = "class_sequence"
	 */
	public Long getClassSequence() {
		return classSequence;
	}
	public void setClassSequence(Long class_sequence) {
		this.classSequence = class_sequence;
	}
	
	public synchronized Long increment() {
		this.classSequence = new Long(this.classSequence.intValue()+1);
		return this.classSequence;
	}
	
	public String toString() {
		return new ToStringBuilder(this)
		.append("class_name", this.className)
		.toString();
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof LastSequence)) {
			return false;
		}
		LastSequence rhs = (LastSequence) o;
		return new EqualsBuilder().append(this.className, rhs.getClassName())
		.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(2090939697, 874530185)
		.append(this.className)
		.toHashCode();
	}
	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return this.className;
	}

	

}
