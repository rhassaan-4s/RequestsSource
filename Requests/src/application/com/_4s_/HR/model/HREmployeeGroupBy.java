package com._4s_.HR.model;

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

@Entity//(access=AccessType.FIELD)
@Table(name="hr_employee_group_by")
public class HREmployeeGroupBy implements Auditable,Serializable {
		@Id
		private Long id;
		
		private String name; 
		private String eName;
		
		public String getEntityDisplayName() {
			return "HREmployeeGroupBy Addition";
		}
		
		@Override
		public String toString() {
			 return new ToStringBuilder(this)
			 .append("id", this.id)
			 .append("name", this.name)
			 .append("eName", this.eName)
			 .toString();
		}
		
		@Override
		public boolean equals(Object o) {
			 if (o == this) {
			 return true;
			 }
			 if (!(o instanceof HREmployeeGroupBy)) {
			 return false;
			 }
			 HREmployeeGroupBy hregb = (HREmployeeGroupBy) o;
			 return new EqualsBuilder()
			 .append(this.id, hregb.getId())
			 .append(this.name, hregb.getName())
			 .append(this.eName, hregb.getEName())
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

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEName() {
			return eName;
		}

		public void setEName(String name) {
			eName = name;
		}
}