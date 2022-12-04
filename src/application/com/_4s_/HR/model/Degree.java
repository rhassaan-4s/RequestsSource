package com._4s_.HR.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;
import com._4s_.common.util.LocaleUtil;

@Entity//(access=AccessType.FIELD)
@Table(name="hr_degree")
public class Degree implements Auditable,Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_degree_seq")
	@SequenceGenerator(name="hr_degree_seq",sequenceName="hr_degree_seq")
	private  Long id ;
	
	private String degree;
	private String name ;
	private Long start_   ;
	private Long end_ ;
	private String ename ;
	private Long anual_raise ;
	private Long estemated_average_basic;
	private  Boolean apply_overtime ;
	private Long max_levels;
	
	public String getEntityDisplayName() {
		return "degree Addition";
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
		 if (!(o instanceof Degree)) {
		 return false;
		 }
		 Degree rhs = (Degree) o;
		 return new EqualsBuilder()
		 .append(this.name, rhs.getName())
		 .append(this.ename, rhs.getEname())
		 .isEquals();
	}
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.degree)

		.toHashCode();
	}
	
	@Transient
	public String getNameForCertainLocale(){
		LocaleUtil localeUtil = LocaleUtil.getInstance();
		if(localeUtil.getLocale() != null && localeUtil.getLocale().equalsIgnoreCase("en")) {
			return getEname();
		}
		return getName();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getStart_() {
		return start_;
	}
	public void setStart_(Long start_) {
		this.start_ = start_;
	}
	public Long getEnd_() {
		return end_;
	}
	public void setEnd_(Long end_) {
		this.end_ = end_;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	
	public Long getAnual_raise() {
		return anual_raise;
	}
	public void setAnual_raise(Long anual_raise) {
		this.anual_raise = anual_raise;
	}
	public Long getEstemated_average_basic() {
		return estemated_average_basic;
	}
	public void setEstemated_average_basic(Long estemated_average_basic) {
		this.estemated_average_basic = estemated_average_basic;
	}
	public Boolean getApply_overtime() {
		return apply_overtime;
	}
	public void setApply_overtime(Boolean apply_overtime) {
		this.apply_overtime = apply_overtime;
	}
	public Long getMax_levels() {
		return max_levels;
	}
	public void setMax_levels(Long max_levels) {
		this.max_levels = max_levels;
	}
}
