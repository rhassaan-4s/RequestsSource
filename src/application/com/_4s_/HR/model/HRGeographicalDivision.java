package com._4s_.HR.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com._4s_.common.model.TreeDivisions;

@Entity//(access=AccessType.FIELD)
@Table(name="hr_geographical_division")
public class HRGeographicalDivision extends TreeDivisions {
			
			public HRGeographicalDivision() {
		// TODO Auto-generated constructor stub
	}
			@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_geog_division_seq")
			@SequenceGenerator(name="hr_geog_division_seq",sequenceName="hr_geog_division_seq")
			private Long id;
		
			@ManyToOne
			@JoinColumn (name="parent")
			private  HRGeographicalDivision parent;
			
			@ManyToOne
			@JoinColumn (name="divisionLevel")
			private HRGeographicalLevel divisionLevel;
			
			
			@OneToMany (mappedBy="parent",cascade=CascadeType.ALL)
	       private List  <HRGeographicalDivision> childs;
		   
			
			public String getEntityDisplayName() {
				return this.getId().toString();
			}
			/*@Transient
			private Integer order;
			
			
			public String toString() {
				 return new ToStringBuilder(this)
				 .append("ardesc", this.ardesc)
				 .append("endesc", this.endesc)
				 .toString();
			}
			public boolean equals(Object o) {
				 if (o == this) {
				 return true;
				 }
				 if (!(o instanceof HRGeographicalDivision)) {
				 return false;
				 }
				 HRGeographicalDivision rhs = (HRGeographicalDivision) o;
				 return new EqualsBuilder()
				 .append(this.ardesc, rhs.getArdesc())
				 .append(this.endesc, rhs.getEndesc())
				 .isEquals();
			}*/
			public int hashCode() {
				return new HashCodeBuilder(991383961, 1226766147)
				.append(this.getId())

				.toHashCode();
			}
			
			
			/*@Transient
			public String getNameForCertainLocale(){
				LocaleUtil localeUtil = LocaleUtil.getInstance();
				if(localeUtil.getLocale() != null && localeUtil.getLocale().equalsIgnoreCase("en")) {
					return getEndesc();
				}
				return getEndesc();
			}*/
			public Long getId() {
				return id;
			}
			public void setId(Long id) {
				this.id = id;
			}
			/*public String getCode() {
				return code;
			}
			public void setCode(String code) {
				this.code = code;
			}
			public String getArdesc() {
				return ardesc;
			}
			public void setArdesc(String ardesc) {
				this.ardesc = ardesc;
			}
			public String getEndesc() {
				return endesc;
			}
			public void setEndesc(String endesc) {
				this.endesc = endesc;
			}
			public Boolean getIsLast() {
				return isLast;
			}
			public void setIsLast(Boolean isLast) {
				this.isLast = isLast;
			}*/
			public HRGeographicalDivision getParent() {
				return parent;
			}
			public void setParent(HRGeographicalDivision parent) {
				this.parent = parent;
			}
			public HRGeographicalLevel getDivisionLevel() {
				return divisionLevel;
			}
			public void setDivisionLevel(HRGeographicalLevel divisionLevel) {
				this.divisionLevel = divisionLevel;
			}
			public List<HRGeographicalDivision> getChilds() {
				return childs;
			}
			public void setChilds(List<HRGeographicalDivision> childs) {
				this.childs = childs;
			}
			
			
			
			/*public String getLongCode() {
				return longCode;
			}
			public void setLongCode(String longCode) {
				this.longCode = longCode;
			}
			public Integer getOrder() {
				return order;
			}
			public void setOrder(Integer order) {
				this.order = order;
			}*/
			

	}


