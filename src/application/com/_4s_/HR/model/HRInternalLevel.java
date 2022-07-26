package com._4s_.HR.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com._4s_.common.model.TreeDivisionLevels;

@Entity//(access=AccessType.FIELD)
@Table(name="hr_internal_level")
public class HRInternalLevel extends TreeDivisionLevels {
	
	public HRInternalLevel() {
		// TODO Auto-generated constructor stub
	}
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_internal_level_seq")
	@SequenceGenerator(name="hr_internal_level_seq",sequenceName="hr_internal_level_seq")//(generate=GeneratorType.IDENTITY)
	private Long id;
	/*private Integer levelNo;
	private Integer length=3;
	private Boolean isLastLevel;
	private String name;
	private String ename;*/
	private Boolean hasFunctionalGroup;
		

	
	/*public String getEntityDisplayName() {
		return this.getName();
	}
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("levelNo", this.levelNo)
		 .append("name",this.name)
		 .toString();
	}*/
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof HRInternalLevel)) {
		 return false;
		 }
		 HRInternalLevel rhs = (HRInternalLevel) o;
		 return new EqualsBuilder()
		 .append(this.id, rhs.getId())
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
	public Boolean getHasFunctionalGroup() {
		return hasFunctionalGroup;
	}
	public void setHasFunctionalGroup(Boolean hasFunctionalGroup) {
		this.hasFunctionalGroup = hasFunctionalGroup;
	}
	
	

}
