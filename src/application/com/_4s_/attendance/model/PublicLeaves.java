package com._4s_.attendance.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;
import java.util.Calendar;

@Entity
@Table (name = "public_leaves")

public class PublicLeaves implements Serializable,Auditable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6544437517628503952L;
	@Id
	private Date indate;
	private String description;
	private Integer weight = 1;
	
	public Long getId() {
		return null;
	}

		public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return "public leaves";
	}

		
	public Date getIndate() {
			return indate;
		}

		public void setIndate(Date indate) {
			this.indate = indate;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Integer getWeight() {
			return weight;
		}

		public void setWeight(Integer weight) {
			this.weight = weight;
		}

	public String toString() {
		return new ToStringBuilder(this)
		.append("indate ", this.indate)
		/*.append("watchedThreads", this.watchedThreads)*/.toString();
	}

	public boolean equals(Object o) {
		System.out.println("object o " + o);
		System.out.println("this " + this);
		if (o == this) {
			return true;
		}
		System.out.println(o instanceof PublicLeaves);
		if (!(o instanceof PublicLeaves)) {
			return false;
		}
		PublicLeaves rhs = (PublicLeaves) o;
		
		Calendar thisCal = Calendar.getInstance();
		thisCal.setTime(this.indate);
		Calendar rhsCal = Calendar.getInstance();
		rhsCal.setTime(rhs.getIndate());
		
		System.out.println("thisCal " + thisCal);
		System.out.println("rhsCal " + rhsCal);
		
		
		System.out.println("first date " + this.indate);
		System.out.println("second date " + rhs.getIndate());
		return new EqualsBuilder().append(thisCal.get(Calendar.DAY_OF_MONTH), rhsCal.get(Calendar.DAY_OF_MONTH))
				.append(thisCal.get(Calendar.MONTH), rhsCal.get(Calendar.MONTH))
				.append(thisCal.get(Calendar.YEAR), rhsCal.get(Calendar.YEAR))
				.append(this.description, rhs.getDescription()).append(this.weight, rhs.getWeight()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(2090939697, 874530185).append(this.indate).toHashCode();
	}

}
