package com._4s_.HR.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;

@Entity//(access=AccessType.FIELD)
@Table(name="hr_internal_division_branches")
public class HRInternalDivisionBranch implements Auditable,Serializable {
		
		public HRInternalDivisionBranch() {
		// TODO Auto-generated constructor stub
	}
		@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_internaldivision_branch_seq")
		@SequenceGenerator(name="hr_internaldivision_branch_seq",sequenceName="hr_internaldivision_branch_seq")
		private Long id;
	    private String  internal_division_branch;
		private String name;
		private String ename;
		
		@ManyToOne
		@JoinColumn (name="type")
		private HRInternalDivision type;
		 
		
		public String getEntityDisplayName() {
			return "InternalDivisionBranch Addition";
		}
		public String toString() {
			 return new ToStringBuilder(this)
			 .append("name", this.name)
			 .append("ename", this.ename)
			 .toString();
		}
		public boolean equals(Object o) {
			 if (o == this) {
			 return true;
			 }
			 if (!(o instanceof HRInternalDivisionBranch)) {
			 return false;
			 }
			 HRInternalDivisionBranch rhs = (HRInternalDivisionBranch) o;
			 return new EqualsBuilder()
			 .append(this.name, rhs.getName())
			 .append(this.ename, rhs.getEname())
			 .isEquals();
		}
		public int hashCode() {
			return new HashCodeBuilder(991383961, 1226766147)
			.append(this.getId())

			.toHashCode();
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getInternal_division_branch() {
			return internal_division_branch;
		}
		public void setInternal_division_branch(String internal_division_branch) {
			this.internal_division_branch = internal_division_branch;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getEname() {
			return ename;
		}
		public void setEname(String ename) {
			this.ename = ename;
		}
		public HRInternalDivision getType() {
			return type;
		}
		public void setType(HRInternalDivision type) {
			this.type = type;
		}

}
