package com._4s_.attendance.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com._4s_.auditing.model.Auditable;

@Entity
@Table (name = "region")

public class AttendanceRegion implements Serializable,Auditable {
	@Id
//	@GenericGenerator(name="title_seq",strategy="com._4s_.attendance.dao.TitleStringKeyGenerator")
//	@GeneratedValue(generator="title_seq")
	private String region;
	private String name;
	
	public Long getId() {
		return null;
	}

	
	

	public String getRegion() {
		return region;
	}




	public void setRegion(String region) {
		this.region = region;
	}



	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return "region";
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String toString() {
		return new ToStringBuilder(this)
		.append("name", this.name)
		/*.append("watchedThreads", this.watchedThreads)*/.toString();
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof AttendanceDepartment)) {
			return false;
		}
		AttendanceDepartment rhs = (AttendanceDepartment) o;
		return new EqualsBuilder().append(this.name, rhs.getName()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(2090939697, 874530185).append(this.name).toHashCode();
	}

}
