package com._4s_.HR.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;

@Entity//(access=AccessType.FIELD)
@Table(name="hr_year")
public class HRYear implements Auditable,Serializable {
		public HRYear() {
		// TODO Auto-generated constructor stub
	}

		@Id
		private Long id;
		private Integer year; 
		
		public String getEntityDisplayName() {
			return "HRYear Addition";
		}
		
		@Override
		public String toString() {
			 return new ToStringBuilder(this)
			 .append("id", this.id)
			 .append("year", this.year)
			 .toString();
		}
		
		@Override
		public boolean equals(Object o) {
			 if (o == this) {
			 return true;
			 }
			 if (!(o instanceof HRYear)) {
			 return false;
			 }
			 HRYear hry = (HRYear) o;
			 return new EqualsBuilder()
			 .append(this.id, hry.getId())
			 .append(this.year, hry.getYear())
			 .isEquals();
		}
		
		@Override
		public int hashCode() {
			return new HashCodeBuilder(991383961, 1226766147)
			.append(this.id)
			.toHashCode();
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Integer getYear() {
			return year;
		}

		public void setYear(Integer year) {
			this.year = year;
		}
}
